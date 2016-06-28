package com.malski.core.web.elements.api;

public interface CheckBox extends Element {
    void select(boolean state);

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