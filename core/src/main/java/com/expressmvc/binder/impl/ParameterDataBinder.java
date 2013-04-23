package com.expressmvc.binder.impl;

import com.expressmvc.binder.DataBinder;
import com.expressmvc.exception.DataBindException;
import com.expressmvc.utility.IOCUtility;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.security.InvalidParameterException;

public class ParameterDataBinder implements DataBinder {

    @Override
    public void bind(HttpServletRequest request, Object parameter) throws Exception {
        try {
            doBind(request, parameter, getClazzName(parameter));
        } catch (Exception e) {
            throw new DataBindException();
        }
    }

    private void doBind(HttpServletRequest request, Object parameter, String preFix) throws Exception {
        for (Field field : parameter.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (IOCUtility.isFieldBasicType(field.getType())) {
                String requestValue = request.getParameter(preFix + "." + getMemberName(field));
                if (requestValue == null) {
                    continue;
                }
                field.set(parameter, convert(parameter, requestValue, field.getType()));
            } else {
                Object nestedParameter = field.getType().newInstance();
                field.set(parameter, nestedParameter);
                doBind(request, nestedParameter, preFix + "." + getClazzName(nestedParameter));
            }
        }
    }

    private String getClazzName(Object clazzName) {
        String[] classNames = clazzName.getClass().getName().toLowerCase().split("\\.");
        return classNames[classNames.length - 1];
    }

    private String getMemberName(Field field) {
        return field.getName().toLowerCase();
    }

    private Object convert(Object parameter, String value, Class type) {
        String[] members = value.split(".");
        if (members.length > 1) {
            try {
                Field field = parameter.getClass().getDeclaredField(members[1]);
                convert(field.getClass().newInstance(), getMemberParams(members), field.getType());
            } catch (Exception e) {
                throw new InvalidParameterException();
            }
        }

        return IOCUtility.assembleParameter(value, type);
    }

    private String getMemberParams(String[] members) {
        String paramValue = "";
        for (int i = 2; i <= members.length; i++) {
            paramValue += members[i];
            paramValue += ".";
        }
        return paramValue;
    }
}
