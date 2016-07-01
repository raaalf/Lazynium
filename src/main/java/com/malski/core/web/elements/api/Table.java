package com.malski.core.web.elements.api;

public interface Table extends Element {

    int getRowCount();

    Elements<Element> getColumnHeaders();

    Element getColumnHeader(int index);

    Element getColumnHeader(String columnName);

    int getColumnHeaderIndex(String columnName);

    Elements<Element> getRows();

    <T extends Element> Elements<T> getRows(Class<T> clazz);

    Elements<Element> getRows(String text);

    Elements<Element> getRows(int columnIndex, String text);

    Elements<Element> getRows(String columnName, String text);

    Element getRow(int index);

    <T extends Element> T getRow(int index, Class<T> clazz);

    Element getRow(String text);

    <T extends Element> T getRow(String text, Class<T> clazz);

    int getRowIndex(String text);

    Elements<Element> getRowCells(int index);

    <T extends Element> Elements<T> getRowCells(int index, Class<T> clazz);

    Elements<Element> getRowCells(String text);

    <T extends Element> Elements<T> getRowCells(String text, Class<T> clazz);

    Element getCell(int rowIndex, int colIndex);

    <T extends Element> T getCell(int rowIndex, int colIndex, Class<T> clazz);

    Element getCell(int rowIndex, String columnName);

    <T extends Element> T getCell(int rowIndex, String columnName, Class<T> clazz);

    Element getCell(String rowText, int colIndex);

    <T extends Element> T getCell(String rowText, int colIndex, Class<T> clazz);

    Element getCell(String rowText, String columnName);

    <T extends Element> T getCell(String rowText, String columnName, Class<T> clazz);

    Element getHead();

    Element getFooter();
}
