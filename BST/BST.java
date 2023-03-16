import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Your implementation of a BST.
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
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data is null");
        }
        for (T element : data) {
            // if element is null, exception will be thrown in the add function
            // so no need to throw exception here
            add(element);
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data is null");
        }
        root = add(root, data);

    }

    /**
     * Add data to the BST with curr as the root
     * @param curr the root of the BST
     * @param data the data to be added into the BST
     * @return the root of the BST with data added
     */
    private BSTNode<T> add(BSTNode<T> curr, T data) {
        if (curr == null) {
            size ++;
            return new BSTNode<T>(data); 
        } else if (curr.getData().compareTo(data) > 0) {
            curr.setLeft(add(curr.getLeft(), data));
        } else {
            curr.setRight(add(curr.getRight(), data));
        }
        return curr;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Null Data");
        }
        BSTNode<T> dummy = new BSTNode<>(null);
        root = remove(root, data, dummy);
        if (dummy.getData() == null) {
            throw new java.util.NoSuchElementException("Data Not Found");
        } else {
            return dummy.getData();
        }
    }

    /**
     * removes the data from the BST with curr as the root
     * and returns the root of the BST
     * @param curr the root of the BST
     * @param data the data to be deleted
     * @param dummy the node to contain the deleted data
     * @return the root of the BST after deletion
     */
    private BSTNode<T> remove(BSTNode<T> curr, T data, BSTNode<T> dummy) {
        if (curr == null) {
            throw new java.util.NoSuchElementException("Data is null");
        }
        if (curr.getData().compareTo(data) < 0) {
            curr.setRight(remove(curr.getRight(), data, dummy));
            return curr;
        } else if (curr.getData().compareTo(data) > 0) {
            curr.setLeft(remove(curr.getLeft(), data, dummy));
            return curr;
        } else {
            dummy.setData(curr.getData());
            size--;
            if (curr.getLeft() == null && curr.getRight() == null) {
                return null;
            } else if (curr.getRight() == null) {
                return curr.getLeft();
            } else if (curr.getLeft() == null) {
                return curr.getRight();
            } else {
                BSTNode<T> dummy2 = new BSTNode<>(null);
                curr.setRight(removePredecessor(curr.getRight(), dummy2));
                curr.setData(dummy2.getData());
                return curr;
            }
        }
    }

    /**
     * removes the most right node in the BST with root at curr
     * and returns the data in the deleted node
     * @param curr the root of the BST
     * @param dummy dummy node to contain the data in the most right node
     * @return the root of the BST after deletion
     */
    private BSTNode<T> removePredecessor(BSTNode<T> curr, BSTNode<T> dummy) {
        if (curr.getLeft() == null) {
            dummy.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(removePredecessor(curr.getLeft(), dummy));
            return curr;
        }
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Null Data");
        }
        T dataFound = get(root, data);
        if (dataFound == null) {
            throw new java.util.NoSuchElementException("Data Not Found");
        } else {
            return dataFound;
        }
    }

    /**
     * gets data from BST with curr as the root
     * and returns the data found
     * @param curr the root of the BST
     * @param data the data to be found in the BST
     * @return the data found int the BST
     */
    private T get(BSTNode<T> curr, T data) {
        if (curr == null) {
            return null;
        }
        if (curr.getData().compareTo(data) > 0) {
            return get(curr.getLeft(), data);
        } else if (curr.getData().compareTo(data) < 0) {
            return get(curr.getRight(), data);
        }
        return curr.getData();
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Null Data");
        }
        T dataFound = get(root, data);
        return !(dataFound == null);
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> res = new ArrayList<>();
        preorder(root, res);
        return res;
    }

    /**
     * traverse the BST with root curr in preorder
     * @param curr the root of the BST to be traversed
     * @param res the result list containing the preorder of traversal of the BST
     */
    private void preorder(BSTNode<T> curr, List<T> res) {
        if (curr == null) {
            return;
        }
        res.add(curr.getData());
        preorder(curr.getLeft(), res);
        preorder(curr.getRight(), res);
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> res = new ArrayList<>();
        inorder(root, res);
        return res;
    }

    /**
     * traverse the BST with root curr in inorder
     * @param curr the root of the BST to be traversed
     * @param res the result list containing the inorder of traversal of the BST
     */
    private void inorder(BSTNode<T> curr, List<T> res) {
        if (curr == null) {
            return;
        }
        inorder(curr.getLeft(), res);
        res.add(curr.getData());
        inorder(curr.getRight(), res);
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> res = new ArrayList<>();
        postorder(root, res);
        return res;
    }

    /**
     * traverse the BST with root curr in postorder
     * @param curr the root of the BST to be traversed
     * @param res the result list containing the postorder of traversal of the BST
     */
    private void postorder(BSTNode<T> curr, List<T> res) {
        if (curr == null) {
            return;
        }
        postorder(curr.getLeft(), res);
        postorder(curr.getRight(), res);
        res.add(curr.getData());
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> res = new ArrayList<>();
        levelorder(root, res);
        return res;
    }

    /**
     * traverse the BST with root curr in levelorder
     * @param curr the root of the BST to be traversed
     * @param res the result list containing the levelorder of traversal of the BST
     */
    private void levelorder(BSTNode<T> curr, List<T> res) {
        if (curr == null) {
            return;
        }
        Queue<BSTNode<T>> q = new LinkedList<>();
        q.offer(curr);
        while (!q.isEmpty()) {
            BSTNode<T> node = q.poll();
            res.add(node.getData());
            if (node.getLeft() != null) {
                q.offer(node.getLeft());
            }
            if (node.getRight() != null) {
                q.offer(node.getRight());
            }
        }
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return height(root);
    }

    /**
     * returns the height of the BST with curr as the root
     * @param curr the root of the BST
     * @return the height of the BST
     */
    private int height(BSTNode<T> curr) {
        if (curr == null) {
            return -1;
        }
        return Math.max(height(curr.getLeft()), height(curr.getRight())) + 1;
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds and retrieves the k-largest elements from the BST in sorted order,
     * least to greatest.
     *
     * This must be done recursively.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * EXAMPLE: Given the BST below composed of Integers:
     *
     *                50
     *              /    \
     *            25      75
     *           /  \
     *          12   37
     *         /  \    \
     *        10  15    40
     *           /
     *          13
     *
     * kLargest(5) should return the list [25, 37, 40, 50, 75].
     * kLargest(3) should return the list [40, 50, 75].
     *
     * Should have a running time of O(log(n) + k) for a balanced tree and a
     * worst case of O(n + k), with n being the number of data in the BST
     *
     * @param k the number of largest elements to return
     * @return sorted list consisting of the k largest elements
     * @throws java.lang.IllegalArgumentException if k < 0 or k > size
     */
    public List<T> kLargest(int k) {
        if (k < 0 || k > size) {
            throw new java.lang.IllegalArgumentException("Illegal k value");
        }
        List<T> res = new LinkedList<>();
        kLargest(root, k, res);
        return res;
    }

    private void kLargest(BSTNode<T> curr, int k, List<T> list) {
        if (curr != null && list.size() < k) {
            kLargest(curr.getRight(), k, list);
            if (list.size() < k) {
                list.add(0, curr.getData());
                kLargest(curr.getLeft(), k, list);
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
    public BSTNode<T> getRoot() {
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
