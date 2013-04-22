package com.expressmvc.binder.impl;

import com.expressmvc.binder.DataBinder;
import com.expressmvc.exception.AssembleFieldException;
import com.expressmvc.exception.NewInstanceException;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;

public class ParameterDataBinder implements DataBinder{

    private Object convert(String value, Class type) {
        if (type.equals(Boolean.class)) {
            return Boolean.parseBoolean(value);
        } else if (type.equals(String.class)) {
            return value;
        } else if (type.equals(int.class)) {
            return Integer.parseInt(value);
        }
        return null;
    }

    private Boolean isFieldBasicType(Class<?> type) {
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

    @Override
    public void bind(HttpServletRequest request, Object parameter) {
        for (Field field : parameter.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (isFieldBasicType(field.getType())) {
                try {
                    field.set(parameter, convert(request.getParameter(field.getName()), field.getType()));
                } catch (Exception e) {
                    throw new AssembleFieldException();
                }
            } else {
                try {
                    Object nestedParameter = field.getType().newInstance();
                    field.set(parameter, nestedParameter);
                    bind(request, nestedParameter);
                } catch (Exception e) {
                    throw new NewInstanceException(e);
                }
            }
        }
    }
}
