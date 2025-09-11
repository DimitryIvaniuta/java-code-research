package com.code.research.singleton;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class AppRegistrySingletonApplication {
    /**
     * Enum-based singleton.
     * - Implicitly Serializable
     * - JVM forbids reflective instantiation
     * - One instance per JVM, guaranteed by the language spec
     */
    public enum AppRegistrySingleton {
        INSTANCE;
        private final Map<String, String> settings = new ConcurrentHashMap<>();

        public void registerSetting(String key, String value) {
            settings.put(key, value);
        }

        public String getSetting(String key) {
            return settings.get(key);
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        var appRegistry = AppRegistrySingleton.INSTANCE;
        appRegistry.registerSetting("region", "eu-central-1");
        // Test serialization round-trip
        byte[] bytes = serialize(appRegistry);
        AppRegistrySingleton copy = (AppRegistrySingleton) deserialize(bytes);
        log.info("AppRegistrySingleton deserialized: {}", copy.getSetting("region"));
    }

    private static byte[] serialize(Object o) throws IOException {
        try (var bos = new ByteArrayOutputStream();
             var out = new ObjectOutputStream(bos);) {
            out.writeObject(o);
            return bos.toByteArray();
        }
    }

    private static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        try (var bis = new ByteArrayInputStream(bytes);
             var bos = new ObjectInputStream(bis)) {
            return bos.readObject();
        }
    }
}
