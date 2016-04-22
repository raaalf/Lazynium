package com.malski.core.web.page;

import com.malski.core.cucumber.TestContext;
import com.malski.core.web.Browser;
import com.malski.core.web.elements.Element;
import com.malski.core.web.elements.Elements;
import com.malski.core.web.factory.PageElementsFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

public class Frame implements WebComponent{
    protected final Browser browser;
    protected Element rootElement;

    public Frame(By locator) {
        this.browser = TestContext.getBrowser();
        this.rootElement = getBrowser().getElement(locator);
        this.initElements();
    }

    public Frame(Element rootElement) {
        this.browser = TestContext.getBrowser();
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
    public Elements<Element> getElements(By by) {
        return getRoot().getElements(by);
    }

    @Override
    public Elements<Element> $$(String css) {
        return getRoot().$$(css);
    }

    @Override
    public Elements<Element> $$i(String id) {
        return getRoot().$$i(id);
    }

    @Override
    public Elements<Element> $$n(String name) {
        return getRoot().$$n(name);
    }

    @Override
    public Elements<Element> $$x(String xpath) {
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