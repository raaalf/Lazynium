package com.malski.lazynium.web.factory;

import com.malski.lazynium.web.control.LazySearchContext;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.openqa.selenium.By;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

abstract class LazyInterceptor<T> implements MethodInterceptor {
    private final LazyLocator locator;
    private Class<T> wrapper;

    LazyInterceptor(Class<T> interfaceType, LazyLocator locator) {
        this.locator = locator;
        init(interfaceType);
    }

    LazyInterceptor(Class<T> interfaceType, By by, LazySearchContext searchContext) {
        this.locator = new LazyLocator(searchContext, by);
        init(interfaceType);
    }

    LazyInterceptor(Class<T> interfaceType, Selector selector, LazySearchContext searchContext) {
        this.locator = new LazyLocator(searchContext, selector);
        init(interfaceType);
    }

    void setWrapper(Class<T> type) {
        this.wrapper = type;
    }

    Class<T> getWrapper() {
        return this.wrapper;
    }

    protected LazyLocator getLocator() {
        return this.locator;
    }

    protected abstract void init(Class<T> interfaceType);

    public abstract Object getImplementation();

    @Override
    public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        try {
            return interceptInvoke(object, method, args, methodProxy);
        } catch (Throwable e) {
            if(e.getCause() == null) {
                throw clearStackTrace(e);
            } else {
                throw clearStackTrace(e.getCause());
            }
        }
    }

    @SuppressWarnings("unchecked")
    protected Object interceptInvoke(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        T thing = (T) getImplementation();
        return invoke(thing, object, method, args, methodProxy);
    }

    protected Object invoke(T thing, Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
//        return methodProxy.invoke(thing, args);
        return method.invoke(thing, args);
    }

    private <E extends Throwable> E clearStackTrace(E throwable) {
        List<StackTraceElement> newStack = new ArrayList<>();
        if(throwable != null) {
            for (StackTraceElement stack : throwable.getStackTrace()) {
                if (!stack.getClassName().contains(".factory.")) {
                    newStack.add(stack);
                }
            }
            throwable.setStackTrace(newStack.toArray(new StackTraceElement[newStack.size()]));
        }
        return throwable;
    }
}
