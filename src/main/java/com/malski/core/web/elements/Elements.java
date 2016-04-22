package com.malski.core.web.elements;

import com.malski.core.web.factory.LazyLocator;
import org.openqa.selenium.By;

import java.util.List;

public interface Elements<T extends Element> extends List<T> {
    By getBy();

    LazyLocator getLocator();

    List<String> getTexts();

    List<String> getValues();

    List<String> getIds();

    void refresh();
}
