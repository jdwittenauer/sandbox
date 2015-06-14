import interfaces.*;
import common.*;

/**
 * Hash Map using either chaining or probing. hash map is a data structure that
 * uses a hash function to map identifying values, known as keys, to their
 * associated values.
 *
 * http://en.wikipedia.org/wiki/Hash_table
 *
 * Original implementation borrowed from https://github.com/phishman3579/java-algorithms-implementation
 */
@SuppressWarnings("unchecked")
public class HashMap<K extends Number, V> implements IMap<K,V> {
    private K hashingKey = null;
    private float loadFactor = 0.7f;
    private int minimumSize = 256;
    private Pair<K, V>[] array = null;
    private int size = 0;

    /**
     * Create a hash map with the default hashing key.
     */
    public HashMap() {
        if (hashingKey == null)
            hashingKey = (K) new Integer(minimumSize);
        initializeMap();
    }

    /**
     * Create a hash map with K as the hashing key.
     *
     * @param key to use for the hashing key.
     */
    public HashMap(K key) {
        if (key.intValue() > 0)
            hashingKey = key;
        initializeMap();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V put(K key, V value) {
        return put(new Pair<>(key, value));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V get(K key) {
        int hashedKey = hashingFunction(key);
        Pair<K, V> pair = array[hashedKey];

        // Check initial position
        if (pair == null)
            return null;
        if (pair.key.equals(key))
            return pair.value;

        // Probing until we get back to the starting index
        int start = getNextIndex(hashedKey);
        while (start != hashedKey) {
            if (start >= array.length) {
                start = 0;
                if (start == hashedKey)
                    break;
            }
            pair = array[start];
            if (pair == null)
                return null;
            if (pair.key.equals(key))
                return pair.value;
            start = getNextIndex(start);
        }
        // If we get here, probing failed
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V remove(K key) {
        int hashedKey = hashingFunction(key);
        Pair<K, V> prev;

        // Check initial position
        Pair<K, V> pair = array[hashedKey];
        if (pair!=null && pair.key.equals(key)) {
            prev = array[hashedKey];
            array[hashedKey] = null;
            size--;

            int prevSize = getSmallerSize(array.length);
            if (prevSize>minimumSize && size < prevSize)
                reduce();

            return prev.value;
        }

        // Probing until we get back to the starting index
        int start = getNextIndex(hashedKey);
        while (start != hashedKey) {
            if (start >= array.length) {
                start = 0;
                if (start == hashedKey)
                    break;
            }
            pair = array[start];
            if (pair != null && pair.key.equals(key)) {
                prev = array[start];
                array[start] = null;
                size--;

                int prevSize = getSmallerSize(array.length);
                if (prevSize > minimumSize && size < prevSize)
                    reduce();

                return prev.value;
            }
            start = getNextIndex(start);
        }
        // If we get here, probing failed
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        for (int i = 0; i < array.length; i++)
            array[i] = null;
        size = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(K key) {
        return (get(key) != null);
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
        java.util.Set<K> keys = new java.util.HashSet<>();
        for (Pair<K, V> pair : array) {
            if (pair == null)
                continue;
            K k = pair.key;
            V v = pair.value;
            if (k == null || v == null) return false;
            if (keys.contains(k)) return false;
            keys.add(k);
        }
        return (keys.size() == size());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int key = 0; key < array.length; key++) {
            Pair<K, V> p = array[key];
            if (p == null)
                continue;
            V value = p.value;
            if (value != null)
                builder.append(key).append("=").append(value).append(", ");
        }
        return builder.toString();
    }

    private static int getLargerSize(int input) {
        int b = (int)((Math.log(2*input) / Math.log(2)));
        return (int) Math.pow(2, b);
    }

    private static int getSmallerSize(int input) {
        int b = (int)((Math.log(input/3) / Math.log(2)));
        return (int) Math.pow(2, b);
    }

    private static int getNextIndex(int input) {
        return input+1;
    }

    private void initializeMap() {
        int length = getLargerSize(minimumSize);
        array = new Pair[length];
        size = 0;
        hashingKey = (K) new Integer(length);
    }

    private int hashingFunction(K key) {
        int k = key.intValue() % hashingKey.intValue();
        if (k >= array.length)
            k = k - ((k / array.length) * array.length);
        return k;
    }

    private void resize() {
        // Save old data
        Pair<K,V>[] temp = this.array;

        // Calculate new size and assign
        int length = getLargerSize(array.length);
        Pair<K,V>[] newArray = new Pair[length];
        this.size = 0;
        this.array = newArray;
        this.hashingKey = (K) new Integer(length);

        // Re-hash old data
        for (Pair<K,V> p : temp) {
            if (p != null)
                this.put(p);
        }
    }

    private void reduce() {
        // Save old data
        Pair<K,V>[] temp = this.array;

        // Calculate new size and check minimum
        int length = getSmallerSize(array.length);
        Pair<K,V>[] newArray = new Pair[length];
        this.size = 0;
        this.array = newArray;
        this.hashingKey = (K) new Integer(length);

        // Re-hash old data
        for (Pair<K,V> p : temp) {
            if (p!=null)
                this.put(p);
        }
    }

    private V put(Pair<K,V> newPair) {
        V prev = null;
        int hashedKey = hashingFunction(newPair.key);

        // Check initial position
        Pair<K, V> pair = array[hashedKey];
        if (pair == null) {
            array[hashedKey] = newPair;
            size++;

            // If size is greater than threshold
            int maxSize = (int)(loadFactor * array.length);
            if (size >= maxSize)
                resize();

            return null;
        }
        if (pair.key.equals(newPair.key)) {
            prev = pair.value;
            pair.value = newPair.value;
            return prev;
        }

        // Probing until we get back to the starting index
        int start = getNextIndex(hashedKey);
        while (start != hashedKey) {
            if (start >= array.length) {
                start = 0;
                if (start == hashedKey)
                    break;
            }
            pair = array[start];
            if (pair == null) {
                array[start] = newPair;
                size++;

                // If size is greater than threshold
                int maxSize = (int)(loadFactor*array.length);
                if (size >= maxSize)
                    resize();

                return null;
            }
            if (pair.key.equals(newPair.key)) {
                prev = pair.value;
                pair.value = newPair.value;
                return prev;
            }
            start = getNextIndex(start);
        }
        // We should never get here
        return null;
    }
}
