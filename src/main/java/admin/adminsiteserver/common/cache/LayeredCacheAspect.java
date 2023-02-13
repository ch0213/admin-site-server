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
    private static final String EMPTY = "";

    private final CacheManager caffeineCacheManager;

    private final CacheManager redisCacheManager;

    private final ApplicationEventPublisher eventPublisher;

    @Around("@annotation(LayeredCacheable)")
    public Object cacheable(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        LayeredCacheable cacheable = methodSignature.getMethod().getAnnotation(LayeredCacheable.class);

        String cacheName = cacheable.cacheName();
        String key = key(joinPoint, cacheable.key());

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
    public Object cacheEvict(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        LayeredCacheEvict cacheEvict = methodSignature.getMethod().getAnnotation(LayeredCacheEvict.class);

        String cacheName = cacheEvict.cacheName();
        String key = key(joinPoint, cacheEvict.key());

        Object result = joinPoint.proceed(joinPoint.getArgs());

        Cache caffeineCache = findCaffeineCacheByName(cacheName);
        Cache redisCache = findRedisCacheByName(cacheName);
        clear(key, caffeineCache, redisCache);
        eventPublisher.publishEvent(CacheEvent.evict(key));
        return result;
    }

    private void clear(String key, Cache caffeineCache, Cache redisCache) {
        if (key.isBlank()) {
            caffeineCache.clear();
            redisCache.clear();
            return;
        }
        caffeineCache.evict(key);
        redisCache.evict(key);
    }

    private Cache findRedisCacheByName(String cacheName) {
        return Objects.requireNonNull(redisCacheManager.getCache(cacheName),
                "redis cache is null. cacheName: " + cacheName);
    }

    private Cache findCaffeineCacheByName(String cacheName) {
        return Objects.requireNonNull(caffeineCacheManager.getCache(cacheName),
                "caffeine cache is null. cacheName: " + cacheName);
    }

    private String key(ProceedingJoinPoint joinPoint, String key) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = methodSignature.getParameterNames();
        String parameterName = Arrays.stream(parameterNames)
                .filter(param -> param.equals(key))
                .findAny()
                .orElse(EMPTY);

        return Arrays.stream(joinPoint.getArgs())
                .map(Object::toString)
                .filter(it -> it.equals(parameterName))
                .findAny()
                .orElse(EMPTY);
    }
}