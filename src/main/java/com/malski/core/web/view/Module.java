package com.malski.core.web.view;

import com.malski.core.web.control.LazySearchContext;
import com.malski.core.web.elements.Element;
import com.malski.core.web.elements.states.ElementState;
import com.malski.core.web.factory.ElementHandler;
import com.malski.core.web.factory.LazyLocator;
import com.malski.core.web.factory.LazyPageFactory;
import com.malski.core.web.factory.Selector;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;

/**
 * Class which is representing displayed module on page and allow to performing basic actions on it
 */
public abstract class Module implements LazySearchContext, View, ElementState {
    protected final Logger log = Logger.getLogger(getClass());
    private Element rootElement;

    public Module() {
    }

    public void setRoot(LazyLocator locator) {
        this.rootElement = locator.getElement();
    }

    @Override
    public void initElements() {
        LazyPageFactory.initElements(this);
    }

    @Override
    public SearchContext getContext() {
        return getRoot();
    }

    protected Element getRoot() {
        return rootElement;
    }

    public LazyLocator getLocator() {
        return getRoot().getLocator();
    }

    @Override
    public boolean refresh() {
        boolean result = getRoot().refresh();
        initElements();
        return result;
    }

    public boolean isDisplayed() {
        return getRoot().isDisplayed();
    }

    public <T extends Element> T getElement(By by, Class<T> clazz) {
        return new ElementHandler<>(clazz, by, getRoot()).getImplementation();
    }

    public <T extends Element> T getElement(Selector selector, Class<T> clazz) {
        return new ElementHandler<>(clazz, selector, getRoot()).getImplementation();
    }
}
