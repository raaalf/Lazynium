package com.malski.lazynium.web.factory;

import com.malski.lazynium.web.control.LazySearchContext;
import com.malski.lazynium.web.elements.Element;
import com.malski.lazynium.web.view.Frame;
import net.sf.cglib.proxy.MethodProxy;
import org.openqa.selenium.By;

import java.lang.reflect.Method;

public class FrameElementHandler<T extends Element> extends ElementHandler<T> {

    public FrameElementHandler(Class<T> interfaceType, LazyLocator locator) {
        super(interfaceType, locator);
    }

    public FrameElementHandler(Class<T> interfaceType, By by, LazySearchContext searchContext) {
        super(interfaceType, by, searchContext);
    }

    public FrameElementHandler(Class<T> interfaceType, Selector selector, LazySearchContext searchContext) {
        super(interfaceType, selector, searchContext);
    }

    @Override
    protected Object invoke(T thing, Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        ((Frame) thing.searchContext()).switchIn();
        Object result = super.invoke(thing, object, method, args, methodProxy);
        ((Frame) thing.searchContext()).switchOut();
        return result;
    }

}
