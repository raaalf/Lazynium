package com.malski.lazynium.web.elements.states;

import com.malski.lazynium.web.elements.waits.SelectWait;

public interface SelectState extends SelectWait {

    default boolean isMultiple() {
        return getWrappedSelect().isMultiple();
    }

    default boolean isSelected(long timeout) {
        try {
            waitUntilSelected(timeout);
        } catch (Exception ignore) {
            return false;
        }
        return getWrappedElement().isSelected();
    }
}
