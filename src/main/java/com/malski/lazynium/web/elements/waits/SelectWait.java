package com.malski.lazynium.web.elements.waits;

import com.malski.lazynium.web.elements.Element;
import com.malski.lazynium.web.elements.Select;

import static com.malski.lazynium.utils.TestContext.browser;
import static com.malski.lazynium.web.conditions.WaitConditions.optionSelectedByIndex;
import static com.malski.lazynium.web.conditions.WaitConditions.optionSelectedByValue;
import static com.malski.lazynium.web.conditions.WaitConditions.optionSelectedByVisibleText;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementSelectionStateToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeSelected;

public interface SelectWait extends ElementWait {

    org.openqa.selenium.support.ui.Select getWrappedSelect();

    Select getSelect();

    default void waitUntilSelected() {
        browser().getWait().until(elementToBeSelected(getWrappedElement()));
    }

    default void waitUntilSelected(long timeout) {
        browser().getWait(timeout).until(elementToBeSelected(getWrappedElement()));
    }

    default void waitUntilUnselected() {
        browser().getWait().until(elementSelectionStateToBe(getWrappedElement(), false));
    }

    default void waitUntilUnselected(long timeout) {
        browser().getWait(timeout).until(elementSelectionStateToBe(getWrappedElement(), false));
    }

    default void waitUntilSelectedVisibleText(String text) {
        browser().getWait().until(optionSelectedByVisibleText(getSelect(), text));
    }

    default void waitUntilSelectedVisibleText(String text, long timeout) {
        browser().getWait(timeout).until(optionSelectedByVisibleText(getSelect(), text));
    }

    default void waitUntilSelectedValue(String value) {
        browser().getWait().until(optionSelectedByValue(getSelect(), value));
    }

    default void waitUntilSelectedValue(String value, long timeout) {
        browser().getWait(timeout).until(optionSelectedByValue(getSelect(), value));
    }

    default void waitUntilSelectedIndex(int index) {
        browser().getWait().until(optionSelectedByIndex(getSelect(), index));
    }

    default void waitUntilSelectedIndex(int index, long timeout) {
        browser().getWait(timeout).until(optionSelectedByIndex(getSelect(), index));
    }

    default void waitUntilSelectedOption(Element option) {
        browser().getWait().until(elementToBeSelected(option));
    }

    default void waitUntilSelectedOption(Element option, long timeout) {
        browser().getWait(timeout).until(elementToBeSelected(option));
    }
}
