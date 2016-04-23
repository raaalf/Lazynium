package com.malski.core.web.factory;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import java.lang.reflect.Field;

public class LazyLocatorFactory implements ElementLocatorFactory {
    private final SearchContext searchContext;

    public LazyLocatorFactory(SearchContext searchContext) {
        this.searchContext = searchContext;
    }

    public LazyLocator createLocator(Field field) {
        return new LazyLocatorImpl(this.searchContext, new LazyAnnotations(field));
    }

    public LazyLocator createLocator(LazyAnnotations lazyAnnotations) {
        return new LazyLocatorImpl(this.searchContext, lazyAnnotations);
    }
}