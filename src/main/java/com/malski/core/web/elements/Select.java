package com.malski.core.web.elements;

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

    List<Element> getAllSelectedOptions();

    List<Element> getOptions();

    String getSelectedVisibleText();

    String getSelectedValue();

    org.openqa.selenium.support.ui.Select getWrappedSelect();

    void waitUntilSelected();

    void waitUntilUnselected();
}