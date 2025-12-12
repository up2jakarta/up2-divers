package io.github.up2jakarta.lov.bst;

import io.github.up2jakarta.lov.core.Wrapper;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Simple implementation of {@link java.util.SequencedSet} based on red-black binary search tree.
 *
 * @param <E> the element type
 */
public non-sealed class RedBlackSet<E> extends RedBlackTree<E, RedBlackSet.Element<E>> implements SequencedSet<E> {

    private SequencedSet<E> reversed;

    public RedBlackSet(Comparator<? super E> comparator) {
        super(comparator);
    }

    private Object[] array(final Object[] target, final Iterator<E> iterator) {
        for (int i = 0, size = target.length; i < size && iterator.hasNext(); i++) {
            target[i] = iterator.next();
        }
        return target;
    }

    @SuppressWarnings("unchecked")
    private <T> T[] array(final Iterator<E> iterator, T[] target) {
        final int size = this.size();
        if (target.length < size) {
            target = (T[]) Array.newInstance(target.getClass().getComponentType(), size);
        }
        this.array(target, iterator);
        return target;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final boolean contains(Object key) {
        return this.search((E) key) != null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final boolean remove(Object key) {
        return this.delete((E) key) != null;
    }

    @Override
    public final Iterator<E> iterator() {
        return ascending(Node::getKey);
    }

    @Override
    public final Object[] toArray() {
        final int size = this.size();
        return this.array(new Object[size], ascending(Node::getKey));
    }

    @Override
    public final <T> T[] toArray(T[] target) {
        return this.array(ascending(Node::getKey), target);
    }

    @Override
    public final boolean retainAll(Collection<?> source) {
        var mod = false;
        final Iterator<E> it = this.iterator();
        while (it.hasNext()) {
            if (!source.contains(it.next())) {
                it.remove();
                mod = true;
            }
        }
        return mod;
    }

    @Override
    public final boolean add(E key) {
        final Wrapper<Boolean> mod = new Wrapper<>(false);
        this.insert(key, () -> {
            mod.accept(true);
            return new Element<>(key);
        });
        return mod.get();
    }

    @Override
    public final boolean containsAll(Collection<?> source) {
        for (final Object key : source) {
            if (!this.contains(key)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public final boolean addAll(Collection<? extends E> source) {
        var mod = false;
        for (final E key : source) {
            mod |= this.add(key);
        }
        return mod;
    }

    @Override
    public final boolean removeAll(Collection<?> source) {
        var mod = false;
        for (Object key : source) {
            mod |= this.remove(key);
        }
        return mod;
    }

    @Override
    public final E getFirst() {
        final Element<E> node = this.min();
        if (node != null) {
            return node.getKey();
        }
        return null;
    }

    @Override
    public final E getLast() {
        final Element<E> node = this.max();
        if (node != null) {
            return node.getKey();
        }
        return null;
    }

    @Override
    public final E removeFirst() {
        final Element<E> node = this.min();
        if (node != null) {
            this.detach(node);
            return node.getKey();
        }
        return null;
    }

    @Override
    public final E removeLast() {
        final Element<E> node = this.max();
        if (node != null) {
            this.detach(node);
            return node.getKey();
        }
        return null;
    }

    @Override
    public SequencedSet<E> reversed() {
        if (reversed == null) {
            reversed = new SetView();
        }
        return reversed;
    }

    @Override
    public Spliterator<E> spliterator() {
        return RedBlackTree.spliterator(iterator(), size());
    }

    @Override
    public Stream<E> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    static final class Element<E> extends KSNode<E, Element<E>> {

        private Element(E key) {
            super(key);
        }

        @Override
        public String toString() {
            return this.getKey().toString();
        }
    }

    private final class SetView implements SequencedSet<E> {
        @Override
        public SequencedSet<E> reversed() {
            return RedBlackSet.this;
        }

        @Override
        public int size() {
            return RedBlackSet.this.size();
        }

        @Override
        public boolean isEmpty() {
            return RedBlackSet.this.isEmpty();
        }

        @Override
        public boolean contains(Object key) {
            return RedBlackSet.this.contains(key);
        }

        @Override
        public Iterator<E> iterator() {
            return descending(Node::getKey);
        }

        @Override
        public Spliterator<E> spliterator() {
            return RedBlackTree.spliterator(iterator(), size());
        }

        @Override
        public Stream<E> stream() {
            return StreamSupport.stream(spliterator(), false);
        }

        @Override
        public Object[] toArray() {
            final int size = RedBlackSet.this.size();
            return RedBlackSet.this.array(new Object[size], descending(Node::getKey));
        }

        @Override
        public <T> T[] toArray(T[] target) {
            return RedBlackSet.this.array(descending(Node::getKey), target);
        }

        @Override
        public boolean add(E key) {
            return RedBlackSet.this.add(key);
        }

        @Override
        public boolean remove(Object key) {
            return RedBlackSet.this.remove(key);
        }

        @Override
        public boolean containsAll(Collection<?> source) {
            return RedBlackSet.this.containsAll(source);
        }

        @Override
        public boolean addAll(Collection<? extends E> source) {
            return RedBlackSet.this.addAll(source);
        }

        @Override
        public boolean removeAll(Collection<?> source) {
            return RedBlackSet.this.removeAll(source);
        }

        @Override
        public boolean retainAll(Collection<?> source) {
            return RedBlackSet.this.retainAll(source);
        }

        @Override
        public void clear() {
            RedBlackSet.this.clear();
        }

        @Override
        public E getFirst() {
            return RedBlackSet.this.getLast();
        }

        @Override
        public E getLast() {
            return RedBlackSet.this.getFirst();
        }

        @Override
        public E removeFirst() {
            return RedBlackSet.this.removeLast();
        }

        @Override
        public E removeLast() {
            return RedBlackSet.this.removeFirst();
        }
    }
}
