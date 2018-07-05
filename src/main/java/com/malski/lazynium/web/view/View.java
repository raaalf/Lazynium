package com.malski.lazynium.web.view;

import com.malski.lazynium.utils.TestContext;
import com.malski.lazynium.web.control.Browser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

public interface View {
    default Browser browser() {
        return TestContext.browser();
    }

    default FluentWait<WebDriver> getWait() {
        return browser().getWait();
    }

    default FluentWait<WebDriver> getWait(long timeout) {
        return browser().getWait(timeout);
    }

    void initElements();
}
