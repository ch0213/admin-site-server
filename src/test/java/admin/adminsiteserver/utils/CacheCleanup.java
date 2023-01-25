package admin.adminsiteserver.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Profile("test")
@Component
public class CacheCleanup {
    @Autowired
    private CacheManager caffeineCacheManager;

    @Autowired
    private CacheManager redisCacheManager;

    public void execute() {
        List<String> caffeineCacheNames = new ArrayList<>(caffeineCacheManager.getCacheNames());
        List<String> redisCacheNames = new ArrayList<>(redisCacheManager.getCacheNames());

        caffeineCacheNames.stream()
                .map(caffeineCacheManager::getCache)
                .filter(Objects::nonNull)
                .forEach(Cache::clear);
        redisCacheNames.stream()
                .map(redisCacheManager::getCache)
                .filter(Objects::nonNull)
                .forEach(Cache::clear);
    }
}
