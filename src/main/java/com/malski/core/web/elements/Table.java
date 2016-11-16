package com.malski.core.web.elements;

import com.malski.core.web.factory.LazyLocator;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Table extends Element {

    public Table(LazyLocator locator) {
        super(locator);
    }

    public Table(LazyLocator locator, WebElement element) {
        super(locator, element);
    }

    public int getRowCount() {
        if ($x("./tbody/tr[./td]").isPresent(1)) {
            return getRows().size();
        } else {
            return 0;
        }
    }

    public Elements<Element> getColumnHeaders() {
        return $$x("./*[self::thead or self::tbody]/tr/th");
    }

    public Element getColumnHeader(int colIdx) {
        return getColumnHeaders().get(colIdx);
    }

    public Element getColumnHeader(String headerText) {
        for (Element header : getColumnHeaders()) {
            if (headerText.equalsIgnoreCase(header.getText().trim())) {
                return header;
            }
        }
        throw new NoSuchElementException("No header with name: " + headerText);
    }

    public int getColumnHeaderIndex(String headerText) {
        Elements<Element> headers = getColumnHeaders();
        for (int i = 0; i < headers.size(); ++i) {
            if (headerText.equalsIgnoreCase(headers.get(i).getText().trim())) {
                return i;
            }
        }
        throw new NoSuchElementException("No header with name: " + headerText);
    }

    public int getColumnIndex(String firstRowText) {
        Elements<Element> firstRow = getRowCells(0);
        for (int i = 0; i < firstRow.size(); ++i) {
            if (firstRowText.equalsIgnoreCase(firstRow.get(i).getText().trim())) {
                return i;
            }
        }
        throw new NoSuchElementException("No first row with text: " + firstRowText);
    }

    public Elements<Element> getRows() {
        return getRows(Element.class);
    }

    public <T extends Element> Elements<T> getRows(Class<T> clazz) {
        return $$x("./tbody/tr[./td]", clazz);
    }

    public Elements<Element> getRows(String text) {
        return getRows(text, Element.class);
    }

    public <T extends Element> Elements<T> getRows(String text, Class<T> clazz) {
        Elements<T> rows = getEmptyElementsList();
        rows.addAll(getRows(clazz).stream().filter(row -> StringUtils.containsIgnoreCase(row.getText(), text)).collect(Collectors.toList()));
        return rows;
    }

    public Elements<Element> getRows(String text, int colIndex) {
        return getRows(text, colIndex, Element.class);
    }

    public <T extends Element> Elements<T> getRows(String text, int colIndex, Class<T> clazz) {
        int headersSize = getColumnHeaders().size();
        if (headersSize <= colIndex) {
            throw new NoSuchElementException("No column with index: " + colIndex + ". Max index: " + headersSize);
        }
        Elements<T> rowsWitText = getEmptyElementsList();
        rowsWitText.addAll(getRows(clazz).stream().filter(row -> StringUtils.containsIgnoreCase(getCells(row).get(colIndex).getText(), text)).collect(Collectors.toList()));
        return rowsWitText;
    }

    public Elements<Element> getRows(String text, String columnName) {
        return getRows(text, columnName, Element.class);
    }

    public <T extends Element> Elements<T> getRows(String text, String columnName, Class<T> clazz) {
        int colIndex = getColumnHeaderIndex(columnName);
        return getRows(text, colIndex, clazz);
    }

    public Element getRow(int rowIdx) {
        return getRow(rowIdx, Element.class);
    }

    public <T extends Element> T getRow(int rowIdx, Class<T> clazz) {
        Elements<T> rows = getRows(clazz);
        if (rows.size() <= rowIdx) {
            throw new NoSuchElementException("No row with index: " + rowIdx + ". Max row: " + rows.size());
        }
        return rows.get(rowIdx);
    }

    public Element getRow(String text) {
        return getRow(text, Element.class);
    }

    public <T extends Element> T getRow(String text, Class<T> clazz) {
        for (T row : getRows(clazz)) {
            if (StringUtils.containsIgnoreCase(row.getText(), text)) {
                return row;
            }
        }
        throw new NoSuchElementException("No row with text: " + text);
    }

    public Element getRow(String rowText, String columnName) {
        return getRow(rowText, columnName, Element.class);
    }

    public <T extends Element> T getRow(String rowText, String columnName, Class<T> clazz) {
        int colIndex = getColumnHeaderIndex(columnName);
        return getRow(rowText, colIndex, clazz);
    }

    public Element getRow(String rowText, int columnIndex) {
        return getRow(rowText, columnIndex, Element.class);
    }

    public <T extends Element> T getRow(String rowText, int colIndex, Class<T> clazz) {
        int headersSize = getColumnHeaders().size();
        if (headersSize <= colIndex) {
            throw new NoSuchElementException("No column with index: " + colIndex + ". Max index: " + headersSize);
        }
        for (T row : getRows(clazz)) {
            Elements<Element> cells = getCells(row);
            if (cells.size() > colIndex && StringUtils.containsIgnoreCase(cells.get(colIndex).getText(), rowText)) {
                return row;
            }
        }
        throw new NoSuchElementException("No row with text: " + rowText);
    }

    public int getRowIndex(String text) {
        int i = 0;
        for (Element row : getRows()) {
            if (StringUtils.containsIgnoreCase(row.getText(), text)) {
                return i;
            }
            i++;
        }
        throw new NoSuchElementException("No row with text: " + text);
    }

    public Elements<Element> getRowCells(int index) {
        return getRowCells(index, Element.class);
    }

    public <T extends Element> Elements<T> getRowCells(int index, Class<T> clazz) {
        return getCells(getRow(index), clazz);
    }

    public Elements<Element> getRowCells(String text) {
        return getRowCells(text, Element.class);
    }

    public <T extends Element> Elements<T> getRowCells(String text, Class<T> clazz) {
        return getCells(getRow(text), clazz);
    }

    public Elements<Element> getRowCells(String rowText, String columnName) {
        return getRowCells(rowText, columnName, Element.class);
    }

    public <T extends Element> Elements<T> getRowCells(String rowText, String columnName, Class<T> clazz) {
        int colIndex = getColumnHeaderIndex(columnName);
        return getRowCells(rowText, colIndex, clazz);
    }

    public Elements<Element> getRowCells(String rowText, int columnIndex) {
        return getRowCells(rowText, columnIndex, Element.class);
    }

    public <T extends Element> Elements<T> getRowCells(String rowText, int colIndex, Class<T> clazz) {
        return getCells(getRow(rowText, colIndex), clazz);
    }

    public Element getCell(int rowIndex, int colIndex) {
        return getCell(rowIndex, colIndex, Element.class);
    }

    public <T extends Element> T getCell(int rowIndex, int colIndex, Class<T> clazz) {
        Elements<T> cells = getRowCells(rowIndex, clazz);
        if (cells.size() <= colIndex) {
            throw new NoSuchElementException("No column with index: " + colIndex + ". Max index: " + cells.size());
        }
        return cells.get(colIndex);
    }

    public Element getCell(int rowIndex, String columnName) {
        return getCell(rowIndex, columnName, Element.class);
    }

    public <T extends Element> T getCell(int rowIndex, String columnName, Class<T> clazz) {
        Elements<T> cells = getRowCells(rowIndex, clazz);
        int colIndex = getColumnHeaderIndex(columnName);
        return cells.get(colIndex);
    }

    public Element getCell(String rowText, int colIndex) {
        return getCell(rowText, colIndex, Element.class);
    }

    public <T extends Element> T getCell(String rowText, int colIndex, Class<T> clazz) {
        Elements<T> cells = getRowCells(rowText, clazz);
        if (cells.size() <= colIndex) {
            throw new NoSuchElementException("No column with index: " + colIndex + ". Max index: " + cells.size());
        }
        return cells.get(colIndex);
    }

    public Element getCell(String rowText, String columnName) {
        return getCell(rowText, columnName, Element.class);
    }

    public <T extends Element> T getCell(String rowText, String columnName, Class<T> clazz) {
        Elements<T> cells = getRowCells(rowText, clazz);
        int colIndex = getColumnHeaderIndex(columnName);
        return cells.get(colIndex);
    }

    public Element getCellByExactMatch(String rowText, String columnToMatch, String columnName) {
        return getCellByExactMatch(rowText, columnToMatch, columnName, Element.class);
    }

    public <T extends Element> T getCellByExactMatch(String rowText, String columnToMatch, String columnName, Class<T> clazz) {
        Predicate<String> checkForExactMatch = n -> n.equals(rowText.trim());
        List<Element> foundRow = new ArrayList<>();
        for (Element row : getRows(rowText, columnToMatch)) {
            getCells(row).getTexts().stream().map(String::trim).filter(checkForExactMatch).forEach(n -> foundRow.add(row));
        }

        int colIndex = getColumnHeaderIndex(columnName);
        return getCells(foundRow.get(0), clazz).get(colIndex);
    }

    public Element getHead() {
        return $x("./thead");
    }

    public Element getFooter() {
        return $x("./tfoot");
    }

    public List<Map<String, String>> getTableAsListOfMaps() {
        List<Map<String, String>> rowList = new ArrayList<>();
        List<String> headers = getColumnHeaders().getTexts();
        for (Element row : getRows()) {
            List<String> cells = getCells(row).getTexts();
            Map<String, String> rowMap = new HashMap<>();
            for (int i = 0; i < headers.size(); ++i) {
                rowMap.put(headers.get(i), cells.get(i).trim());
            }
            rowList.add(rowMap);
        }
        return rowList;
    }

    public List<String> getColumnTexts(int columnIndex) {
        List<String> rowList = new ArrayList<>();
        for (Element w : getRows()) {
            Elements<Element> cells = getCells(w);
            if (cells.size() > 0)
                rowList.add(cells.get(columnIndex).getText().trim());
        }
        return rowList;
    }

    public List<String> getColumnTexts(String columnName) {
        List<String> rowList = new ArrayList<>();
        int columnIndex = getColumnHeaderIndex(columnName);
        for (Element w : getRows()) {
            Elements<Element> cells = getCells(w);
            if (cells.size() > 0 && cells.size() > columnIndex)
                rowList.add(cells.get(columnIndex).getText().trim());
        }
        return rowList;
    }

    public List<String> getColumnTextsByFirstRow(String firstRowText) {
        List<String> rowList = new ArrayList<>();
        int columnIndex = getColumnIndex(firstRowText);
        for (Element w : getRows()) {
            Elements<Element> cells = getCells(w);
            if (cells.size() > 0 && cells.size() > columnIndex)
                rowList.add(cells.get(columnIndex).getText().trim());
        }
        return rowList;
    }

    public String getTextFromColumn(String rowText, String columnName) {
        return getCell(rowText, columnName).getText();
    }

    private Elements<Element> getCells(Element row) {
        return getCells(row, Element.class);
    }

    private <T extends Element> Elements<T> getCells(Element row, Class<T> clazz) {
        return row.$$x("./td", clazz);
    }
}
