package com.malski.core.web.elements;

public interface RadioButton extends Element {
    void check();

    boolean isChecked();

    boolean isChecked(long timeout);

    void waitUntilChecked();

    void waitUntilChecked(long timeout);

    void waitUntilUnchecked();

    void waitUntilUnchecked(long timeout);
}
