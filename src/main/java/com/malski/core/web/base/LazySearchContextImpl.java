package com.malski.core.web.base;

import com.malski.core.web.elements.api.Element;
import com.malski.core.web.elements.impl.ElementImpl;
import com.malski.core.web.elements.api.Elements;
import com.malski.core.web.elements.impl.ElementsImpl;
import com.malski.core.web.factory.*;
import com.malski.core.web.page.api.Frame;
import com.malski.core.web.page.api.WebModule;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;

public class LazySearchContextImpl {
    private SearchContext searchContext;

    public LazySearchContextImpl() {
        this.searchContext = null;
    }

    public LazySearchContextImpl(SearchContext searchContext) {
        setSearchContext(searchContext);
    }

    public void setSearchContext(SearchContext searchContext) {
        this.searchContext = searchContext;
    }

    public SearchContext getSearchContext() {
        return searchContext;
    }

    public Element getElement(By by) {
        return new ElementImpl(by, getSearchContext());
    }

    public Element getElement(LazyLocator locator) {
        return new ElementImpl(locator);
    }

    public Element getElement(Selector selector) {
        return new ElementImpl(selector, getSearchContext());
    }

    public Element $(String css) {
        return getElement(By.cssSelector(css));
    }

    public Element $i(String id) {
        return getElement(By.id(id));
    }

    public Element $n(String name) {
        return getElement(By.name(name));
    }

    public Element $x(String xpath) {
        return getElement(By.xpath(xpath));
    }

    public Element $t(String tagName) {
        return getElement(By.tagName(tagName));
    }

    public Element $c(String className) {
        return getElement(By.className(className));
    }

    public Elements<Element> getElements(By by) {
        return new ElementsImpl<>(by, getSearchContext(), Element.class);
    }

    public Elements<Element> getElements(LazyLocator locator) {
        return new ElementsImpl<>(locator, Element.class);
    }

    public Elements<Element> getElements(Selector selector) {
        return new ElementsImpl<>(selector, getSearchContext(), Element.class);
    }

    public Elements<Element> $$(String css) {
        return getElements(By.cssSelector(css));
    }

    public Elements<Element> $$i(String id) {
        return getElements(By.id(id));
    }

    public Elements<Element> $$n(String name) {
        return getElements(By.name(name));
    }

    public Elements<Element> $$x(String xpath) {
        return getElements(By.xpath(xpath));
    }

    public Elements<Element> $$t(String tagName) {
        return getElements(By.tagName(tagName));
    }

    public Elements<Element> $$c(String className) {
        return getElements(By.className(className));
    }

    public <T extends WebModule> T getModule(Class<T> iface, By by) {
        return getModule(iface, new LazyLocatorImpl(getSearchContext(), by));
    }

    public <T extends WebModule> T getModule(Class<T> iface, Selector selector) {
        return getModule(iface, new LazyLocatorImpl(getSearchContext(), selector));
    }

    public <T extends WebModule> T getModule(Class<T> iface, LazyLocator locator) {
        WebModuleHandler handler = new WebModuleHandler(iface, locator);
        try {
            return handler.getWebModuleImplementation();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public <T extends Frame> T getFrame(Class<T> iface, By by) {
        return getFrame(iface, new LazyLocatorImpl(getSearchContext(), by));
    }

    public <T extends Frame> T getFrame(Class<T> iface, Selector selector) {
        return getFrame(iface, new LazyLocatorImpl(getSearchContext(), selector));
    }

    public <T extends Frame> T getFrame(Class<T> iface, LazyLocator locator) {
        FrameHandler handler = new FrameHandler(iface, locator);
        try {
            return handler.getFrameImplementation();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}