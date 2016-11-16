package com.malski.core.web.view;

import com.malski.core.web.control.LazySearchContext;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public abstract class Frame extends Module {
    private boolean inFrame = false;
    private LazySearchContext frameContext;

    public Frame() {
        super();
    }

    public void switchIn() {
        if (!inFrame) {
            forceSwitchIn();
        }
    }

    public void forceSwitchIn() {
        WebElement root = getRoot().getWrappedElement();
        getBrowser().switchTo().frame(root);
        frameContext = getBrowser();
        inFrame = true;
    }

    public void switchOut() {
        if (inFrame) {
            forceSwitchOut();
        }
    }

    public void forceSwitchOut() {
        getBrowser().switchTo().parentFrame();
        frameContext = getRoot();
        inFrame = false;
    }

    @Override
    public SearchContext getContext() {
        return frameContext;
    }
}