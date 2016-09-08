package com.malski.core.web.factory;

import com.malski.core.web.view.Frame;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;


public class FrameHandler<T extends Frame> extends ModuleHandler<T> {

    private final List<String> outsideMethodNames = Arrays.asList("isDisplayed", "isDisplayed", "isVisible", "isVisible",
            "isPresent", "isPresent", "isEnabled", "isEnabled", "hasFocus", "hasFocus", "isInViewport", "isInViewport",
            "isStaleness", "waitUntilPresent", "waitUntilPresent", "waitUntilVisible", "waitUntilVisible", "waitUntilDisappear",
            "waitUntilDisappear", "waitUntilEnabled", "waitUntilEnabled", "waitUntilDisabled", "waitUntilDisabled",
            "waitUntilAttributeChange", "waitUntilAttributeChange", "waitUntilIsInViewport", "waitUntilIsInViewport",
            "refresh", "getBrowser", "getSearchContext", "switchIn", "switchOut", "getRoot", "setSearchContext");

    public FrameHandler(Class<T> type, LazyLocator locator) {
        super(type, locator);
    }

    @Override
    protected void init(Class<T> type) {
        if (!Frame.class.isAssignableFrom(type)) {
            throw new RuntimeException("interface not assignable to Frame.");
        }
        setWrapper(type);
    }

    @Override
    protected Object interceptInvoke(Object object, Method method, Object[] args, MethodProxy methodProxy) throws InvocationTargetException, IllegalAccessException {
        T thing = getImplementation();
        if (shouldSwitchIntoFrame(method.getName())) {
            thing.switchIn();
            Object result = invoke(thing, object, method, args, methodProxy);
            thing.switchOut();
            return result;
        } else {
            return invoke(thing, object, method, args, methodProxy);
        }
    }

    private boolean shouldSwitchIntoFrame(String methodName) {
        return !outsideMethodNames.contains(methodName);
    }
}
