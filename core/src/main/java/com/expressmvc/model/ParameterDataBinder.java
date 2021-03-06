package com.expressmvc.model;

import com.expressioc.exception.CycleDependencyException;
import com.expressioc.utility.ClassUtility;
import com.expressmvc.exception.DataBindException;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.Set;

public class ParameterDataBinder implements DataBinder {
    private Set<String> cycleMap = new HashSet<String>();

    @Override
    public void bind(HttpServletRequest request, Object parameter) throws DataBindException {
        try {
            cycleMap.clear();
            doBind(request, parameter, getClazzName(parameter));
        } catch (Exception e) {
            throw new DataBindException(e);
        }
    }

    private void doBind(HttpServletRequest request, Object parameter, String prefix) throws Exception {
        cycleMap.add(parameter.getClass().getName());
        for (Field field : parameter.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (ClassUtility.isBasicType(field.getType())) {
                String requestValue = request.getParameter(prefix + "." + getMemberName(field));
                if (requestValue == null) {
                    continue;
                }
                field.set(parameter, convert(parameter, requestValue, field.getType()));
            } else {
                if (field.getType().isArray()) {
                    continue;
                }
                if (cycleMap.contains(field.getName())) {
                    throw new CycleDependencyException(parameter.getClass());
                }
                Object nestedParameter = field.getType().newInstance();
                field.set(parameter, nestedParameter);
                doBind(request, nestedParameter, prefix + "." + getClazzName(nestedParameter));
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

    private Object convert(Object parameter, String value, Class type) throws Exception {
        String[] members = value.split(".");
        if (members.length > 1) {
            try {
                Field field = parameter.getClass().getDeclaredField(members[1]);
                convert(field.getClass().newInstance(), getMemberParams(members), field.getType());
            } catch (Exception e) {
                throw new InvalidParameterException();
            }
        }

        return ClassUtility.assembleParameter(value, type);
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
