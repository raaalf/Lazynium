package com.malski.core.web.page;

import com.malski.core.web.Browser;
import com.malski.core.web.elements.Element;
import com.malski.core.web.elements.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

public interface WebView {
    Browser getBrowser();

    FluentWait<WebDriver> getWait();

    Element getElement(By by);

    Element $(String css);

    Element $i(String id);

    Element $n(String name);

    Element $x(String xpath);

    Elements<Element> getElements(By by);

    Elements<Element> $$(String css);

    Elements<Element> $$i(String id);

    Elements<Element> $$n(String name);

    Elements<Element> $$x(String xpath);
}
