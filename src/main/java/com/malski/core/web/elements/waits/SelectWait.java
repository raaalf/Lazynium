package com.malski.core.web.elements.waits;

import com.malski.core.web.elements.Element;
import com.malski.core.web.elements.Select;

import static com.malski.core.utils.TestContext.getBrowser;
import static com.malski.core.web.conditions.WaitConditions.optionSelectedByIndex;
import static com.malski.core.web.conditions.WaitConditions.optionSelectedByValue;
import static com.malski.core.web.conditions.WaitConditions.optionSelectedByVisibleText;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementSelectionStateToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeSelected;

public interface SelectWait extends ElementWait {

    org.openqa.selenium.support.ui.Select getWrappedSelect();

    Select getSelect();

    default void waitUntilSelected() {
        getBrowser().getWait().until(elementToBeSelected(getWrappedElement()));
    }

    default void waitUntilSelected(long timeout) {
        getBrowser().getWait(timeout).until(elementToBeSelected(getWrappedElement()));
    }

    default void waitUntilUnselected() {
        getBrowser().getWait().until(elementSelectionStateToBe(getWrappedElement(), false));
    }

    default void waitUntilUnselected(long timeout) {
        getBrowser().getWait(timeout).until(elementSelectionStateToBe(getWrappedElement(), false));
    }

    default void waitUntilSelectedVisibleText(String text) {
        getBrowser().getWait().until(optionSelectedByVisibleText(getSelect(), text));
    }

    default void waitUntilSelectedVisibleText(String text, long timeout) {
        getBrowser().getWait(timeout).until(optionSelectedByVisibleText(getSelect(), text));
    }

    default void waitUntilSelectedValue(String value) {
        getBrowser().getWait().until(optionSelectedByValue(getSelect(), value));
    }

    default void waitUntilSelectedValue(String value, long timeout) {
        getBrowser().getWait(timeout).until(optionSelectedByValue(getSelect(), value));
    }

    default void waitUntilSelectedIndex(int index) {
        getBrowser().getWait().until(optionSelectedByIndex(getSelect(), index));
    }

    default void waitUntilSelectedIndex(int index, long timeout) {
        getBrowser().getWait(timeout).until(optionSelectedByIndex(getSelect(), index));
    }

    default void waitUntilSelectedOption(Element option) {
        getBrowser().getWait().until(elementToBeSelected(option));
    }

    default void waitUntilSelectedOption(Element option, long timeout) {
        getBrowser().getWait(timeout).until(elementToBeSelected(option));
    }
}
