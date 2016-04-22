package com.malski.core.web.factory;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import java.lang.reflect.Field;

public class LocatorFactory implements ElementLocatorFactory {
    private final SearchContext searchContext;

    public LocatorFactory(SearchContext searchContext) {
        this.searchContext = searchContext;
    }

    public LazyLocator createLocator(Field field) {
        return new Locator(this.searchContext, field);
    }
}