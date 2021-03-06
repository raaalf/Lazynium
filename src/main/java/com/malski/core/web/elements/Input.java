package com.malski.core.web.elements;

import com.malski.core.web.factory.LazyLocator;
import org.openqa.selenium.WebElement;

public class Input extends Element {

    public Input(LazyLocator locator) {
        super(locator);
    }

    public Input(LazyLocator locator, WebElement element) {
        super(locator, element);
    }

    public void fill(String text) {
        this.clear();
        this.sendKeys(text);
    }
}
