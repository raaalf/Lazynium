package com.malski.core.mobile.control;

import com.malski.core.mobile.elements.Element;
import com.malski.core.mobile.elements.Elements;
import com.malski.core.mobile.factory.*;
import com.malski.core.mobile.view.Module;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;

import java.util.List;

import static com.malski.core.utils.TestContext.getApplication;

public abstract class LazyMobileContext extends MobileElement implements LazySearchContext {

    public LazyMobileContext() {
    }

    public MobileElement findElementByOsAutomation(String using) throws WebDriverException {
        switch (getApplication().getOsType()) {
            case "ios":
                return ((IOSElement) getSearchContext()).findElementByIosUIAutomation(using);
            case "android":
                return ((AndroidElement) getSearchContext()).findElementByAndroidUIAutomator(using);
            default:
                throw new RuntimeException("Not implemented ui specific find for type " + getSearchContext().getClass());
        }
    }

    public List<MobileElement> findElementsByOsAutomation(String using) throws WebDriverException {
        switch (getApplication().getOsType()) {
            case "ios":
                return ((IOSElement) getSearchContext()).findElementsByIosUIAutomation(using);
            case "android":
                return ((AndroidElement) getSearchContext()).findElementsByAndroidUIAutomator(using);
            default:
                throw new RuntimeException("Not implemented ui specific find for type " + getSearchContext().getClass());
        }
    }

    public void replaceValue(String value) {
        if (getSearchContext() instanceof AndroidElement) {
            ((AndroidElement) getSearchContext()).replaceValue(value);
        } else {
            throw new RuntimeException("No 'replaceValue' for type " + getSearchContext().getClass());
        }
    }

    @Override
    public List<MobileElement> findElements(By by) {
        return getSearchContext().findElements(by);
    }

    @Override
    public MobileElement findElement(By by) {
        return getSearchContext().findElement(by);
    }

    @Override
    public MobileElement findElement(String by, String using) {
        return getSearchContext().findElement(by, using);
    }

    @Override
    public List<MobileElement> findElements(String by, String using) {
        return getSearchContext().findElements(by, using);
    }

    @Override
    public MobileElement findElementByAccessibilityId(String using) {
        return getSearchContext().findElementByAccessibilityId(using);
    }

    @Override
    public List<MobileElement> findElementsByAccessibilityId(String using) {
        return getSearchContext().findElementsByAccessibilityId(using);
    }

    public Element getElement(By by) {
        return getElement(by, Element.class);
    }

    public <T extends Element> T getElement(By by, Class<T> clazz) {
        return new ElementHandler<>(clazz, by, this).getImplementation();
    }

    public Element getElement(LazyLocator locator) {
        return getElement(locator, Element.class);
    }

    public <T extends Element> T getElement(LazyLocator locator, Class<T> clazz) {
        return new ElementHandler<>(clazz, locator).getImplementation();
    }

    public Element getElement(Selector selector) {
        return getElement(selector, Element.class);
    }

    public <T extends Element> T getElement(Selector selector, Class<T> clazz) {
        return new ElementHandler<>(clazz, selector, this).getImplementation();
    }

    public Element $(String css) {
        return getElement(By.cssSelector(css));
    }

    public <T extends Element> T $(String css, Class<T> clazz) {
        return getElement(By.cssSelector(css), clazz);
    }

    public Element $i(String id) {
        return getElement(By.id(id));
    }

    public <T extends Element> T $i(String id, Class<T> clazz) {
        return getElement(By.id(id), clazz);
    }

    public Element $n(String name) {
        return getElement(By.name(name));
    }

    public <T extends Element> T $n(String name, Class<T> clazz) {
        return getElement(By.name(name), clazz);
    }

    public Element $x(String xpath) {
        return getElement(By.xpath(xpath));
    }

    public <T extends Element> T $x(String xpath, Class<T> clazz) {
        return getElement(By.xpath(xpath), clazz);
    }

    public Element $t(String tagName) {
        return getElement(By.tagName(tagName));
    }

    public <T extends Element> T $t(String tagName, Class<T> clazz) {
        return getElement(By.tagName(tagName), clazz);
    }

    public Element $c(String className) {
        return getElement(By.className(className));
    }

    public <T extends Element> T $c(String className, Class<T> clazz) {
        return getElement(By.className(className), clazz);
    }

    // TODO
    public Element $os(String className) {
        return getElement(By.className(className));
    }

    // TODO
    public <T extends Element> T $os(String className, Class<T> clazz) {
        return getElement(By.className(className), clazz);
    }

    public <T extends Element> Elements<T> getEmptyElementsList() {
        return new Elements<>();
    }

    public Elements<Element> getElements(By by) {
        return getElements(by, Element.class);
    }

    public <T extends Element> Elements<T> getElements(By by, Class<T> clazz) {
        return new ElementListHandler<>(clazz, by, this).getImplementation();
    }

    public Elements<Element> getElements(LazyLocator locator) {
        return getElements(locator, Element.class);
    }

    public <T extends Element> Elements<T> getElements(LazyLocator locator, Class<T> clazz) {
        return new ElementListHandler<>(clazz, locator).getImplementation();
    }

    public Elements<Element> getElements(Selector selector) {
        return getElements(selector, Element.class);
    }

    public <T extends Element> Elements<T> getElements(Selector selector, Class<T> clazz) {
        return new ElementListHandler<>(clazz, selector, this).getImplementation();
    }

    public Elements<Element> $$(String css) {
        return getElements(By.cssSelector(css));
    }

    public <T extends Element> Elements<T> $$(String css, Class<T> clazz) {
        return getElements(By.cssSelector(css), clazz);
    }

    public Elements<Element> $$i(String id) {
        return getElements(By.id(id));
    }

    public <T extends Element> Elements<T> $$i(String id, Class<T> clazz) {
        return getElements(By.id(id), clazz);
    }

    public Elements<Element> $$n(String name) {
        return getElements(By.name(name));
    }

    public <T extends Element> Elements<T> $$n(String name, Class<T> clazz) {
        return getElements(By.name(name), clazz);
    }

    public Elements<Element> $$x(String xpath) {
        return getElements(By.xpath(xpath));
    }

    public <T extends Element> Elements<T> $$x(String xpath, Class<T> clazz) {
        return getElements(By.xpath(xpath), clazz);
    }

    public Elements<Element> $$t(String tagName) {
        return getElements(By.tagName(tagName));
    }

    public <T extends Element> Elements<T> $$t(String tagName, Class<T> clazz) {
        return getElements(By.tagName(tagName), clazz);
    }

    public Elements<Element> $$c(String className) {
        return getElements(By.className(className));
    }

    public <T extends Element> Elements<T> $$c(String className, Class<T> clazz) {
        return getElements(By.className(className), clazz);
    }

    public Elements<Element> $$os(String className) {
        return getElements(By.className(className));
    }

    public <T extends Element> Elements<T> $$os(String className, Class<T> clazz) {
        return getElements(By.className(className), clazz);
    }

    public <T extends Module> T getModule(Class<T> iface) {
        return getModule(iface, new LazyLocator(this, new LazyAnnotations(iface)));
    }

    public <T extends Module> T getModule(Class<T> iface, By by) {
        return getModule(iface, new LazyLocator(this, by));
    }

    public <T extends Module> T getModule(Class<T> iface, Selector selector) {
        return getModule(iface, new LazyLocator(this, selector));
    }

    public <T extends Module> T getModule(Class<T> iface, LazyLocator locator) {
        ModuleHandler<T> handler = new ModuleHandler<>(iface, locator);
        try {
            return handler.getImplementation();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
