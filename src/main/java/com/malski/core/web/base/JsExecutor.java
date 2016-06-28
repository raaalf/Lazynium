package com.malski.core.web.base;

import com.malski.core.web.elements.api.Element;
import com.malski.core.web.factory.Selector;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.How;

public class JsExecutor {
    private JavascriptExecutor executor;

    public JsExecutor(WebDriver driver) {
        executor = (JavascriptExecutor) driver;
    }

    public Object executeScript(String script, Object... objects) {
        return executor.executeScript(script, objects);
    }

    public void check(Element element, boolean state) {
        performAction(element.getSelector(), String.format("checked=%b", state));
    }

    public void click(Element element) {
        performAction(element.getSelector(), "click()");
    }

    public void focus(Element element) {
        performAction(element.getSelector(), "focus()");
    }

    public void hover(Element element) {
        String jsSelector = getSelectorForJs(element.getSelector());
        if(useCssExecutor(element.getSelector())) {
            callCssSelector(jsSelector, "hover()");
        } else {
            String script = "var element = arguments[0];"
                    + "var mouseEventObj = document.createEvent('MouseEvents');"
                    + "mouseEventObj.initEvent( 'mouseover', true, true );"
                    + "element.dispatchEvent(mouseEventObj);";
            executeScript(script, element.getWrappedElement());
        }
    }

    public void setValue(Element element, String value) {
        String script = element.getSelector().getHow().equals(How.CSS) ? String.format("value=%s", value) : String.format("val('%s').change()", value);
        performAction(element.getSelector(), script);
    }

    public void moveBack() {
        executeScript("window.history.back()");
    }

    public void moveForward() {
        executeScript("window.history.forward()");
    }

    public void closeWindow() {
        executeScript("window.closeIfOpen()");
    }

    public String getReadyState() {
        return executeScript("return document.readyState").toString();
    }

    public void scrollIntoView(WebElement element) {
        executeScript("arguments[0].scrollIntoView(true);", element);
    }

    private String getSelectorForJs(Selector selector) {
        switch (selector.getHow()) {
            case CSS:
                return String.format("%s", selector.getUsing());
            case ID:
                return String.format("#%s", selector.getUsing());
            case XPATH:
                return String.format("%s", selector.getUsing());
            case NAME:
                return String.format("[name=%s]", selector.getUsing());
            case CLASS_NAME:
                return String.format(".%s", selector.getUsing());
            case TAG_NAME:
                return String.format(".//%s", selector.getUsing());
            case LINK_TEXT:
                return String.format(".//a[normalize-space()='%s']", selector.getUsing());
            case PARTIAL_LINK_TEXT:
                return String.format(".//a[contains(.,'%s')]", selector.getUsing());
            default:
                throw new InvalidSelectorException(String.format("Selector type '%s' is not supported", selector.getHow().toString()));
        }
    }

    private boolean useCssExecutor(Selector selector) {
        switch (selector.getHow()) {
            case CSS:
            case ID:
            case NAME:
            case CLASS_NAME:
                return true;
            case XPATH:
            case TAG_NAME:
            case LINK_TEXT:
            case PARTIAL_LINK_TEXT:
                return false;
            default:
                throw new InvalidSelectorException(String.format("Selector type '%s' is not supported", selector.getHow().toString()));
        }
    }

    private void performAction(Selector selector, String action) {
        String jsSelector = getSelectorForJs(selector);
        if(useCssExecutor(selector)) {
            callCssSelector(jsSelector, action);
        } else {
            callXpathSelector(jsSelector, action);
        }
    }

    private void callXpathSelector(String xpathSelector, String action) {
        executeScript(String.format("document.evaluate(\"%s\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.%s", xpathSelector, action));
    }

    private void callCssSelector(String cssSelector, String action) {
        try {
            executeScript(String.format("$(\"%s\").%s", cssSelector, action));
        } catch (Exception e) {
            executeScript(String.format("document.querySelector(\"%s\").%s", cssSelector, action));
        }
    }
}
