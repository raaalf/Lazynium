package com.malski.core.web.page;

import com.malski.core.web.Browser;
import com.malski.core.web.elements.Element;
import com.malski.core.web.elements.ElementsList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

public interface WebComponent {
    Browser getBrowser();

    FluentWait<WebDriver> getWait();

    Element getElement(By by);

    Element $(String css);

    Element $i(String id);

    Element $n(String name);

    Element $x(String xpath);

    ElementsList<Element> getElements(By by);

    ElementsList<Element> $$(String css);

    ElementsList<Element> $$i(String id);

    ElementsList<Element> $$n(String name);

    ElementsList<Element> $$x(String xpath);

    void initElements();

    void checkUrl(String url2Check);
}
