package com.malski.core.web.control;

import com.malski.core.web.elements.Element;
import com.malski.core.web.elements.Elements;
import com.malski.core.web.factory.*;
import com.malski.core.web.view.Frame;
import com.malski.core.web.view.Module;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import java.util.List;

public abstract class LazySearchContext implements SearchContext {

    public LazySearchContext() {
    }

    public abstract SearchContext getContext();

    public Element getElement(By by) {
        return getElement(by, Element.class);
    }

    public <T extends Element> T getElement(By by, Class<T> clazz) {
        return new ElementHandler<>(clazz, by, this).getImplementation();
    }

    public Element getElement(LazyLocator locator) {
        return getElement(locator, Element.class);
    }

    public <T extends Element> T getElement(LazyLocator locator, Class<T> clazz) {
        return new ElementHandler<>(clazz, locator).getImplementation();
    }

    public Element getElement(Selector selector) {
        return getElement(selector, Element.class);
    }

    public <T extends Element> T getElement(Selector selector, Class<T> clazz) {
        return new ElementHandler<>(clazz, selector, this).getImplementation();
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
        return new Elements<>();
    }

    public Elements<Element> getElements(By by) {
        return getElements(by, Element.class);
    }

    public <T extends Element> Elements<T> getElements(By by, Class<T> clazz) {
        return new ElementListHandler<>(clazz, by, this).getImplementation();
    }

    public Elements<Element> getElements(LazyLocator locator) {
        return getElements(locator, Element.class);
    }

    public <T extends Element> Elements<T> getElements(LazyLocator locator, Class<T> clazz) {
        return new ElementListHandler<>(clazz, locator).getImplementation();
    }

    public Elements<Element> getElements(Selector selector) {
        return getElements(selector, Element.class);
    }

    public <T extends Element> Elements<T> getElements(Selector selector, Class<T> clazz) {
        return new ElementListHandler<>(clazz, selector, this).getImplementation();
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

    @Override
    public List<WebElement> findElements(By by) {
        try {
            return getContext().findElements(by);
        } catch (StaleElementReferenceException ignore) {
            refresh();
            return getContext().findElements(by);
        }
    }

    @Override
    public WebElement findElement(By by) {
        try {
            return getContext().findElement(by);
        } catch (StaleElementReferenceException ignore) {
            refresh();
            return getContext().findElement(by);
        }
    }

    public <T extends Module> T getModule(Class<T> iface) {
        return getModule(iface, new LazyLocator(this, new LazyAnnotations(iface)));
    }

    public <T extends Module> T getModule(Class<T> iface, By by) {
        return getModule(iface, new LazyLocator(this, by));
    }

    public <T extends Module> T getModule(Class<T> iface, Selector selector) {
        return getModule(iface, new LazyLocator(this, selector));
    }

    public <T extends Module> T getModule(Class<T> iface, LazyLocator locator) {
        ModuleHandler<T> handler = new ModuleHandler<>(iface, locator);
        try {
            return handler.getImplementation();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public <T extends Frame> T getFrame(Class<T> iface) {
        return getFrame(iface, new LazyLocator(this, new LazyAnnotations(iface)));
    }

    public <T extends Frame> T getFrame(Class<T> iface, By by) {
        return getFrame(iface, new LazyLocator(this, by));
    }

    public <T extends Frame> T getFrame(Class<T> iface, Selector selector) {
        return getFrame(iface, new LazyLocator(this, selector));
    }

    public <T extends Frame> T getFrame(Class<T> iface, LazyLocator locator) {
        FrameHandler<T> handler = new FrameHandler<>(iface, locator);
        try {
            return handler.getImplementation();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public abstract boolean refresh();
}
