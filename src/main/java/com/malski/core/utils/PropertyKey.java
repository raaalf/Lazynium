package com.malski.core.utils;

import java.lang.reflect.Field;

public enum PropertyKey {
    DOWNLOAD_DIR("downloadDir"),
    DRIVER("driver"),
    MIN_TIMEOUT("minTimeout"),
    TIMEOUT("timeout"),
    MAX_TIMEOUT("maxTimeout"),
    SCRIPT_TIMEOUT("scriptTimeout"),
    DRIVER_SLEEP_MS("driverSleepMs"),
    IMPLICITLY_TIMEOUT_MS("implicitlyTimeoutMs"),
    EXPLICITLY_TIMEOUT("explicitlyTimeout"),
    APP("app"),
    DEVICE("device"),
    OS_VERSION("osVersion"),
    TEST_RESOURCE_DIR_PATH("testResourceDirPath"),
    RESOURCE_DIR_PATH("resourceDirPath"),
    DOWNLOAD_DIR_PATH("downloadDirPath"),
    DISPLAY_DIRECTION("displayDirection"),
    PROXY("proxy"),
    PROXY_URL("proxyUrl"),
    RETRY_COUNT("retryCount"),
    VIDEO_RECORDING("videoRecording"),
    VIDEO_RECORDING_FAIL("videoRecordingOnFail"),
    VIDEO_MIME_TYPE("videoMimeType"),
    VIDEO_FRAME_RATE("videoFrameRate"),
    VIDEO_QUALITY("videoQuality"),
    VIDEO_DESTINATION_PATH("videoDestinationPath"),

    ANGULAR_VERSION("angularVersion"),
    ANGULAR_TIMEOUT("angularTimeout");

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
