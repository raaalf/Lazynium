package com.malski.core.web.elements.impl;

import com.malski.core.web.elements.api.Element;
import com.malski.core.web.elements.api.Elements;
import com.malski.core.web.elements.api.Table;
import com.malski.core.web.factory.LazyLocator;
import com.malski.core.web.factory.Selector;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class TableImpl extends ElementImpl implements Table {

    public TableImpl(By by, SearchContext context) {
        super(by, context);
    }

    public TableImpl(Selector selector, SearchContext context) {
        super(selector, context);
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
    public Elements<Element> getColumnHeaders() {
        return $$("tr th");
    }

    @Override
    public Element getColumnHeader(int colIdx) {
        return getColumnHeaders().get(colIdx);
    }

    @Override
    public Element getColumnHeader(String headerText) {
        for (Element header : getColumnHeaders()) {
            if (headerText.equalsIgnoreCase(header.getText().trim())) {
                return header;
            }
        }
        throw new NoSuchElementException("No header with name: " + headerText);
    }

    @Override
    public int getColumnHeaderIndex(String headerText) {
        for (int i = 0; i < getColumnHeaders().size(); ++i) {
            if (headerText.equalsIgnoreCase(getColumnHeaders().get(i).getText().trim())) {
                return i;
            }
        }
        throw new NoSuchElementException("No header with name: " + headerText);
    }

    @Override
    public Elements<Element> getRows() {
        return $$("tbody>tr");
    }

    @Override
    public Element getRow(int rowIdx) {
        Elements<Element> rows = getRows();
        if (rows.size() <= rowIdx) {
            throw new NoSuchElementException("No row with index: " + rowIdx + ". Max row: " + rows.size());
        }
        return rows.get(rowIdx);
    }

    @Override
    public Element getRow(String text) {
        for (Element row : getRows()) {
            if (row.getText().contains(text)) {
                return row;
            }
        }
        throw new NoSuchElementException("No row with text: " + text);
    }

    @Override
    public int getRowIndex(String text) {
        int i = 0;
        for (Element row : getRows()) {
            if (row.getText().contains(text)) {
                return i;
            }
            i++;
        }
        throw new NoSuchElementException("No row with text: " + text);
    }

    @Override
    public Elements<Element> getRowCells(int index) {
        return getRow(index).getElements(By.tagName("td"));
    }

    @Override
    public Elements<Element> getRowCells(String text) {
        return getRow(text).getElements(By.tagName("td"));
    }

    @Override
    public Element getCell(int rowIndex, int colIndex) {
        Elements<Element> cells = getRowCells(rowIndex);
        if (cells.size() <= colIndex) {
            throw new NoSuchElementException("No column with index: " + colIndex + ". Max index: " + cells.size());
        }
        return cells.get(colIndex);
    }

    @Override
    public Element getCell(int rowIndex, String columnName) {
        Elements<Element> cells = getRowCells(rowIndex);
        int colIndex = getColumnHeaderIndex(columnName);
        if (colIndex < 0) {
            throw new NoSuchElementException("No column with name: " + columnName);
        }
        return cells.get(colIndex);
    }

    @Override
    public Element getCell(String rowText, int colIndex) {
        Elements<Element> cells = getRowCells(rowText);
        if (cells.size() <= colIndex) {
            throw new NoSuchElementException("No column with index: " + colIndex + ". Max index: " + cells.size());
        }
        return cells.get(colIndex);
    }

    @Override
    public Element getCell(String rowText, String columnName) {
        Elements<Element> cells = getRowCells(rowText);
        int colIndex = getColumnHeaderIndex(columnName);
        if (colIndex < 0) {
            throw new NoSuchElementException("No column with name: " + columnName);
        }
        return cells.get(colIndex);
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
