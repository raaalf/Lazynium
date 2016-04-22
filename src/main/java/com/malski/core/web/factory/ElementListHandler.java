package com.malski.core.web.factory;

import com.malski.core.web.elements.Element;
import com.malski.core.web.elements.Elements;
import com.malski.core.web.elements.ElementsImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ElementListHandler implements InvocationHandler {
    private final LazyLocator locator;
    private final Class<? extends Element> wrappingInterface;

    /* Generates a handler to retrieve the WebElement from a locator for
       a given WebElement interface descendant. */
    @SuppressWarnings("unchecked")
    public ElementListHandler(Class<?> interfaceType, LazyLocator locator) {
        this.locator = locator;
        if (!Element.class.isAssignableFrom(interfaceType)) {
            throw new RuntimeException("interface not assignable to Element.");
        }
        this.wrappingInterface = (Class<? extends Element>) interfaceType;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object invoke(Object object, Method method, Object[] objects) throws Throwable {
        Elements<? extends Element> wrappedList = new ElementsImpl<>(locator, wrappingInterface);
        try {
            return method.invoke(wrappedList, objects);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}