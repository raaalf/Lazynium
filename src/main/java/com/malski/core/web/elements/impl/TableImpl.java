package com.malski.core.web.elements.impl;

import com.malski.core.web.base.LazySearchContext;
import com.malski.core.web.elements.api.Element;
import com.malski.core.web.elements.api.Elements;
import com.malski.core.web.elements.api.Table;
import com.malski.core.web.factory.LazyLocator;
import com.malski.core.web.factory.Selector;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.stream.Collectors;

public class TableImpl extends ElementImpl implements Table {

    public TableImpl(By by, LazySearchContext context) {
        super(by, context);
    }

    public TableImpl(Selector selector, LazySearchContext context) {
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
        if ($("tbody>tr").isPresent(1)) {
            return getRows().size();
        } else {
            return 0;
        }
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
        return getRows(Element.class);
    }

    @Override
    public <T extends Element> Elements<T> getRows(Class<T> clazz) {
        return $$("tbody>tr", clazz);
    }

    @Override
    public Elements<Element> getRows(String text) {
        Elements<Element> rows = getEmptyElementsList();
        rows.addAll(getRows().stream().filter(row -> row.getText().contains(text)).collect(Collectors.toList()));
        return rows;
    }

    @Override
    public Elements<Element> getRows(int colIndex, String text) {
        int headersSize = getColumnHeaders().size();
        if (headersSize <= colIndex) {
            throw new NoSuchElementException("No column with index: " + colIndex + ". Max index: " + headersSize);
        }
        Elements<Element> rows = getEmptyElementsList();
        for (Element row : getRows()) {
            Elements<Element> cells = getCells(row);
            if (cells.get(colIndex).getText().contains(text)) {
                rows.add(row);
            }
        }
        return rows;
    }

    @Override
    public Elements<Element> getRows(String columnName, String text) {
        int colIndex = getColumnHeaderIndex(columnName);
        if (colIndex < 0) {
            throw new NoSuchElementException("No column with name: " + columnName);
        }
        return getRows(colIndex, text);
    }

    @Override
    public Element getRow(int rowIdx) {
        return getRow(rowIdx, Element.class);
    }

    @Override
    public <T extends Element> T getRow(int rowIdx, Class<T> clazz) {
        Elements<T> rows = getRows(clazz);
        if (rows.size() <= rowIdx) {
            throw new NoSuchElementException("No row with index: " + rowIdx + ". Max row: " + rows.size());
        }
        return rows.get(rowIdx);
    }

    @Override
    public Element getRow(String text) {
        return getRow(text, Element.class);
    }

    @Override
    public <T extends Element> T getRow(String text, Class<T> clazz) {
        for (T row : getRows(clazz)) {
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
        return getCells(getRow(index));
    }

    @Override
    public <T extends Element> Elements<T> getRowCells(int index, Class<T> clazz) {
        return getCells(getRow(index), clazz);
    }

    @Override
    public Elements<Element> getRowCells(String text) {
        return getCells(getRow(text));
    }

    @Override
    public <T extends Element> Elements<T> getRowCells(String text, Class<T> clazz) {
        return getCells(getRow(text), clazz);
    }

    @Override
    public Element getCell(int rowIndex, int colIndex) {
        return getCell(rowIndex, colIndex, Element.class);
    }

    @Override
    public <T extends Element> T getCell(int rowIndex, int colIndex, Class<T> clazz) {
        Elements<T> cells = getRowCells(rowIndex, clazz);
        if (cells.size() <= colIndex) {
            throw new NoSuchElementException("No column with index: " + colIndex + ". Max index: " + cells.size());
        }
        return cells.get(colIndex);
    }

    @Override
    public Element getCell(int rowIndex, String columnName) {
        return getCell(rowIndex, columnName, Element.class);
    }

    @Override
    public <T extends Element> T getCell(int rowIndex, String columnName, Class<T> clazz) {
        Elements<T> cells = getRowCells(rowIndex, clazz);
        int colIndex = getColumnHeaderIndex(columnName);
        if (colIndex < 0) {
            throw new NoSuchElementException("No column with name: " + columnName);
        }
        return cells.get(colIndex);
    }

    @Override
    public Element getCell(String rowText, int colIndex) {
        return getCell(rowText, colIndex, Element.class);
    }

    @Override
    public <T extends Element> T getCell(String rowText, int colIndex, Class<T> clazz) {
        Elements<T> cells = getRowCells(rowText, clazz);
        if (cells.size() <= colIndex) {
            throw new NoSuchElementException("No column with index: " + colIndex + ". Max index: " + cells.size());
        }
        return cells.get(colIndex);
    }

    @Override
    public Element getCell(String rowText, String columnName) {
        return getCell(rowText, columnName, Element.class);
    }

    @Override
    public <T extends Element> T getCell(String rowText, String columnName, Class<T> clazz) {
        Elements<T> cells = getRowCells(rowText, clazz);
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

    private Elements<Element> getCells(Element row) {
        return getCells(row, Element.class);
    }

    private <T extends Element> Elements<T> getCells(Element row, Class<T> clazz) {
        return row.getElements(By.tagName("td"), clazz);
    }
}
