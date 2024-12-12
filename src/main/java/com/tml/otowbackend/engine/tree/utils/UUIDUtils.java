package com.tml.otowbackend.engine.tree.utils;

import java.util.UUID;

public class UUIDUtils {

    /**
     * 简短 UUID
     */
    public static String generateShortUUID() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 12);
    }

    /**
     * 标准 UUID
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }
}