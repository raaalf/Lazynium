package com.malski.core.web.base;

import com.malski.core.web.elements.api.Element;
import com.malski.core.web.elements.api.Elements;
import com.malski.core.web.factory.*;
import com.malski.core.web.page.api.Frame;
import com.malski.core.web.page.api.WebModule;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import java.util.List;

public abstract class LazySearchContextImpl implements LazySearchContext {
    private SearchContext searchContext;

    public LazySearchContextImpl() {
        this.searchContext = null;
    }

    public LazySearchContextImpl(WebElement webElement) {
        this.searchContext = webElement;
    }

    public void setSearchContext(SearchContext searchContext) {
        this.searchContext = searchContext;
    }

    public SearchContext getSearchContext() {
        return searchContext;
    }

    public Element getElement(By by) {
        return getElement(by, Element.class);
    }

    public <T extends Element> T getElement(By by, Class<T> clazz) {
        return new ElementHandler(clazz, by, this).getElementImplementation();
    }

    public Element getElement(LazyLocator locator) {
        return getElement(locator, Element.class);
    }

    public <T extends Element> T getElement(LazyLocator locator, Class<T> clazz) {
        return new ElementHandler(clazz, locator).getElementImplementation();
    }

    public Element getElement(Selector selector) {
        return getElement(selector, Element.class);
    }

    public <T extends Element> T getElement(Selector selector, Class<T> clazz) {
        return new ElementHandler(clazz, selector, this).getElementImplementation();
    }

    public Element $(String css) {
        return getElement(By.cssSelector(css));
    }

    public <T extends Element> T $(String css, Class<T> clazz) {
        return getElement(By.cssSelector(css), clazz);
    }

    public Element $i(String id) {
        return getElement(By.id(id));
    }

    public <T extends Element> T $i(String id, Class<T> clazz) {
        return getElement(By.id(id), clazz);
    }

    public Element $n(String name) {
        return getElement(By.name(name));
    }

    public <T extends Element> T $n(String name, Class<T> clazz) {
        return getElement(By.name(name), clazz);
    }

    public Element $x(String xpath) {
        return getElement(By.xpath(xpath));
    }

    public <T extends Element> T $x(String xpath, Class<T> clazz) {
        return getElement(By.xpath(xpath), clazz);
    }

    public Element $t(String tagName) {
        return getElement(By.tagName(tagName));
    }

    public <T extends Element> T $t(String tagName, Class<T> clazz) {
        return getElement(By.tagName(tagName), clazz);
    }

    public Element $c(String className) {
        return getElement(By.className(className));
    }

    public <T extends Element> T $c(String className, Class<T> clazz) {
        return getElement(By.className(className), clazz);
    }

    public <T extends Element> Elements<T> getEmptyElementsList() {
        return ElementListHandler.getEmptyList();
    }

    public Elements<Element> getElements(By by) {
        return getElements(by, Element.class);
    }

    public <T extends Element> Elements<T> getElements(By by, Class<T> clazz) {
        return new ElementListHandler(clazz, by, this).getElementImplementation();
    }

    public Elements<Element> getElements(LazyLocator locator) {
        return getElements(locator, Element.class);
    }

    public <T extends Element> Elements<T> getElements(LazyLocator locator, Class<T> clazz) {
        return new ElementListHandler(clazz, locator).getElementImplementation();
    }

    public Elements<Element> getElements(Selector selector) {
        return getElements(selector, Element.class);
    }

    public <T extends Element> Elements<T> getElements(Selector selector, Class<T> clazz) {
        return new ElementListHandler(clazz, selector, this).getElementImplementation();
    }

    public Elements<Element> $$(String css) {
        return getElements(By.cssSelector(css));
    }

    public <T extends Element> Elements<T> $$(String css, Class<T> clazz) {
        return getElements(By.cssSelector(css), clazz);
    }

    public Elements<Element> $$i(String id) {
        return getElements(By.id(id));
    }

    public <T extends Element> Elements<T> $$i(String id, Class<T> clazz) {
        return getElements(By.id(id), clazz);
    }

    public Elements<Element> $$n(String name) {
        return getElements(By.name(name));
    }

    public <T extends Element> Elements<T> $$n(String name, Class<T> clazz) {
        return getElements(By.name(name), clazz);
    }

    public Elements<Element> $$x(String xpath) {
        return getElements(By.xpath(xpath));
    }

    public <T extends Element> Elements<T> $$x(String xpath, Class<T> clazz) {
        return getElements(By.xpath(xpath), clazz);
    }

    public Elements<Element> $$t(String tagName) {
        return getElements(By.tagName(tagName));
    }

    public <T extends Element> Elements<T> $$t(String tagName, Class<T> clazz) {
        return getElements(By.tagName(tagName), clazz);
    }

    public Elements<Element> $$c(String className) {
        return getElements(By.className(className));
    }

    public <T extends Element> Elements<T> $$c(String className, Class<T> clazz) {
        return getElements(By.className(className), clazz);
    }

    public List<WebElement> findElements(By by) {
        try {
            return getSearchContext().findElements(by);
        } catch (StaleElementReferenceException ignore) {
            refresh();
            return getSearchContext().findElements(by);
        }
    }

    public WebElement findElement(By by) {
        try {
            return getSearchContext().findElement(by);
        } catch (StaleElementReferenceException ignore) {
            refresh();
            return getSearchContext().findElement(by);
        }
    }

    public <T extends WebModule> T getModule(Class<T> iface, By by) {
        return getModule(iface, new LazyLocatorImpl(this, by));
    }

    public <T extends WebModule> T getModule(Class<T> iface, Selector selector) {
        return getModule(iface, new LazyLocatorImpl(this, selector));
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
        return getFrame(iface, new LazyLocatorImpl(this, by));
    }

    public <T extends Frame> T getFrame(Class<T> iface, Selector selector) {
        return getFrame(iface, new LazyLocatorImpl(this, selector));
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