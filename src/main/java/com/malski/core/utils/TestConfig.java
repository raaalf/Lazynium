package com.malski.core.utils;

import org.apache.log4j.Logger;

import java.io.File;

import static com.malski.core.utils.TestContext.getPropertyByKey;

/**
 * Created by rmalski on 2016-08-09.
 */
public class TestConfig {
    private String driversDirPath;
    private String downloadDirPath;
    private String testResourceDirPath;
    private String resourceDirPath;
    private String app;
    private String driver;
    private long timeout;
    private int retryCount;

    protected final Logger log = Logger.getLogger(getClass());

    public TestConfig() {
        setConfiguration();
    }

    private void setConfiguration() {
        setApp();
        setDriver();
        setTimeout();
        setRetryCount();
        setDownloadDirPath();
        setTestResourceDirPath();
        setResourceDirPath();
        setDriversDirPath();
    }

    public void setApp() {
        this.app = getPropertyByKey(PropertyKey.APP);
    }

    public String getApp() {
        return app;
    }

    public void setDriver() {
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
}
