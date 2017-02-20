package com.malski.core.web.factory;

import com.malski.core.web.view.Frame;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class FrameHandler<T extends Frame> extends ComponentHandler<T> {
    private final List<String> generalMethodNames = Arrays.asList("browser", "switchIn", "switchOut", "root",
            "setRoot", "initElements", "findElement", "findElements", "locator", "getWrappedElement");

    private final List<String> onFrameMethodNames = Arrays.asList("isStaleness", "isDisplayed", "isVisible", "isPresent",
            "isEnabled", "hasFocus", "isInViewport", "waitUntilPresent", "waitUntilVisible", "waitUntilDisappear",
            "waitUntilEnabled", "waitUntilDisabled", "waitUntilAttributeChange", "waitUntilAttributeChangeFrom",
            "waitUntilIsInViewport", "waitUntilIsClickable", "refresh", "finalize");

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
    protected Object interceptInvoke(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        T frame = getImplementation();
        if (onFrameMethodNames.contains(method.getName())) {
            frame.switchOut();
            return invoke(frame, object, method, args, methodProxy);
        } else if(generalMethodNames.contains(method.getName())) {
            return invoke(frame, object, method, args, methodProxy);
        } else {
            frame.switchIn();
            Object result = invoke(frame, object, method, args, methodProxy);
            frame.switchOut();
            return result;
        }
    }
}
