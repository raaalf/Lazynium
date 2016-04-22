package com.malski.core.web.elements;

import com.malski.core.web.factory.LazyLocator;
import com.malski.core.web.factory.Selector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;

public interface Element extends WebElement, WrapsElement, Locatable, ElementStates, ElementWait {
    // By getBy();

    LazyLocator getLocator();

    Selector getSelector();

    Element getElement(By by);

    Element $(String css);

    Element $i(String id);

    Element $n(String name);

    Element $x(String xpath);

    Elements<Element> getElements(By by);

    Elements<Element> $$(String css);

    Elements<Element> $$i(String id);

    Elements<Element> $$n(String name);

    Elements<Element> $$x(String xpath);

    String getValue();

    String getId();

    String getCssClass();

    void refresh();

    <T extends Element> T as(Class<T> clazz);
}