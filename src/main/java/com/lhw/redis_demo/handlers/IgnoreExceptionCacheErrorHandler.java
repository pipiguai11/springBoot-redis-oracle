package com.lhw.redis_demo.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;

/**
 * 配置当缓存读写异常的时候，忽略异常或者做一些专门的处理
 *      如果需要保证事务一致性，或者说保证缓存数据和数据库的一致性，可以在这里操作，记录写缓存失败的key
 */
public class IgnoreExceptionCacheErrorHandler implements CacheErrorHandler {

    private static final Logger LOG = LoggerFactory.getLogger(IgnoreExceptionCacheErrorHandler.class);

    /**
     * 读缓存时的异常处理
     * @param e
     * @param cache
     * @param o
     */
    @Override
    public void handleCacheGetError(RuntimeException e, Cache cache, Object o) {
        LOG.error(e.getMessage(),e);
    }

    /**
     * 写缓存时的异常处理
     * @param e
     * @param cache
     * @param o
     * @param o1
     */
    @Override
    public void handleCachePutError(RuntimeException e, Cache cache, Object o, Object o1) {
        LOG.error(e.getMessage(),e);
    }

    /**
     * 剔除某个缓存时的异常处理
     * @param e
     * @param cache
     * @param o
     */
    @Override
    public void handleCacheEvictError(RuntimeException e, Cache cache, Object o) {
        LOG.error(e.getMessage(),e);
    }

    /**
     * 清理缓存时的异常处理
     * @param e
     * @param cache
     */
    @Override
    public void handleCacheClearError(RuntimeException e, Cache cache) {
        LOG.error(e.getMessage(),e);
    }
}
