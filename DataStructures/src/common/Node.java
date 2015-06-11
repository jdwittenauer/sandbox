package common;

/**
 * Generic implementation of a node used by linked list data structures.
 *
 * Original implementation borrowed from https://github.com/phishman3579/java-algorithms-implementation
 */
public class Node<T> {
    public T value = null;
    public Node<T> prev = null;
    public Node<T> next = null;

    public Node(T value) {
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
