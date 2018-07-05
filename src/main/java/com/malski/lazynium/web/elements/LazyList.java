package com.malski.lazynium.web.elements;

import com.malski.lazynium.web.elements.states.ListState;
import com.malski.lazynium.web.elements.waits.ListWait;
import com.malski.lazynium.web.factory.ElementHandler;
import com.malski.lazynium.web.factory.LazyLocator;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LazyList<E extends Element> implements List<E>, ListWait<E>, ListState<E> {
    private LazyLocator locator;
    private List<E> elements = null;
    private Class<E> elementInterface = null;
    private static Logger log = Logger.getLogger(LazyList.class);

    public LazyList(LazyLocator locator, final Class<E> elementInterface) {
        this.locator = locator;
        this.elementInterface = elementInterface;
    }

    public LazyList(final Class<E> elementInterface) {
        this.locator = null;
        this.elementInterface = elementInterface;
    }

    @SuppressWarnings("unchecked")
    public LazyList() {
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
        List<WebElement> webElements = getLocator().findElements();
        ElementHandler<E> handler = new ElementHandler<>(elementInterface, getLocator());
        elements = new ArrayList<>();
        for (int i = 0; i < webElements.size(); ++i) {
            WebElement webElement = webElements.get(i);
            try {
                E element = handler.getImplementation(webElement, i);
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
        return stream().map(Element::value).collect(Collectors.toList());
    }

    public List<String> getAttributes(String attributeName) {
        return stream().map(element -> element.getAttribute(attributeName)).collect(Collectors.toList());
    }

    public List<String> getIds() {
        return stream().map(Element::id).collect(Collectors.toList());
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
}