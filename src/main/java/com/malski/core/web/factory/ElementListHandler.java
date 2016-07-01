package com.malski.core.web.factory;

import com.malski.core.web.base.LazySearchContext;
import com.malski.core.web.elements.api.Element;
import com.malski.core.web.elements.api.Elements;
import com.malski.core.web.elements.impl.ElementsImpl;
import org.openqa.selenium.By;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ElementListHandler implements InvocationHandler {
    private final LazyLocator locator;
    private Class<?> wrappingInterface;

    /* Generates a handler to retrieve the WebElement from a locator for
       a given WebElement interface descendant. */
    public ElementListHandler(Class<?> interfaceType, LazyLocator locator) {
        this.locator = locator;
        init(interfaceType);
    }

    public ElementListHandler(Class<?> interfaceType, By by, LazySearchContext searchContext) {
        this.locator = new LazyLocatorImpl(searchContext, by);
        init(interfaceType);
    }

    public ElementListHandler(Class<?> interfaceType, Selector selector, LazySearchContext searchContext) {
        this.locator = new LazyLocatorImpl(searchContext, selector);
        init(interfaceType);
    }

    private <T> void init(Class<T> interfaceType) {
        if (!Element.class.isAssignableFrom(interfaceType)) {
            throw new RuntimeException("interface not assignable to Element.");
        }
        this.wrappingInterface = interfaceType;
    }

    @SuppressWarnings("unchecked")
    public <T extends Element> Elements<T> getElementImplementation() {
        return new ElementsImpl<>(locator, (Class<T>) wrappingInterface);
    }

    public static <T extends Element> Elements<T> getEmptyList() {
        return new ElementsImpl<>();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object invoke(Object object, Method method, Object[] objects) throws Throwable {
        Elements<? extends Element> wrappedList = new ElementsImpl<>(locator, (Class<? extends Element>) wrappingInterface);
        try {
            return method.invoke(wrappedList, objects);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}