/**
 * Your implementation of a CircularSinglyLinkedList without a tail pointer.
 *
 * @author yaosir
 * @version 1.0
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class CircularSinglyLinkedList<T> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private CircularSinglyLinkedListNode<T> head;
    private int size;

    /*
     * Do not add a constructor.
     */

    /**
     * Adds the data to the specified index.
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new data
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new java.lang.IndexOutOfBoundsException("Index Out of Bound");
        }
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data is null");
        }
        if (size == 0) {
            head = new CircularSinglyLinkedListNode<T>(data);
            head.setNext(head);
            size++;
            return;
        }
        if (index == 0) {
            T num = head.getData();
            CircularSinglyLinkedListNode<T> newNode = new CircularSinglyLinkedListNode<T>(num);
            newNode.setNext(head.getNext());
            head.setData(data);
            head.setNext(newNode);
            size++;
            return;
        }
        if (index == size) {
            T num = head.getData();
            CircularSinglyLinkedListNode<T> newNode = new CircularSinglyLinkedListNode<T>(num);
            newNode.setNext(head.getNext());
            head.setData(data);
            head.setNext(newNode);
            head = newNode;
            size++;
            return;
        }
        CircularSinglyLinkedListNode<T> cur = head;
        while (index > 0) {
            cur = cur.getNext();
            index--;
        }
        T num = cur.getData();
        CircularSinglyLinkedListNode<T> newNode = new CircularSinglyLinkedListNode<T>(num);
        newNode.setNext(cur.getNext());
        cur.setData(data);
        cur.setNext(newNode);
        size++;
        return;
    }

    /**
     * Adds the data to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        addAtIndex(0, data);
        return;
    }

    /**
     * Adds the data to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        addAtIndex(size, data);
        return;
    }

    /**
     * Removes and returns the data at the specified index.
     *
     * Must be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (size == 0) {
            throw new java.util.NoSuchElementException("Empty");
        }
        if (index < 0 || index >= size) {
            throw new java.lang.IndexOutOfBoundsException("Index Out of Bounds");
        }
        if (size == 1) {
            T num = head.getData();
            head = null;
            size--;
            return num;
        }
        if (index == 0) {
            T num = head.getData();
            head.setData(head.getNext().getData());
            head.setNext(head.getNext().getNext());
            size--;
            return num;
        }
        if (index == size - 1) {
            CircularSinglyLinkedListNode<T> cur = head;
            while (index > 0) {
                cur = cur.getNext();
                index--;
            }
            T num = cur.getData();
            head = cur;
            cur.setData(cur.getNext().getData());
            cur.setNext(cur.getNext().getNext());
            size--;
            return num;
        }
        CircularSinglyLinkedListNode<T> cur = head;
        while (index > 0) {
            cur = cur.getNext();
            index--;
        }
        T num = cur.getData();
        cur.setData(cur.getNext().getData());
        cur.setNext(cur.getNext().getNext());
        size--;
        return num;
    }

    /**
     * Removes and returns the first data of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        return removeAtIndex(0);
    }

    /**
     * Removes and returns the last data of the list.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        return removeAtIndex(size - 1);
    }

    /**
     * Returns the data at the specified index.
     *
     * Should be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new java.lang.IndexOutOfBoundsException("Index Out of Bounds");
        }
        CircularSinglyLinkedListNode<T> cur = head;
        while (index > 0) {
            cur = cur.getNext();
            index--;
        }
        return cur.getData();
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        size = 0;
        return;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(n).
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data is null");
        }
        boolean found = false;
        T ans;
        CircularSinglyLinkedListNode<T> target = head;
        CircularSinglyLinkedListNode<T> cur = head;
        while (cur.getNext() != head) {
            if (cur.getData() == data) {
                target = cur;
                found = true;
            }
            cur = cur.getNext();
        }
        if (cur.getData() == data) {
            found = true;
            ans = cur.getData();
            cur.setData(cur.getNext().getData());
            cur.setNext(cur.getNext().getNext());
            head = cur;
            size--;
            return ans;
        } else {
            if (!found) {
                throw new java.util.NoSuchElementException("Not found");
            }
            ans = target.getData();
            target.setData(target.getNext().getData());
            target.setNext(target.getNext().getNext());
            size--;
            return ans;     
        }
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return the array of length size holding all of the data (not the
     * nodes) in the list in the same order
     */
    public T[] toArray() {
        T[] array = (T[]) new Object[size];
        CircularSinglyLinkedListNode<T> cur = head;
        for (int i = 0; i < size; i++) {
            array[i] = cur.getData();
            cur = cur.getNext();
        }
        return array;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public CircularSinglyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
