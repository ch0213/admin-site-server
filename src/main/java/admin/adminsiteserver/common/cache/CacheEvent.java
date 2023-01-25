package admin.adminsiteserver.common.cache;

public class CacheEvent {
    private final String key;

    private final CacheEventType type;

    private CacheEvent(String key, CacheEventType type) {
        this.key = key;
        this.type = type;
    }

    public static CacheEvent put(String key) {
        return new CacheEvent(key, CacheEventType.PUT);
    }

    public static CacheEvent evict(String key) {
        return new CacheEvent(key, CacheEventType.EVICT);
    }
}
