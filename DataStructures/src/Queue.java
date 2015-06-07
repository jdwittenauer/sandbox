import interfaces.*;
import common.*;

/**
 * This queue implementation is backed by a linked list.
 *
 * Origin implementation borrowed from https://github.com/phishman3579/java-algorithms-implementation
 */
@SuppressWarnings("unchecked")
public class Queue<T> implements IQueue<T> {
    private Node<T> head = null;
    private Node<T> tail = null;
    private int size = 0;

    public Queue() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean offer(T value) {
        return add(new Node<>(value));
    }

    /**
     * Enqueue the node in the queue.
     *
     * @param node to enqueue.
     */
    private boolean add(Node<T> node) {
        if (head == null) {
            head = node;
            tail = node;
        } else {
            Node<T> oldHead = head;
            head = node;
            node.next = oldHead;
            oldHead.prev = node;
        }
        size++;
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T poll() {
        T result = null;
        if (tail != null) {
            result = tail.value;

            Node<T> prev = tail.prev;
            if (prev != null) {
                prev.next = null;
                tail = prev;
            } else {
                head = null;
                tail = null;
            }
            size--;
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T peek() {
        return (tail != null) ? tail.value : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(T value) {
        // Find the node
        Node<T> node = head;
        while (node != null && (!node.value.equals(value))) {
            node = node.next;
        }
        return (node != null) && remove(node);
    }

    private boolean remove(Node<T> node) {
        // Update the tail, if needed
        if (node.equals(tail))
            tail = node.prev;

        Node<T> prev = node.prev;
        Node<T> next = node.next;
        if (prev != null && next != null) {
            prev.next = next;
            next.prev = prev;
        } else if (prev != null) {
            prev.next = null;
        } else if (next != null) {
            // Node is the head
            next.prev = null;
            head = next;
        } else {
            head = null;
        }
        size--;
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(T value) {
        if (head == null)
            return false;

        Node<T> node = head;
        while (node != null) {
            if (node.value.equals(value))
                return true;
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
        java.util.Set<T> keys = new java.util.HashSet<T>();
        Node<T> node = head;
        if (node!=null) {
            keys.add(node.value);
            if (node.prev!=null) return false;
            Node<T> child = node.next;
            while (child!=null) {
                if (!validate(child,keys)) return false;
                child = child.next;
            }
        }
        return (keys.size()==size());
    }

    private boolean validate(Node<T> node, java.util.Set<T> keys) {
        if (node.value==null) return false;
        keys.add(node.value);

        Node<T> child = node.next;
        if (child!=null) {
            if (!child.prev.equals(node)) return false;
            if (!validate(child,keys)) return false;
        } else {
            if (!node.equals(tail)) return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Node<T> node = head;
        while (node != null) {
            builder.append(node.value).append(", ");
            node = node.next;
        }
        return builder.toString();
    }
}
