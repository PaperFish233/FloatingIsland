package com.example.floatingisland.utils.net;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.$Gson$Types;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public abstract class OkCallback<T extends Result> implements Callback, ObjectCallback<T> {

    private static final String SUCCESS_LOGIN = "200";

    public static final String ERROR_LOGIN = "-11";


    private static final String ERROR_1000 = "1000";

    private static final String CODE = "code";

    private static final String MSG = "message";

    private static Handler handler = new Handler(Looper.getMainLooper());

    private static Gson GSON = new Gson();

    @Override

    public void onFailure(Call call, final IOException e) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                onFailure(ERROR_1000, e.getMessage());
            }
        });
    }

    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onResponse(Call call, final Response response) throws IOException {
        try {
            final String resStr = response.body().string();
//            final Result<T> result = GSON.fromJson(resStr, new TypeToken<Result<T>>() {}.getType());
            //尖括号内写入转换的类型，这里是一个List，并且是Banner的泛型
//            Type type = new TypeToken<Result<T>>() {
//            }.getType();
//            //进行Json到Gson的转换
//            Result<T> result = GSON.fromJson(resStr, type);

            T result = convert(resStr, getSuperclassTypeParameter(getClass()));

            if (result.getCode().equals(SUCCESS_LOGIN) || result.getCode().equals("0")) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onResponse(result);
                    }
                });
                return;
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onFailure(result.getCode(), result.getMessage());
                }
            });
        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    @Override
    public T convert(String response, Type type) {
        return gson().fromJson(response, type);//这个不要处理json解析错误，因为debug就是需要它该崩溃时崩溃，但是一旦非debug，这个就不会抛出崩溃
    }

    private static Gson gson() {
        if (GSON == null) {
            synchronized (OkCallback.class) {
                if (GSON == null) {
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    GSON = gsonBuilder.create();
                }
            }
        }
        return GSON;
    }

    private static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    public abstract void onResponse(T response);

    public abstract void onFailure(String state, String msg);


}
