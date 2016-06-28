package com.malski.core.web.elements.api;

import com.malski.core.web.base.LazySearchContext;
import com.malski.core.web.factory.LazyLocator;
import com.malski.core.web.factory.Selector;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;

public interface Element extends WebElement, WrapsElement, Locatable, ElementStates, ElementWait, LazySearchContext {

    LazyLocator getLocator();

    Selector getSelector();

    String getValue();

    String getId();

    String getCssClass();

    void doubleClick();

    void rightClick();

    void dragAndDrop(Element elementToDrop);

    void mouseOver();

    void refresh();

    <T extends Element> T as(Class<T> clazz);
}