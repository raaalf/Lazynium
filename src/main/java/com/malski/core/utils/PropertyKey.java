package com.malski.core.utils;

import java.lang.reflect.Field;

public enum PropertyKey {
    DOWNLOAD_DIR("downloadDir"),
    DRIVER("driver"),
    TIMEOUT("timeout"),
    APP("app"),
    TEST_RESOURCE_DIR_PATH("testResourceDirPath"),
    RESOURCE_DIR_PATH("resourceDirPath"),
    DOWNLOAD_DIR_PATH("downloadDirPath"),
    DISPLAY_DIRECTION("displayDirection"),
    RETRY_COUNT("retryCount");

    private String key;

    PropertyKey(String propertyKey) {
        this.key = propertyKey;
        try {
            Field fieldName = getClass().getSuperclass().getDeclaredField("name");
            fieldName.setAccessible(true);
            fieldName.set(this, propertyKey);
            fieldName.setAccessible(false);
        } catch (Exception ignore) {}
    }

    public String toString() {
        return this.key;
    }
}
