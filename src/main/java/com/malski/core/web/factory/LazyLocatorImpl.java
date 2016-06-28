package com.malski.core.web.factory;

import com.malski.core.web.elements.api.Element;
import com.malski.core.web.elements.api.Elements;
import com.malski.core.web.elements.impl.ElementsImpl;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.AbstractAnnotations;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.List;

public class LazyLocatorImpl implements ElementLocator, LazyLocator {
    private final SearchContext searchContext;
    private Selector selector;

    public LazyLocatorImpl(SearchContext searchContext, AbstractAnnotations annotations) {
        this.searchContext = searchContext;
        this.selector = new Selector(annotations.buildBy());
    }

    public LazyLocatorImpl(SearchContext searchContext, By by) {
        this.searchContext = searchContext;
        this.selector = new Selector(by);
    }

    public LazyLocatorImpl(SearchContext searchContext, Selector selector) {
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
        return new ElementsImpl<>(this, clazz);
    }

    @Override
    public Element getElement() {
        return getElement(Element.class);
    }

    @Override
    public List<? extends Element> getElements() {
        return new ElementsImpl<>(this, Element.class);
    }

    @Override
    public Selector getSelector() {
        return this.selector;
    }
}