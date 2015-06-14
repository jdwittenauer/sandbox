package common;

/**
 * Interface for a tree node creator.
 *
 * Original implementation borrowed from https://github.com/phishman3579/java-algorithms-implementation
 */
public interface INodeCreator<T extends Comparable<T>> {
    /**
     * Create a new Node with the following parameters.
     *
     * @param parent of this node.
     * @param id of this node.
     * @return new Node
     */
    TreeNode<T> createNewNode(TreeNode<T> parent, T id);
}
