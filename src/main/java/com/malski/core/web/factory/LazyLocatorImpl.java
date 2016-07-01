package com.malski.core.web.factory;

import com.malski.core.web.base.LazySearchContext;
import com.malski.core.web.elements.api.Element;
import com.malski.core.web.elements.api.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.AbstractAnnotations;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.List;

public class LazyLocatorImpl implements ElementLocator, LazyLocator {
    private final LazySearchContext searchContext;
    private Selector selector;

    public LazyLocatorImpl(LazySearchContext searchContext, AbstractAnnotations annotations) {
        this.searchContext = searchContext;
        this.selector = new Selector(annotations.buildBy());
    }

    public LazyLocatorImpl(LazySearchContext searchContext, By by) {
        this.searchContext = searchContext;
        this.selector = new Selector(by);
    }

    public LazyLocatorImpl(LazySearchContext searchContext, Selector selector) {
        this.searchContext = searchContext;
        this.selector = selector;
    }

    @Override
    public WebElement findElement() {
        return this.searchContext.findElement(getSelector().getBy());
    }

    @Override
    public List<WebElement> findElements() {
        return this.searchContext.findElements(getSelector().getBy());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Element> T getElement(Class<T> clazz) {
        try {
            return (T) new ElementHandler(clazz, this).getElementImplementation();
        } catch (Throwable throwable) {
            return null;
        }
    }

    @Override
    public <T extends Element> Elements<T> getElements(Class<T> clazz) {
        return new ElementListHandler(clazz, this).getElementImplementation();
    }

    @Override
    public Element getElement() {
        return getElement(Element.class);
    }

    @Override
    public List<? extends Element> getElements() {
        return new ElementListHandler(Element.class, this).getElementImplementation();
    }

    @Override
    public Selector getSelector() {
        return this.selector;
    }

    @Override
    public void refresh() {
        try {
            this.searchContext.refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}