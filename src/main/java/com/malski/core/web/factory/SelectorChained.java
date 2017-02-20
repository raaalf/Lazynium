package com.malski.core.web.factory;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SelectorChained extends Selector implements Serializable {
    private static final long serialVersionUID = 1563769051170172451L;
    private Selector[] selectors;

    public SelectorChained(Selector... selectors) {
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
        if(this.selectors.length == 0) {
            return new ArrayList<>();
        } else {
            List<WebElement> elems = null;
            for(Selector selector : this.selectors) {
                List<WebElement> newElems = new ArrayList<>();
                if(elems == null) {
                    newElems.addAll(selector.findElements(context));
                } else {
                    elems.forEach(elem -> newElems.addAll(elem.findElements(selector)));
                }
                elems = newElems;
            }
            return elems;
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Selector.chained(");
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
