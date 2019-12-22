package com.example.androidart;

import java.io.Closeable;
import java.io.IOException;

public class Utils {

    public static void closeQuietly(Closeable... closeables) {
        for (Closeable closeable: closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    // ignored
                }
            }
        }
    }
}
