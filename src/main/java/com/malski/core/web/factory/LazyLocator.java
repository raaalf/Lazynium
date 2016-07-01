package com.malski.core.web.factory;

import com.malski.core.web.elements.api.Element;
import com.malski.core.web.elements.api.Elements;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.List;

public interface LazyLocator extends ElementLocator {
    Selector getSelector();

    <T extends Element> T getElement(Class<T> clazz);

    <T extends Element> Elements<T> getElements(Class<T> clazz);

    Element getElement();

    List<? extends Element> getElements();

    void refresh();
}
