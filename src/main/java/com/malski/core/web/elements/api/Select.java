package com.malski.core.web.elements.api;

import java.util.List;

public interface Select extends Element {

    boolean isMultiple();

    void selectByIndex(int index);

    void selectByVisibleText(String text);

    void selectByValue(String value);

    void selectOption(Element option);

    void selectAll();

    void deselectByIndex(int index);

    void deselectByVisibleText(String text);

    void deselectByValue(String value);

    void deselectOption(Element option);

    void deselectAll();

    Element getFirstSelectedOption();

    Elements<Element> getAllSelectedOptions();

    Elements<Element> getOptions();

    String getSelectedVisibleText();

    String getSelectedValue();

    List<String> getOptionsValues();

    List<String> getOptionsVisibleTexts();

    org.openqa.selenium.support.ui.Select getWrappedSelect();

    boolean isSelected(long timeout);

    void waitUntilSelected();

    void waitUntilSelected(long timeout);

    void waitUntilUnselected();

    void waitUntilUnselected(long timeout);
}