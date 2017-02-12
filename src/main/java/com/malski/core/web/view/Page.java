package com.malski.core.web.view;

import com.malski.core.web.annotations.PageCheck;
import com.malski.core.web.control.LazySearchContext;
import com.malski.core.web.factory.LazyPageFactory;
import org.apache.log4j.Logger;
import org.openqa.selenium.SearchContext;

/**
 * Class which is representing displayed page and allow to performing basic actions on it
 */
public abstract class Page implements LazySearchContext, View {
    protected final Logger log = Logger.getLogger(getClass());
    private String url;
    private boolean isOpened = true;

    public Page() {
        getBrowser().waitUntilPageLoaded();
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