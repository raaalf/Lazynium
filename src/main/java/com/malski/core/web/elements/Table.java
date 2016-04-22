package com.malski.core.web.elements;

import java.util.List;

public interface Table extends Element {

    int getRowCount();

    Element getCell(int row, int col);

    List<Element> getColumnHeaders();

    Element getColumnHeader(int index);

    List<Element> getRows();

    Element getRow(int index);

    Element getHead();

    Element getFooter();
}
