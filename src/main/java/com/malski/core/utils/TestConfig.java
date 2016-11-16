package com.malski.core.utils;

import org.apache.log4j.Logger;

import java.io.File;

import static com.malski.core.utils.TestContext.getPropertyByKey;

public class TestConfig {
    private String driversDirPath;
    private String downloadDirPath;
    private String testResourceDirPath;
    private String resourceDirPath;
    private String app;
    private String device;
    private String osVersion;
    private String driver;
    private long minTimeout;
    private long timeout;
    private long maxTimeout;
    private long driverSleepMs;
    private long implicitlyTimeoutMs;
    private long explicitlyTimeout;
    private int retryCount;
    private boolean videoRecording;

    protected final Logger log = Logger.getLogger(getClass());

    public TestConfig() {
        setConfiguration();
    }

    private void setConfiguration() {
        setApp();
        setDevice();
        setOsVersion();
        setDriver();
        setMinTimeout();
        setTimeout();
        setMaxTimeout();
        setDriverSleepMs();
        setImplicitlyTimeoutMs();
        setExplicitlyTimeoutMs();
        setRetryCount();
        setDownloadDirPath();
        setTestResourceDirPath();
        setResourceDirPath();
        setDriversDirPath();
        setVideoRecording();
    }

    private void setApp() {
        this.app = getPropertyByKey(PropertyKey.APP);
    }

    public String getApp() {
        return app;
    }

    private void setDevice() {
        this.device = getPropertyByKey(PropertyKey.DEVICE);
    }

    public String getDevice() {
        return device;
    }

    private void setOsVersion() {
        this.osVersion = getPropertyByKey(PropertyKey.OS_VERSION);
    }

    public String getOsVersion() {
        return osVersion;
    }

    private void setDriver() {
        this.driver = getPropertyByKey(PropertyKey.DRIVER);
    }

    public String getDriver() {
        return driver;
    }

    public long getTimeout() {
        return timeout;
    }

    private void setTimeout() {
        this.timeout = Long.parseLong(getPropertyByKey(PropertyKey.TIMEOUT));
    }

    public long getMaxTimeout() {
        return maxTimeout;
    }

    private void setMaxTimeout() {
        this.maxTimeout = Long.parseLong(getPropertyByKey(PropertyKey.MAX_TIMEOUT));
    }

    public long getImplicitlyTimeoutMs() {
        return this.implicitlyTimeoutMs;
    }

    private void setImplicitlyTimeoutMs() {
        this.implicitlyTimeoutMs = Long.parseLong(getPropertyByKey(PropertyKey.IMPLICITLY_TIMEOUT_MS));
    }

    public long getExplicitlyTimeout() {
        return this.explicitlyTimeout;
    }

    private void setExplicitlyTimeoutMs() {
        this.explicitlyTimeout = Long.parseLong(getPropertyByKey(PropertyKey.EXPLICITLY_TIMEOUT));
    }

    public long getDriverSleepMs() {
        return this.driverSleepMs;
    }

    private void  setDriverSleepMs() {
        this.driverSleepMs = Long.parseLong(getPropertyByKey(PropertyKey.DRIVER_SLEEP_MS));
    }

    public long getMinTimeout() {
        return minTimeout;
    }

    private void setMinTimeout() {
        this.minTimeout = Long.parseLong(getPropertyByKey(PropertyKey.MIN_TIMEOUT));
    }

    private void setRetryCount() {
        this.retryCount = Integer.parseInt(getPropertyByKey(PropertyKey.RETRY_COUNT));
    }

    private void setDownloadDirPath() {
        this.downloadDirPath = getPropertyByKey(PropertyKey.DOWNLOAD_DIR_PATH);
    }

    public String getDownloadDirPath() {
        return downloadDirPath;
    }

    private void setTestResourceDirPath() {
        this.testResourceDirPath = getPropertyByKey(PropertyKey.TEST_RESOURCE_DIR_PATH);
    }

    public String getTestResourceDirPath() {
        return testResourceDirPath;
    }

    private void setResourceDirPath() {
        this.resourceDirPath = getPropertyByKey(PropertyKey.RESOURCE_DIR_PATH);
    }

    public String getResourceDirPath() {
        return resourceDirPath;
    }

    private void setDriversDirPath() {
        this.driversDirPath = resourceDirPath + File.separator + "drivers" + File.separator;
    }

    public String getDriversDirPath() {
        return driversDirPath;
    }

    private void setVideoRecording() {
        this.videoRecording = Boolean.parseBoolean(getPropertyByKey(PropertyKey.VIDEO_RECORDING));
    }

    public boolean isVideoRecordingEnabled() {
        return videoRecording;
    }
}