package com.example.androidart;

import android.content.Context;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;

import com.example.androidart.intercept.InterceptActivity1;

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

    public static DisplayMetrics getScreenMetrics(@NonNull Context context) {
        return context.getResources().getDisplayMetrics();
    }
}
