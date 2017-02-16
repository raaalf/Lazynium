package com.malski.core.web.factory;

import com.malski.core.web.annotations.IComponent;
import com.malski.core.web.annotations.IFrame;
import com.malski.core.web.elements.Element;
import com.malski.core.web.elements.LazyList;
import com.malski.core.web.view.Component;
import com.malski.core.web.view.Frame;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import java.lang.reflect.*;
import java.util.List;


public class LazyFieldDecorator {
    /* factory to use when generating LazyLocator. */
    protected ElementLocatorFactory factory;

    /* Constructor for an LazyLocatorFactory. */
    public LazyFieldDecorator(ElementLocatorFactory factory) {
        this.factory = factory;
    }

    public Object decorate(Field field) {
        if(isDecorableFrame(field.getType(), field)) {
            return decorateFrame(field);
        } else if(isDecorableComponent(field.getType(), field)) {
            return decorateComponent(field);
        } else if (isDecorable(field.getType(), field) || isDecorableList(field)) {
            return decorateElements(field);
        } else {
            return null;
        }
    }

    private Object decorateComponent(Field field) {
        Class<?> fieldType = field.getType();
        LazyLocator locator = (LazyLocator)factory.createLocator(field);
        if (locator == null) {
            return null;
        }
        if (Component.class.isAssignableFrom(fieldType)) {
            return proxyForComponent(fieldType ,locator);
        } else {
            return null;
        }
    }

    private Object decorateFrame(Field field) {
        Class<?> fieldType = field.getType();
        LazyLocator locator = (LazyLocator)factory.createLocator(field);
        if (locator == null) {
            return null;
        }
        if (Frame.class.isAssignableFrom(fieldType)) {
            return proxyForFrame(fieldType ,locator);
        } else {
            return null;
        }
    }

    private Object decorateElements(Field field) {
        Class<?> fieldType = field.getType();
        LazyLocator locator = (LazyLocator)factory.createLocator(field);
        if (locator == null) {
            return null;
        }
        if (WebElement.class.equals(fieldType)) {
            fieldType = Element.class;
        }
        if (WebElement.class.isAssignableFrom(fieldType)) {
            return proxyForLocator(fieldType, locator);
        } else if (List.class.isAssignableFrom(fieldType)) {
            Class<?> erasureClass = getErasureClass(field);
            return proxyForListLocator(erasureClass, locator);
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

    private boolean isDecorableComponent(Class clazz, Field field) {
        return !(clazz == null || !com.malski.core.web.view.Component.class.isAssignableFrom(clazz)) &&
                field.getAnnotation(IComponent.class) != null;
    }

    private boolean isDecorableFrame(Class clazz, Field field) {
        return !(clazz == null || !Frame.class.isAssignableFrom(clazz)) &&
                field.getAnnotation(IFrame.class) != null;
    }

    /* Generate a type-parameterized locator proxy for the element in question. */
    @SuppressWarnings("unchecked")
    private <T> T proxyForLocator(Class<T> type, LazyLocator locator) {
        MethodInterceptor handler = new ElementHandler(type, locator);
        return (T) Enhancer.create(type, handler);
    }

    /* generates a proxy for a list of elements to be wrapped. */
    @SuppressWarnings("unchecked")
    private <T> List<T> proxyForListLocator(Class<T> type, LazyLocator locator) {
        MethodInterceptor handler = new ElementListHandler(type, locator);
        return (List<T>) Enhancer.create(LazyList.class, handler);
    }

    /* Generate a type-parametrized locator proxy for the IComponent in question. */
    @SuppressWarnings("unchecked")
    private <T> T proxyForComponent(Class<T> type, LazyLocator locator) {
        MethodInterceptor handler = new ComponentHandler(type, locator);
        return (T) Enhancer.create(type, handler);
    }

    /* Generate a type-parametrized locator proxy for the Frame in question. */
    @SuppressWarnings("unchecked")
    private <T> T proxyForFrame(Class<T> type, LazyLocator locator) {
        MethodInterceptor handler = new FrameHandler(type, locator);
        return (T) Enhancer.create(type, handler);
    }
}