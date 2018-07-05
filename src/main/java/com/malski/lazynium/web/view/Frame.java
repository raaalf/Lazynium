package com.malski.lazynium.web.view;

import com.malski.lazynium.web.control.Browser;
import com.malski.lazynium.web.control.LazySearchContext;
import com.malski.lazynium.web.factory.LazyLocator;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.List;

public class Frame extends Component {
    private boolean isInside = false;
    private LazySearchContext frameContext;

    public Frame() {
        super();
    }

    public void switchIn() {
        if (!isInside) {
            forceSwitchIn();
        }
    }

    public void forceSwitchIn() {
        browser().switchTo().frame(root().getWrappedElement());
        frameContext = browser();
        isInside = true;
    }

    public void switchOut() {
        if (isInside) {
            forceSwitchOut();
        }
    }

    public void forceSwitchOut() {
        browser().switchTo().parentFrame();
        frameContext = root();
        isInside = false;
    }

    @Override
    public WebElement findElement(By by) {
        return super.findElement(by);
    }

    @Override
    public List<WebElement> findElements(By by) {
        return super.findElements(by);
    }

    @Override
    public LazyLocator locator() {
        return super.locator();
    }

    @Override
    public Browser browser() {
        return super.browser();
    }

    @Override
    public void setRoot(LazyLocator locator) {
        super.setRoot(locator);
    }

    @Override
    public void initElements() {
        super.initElements();
    }

    @Override
    public boolean refresh() {
        switchOut();
        boolean result = super.refresh();
        switchIn();
        return result;
    }

    @Override
    public SearchContext searchContext() {
        return frameContext;
    }
}