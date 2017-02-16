package com.malski.core.web.factory;

import com.malski.core.web.annotations.FindBy;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;

public class AnnotationFactory {

    public static By buildByFromShortFindBy(FindBy findBy) {
        return !StringUtils.isBlank(findBy.className()) ? By.className(findBy.className()) :
                (!StringUtils.isBlank(findBy.css()) ? By.cssSelector(findBy.css()) :
                        (!StringUtils.isBlank(findBy.id()) ? By.id(findBy.id()) :
                                (!StringUtils.isBlank(findBy.linkText()) ? By.linkText(findBy.linkText()) :
                                        (!StringUtils.isBlank(findBy.name()) ? By.name(findBy.name()) :
                                                (!StringUtils.isBlank(findBy.partialLinkText()) ? By.partialLinkText(findBy.partialLinkText()) :
                                                        (!StringUtils.isBlank(findBy.tagName()) ? By.tagName(findBy.tagName()) :
                                                                (!StringUtils.isBlank(findBy.xpath()) ? By.xpath(findBy.xpath()) : null)))))));
    }

    public static boolean isFindByUnset(FindBy findBy) {
        return StringUtils.isBlank(findBy.className()) && StringUtils.isBlank(findBy.css()) && StringUtils.isBlank(findBy.id()) && StringUtils.isBlank(findBy.linkText())
                && StringUtils.isBlank(findBy.name()) && StringUtils.isBlank(findBy.partialLinkText()) && StringUtils.isBlank(findBy.tagName())
                && StringUtils.isBlank(findBy.xpath());
    }
}
