package com.malski.core.web.conditions;

import com.malski.core.web.elements.Element;
import com.malski.core.web.elements.Select;
import com.malski.core.web.factory.LazyLocator;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.malski.core.utils.TestContext.getBrowser;

public class WaitConditions {

    private WaitConditions() {
    }

    public static ExpectedCondition<WebElement> presenceOfElementLocated(final LazyLocator locator) {
        return new ExpectedCondition<WebElement>() {
            public WebElement apply(WebDriver driver) {
                return locator.findElement();
            }

            public String toString() {
                return "presenceOfElementLocated: " + locator.toString();
            }
        };
    }

    public static ExpectedCondition<WebElement> visibilityOfElementLocated(final LazyLocator locator) {
        return new ExpectedCondition<WebElement>() {
            public WebElement apply(WebDriver driver) {
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

    public static ExpectedCondition<WebElement> elementToBeClickable(final LazyLocator locator) {
        return new ExpectedCondition<WebElement>() {
            public WebElement apply(WebDriver driver) {
                WebElement element = visibilityOfElementLocated(locator).apply(driver);
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

    public static ExpectedCondition<List<WebElement>> visibilityOfAllElementsLocatedBy(final LazyLocator locator) {
        return new ExpectedCondition<List<WebElement>>() {
            public List<WebElement> apply(WebDriver driver) {
                List<WebElement> elements = locator.findElements();
                Iterator iterator = elements.iterator();
                WebElement element;
                do {
                    if (!iterator.hasNext()) {
                        return elements.size() > 0 ? elements : null;
                    }
                    element = (WebElement) iterator.next();
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

    public static ExpectedCondition<List<WebElement>> elementsToBeClickable(final LazyLocator locator) {
        return new ExpectedCondition<List<WebElement>>() {
            public List<WebElement> apply(WebDriver driver) {
                List<WebElement> elements = visibilityOfAllElementsLocatedBy(locator).apply(driver);
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
            }

            public String toString() {
                return "elementsToBeClickable: " + locator.toString();
            }
        };
    }

    public static ExpectedCondition<List<WebElement>> presenceOfAllElementsLocatedBy(final LazyLocator locator) {
        return new ExpectedCondition<List<WebElement>>() {
            public List<WebElement> apply(WebDriver driver) {
                List<WebElement> elements = locator.findElements();
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
                WebElement webElement = locator.findElement();
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
                long browserHeight = getBrowser().getJsExecutor().getJsClientHeight();
                long scrollHeight = getBrowser().getJsExecutor().getScrollHeight();

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
                return "optionSelectedByIndex: " + select.getLocator().toString() + " visibleText: " + visibleText;
            }
        };
    }

    public static ExpectedCondition<Boolean> optionSelectedByValue(Select select, String value) {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return select.getSelectedValue().equalsIgnoreCase(value);
            }

            public String toString() {
                return "optionSelectedByIndex: " + select.getLocator().toString() + " value: " + value;
            }
        };
    }

    public static ExpectedCondition<Boolean> optionSelectedByIndex(Select select, int index) {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return select.getSelectedIndex() == index;
            }

            public String toString() {
                return "optionSelectedByIndex: " + select.getLocator().toString() + " index: " + index;
            }
        };
    }

    private static WebElement elementIfVisible(WebElement element) {
        return element.isDisplayed() ? element : null;
    }
}
