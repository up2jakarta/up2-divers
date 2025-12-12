package io.github.up2jakarta.lov.bst;

import io.github.up2jakarta.lov.core.Computer;
import io.github.up2jakarta.lov.core.Wrapper;

import java.util.*;
import java.util.function.Function;

import static io.github.up2jakarta.lov.bst.None.NAN;
import static io.github.up2jakarta.lov.core.AccessException.notNull;
import static java.util.Spliterator.*;

/**
 * Internal implementation of red-black binary search tree.
 */
abstract sealed class RedBlackTree<K, N extends Node<K, N>> permits KVTree, RedBlackSet {

    protected final Comparator<? super K> comparator;
    private final Node<K, N> NaN = None.undefined();
    private Node<K, N> root = NaN;
    private int size = 0;

    public RedBlackTree(Comparator<? super K> comparator) {
        notNull(comparator, this.getClass(), "comparator");
        this.comparator = comparator;
    }

    protected static <N> Spliterator<N> spliterator(Iterator<N> iterator, int size) {
        return Spliterators.spliterator(iterator, size, DISTINCT | SORTED | ORDERED);
    }

    protected static void assertValid(RedBlackTree<?, ?> tree) {
        final Node<?, ?> root = tree.root;
        if (root != NAN) {
            assertValid(root, 0, new Wrapper<>());
        }
    }

    private static void assertValid(int c, Wrapper<Integer> w) {
        if (w.isEmpty()) {
            w.accept(c);
        } else if (w.get() != c) {
            throw new AssertionError("Black-height rule violated {first: " + w + ", count: " + c + "}");
        }
    }

    private static void assertValid(Node<?, ?> p, int c, Wrapper<Integer> w) {
        if (p.isBlack()) {
            c++;
        } else if (p.getParent().isRed()) {
            throw new AssertionError("Node[" + p + "] and its parent[" + p.getParent() + "] are both red");
        }
        if (p.getLeft() == NAN) {
            assertValid(c, w);
        } else {
            assertValid(p.getLeft(), c, w);
        }
        if (p.getRight() == NAN) {
            assertValid(c, w);
        } else {
            assertValid(p.getRight(), c, w);
        }
    }

    private static <K, N extends Node<K, N>> Node<K, N> first(Node<K, N> p) {
        while (p.getLeft() != NAN) {
            p = p.getLeft();
        }
        return p;
    }

    private static <K, N extends Node<K, N>> Node<K, N> last(Node<K, N> p) {
        while (p.getRight() != NAN) {
            p = p.getRight();
        }
        return p;
    }

    private Node<K, N> next(final Node<K, N> n) {
        if (n.getRight() != NaN) {
            return first(n.getRight());
        }
        var c = n;
        var p = n.getParent();
        while (p != NaN && c == p.getRight()) {
            c = p;
            p = p.getParent();
        }
        return p;
    }

    private Node<K, N> prev(final Node<K, N> n) {
        if (n.getLeft() != NaN) {
            return last(n.getLeft());
        }
        var c = n;
        var p = n.getParent();
        while (p != NaN && c == p.getLeft()) {
            c = p;
            p = p.getParent();
        }
        return p;
    }

    private void swap(final Node<K, N> x, final Node<K, N> n, final Node<K, N> p) {
        x.setParent(p);
        if (p == NaN) {
            root = x;
        } else if (p.getLeft() == n) {
            p.setLeft(x);
        } else {
            p.setRight(x);
        }
    }

    private void rotateLeft(final Node<K, N> n) {
        final Node<K, N> r = n.getRight();
        final Node<K, N> l = r.getLeft();
        n.setRight(l);
        l.setParent(n);
        this.swap(r, n, n.getParent());
        r.setLeft(n);
        n.setParent(r);
    }

    private void rotateRight(final Node<K, N> n) {
        final Node<K, N> l = n.getLeft();
        final Node<K, N> r = l.getRight();
        n.setLeft(r);
        r.setParent(n);
        this.swap(l, n, n.getParent());
        l.setRight(n);
        n.setParent(l);
    }

