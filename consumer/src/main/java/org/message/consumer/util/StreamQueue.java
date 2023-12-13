package org.message.consumer.util;

import org.springframework.util.CollectionUtils;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.LinkedList;

public class StreamQueue<T> extends AbstractQueue<T> {
    private final LinkedList<T> elements;

    public StreamQueue() {
        this.elements = new LinkedList<>();
    }

    @Override
    public Iterator<T> iterator() {
        return elements.iterator();
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public boolean offer(T t) {
        if (t == null) return false;
        elements.add(t);
        return true;
    }

    @Override
    public T poll() {
        if (CollectionUtils.isEmpty(elements)) {
            return null;
        }
        Iterator<T> iterator = elements.iterator();
        if (iterator.hasNext()) {
            T t = iterator.next();
            if (t != null) {
                iterator.remove();
                return t;
            }
        }
        return null;
    }

    @Override
    public T peek() {
        return elements.getFirst();
    }
}
