package com.malski.core.web.factory;

import com.malski.core.web.page.api.Frame;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class FrameHandler implements InvocationHandler {
    private final LazyLocator locator;
    private final Class<?> wrappingType;

    private final List<String> outsideMethodNames = Arrays.asList("waitUntilPresent", "waitUntilVisible", "waitUntilDisappear",
            "waitUntilEnabled", "waitUntilDisabled", "refresh", "getBrowser", "getSearchContext", "setRoot", "getFrame",
            "switchIn", "switchOut", "getRoot", "setSearchContext", "refresh");

    /* Generates a handler to retrieve the WebElement from a locator for
       a given WebElement interface descendant. */
    public <T> FrameHandler(Class<T> interfaceType, LazyLocator locator) {
        this.locator = locator;
        if (!Frame.class.isAssignableFrom(interfaceType)) {
            throw new RuntimeException("interface not assignable to Frame.");
        }
        try {
            this.wrappingType = ImplHandler.getInterfaceImpl(interfaceType);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("no interface implementation.");
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Frame> T getFrameImplementation() throws Throwable {
        Constructor cons = wrappingType.getConstructor(LazyLocator.class);
        return (T) cons.newInstance(locator);
    }

    @Override
    public Object invoke(Object object, Method method, Object[] objects) throws Throwable {
        Frame thing = getFrameImplementation();
        try {
            if (shouldSwitchIntoFrame(method.getName())) {
                thing.switchIn();
                Object result = method.invoke(wrappingType.cast(thing), objects);
                thing.switchOut();
                return result;
            } else {
                return method.invoke(wrappingType.cast(thing), objects);
            }
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    private boolean shouldSwitchIntoFrame(String methodName) {
        return !outsideMethodNames.contains(methodName);
    }

}