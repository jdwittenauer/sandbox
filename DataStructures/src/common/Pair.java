package common;

/**
 * Generic implementation of a pair used by the hash map implementation.
 *
 * Original implementation borrowed from https://github.com/phishman3579/java-algorithms-implementation
 */
public class Pair<K extends Number, V> {
    public K key = null;
    public V value = null;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return 31 * (this.key.hashCode());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Pair)) return false;

        Pair<K, V> pair = (Pair<K, V>) obj;
        return key.equals(pair.key);
    }
}
