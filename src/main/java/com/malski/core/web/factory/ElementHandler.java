package com.malski.core.web.factory;

import com.malski.core.web.control.LazySearchContext;
import com.malski.core.web.elements.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Constructor;

public class ElementHandler<T extends Element> extends LazyInterceptor<T> {

    public ElementHandler(Class<T> interfaceType, LazyLocator locator) {
        super(interfaceType, locator);
    }

    public ElementHandler(Class<T> interfaceType, By by, LazySearchContext searchContext) {
        super(interfaceType, by, searchContext);
    }

    public ElementHandler(Class<T> interfaceType, Selector selector, LazySearchContext searchContext) {
        super(interfaceType, selector, searchContext);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void init(Class<T> interfaceType) {
        if (!Element.class.isAssignableFrom(interfaceType)) {
            throw new RuntimeException("Interface not assignable to " + interfaceType.getCanonicalName());
        }
        try {
           setWrapper((Class<T>) Class.forName(interfaceType.getCanonicalName()));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Missing implementation for " + interfaceType.getCanonicalName());
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getImplementation() {
        try {
            Constructor cons = getWrapper().getConstructor(LazyLocator.class);
            return (T) cons.newInstance(getLocator());
        } catch (Throwable e) {
            throw new RuntimeException("Not able to create object of type: " + getWrapper().getSimpleName(), e);
        }
    }

    @SuppressWarnings("unchecked")
    public T getImplementation(WebElement element, int index) {
        try {
            Constructor cons = getWrapper().getConstructor(LazyLocator.class, WebElement.class);
            LazyLocator indexed = getLocator().duplicate(index);
            return (T) cons.newInstance(indexed, element);
        } catch (Throwable e) {
            throw new RuntimeException("Not able to create object of type: " + getWrapper().getSimpleName(), e);
        }
    }
}