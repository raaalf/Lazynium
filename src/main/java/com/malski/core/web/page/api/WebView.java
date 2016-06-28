package com.malski.core.web.page.api;

import com.malski.core.web.base.Browser;
import com.malski.core.web.base.LazySearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

public interface WebView extends LazySearchContext {
    Browser getBrowser();

    FluentWait<WebDriver> getWait();
}
