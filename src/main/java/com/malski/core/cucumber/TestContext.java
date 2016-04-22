package com.malski.core.cucumber;

import com.malski.core.web.Browser;
import com.malski.core.web.JsExecutor;
import com.malski.core.web.ScreenShooter;
import com.malski.core.web.page.Page;

import java.util.HashMap;
import java.util.Map;

public class TestContext {
    //browser
    private static final ThreadLocal<Browser> BROWSER_THREAD_LOCAL;

    static {
        BROWSER_THREAD_LOCAL = new ThreadLocal<>();
    }

    public static Browser getBrowser() {
        Browser browser = BROWSER_THREAD_LOCAL.get();
        if (browser == null) throw new IllegalStateException("Browser not set on the TestContext");
        return browser;
    }

    public static void setBrowser(Browser browser) {
        BROWSER_THREAD_LOCAL.set(browser);
        setJsExecutor(new JsExecutor(browser));
        setScreenShooter(new ScreenShooter(browser));
    }

    //js executor
    private static final ThreadLocal<JsExecutor> JS_EXECUTOR_THREAD_LOCAL;

    static {
        JS_EXECUTOR_THREAD_LOCAL = new ThreadLocal<>();
    }

    public static JsExecutor getJsExecutor() {
        JsExecutor jsExecutor = JS_EXECUTOR_THREAD_LOCAL.get();
        if (jsExecutor == null) throw new IllegalStateException("JsExecutor not set on the TestContext");
        return jsExecutor;
    }

    private static void setJsExecutor(JsExecutor jsExecutor) {
        JS_EXECUTOR_THREAD_LOCAL.set(jsExecutor);
    }

    //screen shooter
    private static final ThreadLocal<ScreenShooter> SCREEN_SHOOTER_THREAD_LOCAL;

    static {
        SCREEN_SHOOTER_THREAD_LOCAL = new ThreadLocal<>();
    }

    public static ScreenShooter getScreenShooter() {
        ScreenShooter screenShooter = SCREEN_SHOOTER_THREAD_LOCAL.get();
        if (screenShooter == null) throw new IllegalStateException("JsExecutor not set on the TestContext");
        return screenShooter;
    }

    private static void setScreenShooter(ScreenShooter screenShooter) {
        SCREEN_SHOOTER_THREAD_LOCAL.set(screenShooter);
    }

    //page
    private static final ThreadLocal<Page> PAGE_THREAD_LOCAL;

    static {
        PAGE_THREAD_LOCAL = new ThreadLocal<>();
    }

    public static <T extends Page> T getPage() {
        return (T) PAGE_THREAD_LOCAL.get();
    }

    public static <T extends Page> void setPage(T page) {
        PAGE_THREAD_LOCAL.set(page);
    }

    private static final ThreadLocal<Map<Object, Object>> CONTAINER_THREAD_LOCAL;

    static {
        CONTAINER_THREAD_LOCAL = new ThreadLocal<>();
        CONTAINER_THREAD_LOCAL.set(new HashMap<>());
    }

    public static Map<Object, Object> getContainer() {
        return CONTAINER_THREAD_LOCAL.get();
    }

    public static void addToConainer(Object key, Object value) {
        CONTAINER_THREAD_LOCAL.get().put(key, value);
    }

    public static void removeFromConainer(Object key) {
        CONTAINER_THREAD_LOCAL.get().remove(key);
    }
}
