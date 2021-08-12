package net.cglcapital.coininfo.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;

@Slf4j
public class CustomCacheErrorHandler implements CacheErrorHandler {

    @Override
    public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {
        log.error("Cache GET fail", e);
    }

    @Override
    public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object value) {
        log.error("Cache PUT fail", e);
    }

    @Override
    public void handleCacheEvictError(RuntimeException e, Cache cache, Object key) {
        log.error("Cache EVICT fail", e);
    }

    @Override
    public void handleCacheClearError(RuntimeException e, Cache cache) {
        log.error("Cache CLEAR fail", e);
    }
}
