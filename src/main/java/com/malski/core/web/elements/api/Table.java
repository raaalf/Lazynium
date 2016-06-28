package com.malski.core.web.elements.api;

public interface Table extends Element {

    int getRowCount();

    Elements<Element> getColumnHeaders();

    Element getColumnHeader(int index);

    Element getColumnHeader(String columnName);

    int getColumnHeaderIndex(String columnName);

    Elements<Element> getRows();

    Element getRow(int index);

    Element getRow(String text);

    int getRowIndex(String text);

    Elements<Element> getRowCells(int index);

    Elements<Element> getRowCells(String text);

    Element getCell(int rowIndex, int colIndex);

    Element getCell(int rowIndex, String columnName);

    Element getCell(String rowText, int colIndex);

    Element getCell(String rowText, String columnName);

    Element getHead();

    Element getFooter();
}
