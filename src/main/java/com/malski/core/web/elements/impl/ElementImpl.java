package com.malski.core.web.elements.impl;

import com.malski.core.web.base.LazySearchContextImpl;
import com.malski.core.web.elements.api.Element;
import com.malski.core.web.factory.ElementHandler;
import com.malski.core.web.factory.LazyLocator;
import com.malski.core.web.factory.LazyLocatorImpl;
import com.malski.core.web.factory.Selector;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.List;

import static com.malski.core.cucumber.TestContext.getBrowser;

public class ElementImpl extends LazySearchContextImpl implements Element {

    private WebElement element;
    private LazyLocator locator;

    public ElementImpl(By by, SearchContext context) {
        this.locator = new LazyLocatorImpl(context, by);
    }

    public ElementImpl(Selector selector, SearchContext context) {
        this.locator = new LazyLocatorImpl(context, selector);
    }

    public ElementImpl(LazyLocator locator) {
        this.locator = locator;
    }

    public ElementImpl(LazyLocator locator, WebElement element) {
        this.locator = locator;
        this.element = element;
    }

    public ElementImpl(By by, WebElement element) {
        this.locator = new LazyLocatorImpl(element, by);
        this.element = element;
    }

    @Override
    public SearchContext getSearchContext() {
        if (super.getSearchContext() == null) {
            setSearchContext(getWrappedElement());
        }
        return super.getSearchContext();
    }

    @Override
    public void click() {
        getWrappedElement().click();
        getBrowser().waitForPageToLoad();
    }

    @Override
    public void doubleClick() {
        getBrowser().getActions()
                .doubleClick(getWrappedElement())
                .perform();
    }

    @Override
    public void rightClick() {
        getBrowser().getActions()
                .contextClick(getWrappedElement())
                .perform();
    }

    @Override
    public void dragAndDrop(Element elementToDrop) {
        getBrowser().getActions()
                .clickAndHold(getWrappedElement())
                .moveToElement(elementToDrop.getWrappedElement())
                .release(elementToDrop.getWrappedElement())
                .build()
                .perform();
//        getBrowser().getActions().dragAndDrop(this, elementToDrop).perform();
    }

    @Override
    public void mouseOver() {
        getBrowser().getActions()
                .moveToElement(getWrappedElement())
                .perform();
    }

    @Override
    public void submit() {
        getWrappedElement().submit();
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        getWrappedElement().sendKeys(keysToSend);
    }

    @Override
    public void clear() {
        getWrappedElement().clear();
    }

    @Override
    public String getTagName() {
        return getWrappedElement().getTagName();
    }

    @Override
    public String getAttribute(String s) {
        return getWrappedElement().getAttribute(s);
    }

    @Override
    public boolean isSelected() {
        return getWrappedElement().isSelected();
    }

    @Override
    public boolean isDisplayed() {
        return getWrappedElement().isDisplayed();
    }

    @Override
    public boolean isDisplayed(long timeout) {
        try {
            waitUntilDisabled(timeout);
        } catch (Exception ignore) {
            return false;
        }
        return getWrappedElement().isDisplayed();
    }

    @Override
    public boolean isVisible() {
        return getWrappedElement().isDisplayed();
    }

    @Override
    public boolean isVisible(long timeout) {
        try {
            waitUntilVisible(timeout);
        } catch (Exception ignore) {
            return false;
        }
        return getWrappedElement().isDisplayed();
    }

