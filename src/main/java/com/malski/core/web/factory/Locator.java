package com.malski.core.web.factory;

import com.malski.core.web.elements.Element;
import com.malski.core.web.elements.ElementImpl;
import com.malski.core.web.elements.Elements;
import com.malski.core.web.elements.ElementsImpl;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.AbstractAnnotations;
import org.openqa.selenium.support.pagefactory.Annotations;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.Field;
import java.util.List;

public class Locator implements ElementLocator, LazyLocator {
    private final SearchContext searchContext;
    private Selector selector;

    public Locator(SearchContext searchContext, Field field) {
        this(searchContext, new Annotations(field));
    }

    public Locator(SearchContext searchContext, AbstractAnnotations annotations) {
        this.searchContext = searchContext;
        this.selector = new Selector(annotations.buildBy());
    }

    public Locator(SearchContext searchContext, By by) {
        this.searchContext = searchContext;
        this.selector = new Selector(by);
    }

    @Override
    public WebElement findElement() {
        return this.searchContext.findElement(selector.getBy());
    }

    @Override
    public List<WebElement> findElements() {
        return this.searchContext.findElements(selector.getBy());
    }

    @Override
    public <T extends Element> Element getElement(Class<T> clazz) {
        return new ElementImpl(this);
    }

    @Override
    public <T extends Element> Elements<? extends Element> getElements(Class<T> clazz) {
        return new ElementsImpl<>(this, Element.class);
    }

    @Override
    public Selector getSelector() {
        return this.selector;
    }
}