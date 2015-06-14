package interfaces;

/**
 * A list or sequence is an abstract data type that implements an ordered
 * collection of values, where the same value may occur more than once.
 *
 * http://en.wikipedia.org/wiki/List_(computing)
 *
 * Original implementation borrowed from https://github.com/phishman3579/java-algorithms-implementation
 */
public interface IList<T> {
    /**
     * Add value to list.
     *
     * @param value to add.
     * @return True if added.
     */
    boolean add(T value);

    /**
     * Remove value from list.
     *
     * @param value to remove.
     * @return True if removed.
     */
    boolean remove(T value);

    /**
     * Get value at index.
     *
     * @param index of value to get.
     * @return value at index.
     */
    T get(int index);

    /**
     * Set value at index.
     *
     * @param index of value to set.
     * @param value to set.
     * @return value previously at index.
     */
    T set(int index, T value);

    /**
     * Clear the entire list.
     */
    void clear();

    /**
     * Does the list contain value.
     *
     * @param value to search list for.
     * @return True if list contains value.
     */
    boolean contains(T value);

    /**
     * Size of the list.
     *
     * @return size of the list.
     */
    int size();

    /**
     * Validate the list according to the invariants.
     *
     * @return True if the list is valid.
     */
    boolean validate();
}
