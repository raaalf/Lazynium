package com.malski.core.web.page.impl;

import com.malski.core.cucumber.TestContext;
import com.malski.core.web.annotations.PageCheck;
import com.malski.core.web.base.Browser;
import com.malski.core.web.base.LazySearchContext;
import com.malski.core.web.base.LazySearchContextImpl;
import com.malski.core.web.factory.LazyPageFactory;
import com.malski.core.web.page.api.WebView;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

/**
 * Class which is representing displayed page and allow to performing basic actions on it
 */
public abstract class Page extends LazySearchContextImpl implements WebView, LazySearchContext {
    protected final Logger log = Logger.getLogger(getClass());
    private final Browser browser;
    private String url;
    private boolean isOpened = true;

    public Page() {
        this.browser = TestContext.getBrowser();
        LazyPageFactory.initElements(this, this);
        browser.waitForPageToLoad();
        this.handlePageInfo();
    }

    @Override
    public SearchContext getSearchContext() {
        if(super.getSearchContext() == null) {
            setSearchContext(getBrowser());
        }
        return super.getSearchContext();
    }

    @Override
    public void refresh() {
        getBrowser().refresh();
        setSearchContext(getBrowser());
        LazyPageFactory.initElements(this, this);
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

    public <T extends Page> T cast(Class<T> cls) {
        return cls.cast(this);
    }

    protected void handlePageInfo() {
        if(this.getClass().isAnnotationPresent(PageCheck.class) && isOpened) {
            PageCheck pageValidator = this.getClass().getAnnotation(PageCheck.class);
            url = pageValidator.url();
            /*
            TODO there is a problem that validation now is done during the class initialization so not always page is open at that point.
                 we have to came up with so generic solution for that
            */
//            if(!StringUtils.isBlank(pageValidator.title())) {
//                assertThat(pageValidator.title(), is(getBrowser().getTitle()));
//            }
//            if(!pageValidator.how().equals(How.UNSET)) {
//                String text = getElement(new Selector(pageValidator.how(), pageValidator.using())).getText();
//                if(!StringUtils.isBlank(pageValidator.contains())) {
//                    assertThat(pageValidator.contains(), containsString(text));
//                } else if(!StringUtils.isBlank(pageValidator.equals())) {
//                    assertThat(pageValidator.equals(), is(text));
//                }
//            }
        }
    }
}