package com.malski.core.web.factory;

import com.malski.core.web.annotations.IFrame;
import com.malski.core.web.annotations.Module;
import com.malski.core.web.page.api.WebModule;
import org.openqa.selenium.By;
import org.openqa.selenium.support.*;
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
        if (field == null) {
            return buildByFromClassForModule();
        } else if (WebModule.class.isAssignableFrom(field.getType())) {
            return buildByFromFieldForModule();
        } else {
            return buildByForElement();
        }
    }

    private By buildByFromFieldForModule() {
        FindBy findBy = null;
        if (this.field.isAnnotationPresent(Module.class)) {
            Module module = this.field.getAnnotation(Module.class);
            findBy = module.value();
        } else if (this.field.isAnnotationPresent(IFrame.class)) {
            IFrame frame = this.field.getAnnotation(IFrame.class);
            findBy = frame.value();
        }
        if (findBy == null || isFindByUnset(findBy)) {
            if (field != null) {
                try {
                    this.clazz = ImplHandler.getInterfaceImpl(field.getType());
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException("no interface implementation.");
                }
            }
            return buildByFromClassForModule();
        } else {
            return handleFindByForModule(findBy);
        }
    }

    private boolean isFindByUnset(FindBy findBy) {
        return "".equals(findBy.className()) && "".equals(findBy.css()) && "".equals(findBy.id()) && "".equals(findBy.linkText())
                && "".equals(findBy.name()) && "".equals(findBy.partialLinkText()) && "".equals(findBy.tagName())
                && "".equals(findBy.xpath()) && How.UNSET.equals(findBy.how());
    }

    private By buildByFromClassForModule() {
        if (clazz.isAnnotationPresent(FindBy.class)) {
            FindBy findBy = (FindBy) clazz.getAnnotation(FindBy.class);
            return handleFindByForModule(findBy);
        }
        throw new IllegalArgumentException("\'@FindBy\' annotation has to be specified either in interface definition or in field declaration using \'@WebModuleImpl\' or \'@IFrame\' !");
    }

    private By handleFindByForModule(FindBy findBy) {
        By ans = null;
        if (findBy != null) {
            ans = this.buildByFromFindBy(findBy);
        }
        if (ans == null) {
            ans = this.buildByFromDefault();
        }

        if (ans == null) {
            throw new IllegalArgumentException("Cannot determine how to locate module root element.");
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
        this.assertValidAnnotationsForElement();
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

    private void assertValidAnnotationsForElement() {
        FindBys findBys = this.field.getAnnotation(FindBys.class);
        FindAll findAll = this.field.getAnnotation(FindAll.class);
        FindBy findBy = this.field.getAnnotation(FindBy.class);
        if (findBys != null && findBy != null) {
            throw new IllegalArgumentException("If you use a \'@FindBys\' annotation, you must not also use a \'@FindBy\' annotation");
        } else if (findAll != null && findBy != null) {
            throw new IllegalArgumentException("If you use a \'@FindAll\' annotation, you must not also use a \'@FindBy\' annotation");
        } else if (findAll != null && findBys != null) {
            throw new IllegalArgumentException("If you use a \'@FindAll\' annotation, you must not also use a \'@FindBys\' annotation");
        }
    }
}