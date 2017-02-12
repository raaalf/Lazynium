package com.malski.core.web.elements;

import com.malski.core.web.conditions.WaitConditions;
import com.malski.core.web.control.LazySearchContext;
import com.malski.core.web.elements.states.ElementState;
import com.malski.core.web.factory.ElementHandler;
import com.malski.core.web.factory.LazyLocator;
import com.malski.core.web.factory.Selector;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;

import static com.malski.core.utils.TestContext.getBrowser;
import static com.malski.core.utils.TestContext.getConfig;

public class Element implements LazySearchContext, WebElement, WrapsElement, Locatable, ElementState {

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
    public SearchContext getContext() {
        return getWrappedElement();
    }

    public void lightOn() {
        if (!isStaleness()) {
            cssStyle = getBrowser().jsExecutor().getStyle(this);
            getBrowser().jsExecutor().setStyle(this, "color: red; border: 3px solid red;");
        }
    }

    public void lightOff() {
        if (!isStaleness()) {
            getBrowser().jsExecutor().setStyle(this, cssStyle);
        }
    }

    @Override
    public void click() {
        waitUntilIsClickable(getConfig().getMinTimeout());
        getWrappedElement().click();
    }

    public void clickAndWait() {
        click();
        getBrowser().waitUntilPageLoaded();
    }

    public void doubleClick() {
        WebElement thisElem = getWrappedElement();
        getBrowser().actions()
                .doubleClick(thisElem)
                .perform();
    }

    public void rightClick() {
        WebElement thisElem = getWrappedElement();
        getBrowser().actions()
                .contextClick(thisElem)
                .perform();
    }

    public void dragAndDrop(Element elementToDrop) {
        getBrowser().actions().dragAndDrop(this, elementToDrop).perform();
    }

    public void dragAndDropByOffset(Element elementToDrop, int x, int y) {
        getBrowser().actions().dragAndDropBy(this, elementToDrop.getLocation().getX() + x, elementToDrop.getLocation().getY() + y).perform();
    }

    public void dragAndDropBy(int x, int y) {
        getBrowser().actions().dragAndDropBy(this, x, y).perform();
    }

    public void dragAndDropWithOffset(Element elementToDrop, int xOffset, int yOffset) {
        WebElement thisElem = getWrappedElement();
        WebElement toDropElem = elementToDrop.getWrappedElement();
        getBrowser().actions()
                .clickAndHold(thisElem)
                .moveToElement(toDropElem, xOffset, yOffset)
                .release(toDropElem)
                .build()
                .perform();
    }

    public void mouseOver() {
        WebElement thisElem = getWrappedElement();
        getBrowser().actions()
                .moveToElement(thisElem)
                .perform();
    }

    public void scrollIntoView() {
        if (!isInViewport()) {
            getBrowser().waitUntilPageLoaded();
            getBrowser().jsExecutor().scrollIntoView(this);
            getBrowser().waitUntilPageLoaded();
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
    public boolean isEnabled() {
        return getWrappedElement().isEnabled();
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
            return false;
        } else {
            return WaitConditions.stalenessOf(element);
        }
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
}