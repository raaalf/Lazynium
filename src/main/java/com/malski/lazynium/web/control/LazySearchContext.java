package com.malski.lazynium.web.control;

import com.malski.lazynium.web.elements.Element;
import com.malski.lazynium.web.elements.LazyList;
import com.malski.lazynium.web.factory.*;
import com.malski.lazynium.web.view.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import java.util.List;

public interface LazySearchContext extends SearchContext {

    default ElementHandler<? extends Element> elementHandler() { //TODO each SearchContext should have
        return ;
    }

    SearchContext searchContext();

    LazySearchContext getFrameContext();

    void setFrameContext(LazySearchContext lazySearchContext);

    boolean isAngular();

    boolean setIsAngular(boolean isAngular);

    default Element $e(By by) {
        return $e(by, Element.class);
    }

    default <T extends Element> T $e(By by, Class<T> clazz) {
        return new ElementHandler<>(clazz, by, this).getImplementation();
    }

    default Element $e(LazyLocator locator) {
        return $e(locator, Element.class);
    }

    default <T extends Element> T $e(LazyLocator locator, Class<T> clazz) {
        return new ElementHandler<>(clazz, locator).getImplementation();
    }

    default Element $e(Selector selector) {
        return $e(selector, Element.class);
    }

    default <T extends Element> T $e(Selector selector, Class<T> clazz) {
        return new ElementHandler<>(clazz, selector, this).getImplementation();
    }

    default Element $(String css) {
        return $e(By.cssSelector(css));
    }

    default <T extends Element> T $(String css, Class<T> clazz) {
        return $e(By.cssSelector(css), clazz);
    }

    default Element $i(String id) {
        return $e(By.id(id));
    }

    default <T extends Element> T $i(String id, Class<T> clazz) {
        return $e(By.id(id), clazz);
    }

    default Element $x(String xpath) {
        return $e(By.xpath(xpath));
    }

    default <T extends Element> T $x(String xpath, Class<T> clazz) {
        return $e(By.xpath(xpath), clazz);
    }

    default LazyList<Element> $$e(By by) {
        return $$e(by, Element.class);
    }

    default <T extends Element> LazyList<T> $$e(By by, Class<T> clazz) {
        return new ElementListHandler<>(clazz, by, this).getImplementation();
    }

    default LazyList<Element> $$e(LazyLocator locator) {
        return $$e(locator, Element.class);
    }

    default <T extends Element> LazyList<T> $$e(LazyLocator locator, Class<T> clazz) {
        return new ElementListHandler<>(clazz, locator).getImplementation();
    }

    default LazyList<Element> $$e(Selector selector) {
        return $$e(selector, Element.class);
    }

    default <T extends Element> LazyList<T> $$e(Selector selector, Class<T> clazz) {
        return new ElementListHandler<>(clazz, selector, this).getImplementation();
    }

    default LazyList<Element> $$(String css) {
        return $$e(By.cssSelector(css));
    }

    default <T extends Element> LazyList<T> $$(String css, Class<T> clazz) {
        return $$e(By.cssSelector(css), clazz);
    }

    default LazyList<Element> $$i(String id) {
        return $$e(By.id(id));
    }

    default <T extends Element> LazyList<T> $$i(String id, Class<T> clazz) {
        return $$e(By.id(id), clazz);
    }

    default LazyList<Element> $$x(String xpath) {
        return $$e(By.xpath(xpath));
    }

    default <T extends Element> LazyList<T> $$x(String xpath, Class<T> clazz) {
        return $$e(By.xpath(xpath), clazz);
    }

    @Override
    default List<WebElement> findElements(By by) {
        try {
            return searchContext().findElements(by);
        } catch (StaleElementReferenceException ignore) {
            refresh();
            return searchContext().findElements(by);
        }
    }

    @Override
    default WebElement findElement(By by) {
        try {
            return searchContext().findElement(by);
        } catch (StaleElementReferenceException ignore) {
            refresh();
            return searchContext().findElement(by);
        }
    }

    default <T extends Component> T $c(Class<T> iface) {
        return $c(iface, new LazyLocator(this, new LazyAnnotations(iface)));
    }

    default <T extends Component> T $c(Class<T> iface, By by) {
        return $c(iface, new LazyLocator(this, by));
    }

    default <T extends Component> T $c(Class<T> iface, Selector selector) {
        return $c(iface, new LazyLocator(this, selector));
    }

    default <T extends Component> T $c(Class<T> iface, LazyLocator locator) {
        ComponentHandler<T> handler = new ComponentHandler<>(iface, locator);
        try {
            return handler.getImplementation();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    boolean refresh();
}
