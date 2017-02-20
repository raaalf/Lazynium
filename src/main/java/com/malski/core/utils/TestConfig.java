package com.malski.core.utils;

import org.apache.commons.lang3.StringUtils;
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
    private long scriptTimeout;
    private int retryCount;
    private Boolean videoRecording;
    private Boolean videoRecordingOnFail;
    private String videoMimeTyp;
    private Integer videoFrameRate;
    private String videoQuality;
    private String videoDestinationPath;
    private int angularVersion = 1;
    private long angularTimeout;

    protected final Logger log = Logger.getLogger(getClass());

    TestConfig() {
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
        setScriptTimeout();
        setImplicitlyTimeoutMs();
        setExplicitlyTimeoutMs();
        setRetryCount();
        setDownloadDirPath();
        setTestResourceDirPath();
        setResourceDirPath();
        setDriversDirPath();
        setAngular();
    }

    private void setApp() {
        this.app = getPropertyByKey(PropertyKey.APP);
    }

    public String app() {
        return app;
    }

    private void setDevice() {
        this.device = getPropertyByKey(PropertyKey.DEVICE);
    }

    public String device() {
        return device;
    }

    private void setOsVersion() {
        this.osVersion = getPropertyByKey(PropertyKey.OS_VERSION);
    }

    public String osVersion() {
        return osVersion;
    }

    private void setDriver() {
        this.driver = getPropertyByKey(PropertyKey.DRIVER);
    }

    public String driver() {
        return driver;
    }

    public long timeout() {
        return timeout;
    }

    private void setTimeout() {
        this.timeout = Long.parseLong(getPropertyByKey(PropertyKey.TIMEOUT));
    }

    public long maxTimeout() {
        return maxTimeout;
    }

    private void setScriptTimeout() {
        this.scriptTimeout = Long.parseLong(getPropertyByKey(PropertyKey.SCRIPT_TIMEOUT));
    }

    public long scriptTimeout() {
        return scriptTimeout;
    }

    private void setMaxTimeout() {
        this.maxTimeout = Long.parseLong(getPropertyByKey(PropertyKey.MAX_TIMEOUT));
    }

    public long implicitlyTimeoutMs() {
        return this.implicitlyTimeoutMs;
    }

    private void setImplicitlyTimeoutMs() {
        this.implicitlyTimeoutMs = Long.parseLong(getPropertyByKey(PropertyKey.IMPLICITLY_TIMEOUT_MS));
    }

    public long explicitlyTimeout() {
        return this.explicitlyTimeout;
    }

    private void setExplicitlyTimeoutMs() {
        this.explicitlyTimeout = Long.parseLong(getPropertyByKey(PropertyKey.EXPLICITLY_TIMEOUT));
    }

    public long driverSleepMs() {
        return this.driverSleepMs;
    }

    private void  setDriverSleepMs() {
        this.driverSleepMs = Long.parseLong(getPropertyByKey(PropertyKey.DRIVER_SLEEP_MS));
    }

    public long minTimeout() {
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

    public String downloadDirPath() {
        return downloadDirPath;
    }

    private void setTestResourceDirPath() {
        this.testResourceDirPath = getPropertyByKey(PropertyKey.TEST_RESOURCE_DIR_PATH);
    }

    public String testResourceDirPath() {
        return testResourceDirPath;
    }

    private void setResourceDirPath() {
        this.resourceDirPath = getPropertyByKey(PropertyKey.RESOURCE_DIR_PATH);
    }

    public String resourceDirPath() {
        return resourceDirPath;
    }

    private void setDriversDirPath() {
        this.driversDirPath = resourceDirPath + File.separator + "drivers" + File.separator;
    }

    public String driversDirPath() {
        return driversDirPath;
    }

    private void setVideoRecording() {
        this.videoRecording = Boolean.parseBoolean(getPropertyByKey(PropertyKey.VIDEO_RECORDING));
    }

    public boolean isVideoRecording() {
        if(videoRecording == null) {
            setVideoRecording();
        }
        if(videoRecordingOnFail == null) {
            isVideoRecordingOnFail();
        }
        return videoRecording || videoRecordingOnFail;
    }

    private void setVideoRecordingOnFail() {
        this.videoRecordingOnFail = Boolean.parseBoolean(getPropertyByKey(PropertyKey.VIDEO_RECORDING_FAIL));
    }

    public boolean isVideoRecordingOnFail() {
        if(videoRecordingOnFail == null) {
            setVideoRecordingOnFail();
        }
        return videoRecordingOnFail;
    }

    public String videMimeType() {
        if(videoMimeTyp == null) {
            this.videoMimeTyp = getPropertyByKey(PropertyKey.VIDEO_MIME_TYPE);
        }
        return videoMimeTyp;
    }

    public int videoFrameRate() {
        if(videoFrameRate == null) {
            this.videoFrameRate = Integer.parseInt(getPropertyByKey(PropertyKey.VIDEO_FRAME_RATE));
        }
        return videoFrameRate;
    }

    public String videoQuality() {
        if(videoQuality == null) {
            this.videoQuality = getPropertyByKey(PropertyKey.VIDEO_QUALITY);
        }
        return videoQuality;
    }

    public String videoDestinationPath() {
        if(videoDestinationPath == null) {
            this.videoDestinationPath = getPropertyByKey(PropertyKey.VIDEO_DESTINATION_PATH);
        }
        return videoDestinationPath;
    }

    private void setAngular() {
        String version = getPropertyByKey(PropertyKey.ANGULAR_VERSION);
        this.angularVersion = StringUtils.isBlank(version) ? 1 : Integer.parseInt(version);
        String timeout = getPropertyByKey(PropertyKey.ANGULAR_TIMEOUT);
        this.angularTimeout = StringUtils.isBlank(timeout) ? maxTimeout() : Long.parseLong(timeout);
    }

    public int angularVersion() {
        return angularVersion;
    }

    public long angularTimeout() {
        return angularTimeout;
    }
}
