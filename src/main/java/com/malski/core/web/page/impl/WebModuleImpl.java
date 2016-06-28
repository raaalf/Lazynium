package com.malski.core.web.page.impl;

import com.malski.core.cucumber.TestContext;
import com.malski.core.web.base.Browser;
import com.malski.core.web.base.LazySearchContextImpl;
import com.malski.core.web.elements.api.Element;
import com.malski.core.web.factory.LazyLocator;
import com.malski.core.web.factory.LazyPageFactory;
import com.malski.core.web.page.api.WebModule;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

/**
 * Class which is representing displayed module on page and allow to performing basic actions on it
 */
public abstract class WebModuleImpl extends LazySearchContextImpl implements WebModule {
    protected final Logger log = Logger.getLogger(getClass());
    protected Browser browser;
    protected Element rootElement;

    public WebModuleImpl() {
    }

    public WebModuleImpl(LazyLocator locator) {
        this.browser = TestContext.getBrowser();
        this.rootElement = getBrowser().getElement(locator);
        LazyPageFactory.initElements(getSearchContext(), this);
    }

    public WebModuleImpl(By by) {
        this.browser = TestContext.getBrowser();
        this.rootElement = getBrowser().getElement(by);
        LazyPageFactory.initElements(getSearchContext(), this);
    }

    public WebModuleImpl(Element rootElement) {
        this.browser = TestContext.getBrowser();
        this.rootElement = rootElement;
        LazyPageFactory.initElements(getSearchContext(), this);
    }

    @Override
    public SearchContext getSearchContext() {
        if (super.getSearchContext() == null) {
            setSearchContext(getRoot());
        }
        return super.getSearchContext();
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
    public Element getRoot() {
        return rootElement;
    }

    @Override
    public void refresh() {
        getRoot().refresh();
        LazyPageFactory.initElements(getRoot(), this);
    }

    @Override
    public void waitUntilPresent() {
        getBrowser().getWait().until(presenceOfElementLocated(getRoot().getLocator().getSelector().getBy()));
    }

    @Override
    public void waitUntilPresent(long timeout) {
        getBrowser().getWait(timeout).until(presenceOfElementLocated(getRoot().getLocator().getSelector().getBy()));
    }

    @Override
    public void waitUntilVisible() {
        getBrowser().getWait().until(visibilityOfElementLocated(getRoot().getLocator().getSelector().getBy()));
    }

    @Override
    public void waitUntilVisible(long timeout) {
        getBrowser().getWait(timeout).until(visibilityOfElementLocated(getRoot().getLocator().getSelector().getBy()));
    }

    @Override
    public void waitUntilDisappear() {
        getBrowser().getWait().until(invisibilityOfElementLocated(getRoot().getLocator().getSelector().getBy()));
    }

    @Override
    public void waitUntilDisappear(long timeout) {
        getBrowser().getWait(timeout).until(invisibilityOfElementLocated(getRoot().getLocator().getSelector().getBy()));
    }

    @Override
    public void waitUntilEnabled() {
        getBrowser().getWait().until(elementToBeClickable(getRoot().getLocator().getSelector().getBy()));
    }

    @Override
    public void waitUntilEnabled(long timeout) {
        getBrowser().getWait(timeout).until(elementToBeClickable(getRoot().getLocator().getSelector().getBy()));
    }

    @Override
    public void waitUntilDisabled() {
        getBrowser().getWait().until(not(elementToBeClickable(getRoot().getLocator().getSelector().getBy())));
    }

    @Override
    public void waitUntilDisabled(long timeout) {
        getBrowser().getWait(timeout).until(not(elementToBeClickable(getRoot().getLocator().getSelector().getBy())));
    }

    @Override
    public void waitUntilAttributeChange(String attributeName, String expectedValue) {
        getBrowser().getWait().until((ExpectedCondition<Boolean>) driver -> {
                    getRoot().refresh();
                    String enabled = getRoot().getAttribute(attributeName);
                    if (expectedValue == null) {
                        return enabled == null;
                    } else {
                        return enabled.equals(expectedValue);
                    }
                }
        );
    }
}
