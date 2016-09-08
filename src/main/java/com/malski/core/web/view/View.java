package com.malski.core.web.view;

import com.malski.core.web.control.Browser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

public interface View {
    Browser getBrowser();

    FluentWait<WebDriver> getWait();

    void initElements();
}
