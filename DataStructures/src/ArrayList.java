import java.util.Arrays;
import interfaces.*;

/**
 * A dynamic array, growable array, resizable array, dynamic table, or array
 * list is a random access, variable-size list data structure that allows
 * elements to be added or removed.
 *
 * http://en.wikipedia.org/wiki/Dynamic_array
 *
 * Original implementation borrowed from https://github.com/phishman3579/java-algorithms-implementation
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
     * {@inheritDoc}
     */
    @Override
    public T get(int index) {
        if (index < 0 || index >= size) return null;
        return array[index];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T set(int index, T value) {
        if (index < 0 || index >= size) return null;
        T t = array[index];
        array[index] = value;
        return t;
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

    private boolean add(int index, T value) {
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

    private T remove(int index) {
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
}
