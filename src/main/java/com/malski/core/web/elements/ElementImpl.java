package com.malski.core.web.elements;

import com.malski.core.web.factory.LazyLocator;
import com.malski.core.web.factory.Locator;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class ElementImpl implements Element {

    private WebElement element;
    private LazyLocator locator;

    public ElementImpl(By by, SearchContext context) {
        this.locator = new Locator(context, by);
    }

    public ElementImpl(LazyLocator locator) {
        this.locator = locator;
    }

    public ElementImpl(LazyLocator locator, WebElement element) {
        this.locator = locator;
        this.element = element;
    }

    public ElementImpl(By by, WebElement element) {
        this.locator = new Locator(element, by);
    }

    @Override
    public void click() {
        getWrappedElement().click();
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
    public boolean isEnabled() {
        return getWrappedElement().isEnabled();
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
    public boolean isDisplayed() {
        return getWrappedElement().isDisplayed();
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

    @Override
    public By getBy() {
        return locator.getBy();
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
        if(element == null) {
            refresh();
        }
        return element;
    }

    @Override
    public Element getElement(By by) {
        return new ElementImpl(by, getWrappedElement());
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
        return new ElementsListImpl<>(by, getWrappedElement(), Element.class);
    }

    @Override
    public ElementsList<Element> $$(String css) {
        return this.getElements(By.cssSelector(css));
    }

    @Override
    public ElementsList<Element> $$n(String name) {
        return this.getElements(By.name(name));
    }

    @Override
    public ElementsList<Element> $$i(String id) {
        return this.getElements(By.id(id));
    }

    @Override
    public ElementsList<Element> $$x(String xpath) {
        return this.getElements(By.xpath(xpath));
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
    public <T extends Element> T as(Class<T> inteface) {
        try {
            @SuppressWarnings("unchecked")
            Class<T> clazz = (Class<T>) Class.forName(inteface.getCanonicalName()+"Impl");
            Constructor<T> constructor = clazz.getConstructor(LazyLocator.class);
            return constructor.newInstance(getLocator());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                NoSuchMethodException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public LazyLocator getLocator() {
        return this.locator;
    }

    @Override
    public void refresh() {
        element = locator.findElement();
    }
}
