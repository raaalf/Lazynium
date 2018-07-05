package com.malski.lazynium.web.control;

import com.malski.lazynium.utils.TestContext;
import com.malski.lazynium.web.elements.Element;
import com.malski.lazynium.web.factory.Selector;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.openqa.selenium.support.How.CSS;

public class JsExecutor implements JavascriptExecutor {
    private JavascriptExecutor executor;

    public JsExecutor(WebDriver driver) {
        executor = (JavascriptExecutor) driver;
    }

    @Override
    public Object executeScript(String script, Object... objects) {
        return executor.executeScript(script, objects);
    }

    @Override
    public Object executeAsyncScript(String script, Object... objects) {
        return executor.executeAsyncScript(script, objects);
    }

    @SuppressWarnings("unchecked")
    public <T> T execute(String script, Object... objects) {
        return (T) this.executeScript(script, objects);
    }

    @SuppressWarnings("unchecked")
    public <T> T executeAsync(String script, Object... objects) {
        return (T) this.executeAsyncScript(script, objects);
    }

    public void check(Element element, boolean state) {
        execute(String.format("arguments[0].checked=%b;", state), element.getWrappedElement());
    }

    public void setStyle(Element element, String style) {
        execute("arguments[0].setAttribute('style', arguments[1]);", element, style);
    }

    public String getStyle(Element element) {
        return execute("return arguments[0].getAttribute('style'); ", element);
    }

    public void click(Element element) {
        execute("arguments[0].click();", element.getWrappedElement());
    }

    public void focus(Element element) {
        execute("arguments[0].focus();", element.getWrappedElement());
    }

    public void hover(Element element) {
        String jsSelector = getSelectorForJs(element.selector());
        if (useCssExecutor(element.selector())) {
            callCssSelector(jsSelector, "hover()");
        } else {
            String script = "var element = arguments[0];"
                    + "var mouseEventObj = document.createEvent('MouseEvents');"
                    + "mouseEventObj.initEvent( 'mouseover', true, true );"
                    + "element.dispatchEvent(mouseEventObj);";
            execute(script, element.getWrappedElement());
        }
    }

    public String getCurrentFrameName() {
        return execute("return self.name");
    }

    public void setValue(Element element, String value) {
        String script = element.selector().getHow().equals(CSS) ? String.format("value=%s", value) : String.format("val('%s').change()", value);
        performAction(element.selector(), script);
    }

    public void moveBack() {
        execute("window.history.back()");
    }

    public void moveForward() {
        execute("window.history.forward()");
    }

    public void closeWindow() {
        execute("window.closeIfOpen()");
    }

    public String getReadyState() {
        return execute("return document.readyState").toString();
    }

    public void scrollIntoView(Element element) {
        execute("return arguments[0].scrollIntoView(true);", element.getWrappedElement());
    }

    public void scrollTo(Element element) {
        Point location = element.getLocation();
        execute("window.scrollTo(" + location.getX() + "," + location.getY() + ");", element);
    }

    public long getScrollHeight() {
        String scrollPositionYScript =
                "var pageY;" +
                        "if (typeof(window.pageYOffset) == 'number') {" +
                        "    pageY = window.pageYOffset;" +
                        "} else {" +
                        "    pageY = document.documentElement.scrollTop;" +
                        "}" +
                        "return pageY;";
        Double height = Double.parseDouble(execute(scrollPositionYScript).toString());
        return height.longValue();
    }

    public long getScrollWidth() {
        String scrollPositionXScript =
                "var pageX;" +
                        "if (typeof(window.pageXOffset) == 'number') {" +
                        "    pageX = window.pageXOffset;" +
                        "} else {" +
                        "    pageX = document.documentElement.scrollLeft;" +
                        "}" +
                        "return pageX;";
        Double width = Double.parseDouble(execute(scrollPositionXScript));
        return width.longValue();
    }

    public long getJsClientHeight() {
        return execute("return document.documentElement.clientHeight;");
    }

    public long getJsClientWidth() {
        return execute("return document.documentElement.clientWidth;");
    }

    public static String loadScriptFromFile(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(TestContext.config().resourceDirPath() + File.separator + "js" + File.separator + fileName)));
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
                return String.format("%s", selector.getUsing());
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
            case TAG_NAME:
                return true;
            case XPATH:
            case LINK_TEXT:
            case PARTIAL_LINK_TEXT:
                return false;
            default:
                throw new InvalidSelectorException(String.format("Selector type '%s' is not supported", selector.getHow().toString()));
        }
    }

    private String getExecutorType(Selector selector) {
        if (useCssExecutor(selector)) {
            return "css";
        } else {
            return "xpath";
        }
    }

    private void performAction(Selector selector, String action) {
        String jsSelector = getSelectorForJs(selector);
        if (useCssExecutor(selector)) {
            callCssSelector(jsSelector, action);
        } else {
            callXpathSelector(jsSelector, action);
        }
    }

    private void callXpathSelector(String xpathSelector, String action) {
        execute(String.format("document.evaluate(\"%s\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.%s", xpathSelector, action));
    }

    private void callCssSelector(String cssSelector, String action) {
        try {
            execute(String.format("$(\"%s\").%s", cssSelector, action));
        } catch (Exception e) {
            execute(String.format("document.querySelector(\"%s\").%s", cssSelector, action));
        }
    }
}
