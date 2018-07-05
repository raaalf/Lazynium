package com.malski.lazynium.web.factory;

import com.malski.lazynium.utils.TestContext;
import com.malski.lazynium.web.control.LazySearchContext;
import com.malski.lazynium.web.elements.Element;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.commons.lang3.ArrayUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class ElementHandler<T extends Element> extends LazyInterceptor<T> {
    // TODO extract this to properties
    private String[] highlightActions = {"doubleClick", "click", "rightClick", "dragAndDrop", "dragAndDropWithOffset",
            "mouseOver", "scrollIntoView", "clickWithoutWait", "clickWithOffset", "sendKeys", "clear", "getAttribute",
            "attribute", "getText", "text", "value", "cssClass", "cssValue", "getCssValue", "cssValue", "isEnabled",
            "isSelected", "alt", "src", "fill", "href", "target", "check", "isChecked", "selectByIndex", "selectByVisibleText",
            "selectByVisibleTextIgnoreCases", "selectByContainingVisibleText", "selectByValue", "selectOption", "selectAll",
            "getOptions", "getSelectedVisibleText", "getSelectedValue", "getSelectedIndex", "getOptionsValues", "getOptionsVisibleTexts",
            "isSelected", "isMultiple", "selectAll", "deselectByIndex", "deselectByVisibleText", "deselectByValue", "deselectOption",
            "deselectAll", "getFirstSelectedOption", "getAllSelectedOptions"};

    public ElementHandler(Class<T> interfaceType, LazyLocator locator) {
        super(interfaceType, locator);
    }

    public ElementHandler(Class<T> interfaceType, By by, LazySearchContext searchContext) {
        super(interfaceType, by, searchContext);
    }

    public ElementHandler(Class<T> interfaceType, Selector selector, LazySearchContext searchContext) {
        super(interfaceType, selector, searchContext);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void init(Class<T> interfaceType) {
        if (!Element.class.isAssignableFrom(interfaceType)) {
            throw new RuntimeException("Interface not assignable to " + interfaceType.getCanonicalName());
        }
        try {
           setWrapper((Class<T>) Class.forName(interfaceType.getCanonicalName()));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Missing implementation for " + interfaceType.getCanonicalName());
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getImplementation() {
        try {
            Constructor cons = getWrapper().getConstructor(LazyLocator.class);
            return (T) cons.newInstance(getLocator());
        } catch (Throwable e) {
            throw new RuntimeException("Not able to create object of type: " + getWrapper().getSimpleName(), e);
        }
    }

    @SuppressWarnings("unchecked")
    public T getImplementation(WebElement element, int index) {
        try {
            Constructor cons = getWrapper().getConstructor(LazyLocator.class, WebElement.class);
            LazyLocator indexed = getLocator().duplicate(index);
            return (T) cons.newInstance(indexed, element);
        } catch (Throwable e) {
            throw new RuntimeException("Not able to create object of type: " + getWrapper().getSimpleName(), e);
        }
    }

    @Override
    protected Object invoke(T thing, Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        boolean highlight = shouldHighlightElement(method);
        if(highlight) {
            thing.lightOn();
        }
        Object result = method.invoke(thing, args);
        if(highlight) {
            thing.lightOff();
        }
        return result;
    }

    protected boolean shouldHighlightElement(Method method) {
        return TestContext.config().isVideoRecording() && ArrayUtils.contains(highlightActions, method.getName());
    }
}