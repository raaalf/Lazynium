package com.malski.core.web.view;

import com.malski.core.utils.TestContext;
import com.malski.core.web.control.Browser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

public interface View {
    default Browser getBrowser() {
        return TestContext.getBrowser();
    }

    default FluentWait<WebDriver> getWait() {
        return getBrowser().getWait();
    }

    void initElements();
}
