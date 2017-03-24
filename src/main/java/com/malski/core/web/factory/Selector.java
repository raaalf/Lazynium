package com.malski.core.web.factory;

import com.malski.core.web.annotations.FindBy;
import com.malski.core.web.finders.HowExt;
import com.paulhammant.ngwebdriver.*;
import org.apache.commons.lang3.StringUtils;
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

    public Selector() {
    }

    public Selector(By by) {
        setHowAndUsing(by);
        this.by = by;
    }

    public Selector(FindBy findBy) {
        this.by = buildByFromFindBy(findBy);
    }

    public HowExt getHow() {
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
            setHowAndUsing(HowExt.ID, findBy.id());
            return By.id(findBy.id());
        }
        if(!StringUtils.isBlank(findBy.css())) {
            setHowAndUsing(HowExt.CSS, findBy.css());
            return By.cssSelector(findBy.css());
        }
        if(!StringUtils.isBlank(findBy.xpath())) {
            setHowAndUsing(HowExt.XPATH, findBy.xpath());
            return By.xpath(findBy.xpath());
        }
        if(!StringUtils.isBlank(findBy.className())) {
            setHowAndUsing(HowExt.CLASS_NAME, findBy.className());
            return By.className(findBy.className());
        }
        if(!StringUtils.isBlank(findBy.name())) {
            setHowAndUsing(HowExt.NAME, findBy.name());
            return By.name(findBy.name());
        }
        if(!StringUtils.isBlank(findBy.linkText())) {
            setHowAndUsing(HowExt.LINK_TEXT, findBy.linkText());
            return By.linkText(findBy.linkText());
        }
        if(!StringUtils.isBlank(findBy.partialLinkText())) {
            setHowAndUsing(HowExt.PARTIAL_LINK_TEXT, findBy.partialLinkText());
            return By.partialLinkText(findBy.partialLinkText());
        }
        // for angular
        if(!StringUtils.isBlank(findBy.model())) {
            setHowAndUsing(HowExt.MODEL, findBy.model());
            return ByAngular.model(findBy.model());
        }
        if(!StringUtils.isBlank(findBy.binding())) {
            setHowAndUsing(HowExt.BINDING, findBy.binding());
            return ByAngular.binding(findBy.binding());
        }
        if(!StringUtils.isBlank(findBy.repeater())) {
            setHowAndUsing(HowExt.REPEATER, findBy.repeater());
            return ByAngular.repeater(findBy.repeater());
        }
        if(!StringUtils.isBlank(findBy.buttonText())) {
            setHowAndUsing(HowExt.BUTTON_TEXT, findBy.buttonText());
            return ByAngular.buttonText(findBy.buttonText());
        }
        if(!StringUtils.isBlank(findBy.cssContainingText()[0])) {
            setHowAndUsing(HowExt.CSS_CONTAINING_TEXT, findBy.cssContainingText()[0] +" with text: "+findBy.cssContainingText()[1]);
            return ByAngular.cssContainingText(findBy.cssContainingText()[0], findBy.cssContainingText()[1]);
        }
        if(!StringUtils.isBlank(findBy.exactBinding())) {
            setHowAndUsing(HowExt.EXACT_BINDING, findBy.exactBinding());
            return ByAngular.exactBinding(findBy.exactBinding());
        }
        if(!StringUtils.isBlank(findBy.options())) {
            setHowAndUsing(HowExt.OPTIONS, findBy.options());
            return ByAngular.options(findBy.options());
        }
        if(!StringUtils.isBlank(findBy.partialButtonText())) {
            setHowAndUsing(HowExt.PARTIAL_BUTTON_TEXT, findBy.partialButtonText());
            return ByAngular.partialButtonText(findBy.partialButtonText());
        }
        return null;
    }

    private void setHowAndUsing(HowExt how, String using) {
        this.how = how;
        this.using = using;
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
        } else if (by instanceof ByAngularModel) {
            how = HowExt.MODEL;
            using = getPrivateFieldValue(by, "model");
        } else if (by instanceof ByAngularBinding) {
            how = HowExt.BINDING;
            using = getPrivateFieldValue(by, "bindings");
        } else if (by instanceof ByAngularButtonText) {
            how = HowExt.BUTTON_TEXT;
            using = getPrivateFieldValue(by, "searchText");
        } else if (by instanceof ByAngularCssContainingText) {
            how = HowExt.CSS_CONTAINING_TEXT;
            using = getPrivateFieldValue(by, "cssContainingText");
        } else if (by instanceof ByAngularExactBinding) {
            how = HowExt.EXACT_BINDING;
            using = getPrivateFieldValue(by, "exactBinding");
        } else if (by instanceof ByAngularOptions) {
            how = HowExt.OPTIONS;
            using = getPrivateFieldValue(by, "options");
        } else if (by instanceof ByAngularPartialButtonText) {
            how = HowExt.PARTIAL_BUTTON_TEXT;
            using = getPrivateFieldValue(by, "searchText");
        } else if (by instanceof ByAngularRepeater) {
            how = HowExt.REPEATER;
            using = getPrivateFieldValue(by, "epeater");
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
