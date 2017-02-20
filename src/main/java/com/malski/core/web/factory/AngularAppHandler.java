package com.malski.core.web.factory;

import com.malski.core.web.view.AngularApp;
import com.malski.core.web.view.Frame;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class AngularAppHandler<T extends AngularApp> extends ComponentHandler<T> {
    private final List<String> onAppMethodNames = Arrays.asList("browser", "switchIn", "switchOut", "root",
            "setRoot", "initElements", "findElement", "findElements", "locator", "getWrappedElement", "isStaleness",
            "isDisplayed", "isVisible", "isPresent", "isEnabled", "hasFocus", "isInViewport", "waitUntilPresent",
            "waitUntilVisible", "waitUntilDisappear", "waitUntilEnabled", "waitUntilDisabled", "waitUntilAttributeChange",
            "waitUntilAttributeChangeFrom", "waitUntilIsInViewport", "waitUntilIsClickable", "refresh", "finalize",
            "waitForAngular");

    public AngularAppHandler(Class<T> type, LazyLocator locator) {
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
    protected Object interceptInvoke(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        T angularApp = getImplementation();
        if (onAppMethodNames.contains(method.getName())) {
            angularApp.waitForAngular();
            return invoke(angularApp, object, method, args, methodProxy);
        } else {
            return invoke(angularApp, object, method, args, methodProxy);
        }
    }
}