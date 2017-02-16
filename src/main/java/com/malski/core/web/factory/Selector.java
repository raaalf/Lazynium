package com.malski.core.web.factory;

import com.malski.core.web.annotations.HowExt;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ByIdOrName;

import java.util.List;

public class Selector extends By {
    private HowExt how;
    private String using;
    private By by;

    public Selector(By by) {
        setHowAndUsing(by);
        this.by = by;
    }

    public HowExt getHow() {
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
            how = HowExt.CSS;
            using = getPrivateFieldValue(by, "selector");
        } else if (by instanceof ById) {
            how = HowExt.ID;
            using = getPrivateFieldValue(by, "id");
        } else if (by instanceof ByXPath) {
            how = HowExt.XPATH;
            using = getPrivateFieldValue(by, "xpathExpression");
        } else if (by instanceof ByName) {
            how = HowExt.NAME;
            using = getPrivateFieldValue(by, "name");
        } else if (by instanceof ByClassName) {
            how = HowExt.CLASS_NAME;
            using = getPrivateFieldValue(by, "className");
        } else if (by instanceof ByTagName) {
            how = HowExt.TAG_NAME;
            using = getPrivateFieldValue(by, "name");
        } else if (by instanceof ByLinkText) {
            how = HowExt.LINK_TEXT;
            using = getPrivateFieldValue(by, "linkText");
        } else if (by instanceof ByPartialLinkText) {
            how = HowExt.PARTIAL_LINK_TEXT;
            using = getPrivateFieldValue(by, "linkText");
        } else if (by instanceof ByIdOrName) {
            how = HowExt.ID_OR_NAME;
            using = getPrivateFieldValue(by, "idOrName");
        } else {
            how = HowExt.UNSET;
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
}
