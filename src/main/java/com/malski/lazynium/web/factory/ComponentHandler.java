package com.malski.lazynium.web.factory;

import com.malski.lazynium.web.view.Component;

import java.lang.reflect.Constructor;

public class ComponentHandler<T extends Component> extends LazyInterceptor<T> {

    public ComponentHandler(Class<T> type, LazyLocator locator) {
        super(type, locator);
    }

    @Override
    protected void init(Class<T> type) {
        if (!Component.class.isAssignableFrom(type)) {
            throw new RuntimeException("interface not assignable to IComponent.");
        }
        setWrapper(type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getImplementation() {
        try {
            Constructor cons = getWrapper().getConstructor();
            T component = (T) cons.newInstance();
            component.setRoot(getLocator());
            component.initElements();
            return component;
        } catch (Throwable e) {
            throw new RuntimeException("Not able to create object of type: " + getWrapper().getSimpleName(), e);
        }
    }
}