package com.malski.core.web.page;

import com.malski.core.web.elements.Element;
import org.openqa.selenium.By;

public abstract class Frame extends Module {

    public Frame(By locator) {
        super(locator);
    }

    public Frame(Element rootElement) {
        super(rootElement);
    }

    public Frame performAction(Runnable function) {
        getBrowser().switchTo().frame(getRoot());
        function.run();
        getBrowser().switchTo().defaultContent();
        return this;
    }
}