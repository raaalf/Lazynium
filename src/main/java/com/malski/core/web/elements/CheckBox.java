package com.malski.core.web.elements;

public interface CheckBox extends Element {
    void toggle();
    void check();
    void uncheck();
    boolean isChecked();
}