    private Node<K, N> deleteLeft(final Node<K, N> n) {
        var u = n.getRight();
        if (u.isRed()) {
            u.setBlack();
            n.setRed();
            this.rotateLeft(n);
            u = n.getRight();
        }
        final Node<K, N> ul = u.getLeft();
        final Node<K, N> ur = u.getRight();
        if (ul.isBlack() && ur.isBlack()) {
            u.setRed();
            return n;
        }
        if (ur.isBlack()) {
            ul.setBlack();
            u.setRed();
            this.rotateRight(u);
            u = n.getRight();
        }
        u.setColor(n.isBlack());
        n.setBlack();
        u.getRight().setBlack();
        this.rotateLeft(n);
        return root;
    }

    private Node<K, N> deleteRight(final Node<K, N> n) {
        var u = n.getLeft();
        if (u.isRed()) {
            u.setBlack();
            n.setRed();
            this.rotateRight(n);
            u = n.getLeft();
        }
        final Node<K, N> ul = u.getLeft();
        final Node<K, N> ur = u.getRight();
        if (ur.isBlack() && ul.isBlack()) {
            u.setRed();
            return n;
        }
        if (ul.isBlack()) {
            ur.setBlack();
            u.setRed();
            this.rotateLeft(u);
            u = n.getLeft();
        }
        u.setColor(n.isBlack());
        n.setBlack();
        u.getLeft().setBlack();
        this.rotateRight(n);
        return root;
    }

    private void onDelete(Node<K, N> n) {
        while (n != root && n.isBlack()) {
            final Node<K, N> p = n.getParent();
            if (n == p.getLeft()) {
                n = this.deleteLeft(p);
            } else {
                n = this.deleteRight(p);
            }
        }
        n.setBlack();
    }

    private Node<K, N> insertLeft(Node<K, N> n, Node<K, N> p, Node<K, N> g) {
        final Node<K, N> u = g.getRight();
        if (u.isRed()) {
            p.setBlack();
            u.setBlack();
            g.setRed();
            return g;
        }
        if (n == p.getRight()) {
            this.rotateLeft(n = p);
            p = n.getParent();
            g = p.getParent();
        }
        p.setBlack();
        g.setRed();
        this.rotateRight(g);
        return n;
    }

    private Node<K, N> insertRight(Node<K, N> n, Node<K, N> p, Node<K, N> g) {
        final Node<K, N> u = g.getLeft();
        if (u.isRed()) {
            p.setBlack();
            u.setBlack();
            g.setRed();
            return g;
        }
        if (n == p.getLeft()) {
            this.rotateRight(n = p);
            p = n.getParent();
            g = p.getParent();
        }
        p.setBlack();
        g.setRed();
        this.rotateLeft(g);
        return n;
    }

    private void onInsert(Node<K, N> n) {
        n.setRed();
        while (n != NaN && n != root && n.getParent().isRed()) {
            final Node<K, N> p = n.getParent();
            final Node<K, N> g = p.getParent();
            if (p == g.getLeft()) {
                n = this.insertLeft(n, p, g);
            } else {
                n = this.insertRight(n, p, g);
            }
        }
        root.setBlack();
    }

    private void unlink(final Node<K, N> n) {
        if (n.isBlack()) {
            this.onDelete(n);
        }
        final Node<K, N> p = n.getParent();
        if (p != NaN) {
            if (n == p.getLeft()) {
                p.setLeft(NaN);
            } else if (n == p.getRight()) {
                p.setRight(NaN);
            }
            n.setParent(NaN);
        }
    }

    final void detach(final Node<K, N> n) {
        if (n.getParent() == null) {
            return;
        }
        if (n.getLeft() != NaN && n.getRight() != NaN) {
            final Node<K, N> min = first(n.getRight());
            var tmp = n.getParent();
            this.swap(n, min, min.getParent());
            this.swap(min, n, tmp);
            tmp = n.getLeft();
            n.setLeft(NaN);
            min.setLeft(tmp).setParent(min);
            tmp = n.getRight();
            n.setRight(min.getRight()).setParent(n);
            min.setRight(tmp).setParent(min);
            var nc = n.isBlack();
            n.setColor(min.isBlack());
            min.setColor(nc);
        }
        final Node<K, N> next = (n.getLeft() == NAN) ? n.getRight() : n.getLeft();
        if (next != NaN) {
            this.swap(next, n, n.getParent());
            n.setLeft(NaN);
            n.setRight(NaN);
            n.setParent(NaN);
            if (n.isBlack()) {
                this.onDelete(next);
            }
        } else if (n.getParent() == NaN) {
            root = NaN;
        } else {
            this.unlink(n);
        }
        size--;
        n.setParent(null);
    }

