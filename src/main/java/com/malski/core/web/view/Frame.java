package com.malski.core.web.view;

import com.malski.core.web.control.LazySearchContext;
import com.malski.core.web.factory.LazyPageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;


public abstract class Frame extends Module {
    private boolean switchedIn = false;
    public Frame() {
        super();
    }

    @Override
    public void initElements() {
        LazyPageFactory.initElements(switchIn(), this);
        switchOut();
    }

    public LazySearchContext switchIn() {
        if(!switchedIn) {
            WebDriver frameHandler = getBrowser().switchTo().frame(getRoot());
            switchedIn = true;
            return new LazySearchContext() {
                @Override
                public List<WebElement> simpleFindElements(By by) {
                    return frameHandler.findElements(by);
                }

                @Override
                public WebElement simpleFindElement(By by) {
                    return frameHandler.findElement(by);
                }

                @Override
                public boolean refresh() {
                    return false;
                }
            };
        }
        return getRoot();
    }

    public void switchOut() {
        if(switchedIn) {
            getBrowser().switchTo().parentFrame();
            switchedIn = false;
        }
    }

    @Override
    public List<WebElement> simpleFindElements(By by) {
        List<WebElement> elements = switchIn().findElements(by);
        switchOut();
        return elements;
    }

    @Override
    public WebElement simpleFindElement(By by) {
        WebElement element = switchIn().findElement(by);
        switchOut();
        return element;
    }

    @Override
    public boolean refresh() {
        boolean result = getRoot().refresh();
        initElements();
        return result;
    }

}