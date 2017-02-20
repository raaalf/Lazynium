package com.malski.core.web.factory;

import com.malski.core.web.annotations.*;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ByIdOrName;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.pagefactory.AbstractAnnotations;

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
        return buildSelector().getBy();
    }

    public Selector buildSelector() {
        if (field == null) {
            return buildByFromClassForComponent();
        } else if (com.malski.core.web.view.Component.class.isAssignableFrom(field.getType())) {
            return buildByFromFieldForComponent();
        } else {
            return buildByForElement();
        }
    }

    private Selector buildByFromFieldForComponent() {
        FindBy findBy = null;
        if (this.field.isAnnotationPresent(IComponent.class)) {
            IComponent component = this.field.getAnnotation(IComponent.class);
            findBy = component.value();
        } else if (this.field.isAnnotationPresent(IFrame.class)) {
            IFrame frame = this.field.getAnnotation(IFrame.class);
            findBy = frame.value();
        }
        if (findBy == null || Selector.isFindByUnset(findBy)) {
            if (field != null) {
                this.clazz = field.getType();
            }
            return buildByFromClassForComponent();
        } else {
            return handleFindByForComponent(findBy);
        }
    }

    private Selector buildByFromClassForComponent() {
        if (clazz.isAnnotationPresent(FindBy.class)) {
            FindBy findBy = (FindBy) clazz.getAnnotation(FindBy.class);
            return handleFindByForComponent(findBy);
        }
        throw new IllegalArgumentException("\'@FindBy\' annotation has to be specified either in interface definition or in field declaration using \'@IComponent\' or \'@IFrame\' !");
    }

    private Selector handleFindByForComponent(FindBy findBy) {
        Selector selector = null;
        if (findBy != null) {
            selector = this.buildByFromFindBy(findBy);
        }
        if (selector == null) {
            selector = this.buildByFromDefault();
        }
        if (selector == null) {
            throw new IllegalArgumentException("Cannot determine how to locate component root element.");
        } else {
            return selector;
        }
    }

    private Selector buildByFromDefault() {
        if (this.field == null) {
            return new Selector(new ByIdOrName(this.clazz.getName()));
        } else {
            return new Selector(new ByIdOrName(this.field.getName()));
        }
    }

    private Selector buildByForElement() {
        AnnotationValidator.assertValidAnnotationsForElement(this.field);
        Selector selector = null;
        FindBys findBys = this.field.getAnnotation(FindBys.class);
        if (findBys != null) {
            selector = this.buildByFromFindBys(findBys);
        }
        FindAll findAll = this.field.getAnnotation(FindAll.class);
        if (selector == null && findAll != null) {
            selector = this.buildBysFromFindByOneOf(findAll);
        }
        FindBy findBy = this.field.getAnnotation(FindBy.class);
        if (selector == null && findBy != null) {
            selector = this.buildByFromFindBy(findBy);
        }
        if (selector == null) {
            selector = this.buildByFromDefault();
        }
        if (selector == null) {
            throw new IllegalArgumentException("Cannot determine how to locate element " + this.field);
        } else {
            return selector;
        }
    }

    protected Selector buildByFromFindBys(FindBys findBys) {
        AnnotationValidator.assertValidFindBys(findBys);
        FindBy[] findByArray = findBys.value();
        Selector[] selectorArray = new Selector[findByArray.length];

        for(int i = 0; i < findByArray.length; ++i) {
            selectorArray[i] = this.buildByFromFindBy(findByArray[i]);
        }

        return new SelectorChained(selectorArray);
    }

    protected Selector buildBysFromFindByOneOf(FindAll findBys) {
        AnnotationValidator.assertValidFindAll(findBys);
        FindBy[] findByArray = findBys.value();
        Selector[] selectorArray = new Selector[findByArray.length];

        for(int i = 0; i < findByArray.length; ++i) {
            selectorArray[i] = this.buildByFromFindBy(findByArray[i]);
        }
        return new SelectorAll(selectorArray);
    }

    protected Selector buildByFromFindBy(FindBy findBy) {
        AnnotationValidator.assertValidFindBy(findBy);
        return new Selector(findBy);
    }
}