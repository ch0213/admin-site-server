package admin.adminsiteserver.common.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LayeredCacheAspect {
    private final CacheManager caffeineCacheManager;

    private final CacheManager redisCacheManager;

    private final ApplicationEventPublisher eventPublisher;

    @Around("@annotation(LayeredCacheable)")
    public Object cacheable(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        LayeredCacheable cacheable = methodSignature.getMethod().getAnnotation(LayeredCacheable.class);
        String cacheName = cacheable.cacheName();
        String key = key(methodSignature, cacheable.key());

        Cache caffeineCache = findCaffeineCacheByName(cacheName);
        Cache redisCache = findRedisCacheByName(cacheName);

        var caffeineCacheValue = caffeineCache.get(key);
        if (caffeineCacheValue != null) {
            return caffeineCacheValue.get();
        }

        var redisCacheValue = redisCache.get(key);
        if (redisCacheValue != null) {
            caffeineCache.put(key, redisCacheValue.get());
            return redisCacheValue.get();
        }

        Object result = joinPoint.proceed(joinPoint.getArgs());
        caffeineCache.put(key, result);
        redisCache.put(key, result);

        eventPublisher.publishEvent(CacheEvent.put(key));
        return result;
    }

    @Around("@annotation(LayeredCacheEvict)")
    public void cacheEvict(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        LayeredCacheable cacheable = methodSignature.getMethod().getAnnotation(LayeredCacheable.class);
        String cacheName = cacheable.cacheName();
        String key = key(methodSignature, cacheable.key());

        joinPoint.proceed(joinPoint.getArgs());

        Cache caffeineCache = findCaffeineCacheByName(cacheName);
        Cache redisCache = findRedisCacheByName(cacheName);

        caffeineCache.evict(key);
        redisCache.evict(key);
        eventPublisher.publishEvent(CacheEvent.evict(key));
    }

    private Cache findRedisCacheByName(String cacheName) {
        return Objects.requireNonNull(redisCacheManager.getCache(cacheName),
                "redis cache is null. cacheName: " + cacheName);
    }

    private Cache findCaffeineCacheByName(String cacheName) {
        return Objects.requireNonNull(caffeineCacheManager.getCache(cacheName),
                "caffeine cache is null. cacheName: " + cacheName);
    }

    private String key(MethodSignature methodSignature, String key) {
        return Arrays.stream(methodSignature.getParameterNames())
                .filter(param -> param.equals(key))
                .findAny()
                .orElseThrow();
    }
}
