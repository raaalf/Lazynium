package com.malski.core.web;

import com.malski.core.web.elements.Element;
import com.malski.core.web.factory.Selector;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import static com.malski.core.web.factory.Selector.Type.CSS;

public class JsExecutor {
    private JavascriptExecutor executor;

    public JsExecutor(Browser browser) {
        executor = (JavascriptExecutor) browser.getWebDriver();
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

    public void setValue(Element element, String value) {
        String script = element.getSelector().getType().equals(CSS) ? String.format("value=%s", value) : String.format("val('%s').change()", value);
        performAction(element.getSelector(), script);
    }

    public void moveBack() {
        executeScript("window.history.back()");
    }

    public void moveForward() {
        executeScript("window.history.forward()");
    }

    public void closeWindow() {
        executeScript("window.close()");
    }

    public String getReadyState() {
        return executeScript("return document.readyState").toString();
    }

    public void scrollIntoView(WebElement element) {
        executeScript("arguments[0].scrollIntoView(true);", element);
    }

    private void performAction(Selector selector, String action) {
        switch (selector.getType()) {
            case CSS:
                callCssSelector("%s", selector.getValue(), action);
                break;
            case ID:
                callCssSelector("#%s", selector.getValue(), action);
                break;
            case XPATH:
                callXpathSelector("%s", selector.getValue(), action);
                break;
            case NAME:
                callCssSelector("[name=%s]", selector.getValue(), action);
                break;
            case CLASS_NAME:
                callCssSelector(".%s", selector.getValue(), action);
                break;
            case TAG_NAME:
                callXpathSelector(".//%s", selector.getValue(), action);
                break;
            case LINK_TEXT:
                callXpathSelector(".//a[normalize-space()='%s']", selector.getValue(), action);
                break;
            case PARTIAL_LINK_TEXT:
                callXpathSelector(".//a[contains(.,'%s')]", selector.getValue(), action);
                break;
            default:
                throw new InvalidSelectorException(String.format("Selector type '%s' is not supported", selector.getType().toString()));
        }

    }

    private void callXpathSelector(String xpathBase, String xpathValue, String action) {
        String xpath = String.format(xpathBase, xpathValue);
        executeScript(String.format("document.evaluate(\"%s\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.%s", xpath, action));
    }

    private void callCssSelector(String cssTemplate, String cssValue, String action) {
        String css = String.format(cssTemplate, cssValue);
        try {
            executeScript(String.format("$(\"%s\").%s", css, action));
        } catch (Exception e) {
            executeScript(String.format("document.querySelector(\"%s\").%s", css, action));
        }
    }
}
