package com.malski.core.web.factory;

import com.malski.core.web.elements.api.Element;
import org.openqa.selenium.WebElement;

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
            this.wrappingType = ImplHandler.getInterfaceImpl(interfaceType);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("no interface implementation.");
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Element> T getElementImplementation() throws Throwable {
        Constructor cons = wrappingType.getConstructor(LazyLocator.class);
        return (T) cons.newInstance(locator);
    }

    @SuppressWarnings("unchecked")
    public <T extends Element> T getElementImplementation(WebElement element) throws Throwable {
        Constructor cons = wrappingType.getConstructor(LazyLocator.class, WebElement.class);
        return (T) cons.newInstance(locator, element);
    }

    @Override
    public Object invoke(Object object, Method method, Object[] objects) throws Throwable {
        Object thing = getElementImplementation();
        try {
            return method.invoke(wrappingType.cast(thing), objects);
        } catch (InvocationTargetException e) {
            // Unwrap the underlying exception
            throw e.getCause();
        }
    }
}