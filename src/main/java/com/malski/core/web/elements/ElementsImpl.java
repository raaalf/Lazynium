package com.malski.core.web.elements;

import com.malski.core.web.factory.LazyLocator;
import com.malski.core.web.factory.Locator;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class ElementsImpl<T extends Element> implements Elements<T> {
    private LazyLocator locator;
    private List<T> elements;
    private Class elemClass;

    /*public ElementsImpl(By by, SearchContext context, final Class<T> elemClass) {
        this.locator = new Locator(context, by);
        setElemClass(elemClass);
    }*/

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
            this.elemClass = Class.forName(clazz.getCanonicalName()+"Impl");
        } catch (ClassNotFoundException e) {
            this.elemClass = ElementImpl.class;
        }
    }

    @Override
    public By getBy() {
        return locator.getBy();
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
    public boolean retainAll(Collection<?> c) {
        return getWrappedElements().retainAll(c);
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

    public List<String> getTexts() {
        return elements.stream().map(Element::getText).collect(Collectors.toList());
    }

    public List<String> getValues() {
        return elements.stream().map(Element::getValue).collect(Collectors.toList());
    }

    public List<String> getIds() {
        return elements.stream().map(Element::getId).collect(Collectors.toList());
    }

    protected List<T> getWrappedElements() {
        if(elements == null) {
            refresh();
        }
        return elements;
    }
}
