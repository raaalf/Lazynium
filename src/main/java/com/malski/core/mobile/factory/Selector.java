package com.malski.core.mobile.factory;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ByIdOrName;
import org.openqa.selenium.support.How;

import java.util.List;

public class Selector extends By {
    private How how;
    private String using;
    private By by;

    public Selector(By by) {
        setHowAndUsing(by);
        this.by = by;
    }

    public Selector(How how, String using) {
        this.how = how;
        this.using = using;
        setBy(how, using);
    }

    public How getHow() {
        return how;
    }

    public String getUsing() {
        return using;
    }

    @Override
    public List<WebElement> findElements(SearchContext searchContext) {
        return by.findElements(searchContext);
    }

    @Override
    public WebElement findElement(SearchContext searchContext) {
        return by.findElement(searchContext);
    }

    @Override
    public String toString() {
        return "{ "+how.toString() + ", " + using + " }";
    }

    public By getBy() {
        return by;
    }

    private void setHowAndUsing(By by) {
        if (by instanceof ByCssSelector) {
            how = How.CSS;
            using = getPrivateFieldValue(by, "selector");
        } else if (by instanceof ById) {
            how = How.ID;
            using = getPrivateFieldValue(by, "id");
        } else if (by instanceof ByXPath) {
            how = How.XPATH;
            using = getPrivateFieldValue(by, "xpathExpression");
        } else if (by instanceof ByName) {
            how = How.NAME;
            using = getPrivateFieldValue(by, "name");
        } else if (by instanceof ByClassName) {
            how = How.CLASS_NAME;
            using = getPrivateFieldValue(by, "className");
        } else if (by instanceof ByTagName) {
            how = How.TAG_NAME;
            using = getPrivateFieldValue(by, "name");
        } else if (by instanceof ByLinkText) {
            how = How.LINK_TEXT;
            using = getPrivateFieldValue(by, "linkText");
        } else if (by instanceof ByPartialLinkText) {
            how = How.PARTIAL_LINK_TEXT;
            using = getPrivateFieldValue(by, "linkText");
        } else if (by instanceof ByIdOrName) {
            how = How.ID_OR_NAME;
            using = getPrivateFieldValue(by, "idOrName");
        } else {
            how = How.UNSET;
            using = "";
        }
    }

    private String getPrivateFieldValue(By byObj, String fieldName) {
        try {
            return (String) FieldUtils.readField(byObj, fieldName, true);
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    private void setBy(How how, String using) {
        switch (how.ordinal()) {
            case 1:
                by = By.className(using);
            case 2:
                by = By.cssSelector(using);
            case 3:
            case 4:
                by = By.id(using);
            case 5:
                by = new ByIdOrName(using);
            case 6:
                by = By.linkText(using);
            case 7:
                by = By.name(using);
            case 8:
                by = By.partialLinkText(using);
            case 9:
                by = By.tagName(using);
            case 10:
                by = By.xpath(using);
            default:
                throw new IllegalArgumentException("Cannot determine how to locate element ");
        }
    }
}
