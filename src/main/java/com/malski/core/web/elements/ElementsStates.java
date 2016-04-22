package com.malski.core.web.elements;

public interface ElementsStates {
    boolean areAllVisible();

    boolean isAnyVisible();

    boolean areAllPresent();

    boolean isAnyPresent();

    boolean areAllEnabled();

    boolean isAnyEnabled();

    boolean hasAnyFocus();

    boolean areAllSelected();

    boolean isAnySelected();

    boolean areAllUnselected();

    boolean isAnyUnselected();
}
