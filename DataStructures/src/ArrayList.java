import java.util.Arrays;
import interfaces.*;

/**
 * A dynamic array, growable array, resizable array, dynamic table, or array
 * list is a random access, variable-size list data structure that allows
 * elements to be added or removed.
 *
 * http://en.wikipedia.org/wiki/Dynamic_array
 *
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
@SuppressWarnings("unchecked")
public class ArrayList<T> implements IList<T> {
    private static final int MINIMUM_SIZE = 10;

    private int size = 0;
    private T[] array = (T[]) new Object[MINIMUM_SIZE];

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(T value) {
        return add(size, value);
    }

    /**
     * Add value to list at index.
     *
     * @param index to add value.
     * @param value to add to list.
     */
    public boolean add(int index, T value) {
        int growSize = this.size;
        if (size >= array.length) {
            array = Arrays.copyOf(array, (growSize + (growSize >> 1)));
        }
        if (index == size) {
            array[size++] = value;
        } else {
            // Shift the array down one spot
            System.arraycopy(array, index, array, index + 1, size - index);
            array[index] = value;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(T value) {
        for (int i = 0; i < size; i++) {
            T obj = array[i];
            if (obj.equals(value)) {
                return (remove(i) != null);
            }
        }
        return false;
    }

    /**
     * Remove value at index from list.
     *
     * @param index of value to remove.
     * @return value at index.
     */
    public T remove(int index) {
        if (index < 0 || index >= size) return null;

        T t = array[index];
        if (index != --size) {
            // Shift the array down one spot
            System.arraycopy(array, index + 1, array, index, size - index);
        }
        array[size] = null;

        int shrinkSize = size;
        if (size >= MINIMUM_SIZE && size < (shrinkSize + (shrinkSize << 1))) {
            System.arraycopy(array, 0, array, 0, size);
        }

        return t;
    }

    /**
     * Set value at index.
     *
     * @param index of value to set.
     * @param value to set.
     * @return value previously at index.
     */
    public T set(int index, T value) {
        if (index < 0 || index >= size) return null;
        T t = array[index];
        array[index] = value;
        return t;
    }

    /**
     * Get value at index.
     *
     * @param index of value to get.
     * @return value at index.
     */
    public T get(int index) {
        if (index < 0 || index >= size) return null;
        return array[index];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        size = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(T value) {
        for (int i = 0; i < size; i++) {
            T obj = array[i];
            if (obj.equals(value)) return true;
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
        int localSize = 0;
        for (int i = 0; i < array.length; i++) {
            T t = array[i];
            if (i < size) {
                if (t == null) return false;
                localSize++;
            } else {
                if (t != null) return false;
            }
        }
        return (localSize == size);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            builder.append(array[i]).append(", ");
        }
        return builder.toString();
    }
}
