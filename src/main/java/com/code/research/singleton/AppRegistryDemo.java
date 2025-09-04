package com.code.research.singleton;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.lang.reflect.Constructor;

@Slf4j
public class AppRegistryDemo {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        var appRegistry = AppRegistry.INSTANCE;
        appRegistry.registerSetting("region", "eu-central-1");

        // --- Test serialization round-trip ---
        byte[] bytes = serialize(appRegistry);
        Object copy = deserialize(bytes);
        log.info("AppRegistryDemo deserialized: {}", copy);

        // --- Attempt to break via reflection (will fail for enums) ---
        try{
            Constructor<AppRegistry> c =
                    AppRegistry.class.getDeclaredConstructor();
            c.setAccessible(true);
            log.info("AppRegistryDemo constructor: {}", c);
            var cinstance = c.newInstance();
            log.info("AppRegistryDemo serialized: {}", cinstance);
        } catch (Exception ex) {
            log.error("Reflection attack failed: ", ex);
        }
    }

    private static byte[] serialize(Object o) throws IOException {
        try(var bos = new ByteArrayOutputStream();
            var out = new ObjectOutputStream(bos);
        ) {
            out.writeObject(o);
            return bos.toByteArray();
        }
    }

    private static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        try(
                var bis = new ByteArrayInputStream(bytes);
                var bos = new ObjectInputStream(bis)
                ){
            return bos.readObject();
        }
    }

}
