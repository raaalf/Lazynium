package com.malski.core.mobile.elements;

import com.malski.core.mobile.control.LazyMobileContext;
import com.malski.core.mobile.elements.api.ElementStates;
import com.malski.core.mobile.elements.api.ElementWait;
import com.malski.core.utils.TestContext;
import com.malski.core.mobile.conditions.WaitConditions;
import com.malski.core.mobile.factory.ElementHandler;
import com.malski.core.mobile.factory.LazyLocator;
import com.malski.core.mobile.factory.Selector;
import io.appium.java_client.IllegalCoordinatesException;
import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import io.appium.java_client.TouchableElement;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;

import static com.malski.core.utils.TestContext.getApplication;

public class Element extends LazyMobileContext implements TouchableElement<MobileElement>, ElementStates, ElementWait {

    private MobileElement element;
    private LazyLocator locator;

    public Element(LazyLocator locator) {
        this.locator = locator;
    }

    public <T extends MobileElement> Element(LazyLocator locator, T element) {
        this.locator = locator;
        this.element = element;
    }

    @SuppressWarnings("unchecked")
    @Override
    public MobileElement getSearchContext() {
        return getWrappedElement();
    }

    @Override
    public void click() {
        waitUntilEnabled(TestContext.getConfig().getTimeout());
        getWrappedElement().click();
    }

    public void clickAndWait() {
        click();
        getApplication().waitForScreenToLoad();
    }

    @Override
    public void pinch() {
        getWrappedElement().pinch();
    }

    @Override
    public void tap(int fingers, int duration) {
        getWrappedElement().tap(fingers, duration);
    }

    @Override
    public void zoom() {
        getWrappedElement().zoom();
    }

    @Override
    public void swipe(SwipeElementDirection direction, int duration) {
        getWrappedElement().swipe(direction, duration);
    }

    @Override
    public void swipe(SwipeElementDirection direction, int offsetFromStartBorder, int offsetFromEndBorder, int duration) throws IllegalCoordinatesException {
        getWrappedElement().swipe(direction, offsetFromStartBorder, offsetFromEndBorder, duration);
    }

    @Override
    public void setValue(String value) {
        getWrappedElement().setValue(value);
    }

    public void doubleClick() {
        WebElement thisElem = getWrappedElement();
        getApplication().getActions()
                .doubleClick(thisElem)
                .perform();
    }

    public void rightClick() {
        WebElement thisElem = getWrappedElement();
        getApplication().getActions()
                .contextClick(thisElem)
                .perform();
    }

    public void dragAndDrop(Element elementToDrop) {
        getApplication().getActions().dragAndDrop(this, elementToDrop).perform();
    }

    public void dragAndDropWithOffset(Element elementToDrop, int xOffset, int yOffset) {
        WebElement thisElem = getWrappedElement();
        WebElement toDropElem = elementToDrop.getWrappedElement();
        getApplication().getActions()
                .clickAndHold(thisElem)
                .moveToElement(toDropElem, xOffset, yOffset)
                .release(toDropElem)
                .build()
                .perform();
    }

    public void mouseOver() {
        WebElement thisElem = getWrappedElement();
        getApplication().getActions()
                .moveToElement(thisElem)
                .perform();
    }

    public void scrollIntoView() {
        if (!isInViewport()) {
//            getApplication().waitForPageToLoad();
//            getApplication().getJsExecutor().scrollIntoView(this);
//            getApplication().waitForPageToLoad();
            throw new RuntimeException("Scrolling into view not implemented in mobile");
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
            waitUntilIsInViewport(timeout);
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
            return getLocator().findElement() != null;
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
        return getWrappedElement().equals(getApplication().getDriver().switchTo().activeElement());
    }

    @Override
    public boolean hasFocus(long timeout) {
        try {
            waitUntilVisible(timeout);
        } catch (Exception ignore) {
            return false;
        }
        return getWrappedElement().equals(getApplication().getDriver().switchTo().activeElement());
    }

    @Override
    public boolean isInViewport() {
//        Dimension elemDim = getWrappedElement().getSize();
//        Point point = getWrappedElement().getLocation();
//
//        int elemY = elemDim.getHeight() + point.getY();
//        long browserHeight = getApplication().getJsExecutor().getJsClientHeight();
//        long scrollHeight = getApplication().getJsExecutor().getScrollHeight();
//
//        return elemY >= scrollHeight && elemY <= scrollHeight + browserHeight;
        // TODO
        throw new RuntimeException("Checking if element is in viewport is not implemented in mobile.");
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

    public MobileElement getWrappedElement() {
        if (isStaleness()) {
            refresh();
        }
        return element;
    }

    @Override
    public boolean isStaleness() {
        if (element == null) {
            setWrappedElement();
            return false;
        } else {
            return WaitConditions.stalenessOf(element);
        }
    }

    @Override
    public boolean refresh() {
        boolean result = getLocator().refresh();
        setWrappedElement();
        return result;
    }

    private void setWrappedElement() {
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
        getApplication().waitUntilPresent(getLocator());
    }

    @Override
    public void waitUntilPresent(long timeout) {
        getApplication().waitUntilPresent(getLocator(), timeout);
    }

    @Override
    public void waitUntilVisible() {
        getApplication().waitUntilVisible(getLocator());
    }

    @Override
    public void waitUntilVisible(long timeout) {
        getApplication().waitUntilVisible(getLocator(), timeout);
    }

    @Override
    public void waitUntilDisappear() {
        getApplication().waitUntilDisappear(getLocator());
    }

    @Override
    public void waitUntilDisappear(long timeout) {
        getApplication().waitUntilDisappear(getLocator(), timeout);
    }

    @Override
    public void waitUntilEnabled() {
        getApplication().waitUntilEnabled(getLocator());
    }

    @Override
    public void waitUntilEnabled(long timeout) {
        getApplication().waitUntilEnabled(getLocator(), timeout);
    }

    @Override
    public void waitUntilDisabled() {
        getApplication().waitUntilDisabled(getLocator());
    }

    @Override
    public void waitUntilDisabled(long timeout) {
        getApplication().waitUntilDisabled(getLocator(), timeout);
    }

    @Override
    public void waitUntilAttributeChange(String attributeName, String expectedValue) {
        getApplication().waitUntilAttributeChange(getLocator(), attributeName, expectedValue);
    }

    @Override
    public void waitUntilAttributeChangeFrom(String attributeName, String startValue) {
        getApplication().waitUntilAttributeFrom(getLocator(), attributeName, startValue);
    }

    @Override
    public void waitUntilAttributeChange(String attributeName, String expectedValue, long timeout) {
        getApplication().getWait(timeout).until(WaitConditions.attributeChanged(this, attributeName, expectedValue));
    }

    @Override
    public void waitUntilIsInViewport() {
        getApplication().waitUntilIsInViewport(getLocator());
    }

    @Override
    public void waitUntilIsInViewport(long timeout) {
        getApplication().waitUntilIsInViewport(getLocator(), timeout);
    }
}