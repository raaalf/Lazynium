package com.malski.core.web.view;

import com.malski.core.utils.TestContext;
import com.malski.core.web.control.Browser;
import com.malski.core.web.control.LazySearchContext;
import com.malski.core.web.elements.Element;
import com.malski.core.web.elements.api.ElementStates;
import com.malski.core.web.factory.LazyLocator;
import com.malski.core.web.factory.LazyPageFactory;
import org.apache.log4j.Logger;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

/**
 * Class which is representing displayed module on page and allow to performing basic actions on it
 */
public abstract class Module extends LazySearchContext implements View, ElementStates {
    protected final Logger log = Logger.getLogger(getClass());
    private Element rootElement;

    public Module() {
    }

    public void setRoot(LazyLocator locator) {
        this.rootElement = locator.getElement();
    }

    @Override
    public void initElements() {
        LazyPageFactory.initElements(this);
    }

    @Override
    public Browser getBrowser() {
        return TestContext.getBrowser();
    }

    @Override
    public SearchContext getContext() {
        return getRoot();
    }

    @Override
    public FluentWait<WebDriver> getWait() {
        return getBrowser().getWait();
    }

    public Element getRoot() {
        return rootElement;
    }

    public LazyLocator getLocator() {
        return getRoot().getLocator();
    }

    @Override
    public boolean refresh() {
        boolean result = getRoot().refresh();
        initElements();
        return result;
    }

    public boolean isDisplayed() {
        return getRoot().isDisplayed();
    }

    public boolean isDisplayed(long timeout) {
        return getRoot().isDisplayed(timeout);
    }

    public boolean isVisible() {
        return getRoot().isDisplayed();
    }

    public boolean isVisible(long timeout) {
        return getRoot().isDisplayed(timeout);
    }

    public boolean isPresent() {
        return getRoot().isPresent();
    }

    public boolean isPresent(long timeout) {
        return getRoot().isPresent(timeout);
    }

    public boolean isEnabled() {
        return getRoot().isEnabled();
    }

    public boolean isEnabled(long timeout) {
        return getRoot().isEnabled(timeout);
    }

    public boolean hasFocus() {
        return getRoot().hasFocus();
    }

    public boolean hasFocus(long timeout) {
        return getRoot().hasFocus(timeout);
    }

    public boolean isInViewport() {
        return getRoot().isInViewport();
    }

    public boolean isInViewport(long timeout) {
        return getRoot().isInViewport(timeout);
    }

    public boolean isStaleness() {
        return getRoot().isStaleness();
    }

    public void waitUntilPresent() {
        getRoot().waitUntilPresent();
    }

    public void waitUntilPresent(long timeout) {
        getRoot().waitUntilPresent(timeout);
    }

    public void waitUntilVisible() {
        getRoot().waitUntilVisible();
    }

    public void waitUntilVisible(long timeout) {
        getRoot().waitUntilVisible(timeout);
    }

    public void waitUntilDisappear() {
        getRoot().waitUntilDisappear();
    }

    public void waitUntilDisappear(long timeout) {
        getRoot().waitUntilDisappear(timeout);
    }

    public void waitUntilEnabled() {
        getRoot().waitUntilEnabled();
    }

    public void waitUntilEnabled(long timeout) {
        getRoot().waitUntilEnabled(timeout);
    }

    public void waitUntilDisabled() {
        getRoot().waitUntilDisabled();
    }

    public void waitUntilDisabled(long timeout) {
        getRoot().waitUntilDisabled(timeout);
    }

    public void waitUntilAttributeChange(String attributeName, String expectedValue) {
        getRoot().waitUntilAttributeChange(attributeName, expectedValue);
    }

    public void waitUntilAttributeChange(String attributeName, String expectedValue, long timeout) {
        getRoot().waitUntilAttributeChange(attributeName, expectedValue, timeout);
    }

    public void waitUntilIsInViewport() {
        getRoot().waitUntilIsInViewport();
    }

    public void waitUntilIsInViewport(long timeout) {
        getRoot().waitUntilIsInViewport(timeout);
    }
}
