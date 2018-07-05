package com.malski.lazynium.web.elements;

import com.malski.lazynium.web.factory.LazyLocator;
import org.openqa.selenium.WebElement;

public class Label extends Element {

    public Label(LazyLocator locator) {
        super(locator);
    }

    public Label(LazyLocator locator, WebElement element) {
        super(locator, element);
    }

    public String getFor() {
        return this.getAttribute("for");
    }
}
