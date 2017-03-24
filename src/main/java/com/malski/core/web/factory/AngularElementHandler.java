package com.malski.core.web.factory;

import com.malski.core.web.control.LazySearchContext;
import com.malski.core.web.elements.Element;
import com.malski.core.web.view.AngularApp;
import net.sf.cglib.proxy.MethodProxy;
import org.openqa.selenium.By;

import java.lang.reflect.Method;

public class AngularElementHandler<T extends Element> extends ElementHandler<T> {

    public AngularElementHandler(Class<T> interfaceType, LazyLocator locator) {
        super(interfaceType, locator);
    }

    public AngularElementHandler(Class<T> interfaceType, By by, LazySearchContext searchContext) {
        super(interfaceType, by, searchContext);
    }

    public AngularElementHandler(Class<T> interfaceType, Selector selector, LazySearchContext searchContext) {
        super(interfaceType, selector, searchContext);
    }

    @Override
    protected Object invoke(T thing, Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        ((AngularApp) thing.searchContext()).waitForAngular();
        return super.invoke(thing, object, method, args, methodProxy);
    }
}