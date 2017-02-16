package com.malski.core.web.factory;

import com.malski.core.web.annotations.FindAll;
import com.malski.core.web.annotations.FindBy;
import com.malski.core.web.annotations.FindBys;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.HashSet;

public class AnnotationValidator {

    public static void assertValidAnnotationsForElement(Field field) {
        FindBys findBys = field.getAnnotation(FindBys.class);
        FindAll findAll = field.getAnnotation(FindAll.class);
        FindBy findBy = field.getAnnotation(FindBy.class);
        if (findBys != null && findBy != null) {
            throw new IllegalArgumentException("If you use a \'@FindBys\' annotation, you must not also use a \'@FindBy\' annotation");
        }
        if (findAll != null && findBy != null) {
            throw new IllegalArgumentException("If you use a \'@FindAll\' annotation, you must not also use a \'@FindBy\' annotation");
        }
        if (findAll != null && findBys != null) {
            throw new IllegalArgumentException("If you use a \'@FindAll\' annotation, you must not also use a \'@FindBys\' annotation");
        }
    }

    public static void assertValidFindBys(FindBys findBys) {
        for (FindBy findBy : findBys.value()) {
            assertValidFindBy(findBy);
        }
    }

    public static void assertValidFindAll(FindAll findBys) {
        for (FindBy findBy : findBys.value()) {
            assertValidFindBy(findBy);
        }
    }

    public static void assertValidFindBy(FindBy findBy) {
        HashSet<String> finders = new HashSet<>();
        if (!StringUtils.isBlank(findBy.className())) {
            finders.add("class name:" + findBy.className());
        }
        if (!StringUtils.isBlank(findBy.css())) {
            finders.add("css:" + findBy.css());
        }
        if (!StringUtils.isBlank(findBy.id())) {
            finders.add("id: " + findBy.id());
        }
        if (!StringUtils.isBlank(findBy.linkText())) {
            finders.add("link text: " + findBy.linkText());
        }
        if (!StringUtils.isBlank(findBy.name())) {
            finders.add("name: " + findBy.name());
        }
        if (!StringUtils.isBlank(findBy.partialLinkText())) {
            finders.add("partial link text: " + findBy.partialLinkText());
        }
        if (!StringUtils.isBlank(findBy.tagName())) {
            finders.add("tag name: " + findBy.tagName());
        }
        if (!StringUtils.isBlank(findBy.xpath())) {
            finders.add("xpath: " + findBy.xpath());
        }
        if (!StringUtils.isBlank(findBy.xpath())) {
            finders.add("model: " + findBy.model());
        }
        if (!StringUtils.isBlank(findBy.xpath())) {
            finders.add("binding: " + findBy.binding());
        }
        if (!StringUtils.isBlank(findBy.xpath())) {
            finders.add("buttonText: " + findBy.buttonText());
        }
        if (!StringUtils.isBlank(findBy.xpath())) {
            finders.add("cssContainingText: " + findBy.cssContainingText());
        }
        if (!StringUtils.isBlank(findBy.xpath())) {
            finders.add("deepCss: " + findBy.deepCss());
        }
        if (!StringUtils.isBlank(findBy.xpath())) {
            finders.add("exactBinding: " + findBy.exactBinding());
        }
        if (!StringUtils.isBlank(findBy.xpath())) {
            finders.add("options: " + findBy.options());
        }
        if (!StringUtils.isBlank(findBy.xpath())) {
            finders.add("partialButtonText: " + findBy.partialButtonText());
        }
        if (!StringUtils.isBlank(findBy.xpath())) {
            finders.add("repeater: " + findBy.repeater());
        }
        if (!StringUtils.isBlank(findBy.xpath())) {
            finders.add("repeaterCell: " + findBy.repeaterCell());
        }
        if (!StringUtils.isBlank(findBy.xpath())) {
            finders.add("repeaterColumn: " + findBy.repeaterColumn());
        }
        if (!StringUtils.isBlank(findBy.xpath())) {
            finders.add("repeaterRow: " + findBy.repeaterRow());
        }
        if (finders.size() > 1) {
            throw new IllegalArgumentException(String.format("You must specify at most one location strategy. Number found: %d (%s)", new Object[]{Integer.valueOf(finders.size()), finders.toString()}));
        }
    }
}
