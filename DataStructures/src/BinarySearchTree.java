import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

import interfaces.*;
import common.*;

/**
 * A binary search tree (BST), which may sometimes also be called an ordered or
 * sorted binary tree, is a node-based binary tree data structure which has the
 * following properties: 1) The left subtree of a node contains only nodes with
 * keys less than the node's key. 2) The right subtree of a node contains only
 * nodes with keys greater than the node's key. 3) Both the left and right
 * subtrees must also be binary search trees.
 *
 * http://en.wikipedia.org/wiki/Binary_search_tree
 *
 * Original implementation borrowed from https://github.com/phishman3579/java-algorithms-implementation
 */
@SuppressWarnings("unchecked")
public class BinarySearchTree<T extends Comparable<T>> implements ITree<T> {
    private int modifications = 0;

    protected int size = 0;
    protected TreeNode<T> root = null;
    protected INodeCreator<T> creator = null;

    protected enum Position {
        LEFT, RIGHT
    }

    public enum DepthFirstSearchOrder {
        inOrder, preOrder
    }

    /**
     * Default constructor.
     */
    public BinarySearchTree() { }

    /**
     * Constructor with external Node creator.
     */
    public BinarySearchTree(INodeCreator<T> creator) {
        this.creator = creator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(T value) {
        TreeNode<T> nodeAdded = this.addValue(value);
        return (nodeAdded != null);
    }

    /**
     * Add value to the tree and return the Node that was added. Tree can
     * contain multiple equal values.
     *
     * @param value T to add to the tree.
     * @return TreeNode<T> which was added to the tree.
     */
    protected TreeNode<T> addValue(T value) {
        TreeNode<T> newNode;
        if (this.creator == null)
            newNode = new TreeNode<>(null, value);
        else
            newNode = this.creator.createNewNode(null, value);

        // If root is null, assign
        if (root == null) {
            root = newNode;
            size++;
            return newNode;
        }

        TreeNode<T> node = root;
        while (node != null) {
            if (newNode.value.compareTo(node.value) <= 0) {
                // Less than or equal to goes left
                if (node.left == null) {
                    // New left node
                    node.left = newNode;
                    newNode.parent = node;
                    size++;
                    return newNode;
                }
                node = node.left;
            } else {
                // Greater than goes right
                if (node.right == null) {
                    // New right node
                    node.right = newNode;
                    newNode.parent = node;
                    size++;
                    return newNode;
                }
                node = node.right;
            }
        }
        return newNode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(T value) {
        TreeNode<T> node = getNode(value);
        return (node != null);
    }

    /**
     * Locate T in the tree.
     *
     * @param value T to locate in the tree.
     * @return TreeNode<T> representing first reference of value in tree or NULL if
     *         not found.
     */
    protected TreeNode<T> getNode(T value) {
        TreeNode<T> node = root;
        while (node != null && node.value != null) {
            if (value.compareTo(node.value) == 0) {
                return node;
            } else if (value.compareTo(node.value) < 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return null;
    }

    /**
     * Rotate tree left at sub-tree rooted at node.
     *
     * @param node Root of tree to rotate left.
     */
    protected void rotateLeft(TreeNode<T> node) {
        Position parentPosition = null;
        TreeNode<T> parent = node.parent;
        if (parent != null) {
            if (node.equals(parent.left)) {
                // Lesser
                parentPosition = Position.LEFT;
            } else {
                // Greater
                parentPosition = Position.RIGHT;
            }
        }

        TreeNode<T> greater = node.right;
        node.right = null;
        TreeNode<T> lesser = greater.left;

        greater.left = node;
        node.parent = greater;

        node.right = lesser;
        if (lesser != null)
            lesser.parent = node;

        if (parent != null) {
            if (parentPosition == Position.LEFT) {
                parent.left = greater;
            } else {
                parent.right = greater;
            }
            greater.parent = parent;
        } else {
            root = greater;
            greater.parent = null;
        }
    }

    /**
     * Rotate tree right at sub-tree rooted at node.
     *
     * @param node Root of tree to rotate left.
     */
    protected void rotateRight(TreeNode<T> node) {
        Position parentPosition = null;
        TreeNode<T> parent = node.parent;
        if (parent != null) {
            if (node.equals(parent.left)) {
                // Lesser
                parentPosition = Position.LEFT;
            } else {
                // Greater
                parentPosition = Position.RIGHT;
            }
        }

        TreeNode<T> lesser = node.left;
        node.left = null;
        TreeNode<T> greater = lesser.right;

        lesser.right = node;
        node.parent = lesser;

        node.left = greater;
        if (greater != null)
            greater.parent = node;

        if (parent!=null) {
            if (parentPosition == Position.LEFT) {
                parent.left = lesser;
            } else {
                parent.right = lesser;
            }
            lesser.parent = parent;
        } else {
            root = lesser;
            lesser.parent = null;
        }
    }

    /**
     * Get greatest node in sub-tree rooted at startingNode. The search does not
     * include startingNode in it's results.
     *
     * @param startingNode Root of tree to search.
     * @return TreeNode<T> which represents the greatest node in the startingNode
     *         sub-tree or NULL if startingNode has no greater children.
     */
    protected TreeNode<T> getGreatest(TreeNode<T> startingNode) {
        if (startingNode == null)
            return null;

        TreeNode<T> greater = startingNode.right;
        while (greater != null && greater.value != null) {
            TreeNode<T> node = greater.right;
            if (node != null && node.value != null)
                greater = node;
            else
                break;
        }
        return greater;
    }

    /**
     * Get least node in sub-tree rooted at startingNode. The search does not
     * include startingNode in it's results.
     *
     * @param startingNode Root of tree to search.
     * @return TreeNode<T> which represents the least node in the startingNode
     *         sub-tree or NULL if startingNode has no lesser children.
     */
    protected TreeNode<T> getLeast(TreeNode<T> startingNode) {
        if (startingNode == null)
            return null;

        TreeNode<T> lesser = startingNode.left;
        while (lesser != null && lesser.value != null) {
            TreeNode<T> node = lesser.left;
            if (node != null && node.value != null)
                lesser = node;
            else
                break;
        }
        return lesser;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T remove(T value) {
        TreeNode<T> nodeToRemove = this.removeValue(value);
        return ((nodeToRemove != null) ? nodeToRemove.value : null);
    }

    /**
     * Remove first occurrence of value in the tree.
     *
     * @param value T to remove from the tree.
     * @return TreeNode<T> which was removed from the tree.
     */
    protected TreeNode<T> removeValue(T value) {
        TreeNode<T> nodeToRemoved = this.getNode(value);
        if (nodeToRemoved != null) nodeToRemoved = removeNode(nodeToRemoved);
        return nodeToRemoved;
    }

    /**
     * Remove the node using a replacement
     *
     * @param nodeToRemoved TreeNode<T> to remove from the tree.
     * @return TreeNode<T> removed from the tree, it can be different
     *         then the parameter in some cases.
     */
    protected TreeNode<T> removeNode(TreeNode<T> nodeToRemoved) {
        if (nodeToRemoved != null) {
            TreeNode<T> replacementNode = this.getReplacementNode(nodeToRemoved);
            replaceNodeWithNode(nodeToRemoved, replacementNode);
        }
        return nodeToRemoved;
    }

    /**
     * Get the proper replacement node according to the binary search tree
     * algorithm from the tree.
     *
     * @param nodeToRemoved TreeNode<T> to find a replacement for.
     * @return TreeNode<T> which can be used to replace nodeToRemoved. nodeToRemoved
     *         should NOT be NULL.
     */
    protected TreeNode<T> getReplacementNode(TreeNode<T> nodeToRemoved) {
        TreeNode<T> replacement = null;
        if (nodeToRemoved.left != null && nodeToRemoved.right == null) {
            // Using the less subtree
            replacement = nodeToRemoved.left;
        } else if (nodeToRemoved.right != null && nodeToRemoved.left == null) {
            // Using the greater subtree (there is no lesser subtree, no refactoring)
            replacement = nodeToRemoved.right;
        } else if (nodeToRemoved.right != null) {
            // Two children
            if (modifications % 2 != 0) {
                replacement = this.getGreatest(nodeToRemoved.left);
                if (replacement == null)
                    replacement = nodeToRemoved.left;
            } else {
                replacement = this.getLeast(nodeToRemoved.right);
                if (replacement == null)
                    replacement = nodeToRemoved.right;
            }
            modifications++;
        }
        return replacement;
    }

    /**
     * Replace nodeToRemoved with replacementNode in the tree.
     *
     * @param nodeToRemoved TreeNode<T> to remove replace in the tree. nodeToRemoved should NOT be NULL.
     * @param replacementNode TreeNode<T> to replace nodeToRemoved in the tree. replacementNode can be NULL.
     */
    protected void replaceNodeWithNode(TreeNode<T> nodeToRemoved, TreeNode<T> replacementNode) {
        if (replacementNode != null) {
            // Save for later
            TreeNode<T> replacementNodeLesser = replacementNode.left;
            TreeNode<T> replacementNodeGreater = replacementNode.right;

            // Replace replacementNode's branches with nodeToRemove's branches
            TreeNode<T> nodeToRemoveLesser = nodeToRemoved.left;
            if (nodeToRemoveLesser != null && !nodeToRemoveLesser.equals(replacementNode)) {
                replacementNode.left = nodeToRemoveLesser;
                nodeToRemoveLesser.parent = replacementNode;
            }
            TreeNode<T> nodeToRemoveGreater = nodeToRemoved.right;
            if (nodeToRemoveGreater != null && !nodeToRemoveGreater.equals(replacementNode)) {
                replacementNode.right = nodeToRemoveGreater;
                nodeToRemoveGreater.parent = replacementNode;
            }

            // Remove link from replacementNode's parent to replacement
            TreeNode<T> replacementParent = replacementNode.parent;
            if (replacementParent != null && !replacementParent.equals(nodeToRemoved)) {
                TreeNode<T> replacementParentLesser = replacementParent.left;
                TreeNode<T> replacementParentGreater = replacementParent.right;
                if (replacementParentLesser != null && replacementParentLesser.equals(replacementNode)) {
                    replacementParent.left = replacementNodeGreater;
                    if (replacementNodeGreater != null)
                        replacementNodeGreater.parent = replacementParent;
                } else if (replacementParentGreater != null && replacementParentGreater.equals(replacementNode)) {
                    replacementParent.right = replacementNodeLesser;
                    if (replacementNodeLesser != null)
                        replacementNodeLesser.parent = replacementParent;
                }
            }
        }

        // Update the link in the tree from the nodeToRemoved to the replacementNode
        TreeNode<T> parent = nodeToRemoved.parent;
        if (parent == null) {
            // Replacing the root node
            root = replacementNode;
            if (root != null)
                root.parent = null;
        } else if (parent.left != null && (parent.left.value.compareTo(nodeToRemoved.value) == 0)) {
            parent.left = replacementNode;
            if (replacementNode != null)
                replacementNode.parent = parent;
        } else if (parent.right != null && (parent.right.value.compareTo(nodeToRemoved.value) == 0)) {
            parent.right = replacementNode;
            if (replacementNode != null)
                replacementNode.parent = parent;
        }
        size--;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        root = null;
        size = 0;
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
        return (root == null) || validateNode(root);
    }

    /**
     * Validate the node for all Binary Search Tree invariants.
     *
     * @param node TreeNode<T> to validate in the tree. node should NOT be NULL.
     * @return True if the node is valid.
     */
    protected boolean validateNode(TreeNode<T> node) {
        TreeNode<T> lesser = node.left;
        TreeNode<T> greater = node.right;

        boolean lesserCheck = true;
        if (lesser != null && lesser.value != null) {
            lesserCheck = (lesser.value.compareTo(node.value) <= 0);
            if (lesserCheck)
                lesserCheck = validateNode(lesser);
        }
        if (!lesserCheck)
            return false;

        boolean greaterCheck = true;
        if (greater != null && greater.value != null) {
            greaterCheck = (greater.value.compareTo(node.value) > 0);
            if (greaterCheck)
                greaterCheck = validateNode(greater);
        }
        return greaterCheck;
    }

    /**
     * Get an array representation of the tree in breath first search order.
     *
     * @return breath first search sorted array representing the tree.
     */
    public T[] getBFS() {
        Queue<TreeNode<T>> queue = new ArrayDeque<>();
        T[] values = (T[]) new Comparable[size];
        int count = 0;
        TreeNode<T> node = root;
        while (node != null) {
            values[count++] = node.value;
            if (node.left != null)
                queue.add(node.left);
            if (node.right != null)
                queue.add(node.right);
            if (!queue.isEmpty())
                node = queue.remove();
            else
                node = null;
        }
        return values;
    }

    /**
     * Get an array representation of the tree in level order.
     *
     * @return level order sorted array representing the tree.
     */
    public T[] getLevelOrder() {
        return getBFS();
    }

    /**
     * Get an array representation of the tree in-order.
     *
     * @return in-order sorted array representing the tree.
     */
    public T[] getDFS(DepthFirstSearchOrder order) {
        Set<TreeNode<T>> added = new HashSet<>(2);
        T[] nodes = (T[]) new Comparable[size];
        int index = 0;
        TreeNode<T> node = root;
        while (index < size && node != null) {
            TreeNode<T> parent = node.parent;
            TreeNode<T> lesser = (node.left != null && !added.contains(node.left)) ? node.left : null;
            TreeNode<T> greater = (node.right != null && !added.contains(node.right)) ? node.right : null;

            if (parent == null && lesser == null && greater == null) {
                if (!added.contains(node))
                    nodes[index] = node.value;
                break;
            }

            if (order == DepthFirstSearchOrder.inOrder) {
                if (lesser != null) {
                    node = lesser;
                } else {
                    if (!added.contains(node)) {
                        nodes[index++] = node.value;
                        added.add(node);
                    }
                    if (greater != null) {
                        node = greater;
                    } else if (added.contains(node)) {
                        node = parent;
                    } else {
                        // We should not get here. Stop the loop!
                        node = null;
                    }
                }
            } else if (order == DepthFirstSearchOrder.preOrder) {
                if (!added.contains(node)) {
                    nodes[index++] = node.value;
                    added.add(node);
                }
                if (lesser != null) {
                    node = lesser;
                } else if (greater != null) {
                    node = greater;
                } else if (added.contains(node)) {
                    node = parent;
                } else {
                    // We should not get here. Stop the loop!
                    node = null;
                }
            } else {
                // post-Order
                if (lesser != null) {
                    node = lesser;
                } else {
                    if (!added.contains(node)) {
                        nodes[index++] = node.value;
                        added.add(node);
                    }
                    if (greater != null) {
                        node = greater;
                    } else if (added.contains(node)) {
                        node = parent;
                    } else {
                        // We should not get here. Stop the loop!
                        node = null;
                    }
                }
            }
        }
        return nodes;
    }

    /**
     * Get an array representation of the tree in sorted order.
     *
     * @return sorted array representing the tree.
     */
    public T[] getSorted() {
        // Depth first search to traverse the tree in-order sorted.
        return getDFS(DepthFirstSearchOrder.inOrder);
    }
}
