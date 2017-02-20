package com.malski.core.web.view;

import com.malski.core.web.control.LazySearchContext;
import org.openqa.selenium.SearchContext;

public class Frame extends Component {
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
        browser().switchTo().frame(root().getWrappedElement());
        frameContext = browser();
        inFrame = true;
    }

    public void switchOut() {
        if (inFrame) {
            forceSwitchOut();
        }
    }

    public void forceSwitchOut() {
        browser().switchTo().parentFrame();
        frameContext = root();
        inFrame = false;
    }

    @Override
    public SearchContext searchContext() {
        return frameContext;
    }
}