import interfaces.*;
import common.*;

/**
 * This stack implementation is backed by a linked list.
 *
 * Original implementation borrowed from https://github.com/phishman3579/java-algorithms-implementation
 */
public class Stack<T> implements IStack<T> {
    private Node<T> top = null;
    private int size = 0;

    public Stack() {
        top = null;
        size = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean push(T value) {
        return push(new Node<>(value));
    }

    /**
     * Push node onto the stack.
     *
     * @param node to push on the stack.
     */
    private boolean push(Node<T> node) {
        if (top == null) {
            top = node;
        } else {
            Node<T> oldTop = top;
            top = node;
            top.next = oldTop;
            oldTop.prev = top;
        }
        size++;
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T pop() {
        if (top == null) return null;

        Node<T> nodeToRemove = top;
        top = nodeToRemove.next;
        if (top != null) top.prev = null;

        T value = nodeToRemove.value;
        size--;
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T peek() {
        return (top != null) ? top.value : null;
    }

    /**
     * Get item at index.
     *
     * @param index of item.
     * @return T at index.
     */
    public T get(int index) {
        Node<T> current = top;
        for (int i = 0; i < index; i++) {
            if (current == null) break;
            current = current.next;
        }
        return (current != null) ? current.value : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(T value) {
        // Find the node
        Node<T> node = top;
        while (node != null && (!node.value.equals(value))) {
            node = node.next;
        }
        return (node != null) && remove(node);
    }

    private boolean remove(Node<T> node) {
        Node<T> prev = node.prev;
        Node<T> next = node.next;
        if (prev != null && next != null) {
            prev.next = next;
            next.prev = prev;
        } else if (prev != null) {
            prev.next = null;
        } else if (next != null) {
            // Node is the top
            next.prev = null;
            top = next;
        } else {
            top = null;
        }
        size--;
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        top = null;
        size = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(T value) {
        if (top == null) return false;
        Node<T> node = top;
        while (node != null) {
            if (node.value.equals(value)) return true;
            node = node.next;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate() {
        java.util.Set<T> keys = new java.util.HashSet<>();
        Node<T> node = top;
        if (node != null) {
            keys.add(node.value);
            if (node.prev != null) return false;
            Node<T> child = node.next;
            while (child != null) {
                if (!validate(child,keys)) return false;
                child = child.next;
            }
        }
        return (keys.size() == size());
    }

    private boolean validate(Node<T> node, java.util.Set<T> keys) {
        if (node.value == null) return false;
        keys.add(node.value);

        Node<T> child = node.next;
        return (child == null || child.prev.equals(node));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Node<T> node = top;
        while (node != null) {
            builder.append(node.value).append(", ");
            node = node.next;
        }
        return builder.toString();
    }
}
