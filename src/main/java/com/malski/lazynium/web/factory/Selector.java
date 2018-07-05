package com.malski.lazynium.web.factory;

import com.malski.lazynium.web.annotations.FindBy;
import org.apache.commons.lang3.StringUtils;
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

    public Selector() {
    }

    public Selector(By by) {
        setHowAndUsing(by);
        this.by = by;
    }

    public Selector(FindBy findBy) {
        this.by = buildByFromFindBy(findBy);
    }

    public How getHow() {
        return how;
    }

    public String getUsing() {
        return using;
    }

    public static boolean isFindByUnset(FindBy findBy) {
        return StringUtils.isBlank(findBy.className()) && StringUtils.isBlank(findBy.css()) && StringUtils.isBlank(findBy.id())
                && StringUtils.isBlank(findBy.linkText()) && StringUtils.isBlank(findBy.name()) && StringUtils.isBlank(findBy.partialLinkText())
                && StringUtils.isBlank(findBy.tagName()) && StringUtils.isBlank(findBy.xpath()) && StringUtils.isBlank(findBy.model())
                && StringUtils.isBlank(findBy.binding()) && StringUtils.isBlank(findBy.repeater()) && StringUtils.isBlank(findBy.buttonText())
                && StringUtils.isBlank(findBy.cssContainingText()[0]) && StringUtils.isBlank(findBy.exactBinding())
                && StringUtils.isBlank(findBy.options()) && StringUtils.isBlank(findBy.partialButtonText());
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

    private By buildByFromFindBy(FindBy findBy) {
        if(!StringUtils.isBlank(findBy.id())) {
            setHowAndUsing(How.ID, findBy.id());
            return By.id(findBy.id());
        }
        if(!StringUtils.isBlank(findBy.css())) {
            setHowAndUsing(How.CSS, findBy.css());
            return By.cssSelector(findBy.css());
        }
        if(!StringUtils.isBlank(findBy.xpath())) {
            setHowAndUsing(How.XPATH, findBy.xpath());
            return By.xpath(findBy.xpath());
        }
        if(!StringUtils.isBlank(findBy.className())) {
            setHowAndUsing(How.CLASS_NAME, findBy.className());
            return By.className(findBy.className());
        }
        if(!StringUtils.isBlank(findBy.name())) {
            setHowAndUsing(How.NAME, findBy.name());
            return By.name(findBy.name());
        }
        if(!StringUtils.isBlank(findBy.linkText())) {
            setHowAndUsing(How.LINK_TEXT, findBy.linkText());
            return By.linkText(findBy.linkText());
        }
        if(!StringUtils.isBlank(findBy.partialLinkText())) {
            setHowAndUsing(How.PARTIAL_LINK_TEXT, findBy.partialLinkText());
            return By.partialLinkText(findBy.partialLinkText());
        }

        return null;
    }

    private void setHowAndUsing(How how, String using) {
        this.how = how;
        this.using = using;
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
}
