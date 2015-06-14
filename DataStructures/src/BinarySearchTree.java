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
    private int size = 0;
    private TreeNode<T> root = null;
    private INodeCreator<T> creator = null;

    private enum Position {
        LEFT, RIGHT
    }

    private enum DepthFirstSearchOrder {
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
     * {@inheritDoc}
     */
    @Override
    public T remove(T value) {
        TreeNode<T> nodeToRemove = this.removeValue(value);
        return ((nodeToRemove != null) ? nodeToRemove.value : null);
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
    public boolean contains(T value) {
        TreeNode<T> node = getNode(value);
        return (node != null);
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

    private TreeNode<T> addValue(T value) {
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

    private TreeNode<T> removeValue(T value) {
        TreeNode<T> nodeToRemoved = this.getNode(value);
        if (nodeToRemoved != null) nodeToRemoved = removeNode(nodeToRemoved);
        return nodeToRemoved;
    }

    private TreeNode<T> removeNode(TreeNode<T> nodeToRemoved) {
        if (nodeToRemoved != null) {
            TreeNode<T> replacementNode = this.getReplacementNode(nodeToRemoved);
            replaceNodeWithNode(nodeToRemoved, replacementNode);
        }
        return nodeToRemoved;
    }

    private TreeNode<T> getReplacementNode(TreeNode<T> nodeToRemoved) {
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

    private void replaceNodeWithNode(TreeNode<T> nodeToRemoved, TreeNode<T> replacementNode) {
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

    private TreeNode<T> getNode(T value) {
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

    private void rotateLeft(TreeNode<T> node) {
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

    private void rotateRight(TreeNode<T> node) {
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

    private TreeNode<T> getGreatest(TreeNode<T> startingNode) {
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

    private TreeNode<T> getLeast(TreeNode<T> startingNode) {
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

    private boolean validateNode(TreeNode<T> node) {
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
}
