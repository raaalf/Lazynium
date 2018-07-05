package com.malski.lazynium.web.factory;

import com.malski.lazynium.utils.TestContext;
import com.malski.lazynium.web.view.NgPage;
import com.malski.lazynium.web.view.Frame;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class AngularAppHandler<T extends NgPage> extends ComponentHandler<T> {

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