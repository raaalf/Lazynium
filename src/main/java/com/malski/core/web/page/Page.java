package com.malski.core.web.page;

import com.malski.core.web.Browser;
import com.malski.core.web.elements.Element;
import com.malski.core.web.elements.ElementsList;
import com.malski.core.web.factory.PageElementsFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

/**
 * Class which is representing displayed page and allow to performing basic actions on it
 */
public abstract class Page implements WebComponent {
    private final Browser browser;

    public Page(Browser browser) {
        this.browser = browser;
        this.initElements();
        this.waitToLoad();
    }

    public Page(Browser browser, String url2check) {
        this.browser = browser;
        this.initElements();
        this.waitToLoad();
        this.checkUrl(url2check);
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
        return getBrowser().getElement(by);
    }

    @Override
    public Element $(String css) {
        return this.getElement(By.cssSelector(css));
    }

    @Override
    public Element $i(String id) {
        return this.getElement(By.id(id));
    }

    @Override
    public Element $n(String name) {
        return this.getElement(By.name(name));
    }

    @Override
    public Element $x(String xpath) {
        return this.getElement(By.xpath(xpath));
    }

    @Override
    public ElementsList<Element> getElements(By by) {
        return getBrowser().getElements(by);
    }

    @Override
    public ElementsList<Element> $$(String css) {
        return this.getElements(By.cssSelector(css));
    }

    @Override
    public ElementsList<Element> $$i(String id) {
        return this.getElements(By.id(id));
    }

    @Override
    public ElementsList<Element> $$n(String name) {
        return this.getElements(By.name(name));
    }

    @Override
    public ElementsList<Element> $$x(String xpath) {
        return this.getElements(By.xpath(xpath));
    }

    @Override
    public void initElements() {
        PageElementsFactory.initElements(getBrowser(), this);
    }

    public void checkUrl(String url2Check) {
        browser.getCurrentUrl().matches(url2Check);
    }

    public abstract void waitToLoad();

//    public void waitToLoad() {
//        getWait().until(WaitConditions.pageLoaded());
//    }
//
//    public <T> T cast(Class<T> clazz) {
//        //TODO should return page casted to specific implementation
//    }
}