package com.malski.core.mobile.elements.api;

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
