package com.malski.core.mobile.elements.api;

public interface ElementStates {

    boolean isDisplayed();

    boolean isDisplayed(long timeout);

    boolean isVisible();

    boolean isVisible(long timeout);

    boolean isPresent();

    boolean isPresent(long timeout);

    boolean isEnabled();

    boolean isEnabled(long timeout);

    boolean hasFocus();

    boolean hasFocus(long timeout);

    boolean isInViewport();

    boolean isInViewport(long timeout);

    boolean isStaleness();
}
