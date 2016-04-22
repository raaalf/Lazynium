package com.malski.core.web.elements;

import com.malski.core.web.factory.LazyLocator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;

public interface Element extends WebElement, WrapsElement, Locatable {
    By getBy();

    LazyLocator getLocator();

    Element getElement(By by);

    Element $(String css);

    Element $i(String id);

    Element $n(String name);

    Element $x(String xpath);

    ElementsList<Element> getElements(By by);

    ElementsList<Element> $$(String css);

    ElementsList<Element> $$i(String id);

    ElementsList<Element> $$n(String name);

    ElementsList<Element> $$x(String xpath);

    String getValue();

    String getId();

    String getCssClass();

    void refresh();

    <T extends Element> T as(Class<T> clazz);
}