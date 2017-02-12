package com.malski.core.web.elements.states;

import com.malski.core.web.elements.waits.SelectWait;

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
