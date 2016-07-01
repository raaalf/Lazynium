package com.malski.core.web.base;

import com.malski.core.web.elements.api.Element;
import com.malski.core.web.elements.api.Elements;
import com.malski.core.web.factory.LazyLocator;
import com.malski.core.web.factory.Selector;
import com.malski.core.web.page.api.Frame;
import com.malski.core.web.page.api.WebModule;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;

public interface LazySearchContext extends SearchContext {
    Element getElement(By by);

    <T extends Element> T getElement(By by, Class<T> clazz);

    Element getElement(LazyLocator locator);

    <T extends Element> T getElement(LazyLocator locator, Class<T> clazz);

    Element getElement(Selector selector);

    <T extends Element> T getElement(Selector selector, Class<T> clazz);

    Element $(String css);

    <T extends Element> T $(String css, Class<T> clazz);

    Element $i(String id);

    <T extends Element> T $i(String id, Class<T> clazz);

    Element $n(String name);

    <T extends Element> T $n(String name, Class<T> clazz);

    Element $x(String xpath);

    <T extends Element> T $x(String xpath, Class<T> clazz);

    Element $t(String tagName);

    <T extends Element> T $t(String tagName, Class<T> clazz);

    Element $c(String className);

    <T extends Element> T $c(String className, Class<T> clazz);

    <T extends Element> Elements<T> getEmptyElementsList();

    Elements<Element> getElements(By by);

    <T extends Element> Elements<T> getElements(By by, Class<T> clazz);

    Elements<Element> getElements(LazyLocator locator);

    <T extends Element> Elements<T> getElements(LazyLocator locator, Class<T> clazz);

    Elements<Element> getElements(Selector selector);

    <T extends Element> Elements<T> getElements(Selector selector, Class<T> clazz);

    Elements<Element> $$(String css);

    <T extends Element> Elements<T> $$(String css, Class<T> clazz);

    Elements<Element> $$i(String id);

    <T extends Element> Elements<T> $$i(String id, Class<T> clazz);

    Elements<Element> $$n(String name);

    <T extends Element> Elements<T> $$n(String name, Class<T> clazz);

    Elements<Element> $$x(String xpath);

    <T extends Element> Elements<T> $$x(String xpath, Class<T> clazz);

    Elements<Element> $$t(String tagName);

    <T extends Element> Elements<T> $$t(String tagName, Class<T> clazz);

    Elements<Element> $$c(String className);

    <T extends Element> Elements<T> $$c(String className, Class<T> clazz);

    <T extends WebModule> T getModule(Class<T> iface, By by);

    <T extends WebModule> T getModule(Class<T> iface, Selector selector);

    <T extends WebModule> T getModule(Class<T> iface, LazyLocator locator);

    <T extends Frame> T getFrame(Class<T> iface, By by);

    <T extends Frame> T getFrame(Class<T> iface, Selector selector);

    <T extends Frame> T getFrame(Class<T> iface, LazyLocator locator);

    void refresh();
}