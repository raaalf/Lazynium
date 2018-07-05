package com.malski.lazynium.web.view;

import com.malski.lazynium.web.control.LazySearchContext;
import com.malski.lazynium.web.factory.LazyPageFactory;
import org.apache.log4j.Logger;
import org.openqa.selenium.SearchContext;

/**
 * Class which is representing displayed page and allow to performing basic actions on it
 */
public class Page implements LazySearchContext, View {
    protected final Logger log = Logger.getLogger(getClass());
    private String url;

    public Page() {
        browser().waitUntilPageLoaded();
        initElements();
    }

    @Override
    public void initElements() {
        LazyPageFactory.initElements(this);
    }

    @Override
    public SearchContext searchContext() {
        return browser();
    }

    @Override
    public boolean refresh() {
        if(browser().refresh()) {
            initElements();
            return true;
        }
        return false;
    }

    public String url() {
        return url;
    }

    public <T extends Page> T cast(Class<T> cls) {
        return cls.cast(this);
    }
}