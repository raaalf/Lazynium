package com.malski.core.web.factory;

import com.malski.core.web.annotations.IFrame;
import com.malski.core.web.annotations.Module;
import com.malski.core.web.elements.*;
import com.malski.core.web.page.WebModule;
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
    /* factory to use when generating LazyLocatorImpl. */
    protected ElementLocatorFactory factory;

    /* Constructor for an LazyLocatorFactory. */
    public ElementDecorator(ElementLocatorFactory factory) {
        this.factory = factory;
    }

    @Override
    public Object decorate(ClassLoader loader, Field field) {
        if (isDecorable(field.getType(), field) || isDecorableList(field) || isDecorableWebModule(field.getType(), field)) {
            return decorateComponent(loader, field);
        } else {
            return null;
        }

    }

    private Object decorateComponent(ClassLoader loader, Field field) {
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
        } else if (WebModule.class.isAssignableFrom(fieldType)) {
            return proxyForWebModule(loader, fieldType, locator);
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

    private boolean isDecorableWebModule(Class clazz, Field field) {
        return !(clazz == null || !WebModule.class.isAssignableFrom(clazz)) &&
                !(field.getAnnotation(Module.class) == null && field.getAnnotation(IFrame.class) == null);
    }

    /* Generate a type-parameterized locator proxy for the element in question. */
    private <T> T proxyForLocator(ClassLoader loader, Class<T> interfaceType, LazyLocator locator) {
        InvocationHandler handler = new ElementHandler(interfaceType, locator);
        return interfaceType.cast(Proxy.newProxyInstance(
                loader, new Class[]{interfaceType, WebElement.class, WrapsElement.class, Locatable.class}, handler));
    }

    /* generates a proxy for a list of elements to be wrapped. */
    @SuppressWarnings("unchecked")
    private <T> List<T> proxyForListLocator(ClassLoader loader, Class<T> interfaceType, LazyLocator locator) {
        InvocationHandler handler = new ElementListHandler(interfaceType, locator);
        return (List<T>) Proxy.newProxyInstance(loader, new Class[]{Elements.class, List.class, ElementsWait.class, ElementsStates.class}, handler);
    }

    /* Generate a type-parametrized locator proxy for the WebView in question. */
    private <T> T proxyForWebModule(ClassLoader loader, Class<T> interfaceType, LazyLocator locator) {
        InvocationHandler handler = new WebModuleHandler(interfaceType, locator);
        return interfaceType.cast(Proxy.newProxyInstance(
                loader, new Class[]{interfaceType, WebModule.class}, handler));
    }
}