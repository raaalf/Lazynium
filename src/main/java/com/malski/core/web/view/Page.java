package com.malski.core.web.view;

import com.malski.core.utils.TestContext;
import com.malski.core.web.annotations.PageCheck;
import com.malski.core.web.control.Browser;
import com.malski.core.web.control.LazySearchContext;
import com.malski.core.web.factory.LazyPageFactory;
import org.apache.log4j.Logger;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

/**
 * Class which is representing displayed page and allow to performing basic actions on it
 */
public abstract class Page extends LazySearchContext implements View {
    protected final Logger log = Logger.getLogger(getClass());
    private String url;
    private boolean isOpened = true;

    public Page() {
        getBrowser().waitForPageToLoad();
        initElements();
        this.handlePageInfo();
    }

    @Override
    public void initElements() {
        LazyPageFactory.initElements(this);
    }

    @Override
    public SearchContext getContext() {
        return getBrowser();
    }

    @Override
    public boolean refresh() {
        if(getBrowser().refresh()) {
            initElements();
            return true;
        }
        return false;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public Browser getBrowser() {
        return TestContext.getBrowser();
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
            PageCheck pageCheck = this.getClass().getAnnotation(PageCheck.class);
            url = pageCheck.url();
            /*
            TODO there is a problem that validation now is done during the class initialization so not always page is open at that point.
                 we have to came up with so generic solution for that
            */
        }
    }
}