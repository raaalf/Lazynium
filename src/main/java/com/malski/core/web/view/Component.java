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
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Class which is representing displayed module on page and allow to performing basic actions on it
 */
public class Component implements View, LazySearchContext, ElementState {
    protected final Logger log = Logger.getLogger(getClass());
    private Element root;

    public Component() {
    }

    public void setRoot(LazyLocator locator) {
        this.root = locator.getElement();
    }

    @Override
    public boolean isStaleness() {
        return root().isStaleness();
    }

    @Override
    public void initElements() {
        LazyPageFactory.initElements(this);
    }

    @Override
    public SearchContext getContext() {
        return root();
    }

    @Override
    public Element getWrappedElement() {
        return root();
    }

    public Element root() {
        return root;
    }

    public LazyLocator getLocator() {
        return root().getLocator();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return LazySearchContext.super.findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        return LazySearchContext.super.findElement(by);
    }

    @Override
    public boolean refresh() {
        boolean result = root().refresh();
        initElements();
        return result;
    }

    public boolean isDisplayed() {
        return getWrappedElement().isDisplayed();
    }

    public <T extends Element> T getElement(By by, Class<T> clazz) {
        return new ElementHandler<>(clazz, by, root()).getImplementation();
    }

    public <T extends Element> T getElement(Selector selector, Class<T> clazz) {
        return new ElementHandler<>(clazz, selector, root()).getImplementation();
    }
}
