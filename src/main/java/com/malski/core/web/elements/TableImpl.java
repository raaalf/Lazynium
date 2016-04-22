package com.malski.core.web.elements;

import com.malski.core.web.factory.LazyLocator;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TableImpl extends ElementImpl implements Table {

    public TableImpl(By by, SearchContext context) {
        super(by, context);
    }

    public TableImpl(LazyLocator locator) {
        super(locator);
    }

    public TableImpl(LazyLocator locator, WebElement element) {
        super(locator, element);
    }

    public TableImpl(By by, WebElement element) {
        super(by, element);
    }

    @Override
    public int getRowCount() {
        return getRows().size();
    }

    @Override
    public Element getCell(int rowIdx, int colIdx) {
        Element row = getRow(rowIdx);
        List<Element> cells = row.getElements(By.tagName("td"));
        if (cells.size() > 0) {
            return cells.get(colIdx);
        } else {
            return null;
        }
    }

    @Override
    public List<Element> getColumnHeaders() {
        return $$("tbody tr th");
    }

    @Override
    public Element getColumnHeader(int colIdx) {
        return getColumnHeaders().get(colIdx);
    }

    @Override
    public List<Element> getRows() {
        return $$("tbody tr");
    }

    @Override
    public Element getRow(int rowIdx) {
        return getRows().get(rowIdx);
    }

    @Override
    public Element getHead() {
        return $("thead");
    }

    @Override
    public Element getFooter() {
        return $("tfoot");
    }
}
