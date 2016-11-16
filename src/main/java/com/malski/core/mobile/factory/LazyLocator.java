package com.malski.core.mobile.factory;

import com.malski.core.mobile.control.LazyMobileContext;
import com.malski.core.mobile.elements.Element;
import com.malski.core.mobile.elements.Elements;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.pagefactory.AbstractAnnotations;

import java.util.List;

public class LazyLocator {
    private final LazyMobileContext searchContext;
    private Selector selector;
    private int index = 0;

    public LazyLocator(LazyMobileContext searchContext, AbstractAnnotations annotations) {
        this.searchContext = searchContext;
        this.selector = new Selector(annotations.buildBy());
    }

    public LazyLocator(LazyMobileContext searchContext, By by) {
        this.searchContext = searchContext;
        this.selector = new Selector(by);
    }

    public LazyLocator(LazyMobileContext searchContext, Selector selector) {
        this.searchContext = searchContext;
        this.selector = selector;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public MobileElement findElement() {
        if (index == 0) {
            return this.searchContext.findElement(getSelector().getBy());
        } else {
            return findElements().get(index);
        }
    }

    public List<MobileElement> findElements() {
        return this.searchContext.findElements(getSelector().getBy());
    }

    @SuppressWarnings("unchecked")
    public <T extends Element> T getElement(Class<T> clazz) {
        return (T) new ElementHandler(clazz, this).getImplementation();
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
        return this.searchContext.refresh();
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