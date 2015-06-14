package common;

/**
 * Generic implementation of a node used by tree data structures.
 *
 * Original implementation borrowed from https://github.com/phishman3579/java-algorithms-implementation
 */
public class TreeNode<T extends Comparable<T>> {
    public T value = null;
    public TreeNode<T> parent = null;
    public TreeNode<T> left = null;
    public TreeNode<T> right = null;
    
    public TreeNode(TreeNode<T> parent, T value) {
        this.parent = parent;
        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "value =" + value + " parent=" + ((parent != null) ? parent.value : "NULL") + " left="
                + ((left != null) ? left.value : "NULL") + " right=" + ((right != null) ? right.value : "NULL");
    }
}
