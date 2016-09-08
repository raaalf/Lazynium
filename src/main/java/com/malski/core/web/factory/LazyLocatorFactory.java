package com.malski.core.web.factory;

import com.malski.core.web.control.LazySearchContext;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import java.lang.reflect.Field;

public class LazyLocatorFactory implements ElementLocatorFactory {
    private final LazySearchContext searchContext;

    public LazyLocatorFactory(LazySearchContext searchContext) {
        this.searchContext = searchContext;
    }

    public LazyLocator createLocator(Field field) {
        return new LazyLocator(this.searchContext, new LazyAnnotations(field));
    }

    public LazyLocator createLocator(LazyAnnotations lazyAnnotations) {
        return new LazyLocator(this.searchContext, lazyAnnotations);
    }
}