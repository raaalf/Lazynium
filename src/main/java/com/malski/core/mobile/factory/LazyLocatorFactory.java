package com.malski.core.mobile.factory;

import com.malski.core.mobile.control.LazyMobileContext;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchableElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.TimeOutDuration;
import io.appium.java_client.pagefactory.Widget;
import io.appium.java_client.pagefactory.locator.CacheableLocator;
import io.appium.java_client.pagefactory.utils.ProxyFactory;
import io.appium.java_client.pagefactory.utils.WebDriverUnpackUtility;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class LazyLocatorFactory extends AppiumFieldDecorator {
    private final LazyMobileContext searchContext;
    public static long DEFAULT_IMPLICITLY_WAIT_TIMEOUT = 1L;
    public static TimeUnit DEFAULT_TIMEUNIT;

    public LazyLocatorFactory(LazyMobileContext searchContext) {
        super(searchContext);
        this.searchContext = searchContext;
    }

    private static final List<Class<? extends WebElement>> availableElementClasses = new ArrayList() {
        private static final long serialVersionUID = 1L;

        {
            this.add(WebElement.class);
            this.add(RemoteWebElement.class);
            this.add(MobileElement.class);
            this.add(TouchableElement.class);
            this.add(AndroidElement.class);
            this.add(IOSElement.class);
        }
    };
    private static final Map<Class<? extends SearchContext>, Class<? extends WebElement>> elementRuleMap = new HashMap() {
        private static final long serialVersionUID = 1L;

        {
            this.put(AndroidDriver.class, AndroidElement.class);
            this.put(IOSDriver.class, IOSElement.class);
        }
    };

    static {
        DEFAULT_TIMEUNIT = TimeUnit.SECONDS;
    }

    public Object decorate(ClassLoader ignored, Field field) {
        Object result = this.defaultElementFieldDecoracor.decorate(ignored, field);
        return result != null ? result : this.decorateWidget(field);
    }

    private Class<?> getTypeForProxy() {
        Class driverClass = this.originalDriver.getClass();
        Set rules = elementRuleMap.entrySet();
        Iterator var4 = rules.iterator();

        while (var4.hasNext()) {
            Map.Entry e = (Map.Entry) var4.next();
            if (((Class) e.getKey()).isAssignableFrom(driverClass)) {
                return (Class) e.getValue();
            }
        }

        return RemoteWebElement.class;
    }

    private WebElement proxyForAnElement(ElementLocator locator) {
        ElementInterceptor elementInterceptor = new ElementInterceptor(locator, this.originalDriver);
        return (WebElement) ProxyFactory.getEnhancedProxy(this.getTypeForProxy(), elementInterceptor);
    }
}