package com.malski.core.web.factory;

import com.malski.core.web.elements.Element;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ElementHandler implements InvocationHandler {
    private final LazyLocator locator;
    private final Class<?> wrappingType;

    /* Generates a handler to retrieve the WebElement from a locator for
       a given WebElement interface descendant. */
    public <T> ElementHandler(Class<T> interfaceType, LazyLocator locator) {
        this.locator = locator;
        if (!Element.class.isAssignableFrom(interfaceType)) {
            throw new RuntimeException("interface not assignable to Element.");
        }

        try {
            this.wrappingType = Class.forName(interfaceType.getCanonicalName()+"Impl");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("no interface implementation.");
        }
    }

    @Override
    public Object invoke(Object object, Method method, Object[] objects) throws Throwable {
        Constructor cons = wrappingType.getConstructor(LazyLocator.class);
        Object thing = cons.newInstance(locator);
        try {
            return method.invoke(wrappingType.cast(thing), objects);
        } catch (InvocationTargetException e) {
            // Unwrap the underlying exception
            throw e.getCause();
        }
    }
}