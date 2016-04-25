package com.malski.core.web.factory;

import com.malski.core.web.page.WebModule;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class WebModuleHandler implements InvocationHandler {
    private final Class<?> wrappingType;

    /* Generates a handler to retrieve the WebElement from a locator for
       a given WebElement interface descendant. */
    public <T> WebModuleHandler(Class<T> interfaceType) {
        if (!WebModule.class.isAssignableFrom(interfaceType)) {
            throw new RuntimeException("interface not assignable to WebModule.");
        }
        try {
            this.wrappingType = Class.forName(interfaceType.getCanonicalName()+"Impl");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("no interface implementation.");
        }
    }

    @Override
    public Object invoke(Object object, Method method, Object[] objects) throws Throwable {
        Constructor cons = wrappingType.getConstructor();
        Object thing = cons.newInstance();
        try {
            return method.invoke(wrappingType.cast(thing), objects);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}
