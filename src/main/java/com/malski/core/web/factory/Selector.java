package com.malski.core.web.factory;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.How;

import java.util.List;

public class Selector extends By {
    private How how;
    private String using;
    private By by;

    public Selector(By by) {
        String[] tmp = by.toString().split(":");
        this.how = getHowFromByString(tmp[0]);
        this.using = tmp[1].trim();
        this.by = by;
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

    public By getBy() {
        return by;
    }

    private How getHowFromByString(String by) {
        String text = by.replaceAll("By\\.", "").trim().toLowerCase();
        switch(text) {
            case "cssaelector": return How.CSS;
            case "id": return How.ID;
            case "xpath": return How.CSS;
            case "name": return How.NAME;
            case "classname": return How.CLASS_NAME;
            case "tagname": return How.TAG_NAME;
            case "linktext": return How.XPATH;
            case "partiallinktext": return How.PARTIAL_LINK_TEXT;
            case "by id or name": return How.ID_OR_NAME;
            default: return How.UNSET;
        }
    }
}
