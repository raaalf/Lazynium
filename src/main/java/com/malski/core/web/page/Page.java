package com.malski.core.web.page;

import com.malski.core.cucumber.TestContext;
import com.malski.core.web.Browser;
import com.malski.core.web.JsExecutor;
import com.malski.core.web.ScreenShooter;
import com.malski.core.web.annotations.PageInfo;
import com.malski.core.web.conditions.WaitConditions;
import com.malski.core.web.elements.Element;
import com.malski.core.web.elements.Elements;
import com.malski.core.web.factory.LazyPageFactory;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;

/**
 * Class which is representing displayed page and allow to performing basic actions on it
 */
public abstract class Page implements WebView {
    private final Browser browser;
    private final JsExecutor jsExecutor;
    private final ScreenShooter shooter;
    private String url;

    public Page() {
        this.browser = TestContext.getBrowser();
        this.jsExecutor = TestContext.getJsExecutor();
        this.shooter = TestContext.getScreenShooter();
        LazyPageFactory.initElements(getBrowser(), this);
        this.waitToLoad();
        this.handlePageInfo();
    }

    public String getUrl() {
        return url;
    }

    @Override
    public Browser getBrowser() {
        return this.browser;
    }

    @Override
    public FluentWait<WebDriver> getWait() {
        return getBrowser().getWait();
    }

    @Override
    public Element getElement(By by) {
        return getBrowser().getElement(by);
    }

    @Override
    public Element $(String css) {
        return getElement(By.cssSelector(css));
    }

    @Override
    public Element $i(String id) {
        return getElement(By.id(id));
    }

    @Override
    public Element $n(String name) {
        return getElement(By.name(name));
    }

    @Override
    public Element $x(String xpath) {
        return getElement(By.xpath(xpath));
    }

    @Override
    public Elements<Element> getElements(By by) {
        return getBrowser().getElements(by);
    }

    @Override
    public Elements<Element> $$(String css) {
        return getElements(By.cssSelector(css));
    }

    @Override
    public Elements<Element> $$i(String id) {
        return getElements(By.id(id));
    }

    @Override
    public Elements<Element> $$n(String name) {
        return getElements(By.name(name));
    }

    @Override
    public Elements<Element> $$x(String xpath) {
        return getElements(By.xpath(xpath));
    }

    public <T extends Page> T as(Class<T> cls) {
        return cls.cast(this);
    }

    public <T extends Page> T nextPage(Class<T> cls) {
        //TODO can take screenshot each time we will switch page, worth to thing about this
        return LazyPageFactory.initElements(getBrowser(), cls);
    }

    public void takeScreenShot(String fileName) {
        shooter.getScreenShot(fileName);
    }

    private void handlePageInfo() {
        if(this.getClass().isAnnotationPresent(PageInfo.class)) {
            PageInfo pageInfo = this.getClass().getAnnotation(PageInfo.class);
            url = pageInfo.url();
            if(!StringUtils.isEmpty(pageInfo.check())) {
                Assertions.assertThat(browser.getCurrentUrl()).matches(pageInfo.check());
            }
        }
    }

    private void waitToLoad() {
        try {
            getWait().until(WaitConditions.pageLoaded(browser.getWebDriver()));
        } catch (TimeoutException | NullPointerException e) {
            //sometimes PageInfo remains in Interactive mode and never becomes Complete, then we can still try to access the controls
            String state = jsExecutor.getReadyState();
            if (!state.equalsIgnoreCase("interactive")) throw e;
        } catch (WebDriverException e) {
            String state = jsExecutor.getReadyState();
            if (!(state.equalsIgnoreCase("complete") || state.equalsIgnoreCase("loaded"))) throw e;
        }
    }
}