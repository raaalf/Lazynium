package com.malski.core.web.factory;

import com.malski.core.web.elements.Element;
import com.malski.core.web.elements.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.List;

public interface LazyLocator extends ElementLocator {
    Selector getSelector();
    <T extends Element> Element getElement(Class<T> clazz);
    <T extends Element> Elements<? extends Element> getElements(Class<T> clazz);
    Element getElement();
    List<? extends Element> getElements();

}
