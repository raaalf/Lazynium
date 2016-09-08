package com.malski.core.web.factory;

import com.malski.core.web.control.LazySearchContext;
import com.malski.core.web.elements.Element;
import com.malski.core.web.elements.Elements;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.AbstractAnnotations;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.List;

public class LazyLocator implements ElementLocator {
    private static Logger log = Logger.getLogger(LazyLocator.class);
    private final LazySearchContext searchContext;
    private Selector selector;
    private int index = 0;

    public LazyLocator(LazySearchContext searchContext, AbstractAnnotations annotations) {
        this.searchContext = searchContext;
        this.selector = new Selector(annotations.buildBy());
    }

    public LazyLocator(LazySearchContext searchContext, By by) {
        this.searchContext = searchContext;
        this.selector = new Selector(by);
    }

    public LazyLocator(LazySearchContext searchContext, Selector selector) {
        this.searchContext = searchContext;
        this.selector = selector;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public WebElement findElement() {
        if (index == 0) {
            return this.searchContext.findElement(getSelector().getBy());
        } else {
            return findElements().get(index);
        }
    }

    @Override
    public List<WebElement> findElements() {
        return this.searchContext.findElements(getSelector().getBy());
    }

    @SuppressWarnings("unchecked")
    public <T extends Element> T getElement(Class<T> clazz) {
        try {
            return (T) new ElementHandler(clazz, this).getImplementation();
        } catch (Throwable throwable) {
            return null;
        }
    }

    public <T extends Element> Elements<T> getElements(Class<T> clazz) {
        return new ElementListHandler<>(clazz, this).getImplementation();
    }

    public Element getElement() {
        return getElement(Element.class);
    }

    public List<? extends Element> getElements() {
        return new ElementListHandler<>(Element.class, this).getImplementation();
    }

    public Selector getSelector() {
        return this.selector;
    }

    public boolean refresh() {
        try {
            return this.searchContext.refresh();
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    public LazyLocator duplicate(int index) {
        LazyLocator clone = new LazyLocator(searchContext, selector);
        clone.setIndex(index);
        return clone;
    }

    @Override
    public String toString() {
        return selector == null ? "null" : "Located by: " + selector.toString();
    }
}