    @Override
    public boolean isPresent() {
        try {
            refresh();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Override
    public boolean isPresent(long timeout) {
        try {
            waitUntilPresent(timeout);
        } catch (Exception ignore) {
            return false;
        }
        return isPresent();
    }

    @Override
    public boolean isEnabled() {
        return getWrappedElement().isEnabled();
    }

    @Override
    public boolean isEnabled(long timeout) {
        try {
            waitUntilEnabled(timeout);
        } catch (Exception ignore) {
            return false;
        }
        return getWrappedElement().isEnabled();
    }

    @Override
    public boolean hasFocus() {
        return getWrappedElement().equals(getBrowser().switchTo().activeElement());
    }

    @Override
    public boolean hasFocus(long timeout) {
        try {
            waitUntilVisible(timeout);
        } catch (Exception ignore) {
            return false;
        }
        return getWrappedElement().equals(getBrowser().switchTo().activeElement());
    }

    @Override
    public String getText() {
        return getWrappedElement().getText();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return getWrappedElement().findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        return getWrappedElement().findElement(by);
    }

    @Override
    public Point getLocation() {
        return getWrappedElement().getLocation();
    }

    @Override
    public Dimension getSize() {
        return getWrappedElement().getSize();
    }

    @Override
    public Rectangle getRect() {
        return getWrappedElement().getRect();
    }

    @Override
    public String getCssValue(String s) {
        return getWrappedElement().getCssValue(s);
    }

//    @Override
//    public By getBy() {
//        return getLocator().getBy();
//    }

    @Override
    public Selector getSelector() {
        return getLocator().getSelector();
    }

    @Override
    public Coordinates getCoordinates() {
        return ((Locatable) getWrappedElement()).getCoordinates();
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> outputType) throws WebDriverException {
        return getWrappedElement().getScreenshotAs(outputType);
    }

    @Override
    public WebElement getWrappedElement() {
        if (element == null) {
            refresh();
        }
        return element;
    }

    @Override
    public String getValue() {
        return this.getAttribute("value");
    }

    @Override
    public String getId() {
        return this.getAttribute("id");
    }

    @Override
    public String getCssClass() {
        return this.getAttribute("class");
    }

    @Override
    public <T extends Element> T as(Class<T> iface) {
        try {
            return new ElementHandler(iface, getLocator()).getElementImplementation();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public LazyLocator getLocator() {
        return this.locator;
    }

    @Override
    public void refresh() {
        element = getLocator().findElement();
    }

    @Override
    public void waitUntilPresent() {
        getBrowser().waitUntilPresent(getLocator().getSelector().getBy());
    }

    @Override
    public void waitUntilPresent(long timeout) {
        getBrowser().waitUntilPresent(getLocator().getSelector().getBy(), timeout);
    }

    @Override
    public void waitUntilVisible() {
        getBrowser().waitUntilVisible(getLocator().getSelector().getBy());
    }

    @Override
    public void waitUntilVisible(long timeout) {
        getBrowser().waitUntilVisible(getLocator().getSelector().getBy(), timeout);
    }

    @Override
    public void waitUntilDisappear() {
        getBrowser().waitUntilDisappear(getLocator().getSelector().getBy());
    }

    @Override
    public void waitUntilDisappear(long timeout) {
        getBrowser().waitUntilDisappear(getLocator().getSelector().getBy(), timeout);
    }

    @Override
    public void waitUntilEnabled() {
        getBrowser().waitUntilEnabled(getLocator().getSelector().getBy());
    }

    @Override
    public void waitUntilEnabled(long timeout) {
        getBrowser().waitUntilEnabled(getLocator().getSelector().getBy(), timeout);
    }

    @Override
    public void waitUntilDisabled() {
        getBrowser().waitUntilDisabled(getLocator().getSelector().getBy());
    }

    @Override
    public void waitUntilDisabled(long timeout) {
        getBrowser().waitUntilDisabled(getLocator().getSelector().getBy(), timeout);
    }

    @Override
    public void waitUntilAttributeChange(String attributeName, String expectedValue) {
        getBrowser().getWait().until((ExpectedCondition<Boolean>) driver -> {
                    refresh();
                    String enabled = getAttribute(attributeName);
                    if (expectedValue == null) {
                        return enabled == null;
                    } else {
                        return enabled.equals(expectedValue);
                    }
                }
        );
    }
}