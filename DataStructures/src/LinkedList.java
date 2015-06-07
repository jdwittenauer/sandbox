import interfaces.*;

/**
 * Linked List (doubly link). A linked list is a data structure consisting
 * of a group of nodes which together represent a sequence.
 *
 * http://en.wikipedia.org/wiki/Linked_list
 *
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
@SuppressWarnings("unchecked")
public class LinkedList<T> implements IList<T> {
    private int size = 0;
    private Node<T> head = null;
    private Node<T> tail = null;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(T value) {
        return add(new Node<>(value));
    }

    /**
     * Add node to list.
     *
     * @param node to add to list.
     */
    private boolean add(Node<T> node) {
        if (head == null) {
            head = node;
            tail = node;
        } else {
            Node<T> prev = tail;
            prev.next = node;
            node.prev = prev;
            tail = node;
        }
        size++;
        return true;
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
        if (node == null)
            return false;

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
        java.util.Set<T> keys = new java.util.HashSet<>();
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
        return (keys.size()==size);
    }

    private boolean validate(Node<T> node, java.util.Set<T> keys) {
        if (node.value==null) return false;
        keys.add(node.value);

        Node<T> child = node.next;
        if (child!=null) {
            if (!child.prev.equals(node)) return false;
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

    private class Node<U> {
        private U value = null;
        private Node<U> prev = null;
        private Node<U> next = null;

        private Node() { }

        private Node(U value) {
            this.value = value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "value=" + value + " previous=" + ((prev != null) ? prev.value : "NULL")
                    + " next=" + ((next != null) ? next.value : "NULL");
        }
    }
}
