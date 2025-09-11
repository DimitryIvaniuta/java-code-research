package com.code.research.singleton;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Enum-based singleton.
 * - Implicitly Serializable
 * - JVM forbids reflective instantiation
 * - One instance per JVM, guaranteed by the language spec
 */
public enum AppRegistry {
    INSTANCE;

    private final Map<String, String> settings = new ConcurrentHashMap<>();

    public void registerSetting(String key, String value) {
        settings.put(key, value);
    }

    public String getSetting(String key) {
        return settings.get(key);
    }
}
