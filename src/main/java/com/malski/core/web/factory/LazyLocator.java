package com.malski.core.web.factory;

import com.malski.core.web.elements.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.List;

public interface LazyLocator extends ElementLocator {
    By getBy();
    Element getElement();
    List<Element> getElements();
}
