package com.malski.core.web.factory;

import com.malski.core.web.base.LazySearchContext;
import com.malski.core.web.elements.api.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ElementHandler implements InvocationHandler {
    private final LazyLocator locator;
    private Class<?> wrappingType;

    /* Generates a handler to retrieve the WebElement from a locator for
       a given WebElement interface descendant. */
    public <T> ElementHandler(Class<T> interfaceType, LazyLocator locator) {
        this.locator = locator;
        init(interfaceType);
    }

    public <T> ElementHandler(Class<T> interfaceType, By by, LazySearchContext searchContext) {
        this.locator = new LazyLocatorImpl(searchContext, by);
        init(interfaceType);
    }

    public <T> ElementHandler(Class<T> interfaceType, Selector selector, LazySearchContext searchContext) {
        this.locator = new LazyLocatorImpl(searchContext, selector);
        init(interfaceType);
    }

    private <T> void init(Class<T> interfaceType) {
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
    public <T extends Element> T getElementImplementation() {
        try {
            Constructor cons = wrappingType.getConstructor(LazyLocator.class);
            return (T) cons.newInstance(locator);
        } catch (Throwable e) {
            return null;
        }
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