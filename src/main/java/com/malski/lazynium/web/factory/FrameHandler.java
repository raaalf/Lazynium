package com.malski.lazynium.web.factory;

import com.malski.lazynium.utils.TestContext;
import com.malski.lazynium.web.view.Frame;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

@Deprecated
public class FrameHandler<T extends Frame> extends ComponentHandler<T> {
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
        if (TestContext.config().getOnFrameMethods().contains(method.getName())) {
            frame.switchOut();
            return invoke(frame, object, method, args, methodProxy);
        } else {
            return invoke(frame, object, method, args, methodProxy);
        }
    }
}
