package com.malski.core.web.elements;

import com.malski.core.cucumber.TestContext;
import com.malski.core.web.conditions.WaitConditions;
import com.malski.core.web.factory.LazyLocator;
import com.malski.core.web.factory.Locator;
import org.openqa.selenium.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class ElementsImpl<T extends Element> implements Elements<T> {
    private LazyLocator locator;
    private List<T> elements;
    private Class elemClass;

    public ElementsImpl(LazyLocator locator, final Class<T> elemClass) {
        this.locator = locator;
        setElemClass(elemClass);
    }

    public ElementsImpl(By by, SearchContext context, final Class<T> elemClass) {
        this.locator = new Locator(context, by);
        setElemClass(elemClass);
    }

    protected void setElemClass(Class<T> clazz) {
        try {
            this.elemClass = Class.forName(clazz.getCanonicalName() + "Impl");
        } catch (ClassNotFoundException e) {
            this.elemClass = ElementImpl.class;
        }
    }

    @Override
    public LazyLocator getLocator() {
        return this.locator;
    }

    @Override
    public void refresh() {
        elements = new ArrayList<>();
        List<WebElement> webElements = getLocator().findElements();
        try {
            @SuppressWarnings("unchecked")
            Constructor<T> cons = elemClass.getConstructor(LazyLocator.class, WebElement.class);
            webElements.forEach(webElement -> {
                try {
                    getWrappedElements().add(cons.newInstance(locator, webElement));
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            });
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
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
    public Iterator<T> iterator() {
        return getWrappedElements().iterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
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
    public boolean add(T t) {
        return getWrappedElements().add(t);
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
    public boolean addAll(Collection<? extends T> c) {
        return getWrappedElements().addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return getWrappedElements().addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return getWrappedElements().removeAll(c);
    }

    @Override
    public boolean removeIf(Predicate<? super T> filter) {
        return getWrappedElements().removeIf(filter);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return getWrappedElements().retainAll(c);
    }

    @Override
    public void replaceAll(UnaryOperator<T> operator) {
        getWrappedElements().replaceAll(operator);
    }

    @Override
    public void sort(Comparator<? super T> c) {
        getWrappedElements().sort(c);
    }

    @Override
    public void clear() {
        getWrappedElements().clear();
    }

    @Override
    public T get(int index) {
        return getWrappedElements().get(index);
    }

    @Override
    public T set(int index, T element) {
        return getWrappedElements().set(index, element);
    }

    @Override
    public void add(int index, T element) {
        getWrappedElements().add(index, element);
    }

    @Override
    public T remove(int index) {
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
    public ListIterator<T> listIterator() {
        return getWrappedElements().listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return getWrappedElements().listIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return getWrappedElements().subList(fromIndex, toIndex);
    }

    @Override
    public Spliterator<T> spliterator() {
        return getWrappedElements().spliterator();
    }

    @Override
    public Stream<T> stream() {
        return getWrappedElements().stream();
    }

    @Override
    public Stream<T> parallelStream() {
        return getWrappedElements().parallelStream();
    }

    public List<String> getTexts() {
        return elements.stream().map(Element::getText).collect(Collectors.toList());
    }

    public List<String> getValues() {
        return elements.stream().map(Element::getValue).collect(Collectors.toList());
    }

    public List<String> getIds() {
        return elements.stream().map(Element::getId).collect(Collectors.toList());
    }

    public List<T> getWrappedElements() {
        if (elements == null) {
            refresh();
        }
        return elements;
    }

    @Override
    public void waitUntilAllPresent() {
        TestContext.getBrowser().getWait().until(presenceOfAllElementsLocatedBy(locator.getSelector().getBy()));
    }

    @Override
    public void waitUntilAnyPresent() {
        TestContext.getBrowser().getWait().until(presenceOfElementLocated(locator.getSelector().getBy()));
    }

    @Override
    public void waitUntilAllVisible() {
        TestContext.getBrowser().getWait().until(visibilityOfAllElementsLocatedBy(locator.getSelector().getBy()));
    }

    @Override
    public void waitUntilAnyVisible() {
        TestContext.getBrowser().getWait().until(visibilityOfElementLocated(locator.getSelector().getBy()));
    }

    @Override
    @SuppressWarnings("unchecked")
    public void waitUntilAllDisappear() {
        TestContext.getBrowser().getWait().until(WaitConditions.invisibilityOfAllElements(getWrappedElements()));
    }

    @Override
    public void waitUntilAnyDisappear() {
        TestContext.getBrowser().getWait().until(invisibilityOfElementLocated(locator.getSelector().getBy()));
    }

    @Override
    public void waitUntilAllEnabled() {
        TestContext.getBrowser().getWait().until(WaitConditions.elementsToBeClickable(locator.getSelector().getBy()));
    }

    @Override
    public void waitUntilAnyEnabled() {
        TestContext.getBrowser().getWait().until(elementToBeClickable(locator.getSelector().getBy()));
    }

    @Override
    public void waitUntilAllDisabled() {
        TestContext.getBrowser().getWait().until(not(WaitConditions.elementsToBeClickable(locator.getSelector().getBy())));
    }

    @Override
    public void waitUntilAnyDisabled() {
        TestContext.getBrowser().getWait().until(not(elementToBeClickable(locator.getSelector().getBy())));
    }

//    @Override
//    public void waitUntilAllSelected() {
//        TestContext.getBrowser().getWait().until(elementToBeSelected(this));
//    }
//
//    @Override
//    public void waitUntilAnySelected() {
//        TestContext.getBrowser().getWait().until(elementToBeSelected(this));
//    }
//
//    @Override
//    public void waitUntilAllUnselected() {
//        TestContext.getBrowser().getWait().until(elementSelectionStateToBe(this, false));
//    }
//
//    @Override
//    public void waitUntilAnyUnselected() {
//        TestContext.getBrowser().getWait().until(elementSelectionStateToBe(this, false));
//    }

    @Override
    public boolean areAllVisible() {
        for (T element : getWrappedElements()) {
            if (!element.isVisible()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isAnyVisible() {
        for (T element : getWrappedElements()) {
            if (element.isVisible()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean areAllPresent() {
        for (T element : getWrappedElements()) {
            if (!element.isPresent()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isAnyPresent() {
        for (T element : getWrappedElements()) {
            if (element.isPresent()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean areAllEnabled() {
        for (T element : getWrappedElements()) {
            if (!element.isEnabled()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isAnyEnabled() {
        for (T element : getWrappedElements()) {
            if (element.isEnabled()) {
                return true;
            }
        }
        return true;
    }

    @Override
    public boolean hasAnyFocus() {
        for (T element : getWrappedElements()) {
            if (element.hasFocus()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean areAllSelected() {
        for (T element : getWrappedElements()) {
            if (!element.isSelected()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isAnySelected() {
        for (T element : getWrappedElements()) {
            if (element.isSelected()) {
                return true;
            }
        }
        return true;
    }

    @Override
    public boolean areAllUnselected() {
        for (T element : getWrappedElements()) {
            if (element.isSelected()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isAnyUnselected() {
        for (T element : getWrappedElements()) {
            if (!element.isSelected()) {
                return true;
            }
        }
        return true;
    }
}