    final <X extends Throwable> N insert(final K key, Computer<N, X> computer) throws X {
        if (root == NaN) {
            final N node = computer.get();
            if (node != null) {
                root = node;
                size = 1;
            }
            return node;
        }
        int cmp;
        var entry = root;
        var parent = root;
        do {
            final K k = entry.getKey();
            if (k == null) {
                this.detach(entry);
                return this.insert(key, computer);
            }
            parent = entry;
            cmp = comparator.compare(key, k);
            if (cmp < 0) {
                entry = entry.getLeft();
            } else if (cmp > 0) {
                entry = entry.getRight();
            } else {
                //noinspection unchecked
                return (N) entry;
            }
        } while (entry != NaN);
        final N node = computer.get();
        if (node != null) {
            node.setParent(parent);
            if (cmp < 0) {
                parent.setLeft(node);
            } else {
                parent.setRight(node);
            }
            this.onInsert(node);
            size++;
        }
        return node;
    }

    final N search(final K key) {
        if (root == NaN) {
            return null;
        }
        var node = root;
        do {
            final K k = node.getKey();
            if (k == null) {
                this.detach(node);
                return this.search(key);
            }
            final int cmp = comparator.compare(key, k);
            if (cmp < 0) {
                node = node.getLeft();
            } else if (cmp > 0) {
                node = node.getRight();
            } else {
                //noinspection unchecked
                return (N) node;
            }
        } while (node != NaN);
        return null;
    }

    final N delete(final K key) {
        final N node = this.search(key);
        if (node != null) {
            this.detach(node);
        }
        return node;
    }

    final <T> Iterator<T> ascending(Function<? super N, T> mapper) {
        return new AscendingIterator<>(first(root), mapper);
    }

    final <T> Iterator<T> descending(Function<? super N, T> mapper) {
        return new DescendingIterator<>(last(root), mapper);
    }

    final N min() {
        if (root == NaN) {
            return null;
        }
        //noinspection unchecked
        return (N) first(root);
    }

    final N max() {
        if (root == NaN) {
            return null;
        }
        //noinspection unchecked
        return (N) last(root);
    }

    public final boolean isEmpty() {
        return root == NaN;
    }

    public final int size() {
        return size;
    }

    public final void clear() {
        size = 0;
        root = null;
    }

    private final class DescendingIterator<T> implements Iterator<T> {
        private final Function<? super N, T> mapper;
        private Node<K, N> next;
        private N current;

        private DescendingIterator(Node<K, N> next, Function<? super N, T> mapper) {
            this.mapper = mapper;
            this.current = null;
            this.next = next;
        }

        @Override
        public boolean hasNext() {
            return next != NaN;
        }

        @Override
        public T next() {
            var node = next;
            if (node == NaN) {
                throw new NoSuchElementException();
            }
            next = RedBlackTree.this.prev(node);
            //noinspection unchecked
            current = (N) node;
            return mapper.apply(current);
        }

        @Override
        public void remove() {
            if (current == null) {
                throw new IllegalStateException();
            }
            RedBlackTree.this.detach(current);
            current = null;
        }
    }

    private final class AscendingIterator<T> implements Iterator<T> {
        private final Function<? super N, T> mapper;
        private Node<K, N> next;
        private N current;

        private AscendingIterator(Node<K, N> next, Function<? super N, T> mapper) {
            this.mapper = mapper;
            this.current = null;
            this.next = next;
        }

        @Override
        public T next() {
            var node = next;
            if (node == NaN) {
                throw new NoSuchElementException();
            }
            next = RedBlackTree.this.next(node);
            //noinspection unchecked
            current = (N) node;
            return mapper.apply(current);
        }

        @Override
        public boolean hasNext() {
            return next != NaN;
        }

        @Override
        public void remove() {
            if (current == null) {
                throw new IllegalStateException();
            }
            if (current.getLeft() != NaN && current.getRight() != NaN) {
                next = current;
            }
            RedBlackTree.this.detach(current);
            current = null;
        }
    }
}
