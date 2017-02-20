package com.malski.core.web.factory;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SelectorAll extends Selector implements Serializable {
    private static final long serialVersionUID = 4573668832699497306L;
    private Selector[] selectors;

    public SelectorAll(Selector... selectors) {
        this.selectors = selectors;
    }

    public WebElement findElement(SearchContext context) {
        List<WebElement> elements = this.findElements(context);
        if(elements.isEmpty()) {
            throw new NoSuchElementException("Cannot locate an element using " + this.toString());
        } else {
            return elements.get(0);
        }
    }

    public List<WebElement> findElements(SearchContext context) {
        List<WebElement> elems = new ArrayList<>();
        for(Selector selector : this.selectors) {
            elems.addAll(selector.findElements(context));
        }
        return elems;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Selector.all(");
        stringBuilder.append("{");
        boolean first = true;
        for(Selector selector : this.selectors) {
            stringBuilder.append(first?"":",").append(selector);
            first = false;
        }
        stringBuilder.append("})");
        return stringBuilder.toString();
    }
}