package com.malski.core.web.factory;

import com.malski.core.utils.TestContext;
import com.malski.core.web.view.AngularApp;
import com.malski.core.web.view.Frame;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class AngularAppHandler<T extends AngularApp> extends ComponentHandler<T> {

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
        if (TestContext.config().getAngularMethods().contains(method.getName())) {
            return invoke(angularApp, object, method, args, methodProxy);
        } else {
            angularApp.waitForAngular();
            return invoke(angularApp, object, method, args, methodProxy);
        }
    }
}