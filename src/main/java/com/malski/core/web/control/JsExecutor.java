package com.malski.core.web.control;

import com.malski.core.utils.TestContext;
import com.malski.core.web.elements.Element;
import com.malski.core.web.factory.Selector;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.openqa.selenium.support.How.CSS;

public class JsExecutor {
    private JavascriptExecutor executor;

    public JsExecutor(WebDriver driver) {
        executor = (JavascriptExecutor) driver;
    }

    @SuppressWarnings("unchecked")
    public <T> T executeScript(String script, Object... objects) {
        return (T) executor.executeScript(script, objects);
    }

    @SuppressWarnings("unchecked")
    public <T> T executeAsyncScript(String script, Object... objects) {
        return (T) executor.executeAsyncScript(script, objects);
    }

    public void check(Element element, boolean state) {
        executeScript(String.format("arguments[0].checked=%b;", state), element.getWrappedElement());
    }

    public void setStyle(Element element, String style) {
        executeScript("arguments[0].setAttribute('style', arguments[1]);", element, style);
    }

    public String getStyle(Element element) {
        return (String) executeScript("return arguments[0].getAttribute('style'); ", element);
    }

    public void dragAndDrop(Element source, Element target) {
        try {
            String dndScript = loadScriptFromFile("dnd.js");
            injectScript(dndScript);
            executeScript("DndSimulator.simulate(arguments[0], arguments[1])", source.getWrappedElement(), target.getWrappedElement());
        } catch (IOException e) {
            throw new RuntimeException("Js drag & drop exception", e);
        }
    }

    public void dragAndDropBySelectors(Element source, Element target) {
        try {
            String dndScript = loadScriptFromFile("dnd.js");
            injectScript(dndScript);
            String sourceSelector = getSelectorForJs(source.selector());
            String targetSelector = getSelectorForJs(target.selector());
            String sourceSelectorType = getExecutorType(source.selector());
            String targetSelectorType = getExecutorType(target.selector());
            String script = String.format("DndSimulator.simulate(\"%s\", \"%s\", \"%s\", \"%s\")", sourceSelector, targetSelector, sourceSelectorType, targetSelectorType);
            executeScript(script);
        } catch (IOException e) {
            throw new RuntimeException("Js drag & drop exception", e);
        }
    }

    public void click(Element element) {
        executeScript("arguments[0].click();", element.getWrappedElement());
    }

    public void focus(Element element) {
        executeScript("arguments[0].focus();", element.getWrappedElement());
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
            executeScript(script, element.getWrappedElement());
        }
    }

    public String getCurrentFrameName() {
        return executeScript("return self.name");
    }

    public void setValue(Element element, String value) {
        String script = element.selector().getHow().equals(CSS) ? String.format("value=%s", value) : String.format("val('%s').change()", value);
        performAction(element.selector(), script);
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

    public void scrollIntoView(Element element) {
        executeScript("return arguments[0].scrollIntoView(true);", element.getWrappedElement());
    }

    public void scrollTo(Element element) {
        Point location = element.getLocation();
        executeScript("window.scrollTo(" + location.getX() + "," + location.getY() + ");", element);
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
        Double height = Double.parseDouble(executeScript(scrollPositionYScript).toString());
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
        Double width = Double.parseDouble(executeScript(scrollPositionXScript));
        return width.longValue();
    }

    public long getJsClientHeight() {
        return executeScript("return document.documentElement.clientHeight;");
    }

    public long getJsClientWidth() {
        return executeScript("return document.documentElement.clientWidth;");
    }

    public static String loadScriptFromFile(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(TestContext.config().resourceDirPath() + File.separator + "js" + File.separator + fileName)));
    }

    public void injectScript(String script) throws IOException {
        String injectingScript = loadScriptFromFile("inject_script.js");
        executeScript(injectingScript, script);
    }

    public void injectScriptSrc(String scriptSrc) throws IOException {
        String injectingScript = loadScriptFromFile("inject_script_src.js");
        executeScript(injectingScript, scriptSrc);
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
