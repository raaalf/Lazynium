package com.malski.core.web.factory;

import com.malski.core.web.page.api.WebModule;
import org.apache.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class WebModuleHandler implements InvocationHandler {
    private final LazyLocator locator;
    private final Class<?> wrappingType;
    protected final Logger log = Logger.getLogger(getClass());

    /* Generates a handler to retrieve the WebElement from a locator for
       a given WebElement interface descendant. */
    public <T> WebModuleHandler(Class<T> interfaceType, LazyLocator locator) {
        this.locator = locator;
        if (!WebModule.class.isAssignableFrom(interfaceType)) {
            throw new RuntimeException("interface not assignable to WebModule.");
        }
        try {
            this.wrappingType = ImplHandler.getInterfaceImpl(interfaceType);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("no interface implementation.");
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends WebModule> T getWebModuleImplementation() throws Throwable {
        Constructor cons = wrappingType.getConstructor(LazyLocator.class);
        return (T) cons.newInstance(locator);
    }

    @Override
    public Object invoke(Object object, Method method, Object[] objects) throws Throwable {
        try {
            WebModule thing = getWebModuleImplementation();
            return method.invoke(wrappingType.cast(thing), objects);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        } catch (NoSuchMethodException e) {
            log.error("Make sure that you call super(LazyLocator locator) in module constructor.");
            throw e.getCause();
        }
    }
}
