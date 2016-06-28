package com.malski.core.web.page.impl;

import com.malski.core.cucumber.TestContext;
import com.malski.core.web.elements.api.Element;
import com.malski.core.web.factory.LazyLocator;
import com.malski.core.web.factory.LazyPageFactory;
import com.malski.core.web.page.api.Frame;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FrameImpl extends WebModuleImpl implements Frame {

    public FrameImpl() {
    }

    public FrameImpl(LazyLocator locator) {
        this.browser = TestContext.getBrowser();
        this.rootElement = getBrowser().getElement(locator);
        initElements();
    }

    public FrameImpl(By by) {
        this.browser = TestContext.getBrowser();
        this.rootElement = getBrowser().getElement(by);
        initElements();
    }

    public FrameImpl(Element rootElement) {
        this.browser = TestContext.getBrowser();
        this.rootElement = rootElement;
        initElements();
    }

    @Override
    public void switchIn() {
        WebDriver context = getBrowser().switchTo().frame(getRoot());
        setSearchContext(context);
    }

    @Override
    public void switchOut() {
        getBrowser().switchTo().parentFrame();
        setSearchContext(getRoot());
    }

    private void initElements() {
        switchIn();
        LazyPageFactory.initElements(getSearchContext(), this);
        switchOut();
    }
}
