package com.malski.core.mobile.conditions;

import com.malski.core.mobile.factory.LazyLocator;
import com.malski.core.mobile.elements.Element;
import com.malski.core.web.elements.Select;
import io.appium.java_client.MobileElement;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.malski.core.utils.TestContext.browser;

public class WaitConditions {

    private WaitConditions() {
    }

    public static boolean stalenessOf(MobileElement element) {
        try {
            element.isEnabled();
            return false;
        } catch (StaleElementReferenceException var3) {
            return true;
        }
    }

    public static ExpectedCondition<MobileElement> presenceOfElementLocated(final LazyLocator locator) {
        return new ExpectedCondition<MobileElement>() {
            public MobileElement apply(WebDriver driver) {
                return locator.findElement();
            }

            public String toString() {
                return "presenceOfElementLocated: " + locator.toString();
            }
        };
    }

    public static ExpectedCondition<MobileElement> visibilityOfElementLocated(final LazyLocator locator) {
        return new ExpectedCondition<MobileElement>() {
            public MobileElement apply(WebDriver driver) {
                try {
                    return elementIfVisible(locator.findElement());
                } catch (StaleElementReferenceException ignore) {
                    return null;
                }
            }

            public String toString() {
                return "visibilityOfElementLocated: " + locator.toString();
            }
        };
    }

    public static ExpectedCondition<Boolean> invisibilityOfElementLocated(final LazyLocator locator) {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                try {
                    return !locator.findElement().isDisplayed();
                } catch (NoSuchElementException var3) {
                    return true;
                } catch (StaleElementReferenceException var4) {
                    return true;
                }
            }

