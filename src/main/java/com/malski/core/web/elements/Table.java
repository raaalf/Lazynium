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

    public int rowCount() {
        if ($x("./tbody/tr[./td]").isPresent(1)) {
            return rows().size();
        } else {
            return 0;
        }
    }

    public LazyList<Element> columnHeaders() {
        return $$x("./*[self::thead or self::tbody]/tr/th");
    }

    public Element columnHeader(int colIdx) {
        return columnHeaders().get(colIdx);
    }

    public Element columnHeader(String headerText) {
        for (Element header : columnHeaders()) {
            if (headerText.equalsIgnoreCase(header.getText().trim())) {
                return header;
            }
        }
        throw new NoSuchElementException("No header with name: " + headerText);
    }

    public int columnHeaderIndex(String headerText) {
        LazyList<Element> headers = columnHeaders();
        for (int i = 0; i < headers.size(); ++i) {
            if (headerText.equalsIgnoreCase(headers.get(i).getText().trim())) {
                return i;
            }
        }
        throw new NoSuchElementException("No header with name: " + headerText);
    }

    public int columnIndex(String firstRowText) {
        LazyList<Element> firstRow = rowCells(0);
        for (int i = 0; i < firstRow.size(); ++i) {
            if (firstRowText.equalsIgnoreCase(firstRow.get(i).getText().trim())) {
                return i;
            }
        }
        throw new NoSuchElementException("No first row with text: " + firstRowText);
    }

    public LazyList<Element> rows() {
        return rows(Element.class);
    }

    public <T extends Element> LazyList<T> rows(Class<T> clazz) {
        return $$x("./tbody/tr[./td]", clazz);
    }

    public LazyList<Element> rows(String text) {
        return rows(text, Element.class);
    }

    public <T extends Element> LazyList<T> rows(String text, Class<T> clazz) {
        LazyList<T> rows = getEmptyElementsList();
        rows.addAll(rows(clazz).stream().filter(row -> StringUtils.containsIgnoreCase(row.getText(), text)).collect(Collectors.toList()));
        return rows;
    }

    public LazyList<Element> rows(String text, int colIndex) {
        return rows(text, colIndex, Element.class);
    }

    public <T extends Element> LazyList<T> rows(String text, int colIndex, Class<T> clazz) {
        int headersSize = columnHeaders().size();
        if (headersSize <= colIndex) {
            throw new NoSuchElementException("No column with index: " + colIndex + ". Max index: " + headersSize);
        }
        LazyList<T> rowsWitText = getEmptyElementsList();
        rowsWitText.addAll(rows(clazz).stream().filter(row -> StringUtils.containsIgnoreCase(cells(row).get(colIndex).getText(), text)).collect(Collectors.toList()));
        return rowsWitText;
    }

    public LazyList<Element> rows(String text, String columnName) {
        return rows(text, columnName, Element.class);
    }

    public <T extends Element> LazyList<T> rows(String text, String columnName, Class<T> clazz) {
        int colIndex = columnHeaderIndex(columnName);
        return rows(text, colIndex, clazz);
    }

    public Element row(int rowIdx) {
        return row(rowIdx, Element.class);
    }

    public <T extends Element> T row(int rowIdx, Class<T> clazz) {
        LazyList<T> rows = rows(clazz);
        if (rows.size() <= rowIdx) {
            throw new NoSuchElementException("No row with index: " + rowIdx + ". Max row: " + rows.size());
        }
        return rows.get(rowIdx);
    }

    public Element row(String text) {
        return row(text, Element.class);
    }

    public <T extends Element> T row(String text, Class<T> clazz) {
        for (T row : rows(clazz)) {
            if (StringUtils.containsIgnoreCase(row.getText(), text)) {
                return row;
            }
        }
        throw new NoSuchElementException("No row with text: " + text);
    }

    public Element row(String rowText, String columnName) {
        return row(rowText, columnName, Element.class);
    }

    public <T extends Element> T row(String rowText, String columnName, Class<T> clazz) {
        int colIndex = columnHeaderIndex(columnName);
        return row(rowText, colIndex, clazz);
    }

    public Element row(String rowText, int columnIndex) {
        return row(rowText, columnIndex, Element.class);
    }

    public <T extends Element> T row(String rowText, int colIndex, Class<T> clazz) {
        int headersSize = columnHeaders().size();
        if (headersSize <= colIndex) {
            throw new NoSuchElementException("No column with index: " + colIndex + ". Max index: " + headersSize);
        }
        for (T row : rows(clazz)) {
            LazyList<Element> cells = cells(row);
            if (cells.size() > colIndex && StringUtils.containsIgnoreCase(cells.get(colIndex).getText(), rowText)) {
                return row;
            }
        }
        throw new NoSuchElementException("No row with text: " + rowText);
    }

    public int rowIndex(String text) {
        int i = 0;
        for (Element row : rows()) {
            if (StringUtils.containsIgnoreCase(row.getText(), text)) {
                return i;
            }
            i++;
        }
        throw new NoSuchElementException("No row with text: " + text);
    }

    public LazyList<Element> rowCells(int index) {
        return rowCells(index, Element.class);
    }

    public <T extends Element> LazyList<T> rowCells(int index, Class<T> clazz) {
        return cells(row(index), clazz);
    }

    public LazyList<Element> rowCells(String text) {
        return rowCells(text, Element.class);
    }

    public <T extends Element> LazyList<T> rowCells(String text, Class<T> clazz) {
        return cells(row(text), clazz);
    }

    public LazyList<Element> rowCells(String rowText, String columnName) {
        return rowCells(rowText, columnName, Element.class);
    }

    public <T extends Element> LazyList<T> rowCells(String rowText, String columnName, Class<T> clazz) {
        int colIndex = columnHeaderIndex(columnName);
        return rowCells(rowText, colIndex, clazz);
    }

    public LazyList<Element> rowCells(String rowText, int columnIndex) {
        return rowCells(rowText, columnIndex, Element.class);
    }

    public <T extends Element> LazyList<T> rowCells(String rowText, int colIndex, Class<T> clazz) {
        return cells(row(rowText, colIndex), clazz);
    }

    public Element cell(int rowIndex, int colIndex) {
        return cell(rowIndex, colIndex, Element.class);
    }

    public <T extends Element> T cell(int rowIndex, int colIndex, Class<T> clazz) {
        LazyList<T> cells = rowCells(rowIndex, clazz);
        if (cells.size() <= colIndex) {
            throw new NoSuchElementException("No column with index: " + colIndex + ". Max index: " + cells.size());
        }
        return cells.get(colIndex);
    }

    public Element cell(int rowIndex, String columnName) {
        return cell(rowIndex, columnName, Element.class);
    }

    public <T extends Element> T cell(int rowIndex, String columnName, Class<T> clazz) {
        LazyList<T> cells = rowCells(rowIndex, clazz);
        int colIndex = columnHeaderIndex(columnName);
        return cells.get(colIndex);
    }

    public Element cell(String rowText, int colIndex) {
        return cell(rowText, colIndex, Element.class);
    }

    public <T extends Element> T cell(String rowText, int colIndex, Class<T> clazz) {
        LazyList<T> cells = rowCells(rowText, clazz);
        if (cells.size() <= colIndex) {
            throw new NoSuchElementException("No column with index: " + colIndex + ". Max index: " + cells.size());
        }
        return cells.get(colIndex);
    }

    public Element cell(String rowText, String columnName) {
        return cell(rowText, columnName, Element.class);
    }

    public <T extends Element> T cell(String rowText, String columnName, Class<T> clazz) {
        LazyList<T> cells = rowCells(rowText, clazz);
        int colIndex = columnHeaderIndex(columnName);
        return cells.get(colIndex);
    }

    public Element cellByExactMatch(String rowText, String columnToMatch, String columnName) {
        return cellByExactMatch(rowText, columnToMatch, columnName, Element.class);
    }

    public <T extends Element> T cellByExactMatch(String rowText, String columnToMatch, String columnName, Class<T> clazz) {
        Predicate<String> checkForExactMatch = n -> n.equals(rowText.trim());
        List<Element> foundRow = new ArrayList<>();
        for (Element row : rows(rowText, columnToMatch)) {
            cells(row).getTexts().stream().map(String::trim).filter(checkForExactMatch).forEach(n -> foundRow.add(row));
        }

        int colIndex = columnHeaderIndex(columnName);
        return cells(foundRow.get(0), clazz).get(colIndex);
    }

    public Element head() {
        return $x("./thead");
    }

    public Element footer() {
        return $x("./tfoot");
    }

    public List<Map<String, String>> tableAsListOfMaps() {
        List<Map<String, String>> rowList = new ArrayList<>();
        List<String> headers = columnHeaders().getTexts();
        for (Element row : rows()) {
            List<String> cells = cells(row).getTexts();
            Map<String, String> rowMap = new HashMap<>();
            for (int i = 0; i < headers.size(); ++i) {
                rowMap.put(headers.get(i), cells.get(i).trim());
            }
            rowList.add(rowMap);
        }
        return rowList;
    }

    public List<String> columnTexts(int columnIndex) {
        List<String> rowList = new ArrayList<>();
        for (Element w : rows()) {
            LazyList<Element> cells = cells(w);
            if (cells.size() > 0)
                rowList.add(cells.get(columnIndex).getText().trim());
        }
        return rowList;
    }

    public List<String> columnTexts(String columnName) {
        List<String> rowList = new ArrayList<>();
        int columnIndex = columnHeaderIndex(columnName);
        for (Element w : rows()) {
            LazyList<Element> cells = cells(w);
            if (cells.size() > 0 && cells.size() > columnIndex)
                rowList.add(cells.get(columnIndex).getText().trim());
        }
        return rowList;
    }

    public List<String> columnTextsByFirstRow(String firstRowText) {
        List<String> rowList = new ArrayList<>();
        int columnIndex = columnIndex(firstRowText);
        for (Element w : rows()) {
            LazyList<Element> cells = cells(w);
            if (cells.size() > 0 && cells.size() > columnIndex)
                rowList.add(cells.get(columnIndex).getText().trim());
        }
        return rowList;
    }

    public String textFromColumn(String rowText, String columnName) {
        return cell(rowText, columnName).getText();
    }

    private LazyList<Element> cells(Element row) {
        return cells(row, Element.class);
    }

    private <T extends Element> LazyList<T> cells(Element row, Class<T> clazz) {
        return row.$$x("./td", clazz);
    }
}