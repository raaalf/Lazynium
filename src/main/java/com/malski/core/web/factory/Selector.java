package com.malski.core.web.factory;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.List;

public class Selector extends By {
    private Type type;
    private String value;
    private By by;

    public Selector(By by) {
        String[] tmp = by.toString().split(":");
        this.type = Type.fromString(tmp[0]);
        this.value = tmp[1].trim();
        this.by = by;
    }

    public Type getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public List<WebElement> findElements(SearchContext searchContext) {
        return by.findElements(searchContext);
    }

    @Override
    public WebElement findElement(SearchContext searchContext) {
        return by.findElement(searchContext);
    }

    public By getBy() {
        return by;
    }

    public enum Type {
        CSS("cssSelector"),
        ID("id"),
        XPATH("xpath"),
        NAME("name"),
        CLASS_NAME("className"),
        TAG_NAME("tagName"),
        LINK_TEXT("linkText"),
        PARTIAL_LINK_TEXT("partialLinkText");

        private String type;

        Type(String type) {
            this.type = type;
        }

        public String getType() {
            return this.type;
        }

        public static Type fromString(String stringFromBy) {
            String text = stringFromBy.replaceAll("By\\.", "").trim();
            for (Type t : Type.values()) {
                if (text.equalsIgnoreCase(t.type)) {
                    return t;
                }
            }
            return null;
        }
    }
}
