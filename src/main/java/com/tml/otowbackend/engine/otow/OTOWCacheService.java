package com.tml.otowbackend.engine.otow;

import java.util.Map;

public interface OTOWCacheService {
    void put(Long projectId, String key, Object value);
    Object get(Long projectId, String key);
    void remove(Long projectId);
    Map<String, Object> getAll(Long projectId);
}