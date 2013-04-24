package com.expressmvc.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ClassUtils {
    public static Boolean isBasicType(Class<?> type) {
        //TODO: convert to map and contains
        return type.equals(Boolean.class) || type.equals(boolean.class)
                || type.equals(Byte.class) || type.equals(byte.class)
                || type.equals(Short.class) || type.equals(short.class) ||
                type.equals(Integer.class) || type.equals(int.class) ||
                type.equals(Long.class) || type.equals(long.class)
                || type.equals(Float.class) || type.equals(float.class) ||
                type.equals(Double.class) || type.equals(double.class) ||
                type.equals(Character.class) || type.equals(char.class) ||
                type.equals(String.class);
    }

    public static Object assembleParameter(String value, Class type) {
        if (type.equals(Boolean.class)) {
            return Boolean.parseBoolean(value);
        } else if (type.equals(String.class)) {
            return value;
        } else if (type.equals(int.class)) {
            return Integer.parseInt(value);
        } else if (type.equals(double.class)) {
            return Double.parseDouble(value);
        } else if (type.equals(float.class)) {
            return Float.parseFloat(value);
        }
        return null;
    }

    public static Object newInstanceOf(Class paramClazz) {
        try {
            Constructor cons = paramClazz.getDeclaredConstructor();
            return cons.newInstance();
        } catch (NoSuchMethodException e) {
        } catch (InvocationTargetException e) {
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        }

        return null;
    }
}
