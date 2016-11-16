package com.malski.core.mobile.elements;

import com.malski.core.mobile.elements.api.ElementsStates;
import com.malski.core.mobile.elements.api.ElementsWait;
import com.malski.core.mobile.factory.ElementHandler;
import com.malski.core.mobile.factory.LazyLocator;
import io.appium.java_client.MobileElement;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.malski.core.mobile.conditions.WaitConditions.*;
import static com.malski.core.utils.TestContext.getApplication;
import static org.openqa.selenium.support.ui.ExpectedConditions.not;

public class Elements<E extends Element> implements List<E>, ElementsWait, ElementsStates {
    private LazyLocator locator;
    private List<E> elements = null;
    private Class<E> elementInterface = null;
    private static Logger log = Logger.getLogger(Elements.class);

    public Elements(LazyLocator locator, final Class<E> elementInterface) {
        this.locator = locator;
        this.elementInterface = elementInterface;
    }

    public Elements(final Class<E> elementInterface) {
        this.locator = null;
        this.elementInterface = elementInterface;
    }

    @SuppressWarnings("unchecked")
    public Elements() {
        this((Class<E>) Element.class);
    }

    public LazyLocator getLocator() {
        return this.locator;
    }

    public boolean refresh() {
        boolean result = getLocator() != null && getLocator().refresh();
        init();
        return result;
    }

    private void init() {
        if (getLocator() == null) {
            elements = new ArrayList<>();
            return;
        }
        List<MobileElement> mobileElements = getLocator().findElements();
        ElementHandler<E> handler = new ElementHandler<>(elementInterface, getLocator());
        elements = new ArrayList<>();
        for (int i = 0; i < mobileElements.size(); ++i) {
            MobileElement mobileElement = mobileElements.get(i);
            try {
                E element = handler.getImplementation(mobileElement, i);
                elements.add(element);
            } catch (Throwable throwable) {
                log.error(throwable);
            }
        }
    }

    @Override
    public int size() {
        return getWrappedElements().size();
    }

    @Override
    public boolean isEmpty() {
        return getWrappedElements().isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return getWrappedElements().contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return getWrappedElements().iterator();
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        getWrappedElements().forEach(action);
    }

    @Override
    public Object[] toArray() {
        return getWrappedElements().toArray();
    }

    @Override
    public <W> W[] toArray(W[] a) {
        return getWrappedElements().toArray(a);
    }

    @Override
    public boolean add(E e) {
        return getWrappedElements().add(e);
    }

    @Override
    public boolean remove(Object o) {
        return getWrappedElements().remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return getWrappedElements().containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return getWrappedElements().addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return getWrappedElements().addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return getWrappedElements().removeAll(c);
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        return getWrappedElements().removeIf(filter);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return getWrappedElements().retainAll(c);
    }

    @Override
    public void replaceAll(UnaryOperator<E> operator) {
        getWrappedElements().replaceAll(operator);
    }

    @Override
    public void sort(Comparator<? super E> c) {
        getWrappedElements().sort(c);
    }

    @Override
    public void clear() {
        getWrappedElements().clear();
    }

    @Override
    public E get(int index) {
        return getWrappedElements().get(index);
    }

    @Override
    public E set(int index, E element) {
        return getWrappedElements().set(index, element);
    }

    @Override
    public void add(int index, E element) {
        getWrappedElements().add(index, element);
    }

    @Override
    public E remove(int index) {
        return getWrappedElements().remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return getWrappedElements().indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return getWrappedElements().lastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator() {
        return getWrappedElements().listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return getWrappedElements().listIterator(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return getWrappedElements().subList(fromIndex, toIndex);
    }

    @Override
    public Spliterator<E> spliterator() {
        return getWrappedElements().spliterator();
    }

    @Override
    public Stream<E> stream() {
        return getWrappedElements().stream();
    }

    @Override
    public Stream<E> parallelStream() {
        return getWrappedElements().parallelStream();
    }

    public List<String> getTexts() {
        return stream().map(Element::getText).collect(Collectors.toList());
    }

    public List<String> getValues() {
        return stream().map(Element::getValue).collect(Collectors.toList());
    }

    public List<String> getAttributes(String attributeName) {
        return stream().map(element -> element.getAttribute(attributeName)).collect(Collectors.toList());
    }

    public List<String> getIds() {
        return stream().map(Element::getId).collect(Collectors.toList());
    }

    public List<E> getWrappedElements() {
        if (elements == null) {
            synchronized (this) {
                if (elements == null) {
                    init();
                }
            }
        }
        return elements;
    }

    public E getFirst() {
        return (getWrappedElements() != null && getWrappedElements().size() > 0) ? getWrappedElements().get(0) : null;
    }

    public E getLast() {
        return (getWrappedElements() != null && getWrappedElements().size() > 0) ? getWrappedElements().get(getWrappedElements().size() - 1) : null;
    }

    @Override
    public void waitUntilAllPresent() {
        getApplication().getWait().until(presenceOfAllElementsLocatedBy(getLocator()));
    }

    @Override
    public void waitUntilAnyPresent() {
        getApplication().getWait().until(presenceOfElementLocated(getLocator()));
    }

    @Override
    public void waitUntilAllVisible() {
        getApplication().getWait().until(visibilityOfAllElementsLocatedBy(getLocator()));
    }

    @Override
    public void waitUntilAnyVisible() {
        getApplication().getWait().until(visibilityOfElementLocated(getLocator()));
    }

    @Override
    @SuppressWarnings("unchecked")
    public void waitUntilAllDisappear() {
        getApplication().getWait().until(invisibilityOfAllElements(getWrappedElements()));
    }

    @Override
    public void waitUntilAnyDisappear() {
        getApplication().getWait().until(invisibilityOfElementLocated(getLocator()));
    }

    @Override
    public void waitUntilAllEnabled() {
        getApplication().getWait().until(elementsToBeClickable(getLocator()));
    }

    @Override
    public void waitUntilAnyEnabled() {
        getApplication().getWait().until(elementToBeClickable(getLocator()));
    }

    @Override
    public void waitUntilAllDisabled() {
        getApplication().getWait().until(not(elementsToBeClickable(getLocator())));
    }

    @Override
    public void waitUntilAnyDisabled() {
        getApplication().getWait().until(not(elementToBeClickable(getLocator())));
    }

    @Override
    public boolean areAllVisible() {
        for (E element : getWrappedElements()) {
            if (!element.isVisible()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isAnyVisible() {
        for (E element : getWrappedElements()) {
            if (element.isVisible()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean areAllPresent() {
        for (E element : getWrappedElements()) {
            if (!element.isPresent()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isAnyPresent() {
        for (E element : getWrappedElements()) {
            if (element.isPresent()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean areAllEnabled() {
        for (E element : getWrappedElements()) {
            if (!element.isEnabled()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isAnyEnabled() {
        for (E element : getWrappedElements()) {
            if (element.isEnabled()) {
                return true;
            }
        }
        return true;
    }

    @Override
    public boolean hasAnyFocus() {
        for (E element : getWrappedElements()) {
            if (element.hasFocus()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean areAllSelected() {
        for (E element : getWrappedElements()) {
            if (!element.isSelected()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isAnySelected() {
        for (E element : getWrappedElements()) {
            if (element.isSelected()) {
                return true;
            }
        }
        return true;
    }

    @Override
    public boolean areAllUnselected() {
        for (E element : getWrappedElements()) {
            if (element.isSelected()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isAnyUnselected() {
        for (E element : getWrappedElements()) {
            if (!element.isSelected()) {
                return true;
            }
        }
        return true;
    }
}