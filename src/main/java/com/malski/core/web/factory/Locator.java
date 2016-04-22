package com.malski.core.web.factory;

import com.malski.core.web.elements.Element;
import com.malski.core.web.elements.ElementImpl;
import com.malski.core.web.elements.ElementsList;
import com.malski.core.web.elements.ElementsListImpl;
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
    private final By by;

    public Locator(SearchContext searchContext, Field field) {
        this(searchContext, new Annotations(field));
    }

    public Locator(SearchContext searchContext, AbstractAnnotations annotations) {
        this.searchContext = searchContext;
        this.by = annotations.buildBy();
    }

    public Locator(SearchContext searchContext, By by) {
        this.searchContext = searchContext;
        this.by = by;
    }

    @Override
    public WebElement findElement() {
        return this.searchContext.findElement(this.by);
    }

    @Override
    public List<WebElement> findElements() {
        return this.searchContext.findElements(this.by);
    }

    @Override
    public Element getElement() {
        return new ElementImpl(this);
    }

    @Override
    public ElementsList<Element> getElements() {
        return new ElementsListImpl<>(this, Element.class);
    }

    @Override
    public By getBy() {
        return by;
    }
}