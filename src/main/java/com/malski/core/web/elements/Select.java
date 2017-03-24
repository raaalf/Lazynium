package com.malski.core.web.elements;

import com.malski.core.web.elements.states.SelectState;
import com.malski.core.web.elements.waits.SelectWait;
import com.malski.core.web.factory.LazyLocator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.NoSuchElementException;

public class Select extends Element implements SelectWait, SelectState {
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

    public LazyList<Element> getAllSelectedOptions() {
        LazyList<Element> toReturn = new LazyList<>();
        this.getOptions().forEach( option -> {
            if(option.isSelected()) {
                toReturn.add(option);
            }
        });
        return toReturn;
    }

    public LazyList<Element> getOptions() {
        return $$e(By.tagName("option"));
    }

    public String getSelectedVisibleText() {
        return getFirstSelectedOption().getText();
    }

    public String getSelectedValue() {
        return getFirstSelectedOption().value();
    }

    public int getSelectedIndex() {
        LazyList<Element> options = getOptions();
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

    public Select getSelect() {
        return this;
    }
}