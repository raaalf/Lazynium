package com.malski.lazynium.utils;

import com.malski.lazynium.web.control.Browser;
import com.malski.lazynium.web.control.JsExecutor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestContext {
    private static boolean initiated = true;

    private static Browser browser;

    public static void initialize() {
        if(initiated) {
            if (properties == null) {
                properties = CustomProperties.getProperties("test_properties.xml");
            }
            if (config == null) {
                config = new TestConfig();
            }
            if (container == null) {
                container = new HashMap<>();
            }
            if (inMemoryJsScripts == null) {
                loadInMemoryJsScripts();
            }
            initiated = false;
        }
    }

    public static Browser browser() {
        Browser browser = TestContext.browser;
        if (browser == null) {
            throw new IllegalStateException("Browser not set on the TestContext");
        }
        return browser;
    }

    public static void setBrowser(String driver) {
        browser = new Browser(driver);
    }

    private static Map<ContainerKey, Object> container;

    public static Map<ContainerKey, Object> container() {
        return container;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getFromContainer(ContainerKey key) {
        return (T) container.get(key);
    }

    public static void setInContainer(ContainerKey key, Object value) {
        container.put(key, value);
    }

    public static boolean containerContainsKey(ContainerKey key) {
        return container().containsKey(key);
    }

    public static boolean containerContainsValue(Object value) {
        return container().containsValue(value);
    }

    public static Object removeFromContainer(ContainerKey key) {
        return container().remove(key);
    }

    //properties for running tests
    private static Map<String, String> properties;
    private static TestConfig config;

    public static String getPropertyByKey(PropertyKey key){
        return properties.get(key.toString());
    }

    public static TestConfig config() {
        return config;
    }

    private static Map<String, String> inMemoryJsScripts;

    public static String getInMemoryJsScript(String key){
        return inMemoryJsScripts.get(key);
    }

    private static void loadInMemoryJsScripts() {
        try {
            inMemoryJsScripts = CustomProperties.readPropertiesFromFile("js_to_load.properties");
            for(String key : inMemoryJsScripts.keySet()) {
                String fileName = getInMemoryJsScript(key);
                String script = JsExecutor.loadScriptFromFile(fileName);
                inMemoryJsScripts.put(key, script);
            }
        } catch (IOException ignore) { }
    }
}
