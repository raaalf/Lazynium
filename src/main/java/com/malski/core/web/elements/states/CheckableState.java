package com.malski.core.web.elements.states;

import com.malski.core.web.elements.waits.CheckableWait;

public interface CheckableState extends CheckableWait, ElementState {

    default boolean isChecked() {
        return getWrappedElement().isSelected();
    }

    default boolean isChecked(long timeout) {
        try {
            waitUntilChecked(timeout);
        } catch (Exception ignore) {
            return false;
        }
        return isChecked();
    }
}
