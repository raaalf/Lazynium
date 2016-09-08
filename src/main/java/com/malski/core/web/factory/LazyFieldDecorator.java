package com.malski.core.web.factory;

import com.malski.core.web.annotations.IFrame;
import com.malski.core.web.annotations.IModule;
import com.malski.core.web.elements.Element;
import com.malski.core.web.elements.Elements;
import com.malski.core.web.view.Frame;
import com.malski.core.web.view.Module;
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
        } else if(isDecorableWebModule(field.getType(), field)) {
            return decorateWebModule(field);
        } else if (isDecorable(field.getType(), field) || isDecorableList(field)) {
            return decorateComponent(field);
        } else {
            return null;
        }
    }

    private Object decorateWebModule(Field field) {
        Class<?> fieldType = field.getType();
        LazyLocator locator = (LazyLocator)factory.createLocator(field);
        if (locator == null) {
            return null;
        }
        if (Module.class.isAssignableFrom(fieldType)) {
            return proxyForWebModule(fieldType ,locator);
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

    private Object decorateComponent(Field field) {
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

    private boolean isDecorableWebModule(Class clazz, Field field) {
        return !(clazz == null || !Module.class.isAssignableFrom(clazz)) &&
                field.getAnnotation(IModule.class) != null;
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
        return (List<T>) Enhancer.create(Elements.class, handler);
    }

    /* Generate a type-parametrized locator proxy for the Module in question. */
    @SuppressWarnings("unchecked")
    private <T> T proxyForWebModule(Class<T> type, LazyLocator locator) {
        MethodInterceptor handler = new ModuleHandler(type, locator);
        return (T) Enhancer.create(type, handler);
    }

    /* Generate a type-parametrized locator proxy for the Frame in question. */
    @SuppressWarnings("unchecked")
    private <T> T proxyForFrame(Class<T> type, LazyLocator locator) {
        MethodInterceptor handler = new FrameHandler(type, locator);
        return (T) Enhancer.create(type, handler);
    }
}