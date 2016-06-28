package com.malski.core.web.elements.impl;

import com.malski.core.web.elements.api.Element;
import com.malski.core.web.elements.api.Elements;
import com.malski.core.web.elements.api.Select;
import com.malski.core.web.factory.LazyLocator;
import com.malski.core.web.factory.Selector;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.NoSuchElementException;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementSelectionStateToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeSelected;
import static com.malski.core.cucumber.TestContext.getBrowser;

public class SelectImpl extends ElementImpl implements Select {
    private org.openqa.selenium.support.ui.Select innerSelect;

    public SelectImpl(By by, SearchContext context) {
        super(by, context);
    }

    public SelectImpl(Selector selector, SearchContext context) {
        super(selector, context);
    }

    public SelectImpl(LazyLocator locator) {
        super(locator);
    }

    public SelectImpl(LazyLocator locator, WebElement element) {
        super(locator, element);
    }

    public SelectImpl(By by, WebElement element) {
        super(by, element);
    }

    @Override
    public org.openqa.selenium.support.ui.Select getWrappedSelect() {
        if (innerSelect == null) {
            innerSelect = new org.openqa.selenium.support.ui.Select(getWrappedElement());
        }
        return innerSelect;
    }

    @Override
    public boolean isMultiple() {
        return getWrappedSelect().isMultiple();
    }

    @Override
    public void deselectByIndex(int index) {
        getWrappedSelect().deselectByIndex(index);
    }

    @Override
    public void selectByValue(String value) {
        getWrappedSelect().selectByValue(value);
    }

    @Override
    public void selectOption(Element option) {
        if (!option.isSelected()) {
            option.click();
        }
    }

    @Override
    public void selectAll() {
        this.getOptions().stream().filter(option -> !option.isSelected()).forEach(Element::click);
    }

    public Element getFirstSelectedOption() {
        for (Element option : getOptions()) {
            if (option.isSelected()) {
                return option;
            }
        }
        throw new NoSuchElementException("No options are selected");
    }

    @Override
    public void selectByVisibleText(String text) {
        getWrappedSelect().selectByVisibleText(text);
    }

    @Override
    public void deselectByValue(String value) {
        getWrappedSelect().deselectByValue(value);
    }

    @Override
    public void deselectOption(Element option) {
        if (option.isSelected()) {
            option.click();
        }
    }

    @Override
    public void deselectAll() {
        getWrappedSelect().deselectAll();
    }

    @Override
    public Elements<Element> getAllSelectedOptions() {
        Elements<Element> toReturn = new ElementsImpl<>();
        this.getOptions().forEach(option -> {
            if (option.isSelected()) {
                toReturn.add(option);
            }
        });
        return toReturn;
    }

    @Override
    public Elements<Element> getOptions() {
        return getElements(By.tagName("option"));
    }

    @Override
    public String getSelectedVisibleText() {
        return getFirstSelectedOption().getText();
    }

    @Override
    public String getSelectedValue() {
        return getFirstSelectedOption().getValue();
    }

    @Override
    public List<String> getOptionsValues() {
        return getOptions().getValues();
    }

    @Override
    public List<String> getOptionsVisibleTexts() {
        return getOptions().getTexts();
    }

    @Override
    public void deselectByVisibleText(String text) {
        getWrappedSelect().deselectByVisibleText(text);
    }

    @Override
    public void selectByIndex(int index) {
        getWrappedSelect().selectByIndex(index);
    }

    @Override
    public boolean isSelected(long timeout) {
        try {
            waitUntilSelected(timeout);
        } catch (Exception ignore) {
            return false;
        }
        return getWrappedElement().isSelected();
    }

    @Override
    public void waitUntilSelected() {
        getBrowser().getWait().until(elementToBeSelected(this));
    }

    @Override
    public void waitUntilSelected(long timeout) {
        getBrowser().getWait(timeout).until(elementToBeSelected(this));
    }

    @Override
    public void waitUntilUnselected() {
        getBrowser().getWait().until(elementSelectionStateToBe(this, false));
    }

    @Override
    public void waitUntilUnselected(long timeout) {
        getBrowser().getWait(timeout).until(elementSelectionStateToBe(this, false));
    }
}