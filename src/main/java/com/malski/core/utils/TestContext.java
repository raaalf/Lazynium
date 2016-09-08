package com.malski.core.utils;

import com.malski.core.mobile.control.Application;
import com.malski.core.web.control.Browser;

import java.util.HashMap;
import java.util.Map;

public class TestContext {

    private static final ThreadLocal<Browser> BROWSER_THREAD_LOCAL;
    private static final ThreadLocal<Application> APP_THREAD_LOCAL;

    public static void initialize() {
        if(PROPERTIES_THREAD_LOCAL.get() == null) {
            PROPERTIES_THREAD_LOCAL.set(CustomProperties.getProperties("testProperties.xml"));
        }
        if (CONFIG_THREAD_LOCAL.get() == null) {
            CONFIG_THREAD_LOCAL.set(new TestConfig());
        }
        if(getConfig().getApp().equals("web")) {
            if (BROWSER_THREAD_LOCAL.get() == null) {
                setBrowser(getConfig().getDriver());
            }
        } else {
            if (APP_THREAD_LOCAL.get() == null) {
                setApplication(getConfig().getDriver());
            }
        }
        if(CONTAINER_THREAD_LOCAL.get() == null) {
            CONTAINER_THREAD_LOCAL.set(new HashMap<>());
        }
    }

    static {
        BROWSER_THREAD_LOCAL = new ThreadLocal<>();
        APP_THREAD_LOCAL = new ThreadLocal<>();
    }

    public static Browser getBrowser() {
        Browser browser = BROWSER_THREAD_LOCAL.get();
        if (browser == null) {
            throw new IllegalStateException("Browser not set on the TestContext");
        }
        return browser;
    }

    public static void setBrowser(String driver) {
        BROWSER_THREAD_LOCAL.set(new Browser(driver));
    }

    public static Application getApplication() {
        Application app = APP_THREAD_LOCAL.get();
        if (app == null) {
            throw new IllegalStateException("Application not set on the TestContext");
        }
        return app;
    }


    public static void setApplication(String driver) {
        APP_THREAD_LOCAL.set(new Application(driver));
    }

    private static final ThreadLocal<Map<ContainerKey, Object>> CONTAINER_THREAD_LOCAL;

    //container for handling variables shared between steps
    static {
        CONTAINER_THREAD_LOCAL = new ThreadLocal<>();
    }

    public static Map<ContainerKey, Object> getContainer() {
        return CONTAINER_THREAD_LOCAL.get();
    }

    @SuppressWarnings("unchecked")
    public static <T> T getFromContainer(ContainerKey key) {
        return (T) CONTAINER_THREAD_LOCAL.get().get(key);
    }

    public static void setInContainer(ContainerKey key, Object value) {
        CONTAINER_THREAD_LOCAL.get().put(key, value);
    }

    public static boolean containerContainsKey(ContainerKey key) {
        return getContainer().containsKey(key);
    }

    public static boolean containerContainsValue(Object value) {
        return getContainer().containsValue(value);
    }

    public static Object removeFromContainer(ContainerKey key) {
        return getContainer().remove(key);
    }

    //properties for running tests
    private static final ThreadLocal<Map<String, String>> PROPERTIES_THREAD_LOCAL;
    private static final ThreadLocal<TestConfig> CONFIG_THREAD_LOCAL;

    static {
        PROPERTIES_THREAD_LOCAL = new ThreadLocal<>();
        CONFIG_THREAD_LOCAL = new ThreadLocal<>();
    }

    public static String getPropertyByKey(PropertyKey key){
        return PROPERTIES_THREAD_LOCAL.get().get(key.toString());
    }

    public static TestConfig getConfig() {
        return CONFIG_THREAD_LOCAL.get();
    }
}
