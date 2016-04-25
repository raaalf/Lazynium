package com.malski.core.web.elements;

public interface ElementStates {

    boolean isDisplayed(long timeout);

    boolean isVisible();

    boolean isVisible(long timeout);

    boolean isPresent();

    boolean isPresent(long timeout);

    boolean isEnabled();

    boolean isEnabled(long timeout);

    boolean hasFocus();

    boolean hasFocus(long timeout);
}
