package com.malski.core.web.elements;

import com.malski.core.web.factory.LazyLocator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.NoSuchElementException;

import static com.malski.core.utils.TestContext.getBrowser;
import static com.malski.core.web.conditions.WaitConditions.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementSelectionStateToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeSelected;

public class Select extends Element {
    private org.openqa.selenium.support.ui.Select innerSelect;

    public Select(LazyLocator locator) {
        super(locator);
    }

    public Select(LazyLocator locator, WebElement element) {
        super(locator, element);
    }

    public org.openqa.selenium.support.ui.Select getWrappedSelect() {
        if(innerSelect == null) {
            innerSelect = new org.openqa.selenium.support.ui.Select(getWrappedElement());
        }
        return innerSelect;
    }

    public boolean isMultiple() {
        return getWrappedSelect().isMultiple();
    }

    public void selectByIndex(int index) {
        getWrappedSelect().selectByIndex(index);
    }

    public void selectByValue(String value) {
        getWrappedSelect().selectByValue(value);
    }

    public void selectOption(Element option) {
        if(!option.isSelected()) {
            option.click();
        }
    }

    public void selectAll() {
        this.getOptions().stream().filter(option -> !option.isSelected()).forEach(Element::click);
    }

    public Element getFirstSelectedOption() {
        for(Element option : getOptions()) {
            if(option.isSelected()) {
                return option;
            }
        }
        throw new NoSuchElementException("No options are selected");
    }

    public void selectByVisibleText(String text) {
        getWrappedSelect().selectByVisibleText(text);
    }

    public void deselectByIndex(int index) {
        getWrappedSelect().deselectByIndex(index);
    }

    public void deselectByVisibleText(String text) {
        getWrappedSelect().deselectByVisibleText(text);
    }

    public void deselectByValue(String value) {
        getWrappedSelect().deselectByValue(value);
    }

    public void deselectOption(Element option) {
        if(option.isSelected()) {
            option.click();
        }
    }

    public void deselectAll() {
        getWrappedSelect().deselectAll();
    }

    public Elements<Element> getAllSelectedOptions() {
        Elements<Element> toReturn = getEmptyElementsList();
        this.getOptions().forEach( option -> {
            if(option.isSelected()) {
                toReturn.add(option);
            }
        });
        return toReturn;
    }

    public Elements<Element> getOptions() {
        return getElements(By.tagName("option"));
    }

    public String getSelectedVisibleText() {
        return getFirstSelectedOption().getText();
    }

    public String getSelectedValue() {
        return getFirstSelectedOption().getValue();
    }

    public int getSelectedIndex() {
        Elements<Element> options = getOptions();
        for(int i = 0; i < options.size(); ++i) {
            Element option = options.get(i);
            if(option.isSelected()) {
                return i;
            }
        }
        throw new NoSuchElementException("No options are selected");
    }

    public List<String> getOptionsValues() {
        return getOptions().getValues();
    }

    public List<String> getOptionsVisibleTexts() {
        return getOptions().getTexts();
    }

    public boolean isSelected(long timeout) {
        try {
            waitUntilSelected(timeout);
        } catch (Exception ignore) {
            return false;
        }
        return getWrappedElement().isSelected();
    }

    public void waitUntilSelected() {
        getBrowser().getWait().until(elementToBeSelected(this));
    }

    public void waitUntilSelected(long timeout) {
        getBrowser().getWait(timeout).until(elementToBeSelected(this));
    }

    public void waitUntilUnselected() {
        getBrowser().getWait().until(elementSelectionStateToBe(this, false));
    }

    public void waitUntilUnselected(long timeout) {
        getBrowser().getWait(timeout).until(elementSelectionStateToBe(this, false));
    }

    public void waitUntilSelectedVisibleText(String text) {
        getBrowser().getWait().until(optionSelectedByVisibleText(this, text));
    }

    public void waitUntilSelectedVisibleText(String text, long timeout) {
        getBrowser().getWait(timeout).until(optionSelectedByVisibleText(this, text));
    }

    public void waitUntilSelectedValue(String value) {
        getBrowser().getWait().until(optionSelectedByValue(this, value));
    }

    public void waitUntilSelectedValue(String value, long timeout) {
        getBrowser().getWait(timeout).until(optionSelectedByValue(this, value));
    }

    public void waitUntilSelectedIndex(int index) {
        getBrowser().getWait().until(optionSelectedByIndex(this, index));
    }

    public void waitUntilSelectedIndex(int index, long timeout) {
        getBrowser().getWait(timeout).until(optionSelectedByIndex(this, index));
    }

    public void waitUntilSelectedOption(Element option) {
        getBrowser().getWait().until(elementToBeSelected(option));
    }

    public void waitUntilSelectedOption(Element option, long timeout) {
        getBrowser().getWait(timeout).until(elementToBeSelected(option));
    }
}