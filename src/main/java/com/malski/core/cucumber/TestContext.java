package com.malski.core.cucumber;

import com.malski.core.utils.CustomProperties;
import com.malski.core.web.base.Browser;

import java.util.HashMap;
import java.util.Map;

public class TestContext {
    //browser
    private static final ThreadLocal<Browser> BROWSER_THREAD_LOCAL;

    public static void initialize() {
        if(PROPERTIES_THREAD_LOCAL.get() == null) {
            PROPERTIES_THREAD_LOCAL.set(CustomProperties.getProperties("testProperties.xml"));
        }
        if(BROWSER_THREAD_LOCAL.get() == null) {
            setBrowser(getPropertyByKey("browser"));
        }
        if(CONTAINER_THREAD_LOCAL.get() == null) {
            CONTAINER_THREAD_LOCAL.set(new HashMap<>());
        }
    }

    static {
        BROWSER_THREAD_LOCAL = new ThreadLocal<>();
    }

    public static Browser getBrowser() {
        Browser browser = BROWSER_THREAD_LOCAL.get();
        if (browser == null) {
            throw new IllegalStateException("Browser not set on the TestContext");
        }
        return browser;
    }

    public static void setBrowser(String browser) {
        if(browser == null) {
            BROWSER_THREAD_LOCAL.set(null);
        } else {
            BROWSER_THREAD_LOCAL.set(new Browser(browser));
        }
    }

    private static final ThreadLocal<Map<Object, Object>> CONTAINER_THREAD_LOCAL;

    //container for handling variables shared between steps
    static {
        CONTAINER_THREAD_LOCAL = new ThreadLocal<>();
    }

    public static Map<Object, Object> getContainer() {
        return CONTAINER_THREAD_LOCAL.get();
    }

    public static Object getFromContainer(Object key) {
        return CONTAINER_THREAD_LOCAL.get().get(key);
    }

    public static void addToContainer(Object key, Object value) {
        CONTAINER_THREAD_LOCAL.get().put(key, value);
    }

    public static boolean containerContainsKey(String key) {
        return getContainer().containsKey(key);
    }

    public static boolean containerContainsValue(Object value) {
        return getContainer().containsValue(value);
    }

    public static Object removeFromContainer(String key) {
        return getContainer().remove(key);
    }

    //properties for running tests
    private static final ThreadLocal<Map<String, String>> PROPERTIES_THREAD_LOCAL;

    static {
        PROPERTIES_THREAD_LOCAL = new ThreadLocal<>();
    }

    public static String getPropertyByKey(String key){
        return PROPERTIES_THREAD_LOCAL.get().get(key);
    }
}
