package com.malski.core.web.factory;

import com.malski.core.web.control.LazySearchContext;
import com.malski.core.web.elements.Element;
import com.malski.core.web.elements.Elements;
import net.sf.cglib.proxy.MethodProxy;
import org.openqa.selenium.By;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ElementListHandler<T extends Element> extends LazyInterceptor<T> {

    public ElementListHandler(Class<T> interfaceType, LazyLocator locator) {
        super(interfaceType, locator);
    }

    public ElementListHandler(Class<T> interfaceType, By by, LazySearchContext searchContext) {
        super(interfaceType, by, searchContext);
    }

    public ElementListHandler(Class<T> interfaceType, Selector selector, LazySearchContext searchContext) {
        super(interfaceType, selector, searchContext);
    }

    @Override
    protected void init(Class<T> type) {
        if (!Element.class.isAssignableFrom(type)) {
            throw new RuntimeException("interface not assignable to Element.");
        }
        setWrapper(type);
    }

    public Elements<T> getImplementation() {
        return new Elements<>(getLocator(), getWrapper());
    }

    @Override
    protected Object interceptInvoke(Object object, Method method, Object[] args, MethodProxy methodProxy) throws InvocationTargetException, IllegalAccessException {
        @SuppressWarnings("unchecked")
        Elements<T> wrappedList = new Elements<>(getLocator(), getWrapper());
        return invoke(wrappedList, object, method, args, methodProxy);
    }

    protected Object invoke(Elements<T> wrappedList, Object object, Method method, Object[] args, MethodProxy methodProxy) throws InvocationTargetException, IllegalAccessException {
        try {
            return methodProxy.invoke(wrappedList, args);
        } catch (Throwable ignore) {
            return method.invoke(wrappedList, args);
        }
    }
}