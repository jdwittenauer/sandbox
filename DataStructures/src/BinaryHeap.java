import java.util.Arrays;

import interfaces.*;

/**
 * A binary heap is a heap data structure created using a binary tree. It can be
 * seen as a binary tree with two additional constraints: 1) The shape property:
 * the tree is a complete binary tree; that is, all levels of the tree, except
 * possibly the last one (deepest) are fully filled, and, if the last level of
 * the tree is not complete, the nodes of that level are filled from left to
 * right. 2) The heap property: each node is greater/less than or equal to each of
 * its children according to a comparison predicate defined for the data structure.
 *
 * http://en.wikipedia.org/wiki/Binary_heap
 *
 * Original implementation borrowed from https://github.com/phishman3579/java-algorithms-implementation
 */
@SuppressWarnings("unchecked")
public class BinaryHeap<T extends Comparable<T>> implements IHeap<T> {
    public enum Type {
        MIN, MAX
    }

    private static final int MINIMUM_SIZE = 10;
    private Type type = Type.MIN;
    private int size = 0;
    private T[] array = (T[]) new Comparable[MINIMUM_SIZE];

    /**
     * Constructor for heap, defaults to a min-heap.
     */
    public BinaryHeap() {
        size = 0;
    }

    /**
     * Constructor for heap.
     *
     * @param type Heap type.
     */
    public BinaryHeap(Type type) {
        this();
        this.type = type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(T value) {
        int growSize = this.size;
        if (size >= array.length) {
            array = Arrays.copyOf(array, (growSize + (growSize >> 1)));
        }
        array[size] = value;
        heapUp(size++);

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T remove(T value) {
        if (array.length == 0) return null;
        for (int i = 0; i < size; i++) {
            T node = array[i];
            if (node.equals(value)) return remove(i);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getHeadValue() {
        if (array.length == 0) return null;
        return array[0];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T removeHead() {
        return remove(getHeadValue());
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
        if (array.length == 0) return false;
        for (int i = 0; i < size; i++) {
            T t = array[i];
            if (t.equals(value)) return true;
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
        return (array.length == 0 || validateNode(0));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        // Not implemented
        return null;
    }

    private int getParentIndex(int index) {
        if (index > 0)
            return (int) Math.floor((index - 1) / 2);
        return Integer.MIN_VALUE;
    }

    private int getLeftIndex(int index) {
        return 2 * index + 1;
    }

    private int getRightIndex(int index) {
        return 2 * index + 2;
    }

    private T remove(int index) {
        if (index < 0 || index >= size) return null;

        T t = array[index];
        array[index] = array[--size];
        array[size] = null;

        heapDown(index);

        int shrinkSize = size;
        if (size >= MINIMUM_SIZE && size < (shrinkSize + (shrinkSize << 1))) {
            System.arraycopy(array, 0, array, 0, size);
        }
        return t;
    }

    private void heapUp(int idx) {
        int nodeIndex = idx;
        T value = this.array[nodeIndex];
        while (nodeIndex >= 0) {
            int parentIndex = getParentIndex(nodeIndex);
            if (parentIndex < 0) break;
            T parent = this.array[parentIndex];

            if ((type == Type.MIN && parent != null && value.compareTo(parent) < 0)
                    || (type == Type.MAX && parent != null && value.compareTo(parent) > 0)) {
                // Node is greater/lesser than parent, switch node with parent
                this.array[parentIndex] = value;
                this.array[nodeIndex] = parent;
            } else {
                nodeIndex = parentIndex;
            }
        }
    }

    private void heapDown(int index) {
        T value = this.array[index];
        int leftIndex = getLeftIndex(index);
        int rightIndex = getRightIndex(index);
        T left = (leftIndex != Integer.MIN_VALUE && leftIndex < this.size) ? this.array[leftIndex] : null;
        T right = (rightIndex != Integer.MIN_VALUE && rightIndex < this.size) ? this.array[rightIndex] : null;

        if (left == null && right == null) {
            // Nothing to do here
            return;
        }

        T nodeToMove = null;
        int nodeToMoveIndex = -1;
        if ((type == Type.MIN && left != null && right != null && value.compareTo(left) > 0 && value.compareTo(right) > 0)
                || (type == Type.MAX && left != null && right != null && value.compareTo(left) < 0 && value.compareTo(right) < 0)) {
            // Both children are greater/lesser than node
            if ((type == Type.MIN && (right.compareTo(left) < 0)) || ((type == Type.MAX && right.compareTo(left) > 0))) {
                // Right is greater/lesser than left
                nodeToMove = right;
                nodeToMoveIndex = rightIndex;
            } else if ((type == Type.MIN && left.compareTo(right) < 0) || (type == Type.MAX && left.compareTo(right) > 0)) {
                // Left is greater/lesser than right
                nodeToMove = left;
                nodeToMoveIndex = leftIndex;
            } else {
                // Both children are equal, use right
                nodeToMove = right;
                nodeToMoveIndex = rightIndex;
            }
        } else if ((type == Type.MIN && right != null && value.compareTo(right) > 0)
                || (type == Type.MAX && right != null && value.compareTo(right) < 0)) {
            // Right is greater/lesser than node
            nodeToMove = right;
            nodeToMoveIndex = rightIndex;
        } else if ((type == Type.MIN && left != null && value.compareTo(left) > 0)
                || (type == Type.MAX && left != null && value.compareTo(left) < 0) ) {
            // Left is greater/lesser than node
            nodeToMove = left;
            nodeToMoveIndex = leftIndex;
        }
        // No node to move, stop recursion
        if (nodeToMove == null) return;

        // Re-factor heap sub-tree
        this.array[nodeToMoveIndex] = value;
        this.array[index] = nodeToMove;

        heapDown(nodeToMoveIndex);
    }

    private boolean validateNode(int index) {
        T value = this.array[index];
        int leftIndex = getLeftIndex(index);
        int rightIndex = getRightIndex(index);

        // We shouldn't ever have a right node without a left in a heap
        if (rightIndex != Integer.MIN_VALUE && leftIndex == Integer.MIN_VALUE) return false;

        if (leftIndex != Integer.MIN_VALUE && leftIndex < size) {
            T left = this.array[leftIndex];
            if ((type == Type.MIN && value.compareTo(left) < 0)
                    || (type == Type.MAX && value.compareTo(left) > 0)) {
                return validateNode(leftIndex);
            }
            return false;
        }
        if (rightIndex != Integer.MIN_VALUE && rightIndex < size) {
            T right = this.array[rightIndex];
            if ((type == Type.MIN && value.compareTo(right) < 0)
                    || (type == Type.MAX && value.compareTo(right) > 0)) {
                return validateNode(rightIndex);
            }
            return false;
        }

        return true;
    }
}
