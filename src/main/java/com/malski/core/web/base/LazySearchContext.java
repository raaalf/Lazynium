package com.malski.core.web.base;

import com.malski.core.web.elements.api.Element;
import com.malski.core.web.elements.api.Elements;
import com.malski.core.web.factory.LazyLocator;
import com.malski.core.web.factory.Selector;
import com.malski.core.web.page.api.Frame;
import com.malski.core.web.page.api.WebModule;
import org.openqa.selenium.By;

public interface LazySearchContext {
    Element getElement(By by);

    Element getElement(LazyLocator locator);

    Element getElement(Selector selector);

    Element $(String css);

    Element $i(String id);

    Element $n(String name);

    Element $x(String xpath);

    Element $t(String tagName);

    Element $c(String className);

    Elements<Element> getElements(By by);

    Elements<Element> getElements(LazyLocator locator);

    Elements<Element> getElements(Selector selector);

    Elements<Element> $$(String css);

    Elements<Element> $$i(String id);

    Elements<Element> $$n(String name);

    Elements<Element> $$x(String xpath);

    Elements<Element> $$t(String tagName);

    Elements<Element> $$c(String className);

    <T extends WebModule> T getModule(Class<T> iface, By by);

    <T extends WebModule> T getModule(Class<T> iface, Selector selector);

    <T extends WebModule> T getModule(Class<T> iface, LazyLocator locator);

    <T extends Frame> T getFrame(Class<T> iface, By by);

    <T extends Frame> T getFrame(Class<T> iface, Selector selector);

    <T extends Frame> T getFrame(Class<T> iface, LazyLocator locator);
}