package com.malski.core.web.page;

import com.malski.core.web.Browser;
import com.malski.core.web.elements.Element;
import com.malski.core.web.elements.ElementsList;
import com.malski.core.web.factory.PageElementsFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

/**
 * Class which is representing displayed module on page and allow to performing basic actions on it
 */
public abstract class Module implements WebComponent{
    protected final Browser browser;
    protected Element rootElement;

    public Module(Browser browser, Element rootElement) {
        this.browser = browser;
        this.rootElement = rootElement;
        this.initElements();
    }

    @Override
    public Browser getBrowser() {
        return this.browser;
    }

    @Override
    public FluentWait<WebDriver> getWait() {
        return getBrowser().getWait();
    }

    @Override
    public Element getElement(By by) {
        return getRoot().getElement(by);
    }

    @Override
    public Element $(String css) {
        return getRoot().$(css);
    }

    @Override
    public Element $i(String id) {
        return getRoot().$i(id);
    }

    @Override
    public Element $n(String name) {
        return getRoot().$n(name);
    }

    @Override
    public Element $x(String xpath) {
        return getRoot().$x(xpath);
    }

    @Override
    public ElementsList<Element> getElements(By by) {
        return getRoot().getElements(by);
    }

    @Override
    public ElementsList<Element> $$(String css) {
        return getRoot().$$(css);
    }

    @Override
    public ElementsList<Element> $$i(String id) {
        return getRoot().$$i(id);
    }

    @Override
    public ElementsList<Element> $$n(String name) {
        return getRoot().$$n(name);
    }

    @Override
    public ElementsList<Element> $$x(String xpath) {
        return getRoot().$$x(xpath);
    }

    @Override
    public void initElements() {
        PageElementsFactory.initElements(getRoot(), this);
    }

    public Element getRoot() {
        return rootElement;
    }
}
