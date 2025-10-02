package com.code.research.deserialization;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputFilter;
import java.io.ObjectInputStream;

public class SafeRead {
    public static Object safeDeserialize(byte[] data) throws Exception {
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
            ObjectInputFilter filter = ObjectInputFilter.Config.createFilter(
                    "maxdepth=5;maxbytes=1048576;com.myapp.domain.*;!*"); // allow list then deny all
            ois.setObjectInputFilter(filter);
            return ois.readObject();
        }
    }
}