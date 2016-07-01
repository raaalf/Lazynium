package com.malski.core.web.elements.api;

import com.malski.core.web.factory.LazyLocator;

import java.util.List;

public interface Elements<T extends Element> extends List<T>, ElementsWait, ElementsStates {

    LazyLocator getLocator();

    List<String> getTexts();

    List<String> getValues();

    List<String> getAttributes(String attributeName);

    List<String> getIds();

    void refresh();
}
