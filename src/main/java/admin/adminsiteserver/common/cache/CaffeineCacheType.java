package admin.adminsiteserver.common.cache;

public enum CaffeineCacheType {
    ANNOUNCEMENTS("announcements", 5 * 60, 10_000);

    private final String cacheName;

    private final int expireAfterWrite;

    private final int maximumSize;

    CaffeineCacheType(String cacheName, int expireAfterWrite, int maximumSize) {
        this.cacheName = cacheName;
        this.expireAfterWrite = expireAfterWrite;
        this.maximumSize = maximumSize;
    }

    public String cacheName() {
        return cacheName;
    }

    public int expireAfterWrite() {
        return expireAfterWrite;
    }

    public int maximumSize() {
        return maximumSize;
    }
}
