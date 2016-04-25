package com.malski.core.web.elements;

public interface CheckBox extends Element {
    void toggle();

    void check();

    void uncheck();

    boolean isChecked();

    boolean isChecked(long timeout);

    void waitUntilChecked();

    void waitUntilChecked(long timeout);

    void waitUntilUnchecked();

    void waitUntilUnchecked(long timeout);
}