package com.malski.core.web.elements.states;

import com.malski.core.web.elements.Element;

import java.util.List;

public interface ElementsState<E extends Element> {

    List<E> getWrappedElements();

    default boolean areAllVisible() {
        for (E element : getWrappedElements()) {
            if (!element.isVisible()) {
                return false;
            }
        }
        return true;
    }

    default boolean isAnyVisible() {
        for (E element : getWrappedElements()) {
            if (element.isVisible()) {
                return true;
            }
        }
        return false;
    }

    default boolean areAllPresent() {
        for (E element : getWrappedElements()) {
            if (!element.isPresent()) {
                return false;
            }
        }
        return true;
    }

    default boolean isAnyPresent() {
        for (E element : getWrappedElements()) {
            if (element.isPresent()) {
                return true;
            }
        }
        return false;
    }

    default boolean areAllEnabled() {
        for (E element : getWrappedElements()) {
            if (!element.isEnabled()) {
                return false;
            }
        }
        return true;
    }

    default boolean isAnyEnabled() {
        for (E element : getWrappedElements()) {
            if (element.isEnabled()) {
                return true;
            }
        }
        return true;
    }

    default boolean hasAnyFocus() {
        for (E element : getWrappedElements()) {
            if (element.hasFocus()) {
                return true;
            }
        }
        return false;
    }

    default boolean areAllSelected() {
        for (E element : getWrappedElements()) {
            if (!element.isSelected()) {
                return false;
            }
        }
        return true;
    }

    default boolean isAnySelected() {
        for (E element : getWrappedElements()) {
            if (element.isSelected()) {
                return true;
            }
        }
        return true;
    }

    default boolean areAllUnselected() {
        for (E element : getWrappedElements()) {
            if (element.isSelected()) {
                return false;
            }
        }
        return true;
    }

    default boolean isAnyUnselected() {
        for (E element : getWrappedElements()) {
            if (!element.isSelected()) {
                return true;
            }
        }
        return true;
    }
}
