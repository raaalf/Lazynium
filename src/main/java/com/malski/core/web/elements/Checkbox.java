package com.malski.core.web.elements;

import com.malski.core.web.elements.states.CheckableState;
import com.malski.core.web.elements.waits.CheckableWait;
import com.malski.core.web.factory.LazyLocator;
import org.openqa.selenium.WebElement;

public class Checkbox extends Element implements CheckableState, CheckableWait {

    public Checkbox(LazyLocator locator) {
        super(locator);
    }

    public Checkbox(LazyLocator locator, WebElement element) {
        super(locator, element);
    }

    public void select(boolean state) {
        if (state) {
            check();
        } else {
            uncheck();
        }
    }

    public void toggle() {
        getWrappedElement().click();
    }

    public void check() {
        if (!isChecked()) {
            toggle();
        }
    }

    public void uncheck() {
        if (isChecked()) {
            toggle();
        }
    }
}
