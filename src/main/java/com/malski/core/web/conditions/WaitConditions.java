package com.malski.core.web.conditions;

import com.malski.core.web.elements.api.Element;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class WaitConditions {

    private WaitConditions() {
    }

    public static ExpectedCondition<Boolean> pageLoaded(final WebDriver driver) {
        return d -> {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            String state = StringUtils.EMPTY;
            try {
                state = executor.executeScript("return document.readyState").toString();
                // } catch (InvalidSelectorException e) {
                //Ignore
            } catch (NoSuchWindowException e) {
                //when popup is closed, switch to last windows
                driver.switchTo().window(driver.getWindowHandles().iterator().next());
            }
            //In IE7 there are chances we may get state as loaded instead of complete
            return (state.equalsIgnoreCase("complete") || state.equalsIgnoreCase("loaded"));
        };
    }

    public static ExpectedCondition<List<WebElement>> elementsToBeClickable(final By locator) {
        return driver -> {
            List<WebElement> elements = ExpectedConditions.visibilityOfAllElementsLocatedBy(locator).apply(driver);
            List<WebElement> results = new ArrayList<>();
            if (elements == null) {
                return null;
            }
            for (WebElement element : elements) {
                try {
                    if (element != null && element.isEnabled()) {
                        results.add(element);
                    } else {
                        return null;
                    }
                } catch (StaleElementReferenceException var4) {
                    return null;
                }
            }
            return results;
        };
    }

    public static <T extends Element> ExpectedCondition<Boolean> invisibilityOfAllElements(List<T> elements) {
        return driver -> {
            for (T element : elements) {
                try {
                    if (element.isDisplayed()) {
                        return false;
                    }
                } catch (Exception ignore) {
                }
            }
            return true;
        };
    }

    public static ExpectedCondition<Boolean> attributeChanged(By by, String attributeName, String expectedValue) {
        return driver -> {
            WebElement webElement = driver.findElement(by);
            String enabled = webElement.getAttribute(attributeName);
            if (expectedValue == null) {
                return enabled == null;
            }
            return enabled.equals(expectedValue);
        };
    }

    public static ExpectedCondition<Boolean> attributeChanged(Element element, String attributeName, String expectedValue) {
        return driver -> {
            element.refresh();
            String enabled = element.getAttribute(attributeName);
            if (expectedValue == null) {
                return enabled == null;
            } else {
                return enabled.equals(expectedValue);
            }
        };
    }
}
