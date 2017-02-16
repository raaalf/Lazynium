package com.malski.core.web.factory;

import com.malski.core.web.annotations.*;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ByIdOrName;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.pagefactory.AbstractAnnotations;
import org.openqa.selenium.support.pagefactory.ByAll;
import org.openqa.selenium.support.pagefactory.ByChained;

import java.lang.reflect.Field;

public class LazyAnnotations extends AbstractAnnotations {
    private Field field;
    private Class clazz;

    public LazyAnnotations(Field field) {
        this.field = field;
    }

    public LazyAnnotations(Class clazz) {
        this.clazz = clazz;
    }

    public boolean isLookupCached() {
        return this.field.getAnnotation(CacheLookup.class) != null;
    }

    public By buildBy() {
        if (field == null) {
            return buildByFromClassForComponent();
        } else if (com.malski.core.web.view.Component.class.isAssignableFrom(field.getType())) {
            return buildByFromFieldForComponent();
        } else {
            return buildByForElement();
        }
    }

    private By buildByFromFieldForComponent() {
        FindBy findBy = null;
        if (this.field.isAnnotationPresent(IComponent.class)) {
            IComponent component = this.field.getAnnotation(IComponent.class);
            findBy = component.value();
        } else if (this.field.isAnnotationPresent(IFrame.class)) {
            IFrame frame = this.field.getAnnotation(IFrame.class);
            findBy = frame.value();
        }
        if (findBy == null || AnnotationFactory.isFindByUnset(findBy)) {
            if (field != null) {
                this.clazz = field.getType();
            }
            return buildByFromClassForComponent();
        } else {
            return handleFindByForComponent(findBy);
        }
    }

    private By buildByFromClassForComponent() {
        if (clazz.isAnnotationPresent(FindBy.class)) {
            FindBy findBy = (FindBy) clazz.getAnnotation(FindBy.class);
            return handleFindByForComponent(findBy);
        }
        throw new IllegalArgumentException("\'@FindBy\' annotation has to be specified either in interface definition or in field declaration using \'@IComponent\' or \'@IFrame\' !");
    }

    private By handleFindByForComponent(FindBy findBy) {
        By ans = null;
        if (findBy != null) {
            ans = this.buildByFromFindBy(findBy);
        }
        if (ans == null) {
            ans = this.buildByFromDefault();
        }

        if (ans == null) {
            throw new IllegalArgumentException("Cannot determine how to locate component root element.");
        } else {
            return ans;
        }
    }

    private By buildByFromDefault() {
        if (this.field == null) {
            return new ByIdOrName(this.clazz.getName());
        } else {
            return new ByIdOrName(this.field.getName());
        }
    }

    private By buildByForElement() {
        AnnotationValidator.assertValidAnnotationsForElement(this.field);
        By ans = null;
        FindBys findBys = this.field.getAnnotation(FindBys.class);
        if (findBys != null) {
            ans = this.buildByFromFindBys(findBys);
        }

        FindAll findAll = this.field.getAnnotation(FindAll.class);
        if (ans == null && findAll != null) {
            ans = this.buildBysFromFindByOneOf(findAll);
        }

        FindBy findBy = this.field.getAnnotation(FindBy.class);
        if (ans == null && findBy != null) {
            ans = this.buildByFromFindBy(findBy);
        }

        if (ans == null) {
            ans = this.buildByFromDefault();
        }

        if (ans == null) {
            throw new IllegalArgumentException("Cannot determine how to locate element " + this.field);
        } else {
            return ans;
        }
    }

    protected By buildByFromFindBys(FindBys findBys) {
        AnnotationValidator.assertValidFindBys(findBys);
        FindBy[] findByArray = findBys.value();
        By[] byArray = new By[findByArray.length];

        for(int i = 0; i < findByArray.length; ++i) {
            byArray[i] = this.buildByFromFindBy(findByArray[i]);
        }

        return new ByChained(byArray);
    }

    protected By buildBysFromFindByOneOf(FindAll findBys) {
        AnnotationValidator.assertValidFindAll(findBys);
        FindBy[] findByArray = findBys.value();
        By[] byArray = new By[findByArray.length];

        for(int i = 0; i < findByArray.length; ++i) {
            byArray[i] = this.buildByFromFindBy(findByArray[i]);
        }

        return new ByAll(byArray);
    }

    protected By buildByFromFindBy(FindBy findBy) {
        AnnotationValidator.assertValidFindBy(findBy);
        return AnnotationFactory.buildByFromShortFindBy(findBy);
    }
}