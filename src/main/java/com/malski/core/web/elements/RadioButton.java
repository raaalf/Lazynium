package com.malski.core.web.elements;

import com.malski.core.web.elements.states.CheckableState;
import com.malski.core.web.elements.waits.CheckableWait;
import com.malski.core.web.factory.LazyLocator;
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