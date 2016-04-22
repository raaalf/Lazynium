package com.malski.core.web.elements;

import com.malski.core.web.factory.LazyLocator;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class SelectImpl extends ElementImpl implements Select {
    private org.openqa.selenium.support.ui.Select innerSelect;

    public SelectImpl(By by, SearchContext context) {
        super(by, context);
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
        if(innerSelect == null) {
            innerSelect = new org.openqa.selenium.support.ui.Select(getWrappedElement());
        }
        return innerSelect;
    }

    @Override
    public boolean isMultiple() {
        return innerSelect.isMultiple();
    }

    @Override
    public void deselectByIndex(int index) {
        innerSelect.deselectByIndex(index);
    }

    @Override
    public void selectByValue(String value) {
        innerSelect.selectByValue(value);
    }

    @Override
    public void selectOption(Element option) {
        if(!option.isSelected()) {
            option.click();
        }
    }

    @Override
    public void selectAll() {
        this.getOptions().stream().filter(option -> !option.isSelected()).forEach(com.malski.core.web.elements.Element::click);
    }

    public Element getFirstSelectedOption() {
        for(Element option : this.getOptions()) {
            if(option.isSelected()) {
                return option;
            }
        }
        throw new NoSuchElementException("No options are selected");
    }

    @Override
    public void selectByVisibleText(String text) {
        innerSelect.selectByVisibleText(text);
    }

    @Override
    public void deselectByValue(String value) {
        innerSelect.deselectByValue(value);
    }

    @Override
    public void deselectOption(Element option) {
        if(option.isSelected()) {
            option.click();
        }
    }

    @Override
    public void deselectAll() {
        innerSelect.deselectAll();
    }

    @Override
    public List<Element> getAllSelectedOptions() {
        List<Element> toReturn = new ArrayList<>();
        this.getOptions().forEach( option -> {
            if(option.isSelected()) {
                toReturn.add(option);
            }
        });
        return toReturn;
    }

    @Override
    public List<Element> getOptions() {
        return this.getElements(By.tagName("option"));
    }

    @Override
    public String getSelectedVisibleText() {
        return this.getFirstSelectedOption().getText();
    }

    @Override
    public String getSelectedValue() {
        return this.getFirstSelectedOption().getValue();
    }

    @Override
    public void deselectByVisibleText(String text) {
        innerSelect.deselectByVisibleText(text);
    }

    @Override
    public void selectByIndex(int index) {
        innerSelect.selectByIndex(index);
    }
}