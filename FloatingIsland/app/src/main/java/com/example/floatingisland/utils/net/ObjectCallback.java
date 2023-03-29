package com.example.floatingisland.utils.net;

import java.lang.reflect.Type;

public interface ObjectCallback<T>  {

    T convert(String response, Type type);
}
