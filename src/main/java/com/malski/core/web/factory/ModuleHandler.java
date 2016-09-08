package com.malski.core.web.factory;

import com.malski.core.web.view.Module;

import java.lang.reflect.Constructor;

public class ModuleHandler<T extends Module> extends LazyInterceptor<T> {

    public ModuleHandler(Class<T> type, LazyLocator locator) {
        super(type, locator);
    }

    @Override
    protected void init(Class<T> type) {
        if (!Module.class.isAssignableFrom(type)) {
            throw new RuntimeException("interface not assignable to Module.");
        }
        setWrapper(type);
    }

    @SuppressWarnings("unchecked")
    public T getImplementation() {
        try {
            Constructor cons = getWrapper().getConstructor();
            T module = (T) cons.newInstance();
            module.initialize(getLocator());
            return module;
        } catch (Throwable e) {
            throw new RuntimeException("Not able to create object of type: " + getWrapper().getSimpleName(), e);
        }
    }
}