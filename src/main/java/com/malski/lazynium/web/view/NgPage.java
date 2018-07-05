package com.malski.lazynium.web.view;

import com.malski.lazynium.web.control.Browser;
import com.malski.lazynium.web.elements.Element;
import com.malski.lazynium.web.factory.LazyLocator;
import org.openqa.selenium.support.FindBy;
import com.paulhammant.ngwebdriver.NgWebDriver;

@FindBy(css = "[ng-app],[ng:app],[ng-controller],[ng:controller]")
public class NgPage extends Page {
    private NgWebDriver ngDriver;

    public NgPage() {
        super();
        this.ngDriver = new NgWebDriver(browser().jsExecutor());
    }

    @Override
    public Browser browser() {
        return super.browser();
    }

    public NgWebDriver getNgDriver() {
        return this.ngDriver;
    }

    public void withRootSelector(LazyLocator locator) {
        ngDriver.withRootSelector(locator.getSelector().getUsing());
    }

    public void mutate(Element element, final String variable, final String value) {
        ngDriver.mutate(element, variable, value);
    }

    public String retrieveJson(Element element, final String variable) {
        return ngDriver.retrieveJson(element.getWrappedElement(), variable);
    }

    public Object retrieve(Element element, final String variable) {
        return ngDriver.retrieve(element.getWrappedElement(), variable);
    }

    public String retrieveAsString(Element element, final String variable) {
        return ngDriver.retrieveAsString(element.getWrappedElement(), variable);
    }

    public Long retrieveAsLong(Element element, final String variable) {
        return ngDriver.retrieveAsLong(element.getWrappedElement(), variable);
    }

    public void waitForAngular() {
        ngDriver.waitForAngularRequestsToFinish();
    }

    public String locationAbsUrl() {
        return ngDriver.getLocationAbsUrl();
    }

    public Object evaluateScript(Element element, String script) {
        return ngDriver.evaluateScript(element.getWrappedElement(), script);
    }
}