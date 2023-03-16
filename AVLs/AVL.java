import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

/**
 * Your implementation of an AVL.
 *
 * @author Renjie Yao
 * @version 1.0
 * @userid ryao36
 * @GTID 903622594
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class AVL<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Null data");
        }
        size = 0;
        for (T tmp : data) {
            add(tmp);
        }
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Null data");
        }
        root = addHelper(data, root);
    }

    /**
     * helper function of add
     * @param data data to be added
     * @param node root of a tree
     * @return root of a balanced tree
     */
    private AVLNode<T> addHelper(T data, AVLNode<T> node) {
        if (node == null) {
            size++;
            return new AVLNode<>(data);
        }
        int cmpResult = data.compareTo(node.getData());
        if (cmpResult < 0) {
            node.setLeft(addHelper(data, node.getLeft()));
        } else if (cmpResult > 0) {
            node.setRight(addHelper(data, node.getRight()));
        } else {
            return node;
        }
        // update the bf and height of this node
        update(node);
        if (Math.abs(node.getBalanceFactor()) > 1) {
            node = rotate(node);
        }
        return node;
    }

    /**
     * Update height and balance factor
     *
     * @param node input node
     */
    private void update(AVLNode<T> node) {
        int lHeight = height(node.getLeft());
        int rHeight = height(node.getRight());
        node.setHeight(Math.max(lHeight, rHeight) + 1);
        node.setBalanceFactor(lHeight - rHeight);
    }

    /**
     * Rotate the node to create a balanced tree
     *
     * @param node the node of a tree that will be rorated
     * @return balanced node
     */
    private AVLNode<T> rotate(AVLNode<T> node) {
        if (node.getBalanceFactor() < -1) {
            if (node.getRight().getBalanceFactor() > 0) {
                node.setRight(rotateRight(node.getRight()));
            }
            node = rotateLeft(node);
        } else if (node.getBalanceFactor() > 1) {
            if (node.getLeft().getBalanceFactor() < 0) {
                node.setLeft(rotateLeft(node.getLeft()));
            }
            node = rotateRight(node);
        }
        return node;
    }

    /**
     * Right rotation
     *
     * @param node to-be-rotated node
     * @return right-rotated node
     */
    private AVLNode<T> rotateRight(AVLNode<T> node) {
        AVLNode<T> tmp = node.getLeft();
        node.setLeft(tmp.getRight());
        tmp.setRight(node);
        // update the bf and height of node
        update(node);
        // update the bf and height of tmp
        update(tmp);
        return tmp;
    }

    /**
     * Left rotation
     *
     * @param node input node
     * @return left-rotated node
     */
    private AVLNode<T> rotateLeft(AVLNode<T> node) {
        AVLNode<T> tmp = node.getRight();
        node.setRight(tmp.getLeft());
        tmp.setLeft(node);
        // update the bf and height of node
        update(node);
        // update the bf and height of tmp
        update(tmp);
        return tmp;
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data, NOT successor. As a reminder, rotations can occur
     * after removing the predecessor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Null data");
        }
        AVLNode<T> removed = new AVLNode<>(null);
        root = removeHelper(data, root, removed);
        return removed.getData();
    }

    /**
     * helper function of add
     *
     * @param target target
     * @param node root node
     * @param removed removed node
     * @return parent of the removed node
     */
    private AVLNode<T> removeHelper(T target, AVLNode<T> node, AVLNode<T> removed) {
        if (node == null) {
            throw new java.util.NoSuchElementException("No such data");
        }
        int cmpResult = target.compareTo(node.getData());
        if (cmpResult < 0) {
            node.setLeft(removeHelper(target, node.getLeft(), removed));
        } else if (cmpResult > 0) {
            node.setRight(removeHelper(target, node.getRight(), removed));
        } else {
            size--;
            removed.setData(node.getData());
            if (node.getLeft() == null) {
                return node.getRight();
            } else if (node.getRight() == null) {
                return node.getLeft();
            } else {
                AVLNode<T> predecessor = new AVLNode<>(null);
                node.setLeft(predecessorHelper(node.getLeft(), predecessor));
                node.setData(predecessor.getData());
            }
        }
        // update the bf and height
        update(node);

        if (Math.abs(node.getBalanceFactor()) > 1) {
            node = rotate(node);
        }
        return node;
    }

    /**
     * return predecessor of node
     *
     * @param node input node
     * @param saveNode save the predecessor node
     * @return successor node of an element that will be removed
     */
    private AVLNode<T> predecessorHelper(AVLNode<T> node, AVLNode<T> saveNode) {
        if (node.getRight() == null) {
            saveNode.setData(node.getData());
            return node.getLeft();
        }
        node.setRight(predecessorHelper(node.getRight(), saveNode));
        update(node);
        return rotate(node);
    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data");
        }
        return getHelper(data, root);
    }

    /**
     * Helper method of get
     *
     * @param target the target
     * @param node root node
     * @return data of a node that matches passed in parameter
     */
    private T getHelper(T target, AVLNode<T> node) {
        if (node == null) {
            throw new java.util.NoSuchElementException("No such data");
        }
        int tmp = target.compareTo(node.getData());
        if (tmp > 0) {
            return getHelper(target, node.getRight());
        } else if (tmp < 0) {
            return getHelper(target, node.getLeft());
        } else {
            return node.getData();
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        try {
            get(data);
        } catch (java.util.NoSuchElementException e) {
            return false;
        }
        return true;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * Should be O(1).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return height(root);
    }

    /**
     * Returns height of a node
     *
     * @param node input node
     * @return height of this node
     */
    private int height(AVLNode<T> node) {
        if (node == null) {
            return -1;
        }
        return node.getHeight();
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the data on branches of the tree with the maximum depth. If you
     * encounter multiple branches of maximum depth while traversing, then you
     * should list the remaining data from the left branch first, then the
     * remaining data in the right branch. This is essentially a preorder
     * traversal of the tree, but only of the branches of maximum depth.
     *
     * This must be done recursively.
     *
     * Your list should not have duplicate data, and the data of a branch should be
     * listed in order going from the root to the leaf of that branch.
     *
     * Should run in worst case O(n), but you should not explore branches that
     * do not have maximum depth. You should also not need to traverse branches
     * more than once.
     *
     * Hint: How can you take advantage of the balancing information stored in
     * AVL nodes to discern deep branches?
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * Returns: [10, 5, 2, 1, 0, 7, 8, 9, 15, 20, 25, 30]
     *
     * @return the list of data in branches of maximum depth in preorder
     * traversal order
     */
    public List<T> deepestBranches() {
        List<T> res = new ArrayList<>();
        deepestBranchesHelper(res, root);
        return res;
    }

    /**
     * Helper of deepestBranches
     *
     * @param res the list result
     * @param node the root node to inspect
     */
    private void deepestBranchesHelper(List<T> res, AVLNode<T> node) {
        if (node == null) {
            return;
        } else {
            res.add(node.getData());
            if (node.getLeft() != null && !(node.getHeight() - node.getLeft().getHeight() > 1)) {
                deepestBranchesHelper(res, node.getLeft());
            }
            if (node.getRight() != null && !(node.getHeight() - node.getRight().getHeight() > 1)) {
                deepestBranchesHelper(res, node.getRight());
            }
        }
    }

    /**
     * Returns a sorted list of data that are within the threshold bounds of
     * data1 and data2. That is, the data should be > data1 and < data2.
     *
     * This must be done recursively.
     *
     * Should run in worst case O(n), but this is heavily dependent on the
     * threshold data. You should not explore branches of the tree that do not
     * satisfy the threshold.
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * sortedInBetween(7, 14) returns [8, 9, 10, 13]
     * sortedInBetween(3, 8) returns [4, 5, 6, 7]
     * sortedInBetween(8, 8) returns []
     *
     * @param data1 the smaller data in the threshold
     * @param data2 the larger data in the threshold
     * @return a sorted list of data that is > data1 and < data2
     * @throws IllegalArgumentException if data1 or data2 are null
     * or if data1 > data2
     */
    public List<T> sortedInBetween(T data1, T data2) {
        if (data1 == null || data2 == null || data1.compareTo(data2) > 0) {
            throw new IllegalArgumentException("Null data or data1 > data2");
        }
        List<T> res = new ArrayList<>();
        if (data1.equals(data2)) {
            return res;
        } else {
            sortedInBetweenHelper(data1, data2, root, res);
        }
        return res;
    }

    /**
     * Helper of sortedInBetween
     *
     * @param data1 the smaller data in the threshold
     * @param data2 the larger data in the threshold
     * @param res the result list
     * @param node root node
     */
    private void sortedInBetweenHelper(T data1, T data2, AVLNode<T> node, List<T> res) {
        if (node == null) {
            return;
        } else {
            if (node.getData().compareTo(data1) > 0) {
                sortedInBetweenHelper(data1, data2, node.getLeft(), res);
            }
            if (node.getData().compareTo(data1) > 0 && node.getData().compareTo(data2) < 0) {
                res.add(node.getData());
            }
            if (node.getData().compareTo(data2) < 0) {
                sortedInBetweenHelper(data1, data2, node.getRight(), res);
            }
        }
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
