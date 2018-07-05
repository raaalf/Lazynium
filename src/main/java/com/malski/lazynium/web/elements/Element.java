package com.malski.lazynium.web.elements;

import com.malski.lazynium.web.conditions.WaitConditions;
import com.malski.lazynium.web.control.LazySearchContext;
import com.malski.lazynium.web.elements.states.ElementState;
import com.malski.lazynium.web.factory.ElementHandler;
import com.malski.lazynium.web.factory.LazyLocator;
import com.malski.lazynium.web.factory.Selector;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;

import java.util.List;

import static com.malski.lazynium.utils.TestContext.browser;
import static com.malski.lazynium.utils.TestContext.config;

public class Element implements LazySearchContext, Locatable, ElementState, WebElement {

    private WebElement element;
    private LazyLocator locator;
    private String cssStyle;

    public Element(LazyLocator locator) {
        this.locator = locator;
    }

    public Element(LazyLocator locator, WebElement element) {
        this.locator = locator;
        this.element = element;
    }

    @Override
    public SearchContext searchContext() {
        return getWrappedElement();
    }

    public void lightOn() {
        if (!isStaleness()) {
            cssStyle = browser().jsExecutor().getStyle(this);
            browser().jsExecutor().setStyle(this, "color: red; border: 3px solid red;");
        }
    }

    public void lightOff() {
        if (!isStaleness()) {
            browser().jsExecutor().setStyle(this, cssStyle);
        }
    }

    @Override
    public List<WebElement> findElements(By by) {
        return LazySearchContext.super.findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        return LazySearchContext.super.findElement(by);
    }

    @Override
    public void click() {
        waitUntilIsClickable(config().minTimeout());
        getWrappedElement().click();
    }

    public void clickAndWait() {
        click();
        browser().waitUntilPageLoaded();
    }

    public void doubleClick() {
        WebElement thisElem = getWrappedElement();
        browser().actions()
                .doubleClick(thisElem)
                .perform();
    }

    public void rightClick() {
        WebElement thisElem = getWrappedElement();
        browser().actions()
                .contextClick(thisElem)
                .perform();
    }

    public void dragTo(Element elementToDrop) {
        browser().actions().dragAndDrop(this, elementToDrop).perform();
    }

    public void dragToBy(Element elementToDrop, int x, int y) {
        browser().actions().dragAndDropBy(this, elementToDrop.getLocation().getX() + x, elementToDrop.getLocation().getY() + y).perform();
    }

    public void dragBy(int x, int y) {
        browser().actions().dragAndDropBy(this, x, y).perform();
    }

    public void mouseOver() {
        WebElement thisElem = getWrappedElement();
        browser().actions()
                .moveToElement(thisElem)
                .perform();
    }

    public void scrollIntoView() {
        if (!isInViewport()) {
            browser().waitUntilPageLoaded();
            browser().jsExecutor().scrollIntoView(this);
            browser().waitUntilPageLoaded();
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
        return tagName();
    }

    public String tagName() {
        return getWrappedElement().getTagName();
    }

    @Override
    public String getAttribute(String s) {
        return attribute(s);
    }

    public String attribute(String s) {
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
    public boolean isEnabled() {
        return getWrappedElement().isEnabled();
    }

    @Override
    public String getText() {
        return text();
    }

    public String text() {
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
        return cssValue(s);
    }

    public String cssValue(String s) {
        return getWrappedElement().getCssValue(s);
    }

    public Selector selector() {
        return locator().getSelector();
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
            return false;
        } else {
            return WaitConditions.stalenessOf(element);
        }
    }

    @Override
    public boolean refresh() {
        boolean result = locator().refresh();
        setWebElement();
        return result;
    }

    private void setWebElement() {
        element = locator().findElement();
    }

    public String value() {
        return this.getAttribute("value");
    }

    public String id() {
        return this.getAttribute("id");
    }

    public String cssClass() {
        return this.getAttribute("class");
    }

    public <T extends Element> T as(Class<T> iface) {
        try {
            return new ElementHandler<>(iface, locator()).getImplementation();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public LazyLocator locator() {
        return this.locator;
    }
}