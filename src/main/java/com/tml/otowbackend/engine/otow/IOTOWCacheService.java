package com.tml.otowbackend.engine.otow;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/**
 * 描述: 参数缓存池
 * @author suifeng
 * 日期: 2024/12/16
 */
@Service
public class IOTOWCacheService implements OTOWCacheService {

    private static final Map<Long, Map<String, Object>> CACHE = new ConcurrentHashMap<>();

    @Override
    public void put(Long projectId, String key, Object value) {
        CACHE.computeIfAbsent(projectId, k -> new ConcurrentHashMap<>()).put(key, value);
    }

    @Override
    public Object get(Long projectId, String key) {
        Map<String, Object> projectCache = CACHE.get(projectId);
        return projectCache != null ? projectCache.get(key) : null;
    }

    @Override
    public void remove(Long projectId) {
        CACHE.remove(projectId);
    }

    @Override
    public Map<String, Object> getAll(Long projectId) {
        return CACHE.getOrDefault(projectId, new ConcurrentHashMap<>());
    }
}