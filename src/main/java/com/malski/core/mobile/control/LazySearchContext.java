package com.malski.core.mobile.control;

import com.malski.core.mobile.elements.Element;
import com.malski.core.mobile.elements.Elements;
import com.malski.core.mobile.factory.LazyLocator;
import com.malski.core.mobile.factory.Selector;
import com.malski.core.mobile.view.Module;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;

import java.util.List;

public interface LazySearchContext {

    <T extends MobileElement> T getSearchContext();

    MobileElement findElementByOsAutomation(String using) throws WebDriverException;

    List<MobileElement> findElementsByOsAutomation(String using) throws WebDriverException;

    void replaceValue(String value);

    List<MobileElement> findElements(By by);

    MobileElement findElement(By by);

    MobileElement findElement(String by, String using);

    List<MobileElement> findElements(String by, String using);

    MobileElement findElementByAccessibilityId(String using);

    List<MobileElement> findElementsByAccessibilityId(String using);

    Element getElement(By by);

    <T extends Element> T getElement(By by, Class<T> clazz);

    Element getElement(LazyLocator locator);

    <T extends Element> T getElement(LazyLocator locator, Class<T> clazz);

    Element getElement(Selector selector);

    <T extends Element> T getElement(Selector selector, Class<T> clazz);

    Element $(String css);

    <T extends Element> T $(String css, Class<T> clazz);

    Element $i(String id);

    <T extends Element> T $i(String id, Class<T> clazz);

    Element $n(String name);

    <T extends Element> T $n(String name, Class<T> clazz);

    Element $x(String xpath);

    <T extends Element> T $x(String xpath, Class<T> clazz);

    Element $t(String tagName);

    <T extends Element> T $t(String tagName, Class<T> clazz);

    Element $c(String className);

    <T extends Element> T $c(String className, Class<T> clazz);

    Element $os(String className);

    <T extends Element> T $os(String className, Class<T> clazz);

    <T extends Element> Elements<T> getEmptyElementsList();

    Elements<Element> getElements(By by);

    <T extends Element> Elements<T> getElements(By by, Class<T> clazz);

    Elements<Element> getElements(LazyLocator locator);

    <T extends Element> Elements<T> getElements(LazyLocator locator, Class<T> clazz);

    Elements<Element> getElements(Selector selector);

    <T extends Element> Elements<T> getElements(Selector selector, Class<T> clazz);

    Elements<Element> $$(String css);

    <T extends Element> Elements<T> $$(String css, Class<T> clazz);

    Elements<Element> $$i(String id);

    <T extends Element> Elements<T> $$i(String id, Class<T> clazz);

    Elements<Element> $$n(String name);

    <T extends Element> Elements<T> $$n(String name, Class<T> clazz);

    Elements<Element> $$x(String xpath);

    <T extends Element> Elements<T> $$x(String xpath, Class<T> clazz);

    Elements<Element> $$t(String tagName);

    <T extends Element> Elements<T> $$t(String tagName, Class<T> clazz);

    Elements<Element> $$c(String className);

    <T extends Element> Elements<T> $$c(String className, Class<T> clazz);

    Elements<Element> $$os(String className);

    <T extends Element> Elements<T> $$os(String className, Class<T> clazz);

    <T extends Module> T getModule(Class<T> iface);

    <T extends Module> T getModule(Class<T> iface, By by);

    <T extends Module> T getModule(Class<T> iface, Selector selector);

    <T extends Module> T getModule(Class<T> iface, LazyLocator locator);

    boolean refresh();
}
