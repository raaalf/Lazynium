package com.malski.core.web.elements;

import com.malski.core.web.conditions.WaitConditions;
import com.malski.core.web.control.LazySearchContext;
import com.malski.core.web.elements.api.ElementStates;
import com.malski.core.web.elements.api.ElementWait;
import com.malski.core.web.factory.ElementHandler;
import com.malski.core.web.factory.LazyLocator;
import com.malski.core.web.factory.Selector;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static com.malski.core.utils.TestContext.getBrowser;
import static com.malski.core.utils.TestContext.getConfig;

public class Element extends LazySearchContext implements WebElement, WrapsElement, Locatable, ElementStates, ElementWait {

    private WebElement element;
    private LazyLocator locator;

    public Element(LazyLocator locator) {
        this.locator = locator;
    }

    public Element(LazyLocator locator, WebElement element) {
        this.locator = locator;
        this.element = element;
    }

    @Override
    public List<WebElement> simpleFindElements(By by) {
        return getWrappedElement().findElements(by);
    }

    @Override
    public WebElement simpleFindElement(By by) {
        return getWrappedElement().findElement(by);
    }

    @Override
    public void click() {
        waitUntilIsClickable(getConfig().getTimeout());
        getWrappedElement().click();
        getBrowser().waitForPageToLoad();
    }

    public void doubleClick() {
        WebElement thisElem = getWrappedElement();
        getBrowser().getActions()
                .doubleClick(thisElem)
                .perform();
    }

    public void rightClick() {
        WebElement thisElem = getWrappedElement();
        getBrowser().getActions()
                .contextClick(thisElem)
                .perform();
    }

    public void dragAndDrop(Element elementToDrop) {
        WebElement thisElem = getWrappedElement();
        WebElement toDropElem = elementToDrop.getWrappedElement();
        getBrowser().getActions()
                .clickAndHold(thisElem)
                .moveToElement(toDropElem)
                .release(toDropElem)
                .build()
                .perform();
//        getBrowser().getActions().dragAndDrop(this, elementToDrop).perform();
    }

    public void mouseOver() {
        WebElement thisElem = getWrappedElement();
        getBrowser().getActions()
                .moveToElement(thisElem)
                .perform();
    }

    public void scrollIntoView() {
        if (!isInViewport()) {
            getBrowser().waitForPageToLoad();
            getBrowser().getJsExecutor().scrollIntoView(getWrappedElement());
            getBrowser().waitForPageToLoad();
        }
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
    public boolean isInViewport() {
        Dimension elemDim = getWrappedElement().getSize();
        Point point = getWrappedElement().getLocation();

        int elemY = elemDim.getHeight() + point.getY();
        long browserHeight = getBrowser().getJsExecutor().getJsClientHeight();
        long scrollHeight = getBrowser().getJsExecutor().getScrollHeight();

        return elemY >= scrollHeight && elemY <= scrollHeight + browserHeight;
    }

    @Override
    public boolean isInViewport(long timeout) {
        try {
            waitUntilIsInViewport(timeout);
        } catch (Exception ignore) {
            return false;
        }
        return isInViewport();
    }

    @Override
    public String getText() {
        return getWrappedElement().getText();
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
        if (isStaleness()) {
            refresh();
        }
        return element;
    }

    @Override
    public boolean isStaleness() {
        if (element == null) {
            setWebElement();
        }
        Boolean staleness = ExpectedConditions.stalenessOf(element).apply(getBrowser().getWebDriver());
        return staleness != null && staleness;
    }

    @Override
    public boolean refresh() {
        boolean result = getLocator().refresh();
        setWebElement();
        return result;
    }

    private void setWebElement() {
        element = getLocator().findElement();
    }

    public String getValue() {
        return this.getAttribute("value");
    }

    public String getId() {
        return this.getAttribute("id");
    }

    public String getCssClass() {
        return this.getAttribute("class");
    }

    public <T extends Element> T as(Class<T> iface) {
        try {
            return new ElementHandler<>(iface, getLocator()).getImplementation();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public LazyLocator getLocator() {
        return this.locator;
    }

    @Override
    public void waitUntilPresent() {
        getBrowser().waitUntilPresent(getLocator());
    }

    @Override
    public void waitUntilPresent(long timeout) {
        getBrowser().waitUntilPresent(getLocator(), timeout);
    }

    @Override
    public void waitUntilVisible() {
        getBrowser().waitUntilVisible(getLocator());
    }

    @Override
    public void waitUntilVisible(long timeout) {
        getBrowser().waitUntilVisible(getLocator(), timeout);
    }

    @Override
    public void waitUntilDisappear() {
        getBrowser().waitUntilDisappear(getLocator());
    }

    @Override
    public void waitUntilDisappear(long timeout) {
        getBrowser().waitUntilDisappear(getLocator(), timeout);
    }

    @Override
    public void waitUntilEnabled() {
        getBrowser().waitUntilEnabled(getLocator());
    }

    @Override
    public void waitUntilEnabled(long timeout) {
        getBrowser().waitUntilEnabled(getLocator(), timeout);
    }

    @Override
    public void waitUntilDisabled() {
        getBrowser().waitUntilDisabled(getLocator());
    }

    @Override
    public void waitUntilDisabled(long timeout) {
        getBrowser().waitUntilDisabled(getLocator(), timeout);
    }

    @Override
    public void waitUntilAttributeChange(String attributeName, String expectedValue) {
        getBrowser().getWait().until(WaitConditions.attributeChanged(this, attributeName, expectedValue));
    }

    @Override
    public void waitUntilAttributeChange(String attributeName, String expectedValue, long timeout) {
        getBrowser().getWait(timeout).until(WaitConditions.attributeChanged(this, attributeName, expectedValue));
    }

    @Override
    public void waitUntilIsInViewport() {
        getBrowser().waitUntilIsInViewport(getLocator());
    }

    @Override
    public void waitUntilIsInViewport(long timeout) {
        getBrowser().waitUntilIsInViewport(getLocator(), timeout);
    }

    @Override
    public void waitUntilIsClickable() {
        getBrowser().getWait().until(ExpectedConditions.elementToBeClickable(getWrappedElement()));
    }

    @Override
    public void waitUntilIsClickable(long timeout) {
        getBrowser().getWait(timeout).until(ExpectedConditions.elementToBeClickable(getWrappedElement()));
    }
}