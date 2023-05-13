package com.example.floatingisland.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;

import java.io.File;
import java.util.Arrays;

public class FileUtil {
    public static Uri getFileUri(Context context, File file) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
        } else {
            return Uri.fromFile(file);
        }
    }

    public static String getFilePathFromUri(Context context, Uri uri) {
        if (uri == null) {
            return null;
        }
        String path = null;
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(index);
            cursor.close();
        }
        return path;
    }

    public static boolean isImage(File file) {
        String name = file.getName();
        String extension = name.substring(name.lastIndexOf(".") + 1).toLowerCase();
        return Arrays.asList("jpg", "jpeg", "png", "bmp", "gif").contains(extension);
    }

    public static boolean isVideo(File file) {
        String name = file.getName();
        String extension = name.substring(name.lastIndexOf(".") + 1).toLowerCase();
        return Arrays.asList("mp4", "3gp", "avi", "flv", "wmv", "mkv").contains(extension);
    }
}
