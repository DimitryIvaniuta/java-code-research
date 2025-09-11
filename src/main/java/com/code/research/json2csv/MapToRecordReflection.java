package com.code.research.json2csv;

import java.lang.reflect.Constructor;
import java.lang.reflect.RecordComponent;
import java.util.HashMap;
import java.util.Map;

public class MapToRecordReflection {

    // 1) Utility: map key -> String value -> record instance via reflection
    public static <T extends Record> T mapToRecord(Class<T> rc, Map<String, String> values) {
        try {
            // Get record components and their types
            RecordComponent[] comps = rc.getRecordComponents();
            // Build argument array in declaration order
            Object[] args = new Object[comps.length];
            Class<?>[] paramTypes = new Class<?>[comps.length];
            for (int i = 0; i < comps.length; i++) {
                RecordComponent comp = comps[i];
                String name = comp.getName();
                Class<?> type = comp.getType();
                paramTypes[i] = type;
                String str = values.get(name);
                args[i] = convert(str, type);
            }
            // Find canonical constructor
            Constructor<T> ctor = rc.getDeclaredConstructor(paramTypes);
            ctor.setAccessible(true);
            return ctor.newInstance(args);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to map to record " + rc.getSimpleName(), e);
        }
    }

    // 2) Convert String -> target type
    private static Object convert(String str, Class<?> type) {
        if (str == null) return null;
        if (type == String.class) return str;
        if (type == int.class || type == Integer.class) return Integer.valueOf(str);
        if (type == long.class || type == Long.class) return Long.valueOf(str);
        if (type == boolean.class || type == Boolean.class) return Boolean.valueOf(str);
        if (type == double.class || type == Double.class) return Double.valueOf(str);
        // add more conversions as needed
        throw new IllegalArgumentException("Unsupported component type: " + type.getName());
    }

    // 3) Example record
    public record Contact(String name, String email, String phone, int age, boolean newsletter) {
    }

    public static void main(String[] args) {
        Map<String, String> data = new HashMap<>();
        data.put("name", "Alice Smith");
        data.put("email", "alice@example.com");
        data.put("phone", "+48-123-456-789");
        data.put("age", "31");
        data.put("newsletter", "true");

        Contact c = mapToRecord(Contact.class, data);
        System.out.println(c);
        // prints: Contact[name=Alice Smith, email=alice@example.com, phone=+48-123-456-789, age=31, newsletter=true]
    }
}
