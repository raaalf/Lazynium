package com.malski.lazynium.web.elements;

import com.malski.lazynium.web.elements.states.CheckableState;
import com.malski.lazynium.web.elements.waits.CheckableWait;
import com.malski.lazynium.web.factory.LazyLocator;
import org.openqa.selenium.WebElement;

public class RadioButton extends Element implements CheckableState, CheckableWait {

    public RadioButton(LazyLocator locator) {
        super(locator);
    }

    public RadioButton(LazyLocator locator, WebElement element) {
        super(locator, element);
    }

    public void check() {
        getWrappedElement().click();
    }
}