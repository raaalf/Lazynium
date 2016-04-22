package com.malski.core.web.factory;

import com.malski.core.web.elements.Element;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

import java.lang.reflect.*;
import java.util.List;

public class ElementDecorator implements FieldDecorator {
    /* factory to use when generating Locator. */
    protected ElementLocatorFactory factory;

    /* Constructor for an LocatorFactory. */
    public ElementDecorator(ElementLocatorFactory factory) {
        this.factory = factory;
    }

    @Override
    public Object decorate(ClassLoader loader, Field field) {
        if (!(isDecorable(field.getType(), field) || isDecorableList(field))) {
            return null;
        }
        LazyLocator locator = (LazyLocator)factory.createLocator(field);
        if (locator == null) {
            return null;
        }
        Class<?> fieldType = field.getType();
        if (WebElement.class.equals(fieldType)) {
            fieldType = Element.class;
        }

        if (WebElement.class.isAssignableFrom(fieldType)) {
            return proxyForLocator(loader, fieldType, locator);
        } else if (List.class.isAssignableFrom(fieldType)) {
            Class<?> erasureClass = getErasureClass(field);
            return proxyForListLocator(loader, erasureClass, locator);
        } else {
            return null;
        }
    }

    private Class getErasureClass(Field field) {
        Type genericType = field.getGenericType();
        if (!(genericType instanceof ParameterizedType)) {
            return null;
        }
        return (Class) ((ParameterizedType) genericType).getActualTypeArguments()[0];
    }

    private boolean isDecorableList(Field field) {
        if (!List.class.isAssignableFrom(field.getType())) {
            return false;
        }
        Class erasureClass = getErasureClass(field);
        return isDecorable(erasureClass, field);
    }

    private boolean isDecorable(Class clazz, Field field) {
        return !(clazz == null || !WebElement.class.isAssignableFrom(clazz)) &&
                !(field.getAnnotation(FindBy.class) == null && field.getAnnotation(FindBys.class) == null);
    }

    /* Generate a type-parameterized locator proxy for the element in question. */
    protected <T> T proxyForLocator(ClassLoader loader, Class<T> interfaceType, LazyLocator locator) {
        InvocationHandler handler = new ElementHandler(interfaceType, locator);
        return interfaceType.cast(Proxy.newProxyInstance(
                loader, new Class[]{interfaceType, WebElement.class, WrapsElement.class, Locatable.class}, handler));
    }

    /* generates a proxy for a list of elements to be wrapped. */
    @SuppressWarnings("unchecked")
    protected <T> List<T> proxyForListLocator(ClassLoader loader, Class<T> interfaceType, LazyLocator locator) {
        InvocationHandler handler = new ElementListHandler(interfaceType, locator);
        return (List<T>) Proxy.newProxyInstance(loader, new Class[]{List.class}, handler);
    }
}