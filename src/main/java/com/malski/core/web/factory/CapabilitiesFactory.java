package com.malski.core.web.factory;

import com.malski.core.utils.PropertyKey;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.malski.core.utils.TestContext.getConfig;
import static com.malski.core.utils.TestContext.getPropertyByKey;

public class CapabilitiesFactory {

    private static DesiredCapabilities addProxyCapabilities(DesiredCapabilities capability) {
        String proxyAddress = getPropertyByKey(PropertyKey.PROXY_URL);
        return addProxyCapabilities(capability, proxyAddress, proxyAddress, proxyAddress);
    }

    private static DesiredCapabilities addProxyCapabilities(DesiredCapabilities capability, String httpProxy, String sslProxy,
                                                     String ftpProxy) {
        Proxy proxy = new Proxy();
        proxy.setProxyType(Proxy.ProxyType.MANUAL);
        proxy.setHttpProxy(httpProxy);
        proxy.setSslProxy(sslProxy);
        proxy.setFtpProxy(ftpProxy);
        capability.setCapability(CapabilityType.PROXY, proxy);
        capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        return capability;
    }

    public static DesiredCapabilities getChromeCapabilities() {
        DesiredCapabilities caps = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.geolocation", 2);
        chromePrefs.put("download.prompt_for_download", false);
        chromePrefs.put("download.directory_upgrade", true);
        chromePrefs.put("download.default_directory", getConfig().getDownloadDirPath());
        chromePrefs.put("password_manager_enabled", false);
        chromePrefs.put("safebrowsing.enabled", "true");
        options.setExperimentalOption("prefs", chromePrefs);
        caps.setCapability(ChromeOptions.CAPABILITY, options);
        boolean proxyEnabled = Boolean.valueOf(getPropertyByKey(PropertyKey.PROXY));
        if (proxyEnabled) {
            caps = addProxyCapabilities(caps);
        }
        return caps;
    }

    private static FirefoxProfile addProxyProfile(FirefoxProfile ffProfile) {
        URL proxyAddress;
        try {
            proxyAddress = new URL(getPropertyByKey(PropertyKey.PROXY_URL));
        } catch (MalformedURLException e) {
            throw new RuntimeException("Couldn't set proxy for firefox", e);
        }
        ffProfile.setPreference("network.proxy.type", 1);
        ffProfile.setPreference("network.proxy.http", proxyAddress.getHost());
        ffProfile.setPreference("network.proxy.http_port", proxyAddress.getPort());
        ffProfile.setPreference("network.proxy.ssl", proxyAddress.getHost());
        ffProfile.setPreference("network.proxy.ssl_port", proxyAddress.getPort());
        return ffProfile;
    }

    public static DesiredCapabilities getFirefoxCapabilities(boolean marionette) {
//        System.setProperty("webdriver.firefox.profile", "default");
        DesiredCapabilities caps = DesiredCapabilities.firefox();
        caps.setCapability("marionette", marionette);
//        caps.setCapability(FirefoxDriver.BINARY, getFirefoxBinary());
        caps.setCapability(FirefoxDriver.PROFILE, getFirefoxProfile());
        return caps;
    }

    private static FirefoxBinary getFirefoxBinary() {
        FirefoxBinary ffBinary = new FirefoxBinary();
        ffBinary.setTimeout(TimeUnit.SECONDS.toMillis(180));
        return ffBinary;
    }

    private static FirefoxProfile getFirefoxProfile() {
        FirefoxProfile ffProfile = new FirefoxProfile();
        ffProfile.setPreference("browser.download.folderList", 2);
        ffProfile.setPreference("browser.download.manager.showWhenStarting", false);
        ffProfile.setPreference("browser.download.dir", getConfig().getDownloadDirPath());
        ffProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/zip;application/octet-stream;application/x-zip;application/x-zip-compressed;text/css;text/html;text/plain;text/xml;text/comma-separated-values");
        ffProfile.setPreference("browser.helperApps.neverAsk.openFile", "application/zip;application/octet-stream;application/x-zip;application/x-zip-compressed;text/css;text/html;text/plain;text/xml;text/comma-separated-values");
        ffProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
        boolean proxyEnabled = Boolean.valueOf(getPropertyByKey(PropertyKey.PROXY));
        if (proxyEnabled) {
            ffProfile = addProxyProfile(ffProfile);
        }
        return ffProfile;
    }
}
