package com.malski.core.cucumber;

import com.malski.core.web.Browser;
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
