package com.malski.core.web.factory;

import com.malski.core.web.elements.Element;
import com.malski.core.web.elements.ElementsList;
import com.malski.core.web.elements.ElementsListImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ElementListHandler implements InvocationHandler {
    private final LazyLocator locator;
    private final Class<? extends Element> wrappingType;

    /* Generates a handler to retrieve the WebElement from a locator for
       a given WebElement interface descendant. */
    @SuppressWarnings("unchecked")
    public ElementListHandler(Class<?> interfaceType, LazyLocator locator) {
        this.locator = locator;
        if (!Element.class.isAssignableFrom(interfaceType)) {
            throw new RuntimeException("interface not assignable to Element.");
        }
        this.wrappingType = (Class<? extends Element>) interfaceType;
    }

    @Override
    public Object invoke(Object object, Method method, Object[] objects) throws Throwable {
        //TODO new lazy element init
        ElementsList elements = new ElementsListImpl<>(locator, wrappingType);
        try {
            return method.invoke(elements, objects);
        } catch (InvocationTargetException e) {
            // Unwrap the underlying exception
            throw e.getCause();
        }
    }
}