            public String toString() {
                return "invisibilityOfElementLocated: " + locator.toString();
            }
        };
    }

    public static ExpectedCondition<MobileElement> elementToBeClickable(final LazyLocator locator) {
        return new ExpectedCondition<MobileElement>() {
            public MobileElement apply(WebDriver driver) {
                MobileElement element = visibilityOfElementLocated(locator).apply(driver);
                try {
                    return element != null && element.isEnabled() ? element : null;
                } catch (StaleElementReferenceException var4) {
                    return null;
                }
            }

            public String toString() {
                return "elementToBeClickable: " + locator.toString();
            }
        };
    }

    public static ExpectedCondition<List<MobileElement>> visibilityOfAllElementsLocatedBy(final LazyLocator locator) {
        return new ExpectedCondition<List<MobileElement>>() {
            public List<MobileElement> apply(WebDriver driver) {
                List<MobileElement> elements = locator.findElements();
                Iterator iterator = elements.iterator();
                MobileElement element;
                do {
                    if (!iterator.hasNext()) {
                        return elements.size() > 0 ? elements : null;
                    }
                    element = (MobileElement) iterator.next();
                } while (element.isDisplayed());

                return null;
            }

            public String toString() {
                return "visibilityOfAllElementsLocatedBy: " + locator.toString();
            }
        };
    }

    public static ExpectedCondition<Boolean> pageLoaded() {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                JavascriptExecutor executor = (JavascriptExecutor) driver;
                String state = StringUtils.EMPTY;
                try {
                    state = executor.executeScript("return document.readyState").toString();
                } catch (NoSuchWindowException e) {
                    driver.switchTo().window(driver.getWindowHandles().iterator().next());
                }
                return (state.equalsIgnoreCase("complete") || state.equalsIgnoreCase("loaded"));
            }

            public String toString() {
                return "pageLoaded";
            }
        };
    }

    public static ExpectedCondition<List<MobileElement>> elementsToBeClickable(final LazyLocator locator) {
        return new ExpectedCondition<List<MobileElement>>() {
            public List<MobileElement> apply(WebDriver driver) {
                List<MobileElement> elements = visibilityOfAllElementsLocatedBy(locator).apply(driver);
                List<MobileElement> results = new ArrayList<>();
                if (elements == null) {
                    return null;
                }
                for (MobileElement element : elements) {
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
            }

            public String toString() {
                return "elementsToBeClickable: " + locator.toString();
            }
        };
    }

    public static ExpectedCondition<List<MobileElement>> presenceOfAllElementsLocatedBy(final LazyLocator locator) {
        return new ExpectedCondition<List<MobileElement>>() {
            public List<MobileElement> apply(WebDriver driver) {
                List<MobileElement> elements = locator.findElements();
                return elements.size() > 0 ? elements : null;
            }

            public String toString() {
                return "presenceOfAllElementsLocatedBy: " + locator.toString();
            }
        };
    }

    public static <T extends Element> ExpectedCondition<Boolean> invisibilityOfAllElements(List<T> elements) {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                for (T element : elements) {
                    try {
                        if (element.isDisplayed()) {
                            return false;
                        }
                    } catch (Exception ignore) {
                    }
                }
                return true;
            }

            public String toString() {
                return "invisibilityOfAllElements: " + elements.size();
            }
        };
    }

    public static ExpectedCondition<Boolean> attributeChanged(By by, String attributeName, String expectedValue) {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                if (driver == null) {
                    return false;
                }
                WebElement webElement = driver.findElement(by);
                String enabled = webElement.getAttribute(attributeName);
                if (expectedValue == null) {
                    return enabled == null;
                }
                return enabled.equals(expectedValue);
            }

            public String toString() {
                return "attributeChanged: attr:" + attributeName + " expectedValue: " + expectedValue + " element: " + by.toString();
            }
        };
    }

    public static ExpectedCondition<Boolean> attributeChanged(LazyLocator locator, String attributeName, String expectedValue) {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                MobileElement webElement = locator.findElement();
                String enabled = webElement.getAttribute(attributeName);
                if (expectedValue == null) {
                    return enabled == null;
                }
                return enabled.equals(expectedValue);
            }

            public String toString() {
                return "attributeChanged: attr:" + attributeName + " expectedValue: " + expectedValue + " element: " + locator.toString();
            }
        };
    }

    public static ExpectedCondition<Boolean> attributeChangedFrom(LazyLocator locator, String attributeName, String startValue) {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                MobileElement webElement = locator.findElement();
                String enabled = webElement.getAttribute(attributeName);
                if (startValue == null) {
                    return enabled == null;
                }
                return !enabled.equals(startValue);
            }

            public String toString() {
                return "attributeChanged: attr:" + attributeName + " startValue: " + startValue + " element: " + locator.toString();
            }
        };
    }

    public static ExpectedCondition<Boolean> isInViewPort(LazyLocator locator) {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                Element element = locator.getElement();
                return element.isInViewport();
            }

            public String toString() {
                return "isInViewPort: " + locator.toString();
            }
        };
    }

    public static ExpectedCondition<Boolean> isInViewPort(By by) {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                if (driver == null) {
                    return false;
                }
                WebElement webElement = driver.findElement(by);

                Dimension elemDim = webElement.getSize();
                Point point = webElement.getLocation();

                int elemY = elemDim.getHeight() + point.getY();
                long browserHeight = browser().jsExecutor().getJsClientHeight();
                long scrollHeight = browser().jsExecutor().getScrollHeight();

                return elemY >= scrollHeight && elemY <= scrollHeight + browserHeight;
            }

            public String toString() {
                return "isInViewPort: " + by.toString();
            }
        };
    }

    public static ExpectedCondition<Boolean> attributeChanged(Element element, String attributeName, String expectedValue) {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                element.refresh();
                String enabled = element.getAttribute(attributeName);
                if (expectedValue == null) {
                    return enabled == null;
                } else {
                    return enabled.equals(expectedValue);
                }
            }

            public String toString() {
                return "attributeChanged: attr:" + attributeName + " expectedValue: " + expectedValue + " element: " + element.getLocator().toString();
            }
        };
    }

    public static ExpectedCondition<Boolean> optionSelectedByVisibleText(Select select, String visibleText) {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return select.getSelectedVisibleText().equalsIgnoreCase(visibleText);
            }

            public String toString() {
                return "optionSelectedByIndex: " + select.locator().toString() + " visibleText: " + visibleText;
            }
        };
    }

    public static ExpectedCondition<Boolean> optionSelectedByValue(Select select, String value) {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return select.getSelectedValue().equalsIgnoreCase(value);
            }

            public String toString() {
                return "optionSelectedByIndex: " + select.locator().toString() + " value: " + value;
            }
        };
    }

    public static ExpectedCondition<Boolean> optionSelectedByIndex(Select select, int index) {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return select.getSelectedIndex() == index;
            }

            public String toString() {
                return "optionSelectedByIndex: " + select.locator().toString() + " index: " + index;
            }
        };
    }

    private static MobileElement elementIfVisible(MobileElement element) {
        return element.isDisplayed() ? element : null;
    }
}
