/** List.java
 * Defines the a singly-linked list class
 * @author: Aryan Dadnam
 */

import java.util.NoSuchElementException;

public class List<T extends Comparable<T>> {
	
    private class Node {
        private T data;
        private Node next;
        private Node prev;
       
        public Node(T data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }
   
    private int length;
    private Node first;
    private Node last;
    private Node iterator;
   
    /****CONSTRUCTOR****/
   
    /**
     * Instantiates a new List with default values
     * @postcondition: An empty list is created
     */
    public List() {
    	first = null;
        last = null;
        iterator = null;
        length = 0;
    }
    
    /**
     * Instantiates a new List by copying another List
     * @param original the List to make a copy of
     * @postcondition a new List object, which is an identical
     * but separate copy of the List original
     */
    public List(List<T> original) {
        if (original == null)
            return;
        if (original.length == 0) {
            length = 0;
            first = null;
            last = null;
            iterator = null;
        } else {
            Node temp = original.first;
            while (temp != null) {
                addLast(temp.data);
                temp = temp.next;
            }
            iterator = null;
        }
    }
    
    /****ACCESSORS****/
    
    /**
     * Returns the value stored in the first node
     * @precondition: List is not empty
     * @return the value stored at node first
     * @throws NoSuchElementException when precondition is violated
     */
    public T getFirst() throws NoSuchElementException{
    	if (length == 0)
            throw new NoSuchElementException("getFirst(): List is Empty. No data to access!");
        return first.data;
    }
   
    /**
     * Returns the value stored in the last node
     * @precondition: List is not empty
     * @return the value stored in the node last
     * @throws NoSuchElementException when precondition is violated
     */
    public T getLast() throws NoSuchElementException{
    	if (length == 0)
            throw new NoSuchElementException("getLast(): List is Empty. No data to access!");
        return last.data;
    }
    
    /**
     * Returns the element currently pointed at by the iterator
     * @precondition: Iterator is not off the end of the List
     * @return the value stored at node iterator
     * @throws NullPointerException when precondition is violated
     */
    public T getIterator() throws NullPointerException{
    	if (iterator == null)
    		throw new NullPointerException("getIterator(): Iterator is off end!");
        return iterator.data;
    }
	
	/**
     * Returns the index of the iterator
     * from 1 to n. Note that there is
     * no index 0. Does not use recursion.
     * @precondition
     * @return the index of the iterator
     * @throws NullPointerException when
     * the precondition is violated
     */
    public int getIndex() throws NullPointerException {
    	if (iterator == null)
    		throw new NullPointerException("getIndex(): Iterator is off end!");
    	else {
    		Node temp = first;
    		int index = 1;
    		while (temp != null) {
    			if (temp == iterator)
    				return index;
    			temp = temp.next;
    			index++;
    		}
    	}
        return -1;
    }
   
    /**
     * Returns the current length of the list
     * @return the length of the list from 0 to n
     */
    public int getLength() {
        return length;
    }
   
    /**
     * Returns whether the list is currently empty
     * @return whether the list is empty
     */
    public boolean isEmpty() {
        return length == 0;
    }
    
    /**
     * Returns whether the iterator is pointing to null
     * @return iterator == null
     */
    public boolean offEnd() {
    	return iterator == null;
    }
    
    /**
     * Determines whether two Lists have the same data
     * in the same order
     * @param L the List to compare to this List
     * @return whether the two Lists are equal
     */
    @SuppressWarnings("unchecked")
    @Override public boolean equals(Object o) {
        if(o == this) {
            return true;
        } else if (!(o instanceof List)) {
            return false;
        } else {
            List<T> L = (List<T>) o;
            if (this.length != L.length)
                return false;
            else {
                Node temp1 = this.first;
                Node temp2 = L.first;
                while (temp1 != null) { 
                    if (temp1.data != temp2.data) {
                        return false;
                    }
                    temp1 = temp1.next;
                    temp2 = temp2.next;
                }
                return true;
            }
        }
    }
    
    /**
     * Determines whether a List is sorted
     * by calling its recursive helper method
     * isSorted
     * Note: An empty List can be
     * considered to be (trivially) sorted
     * @return whether this List is sorted
     */
    public boolean inSortedOrder() {
    	if (length == 0)
    		return true;
    	return inSortedOrder(first);
    }

	/**
	* Helper method to inSortedOrder
	* Determines whether a List is
	* sorted in ascending order recursively
	* @return true whether this List is sorted
	*/
	private boolean inSortedOrder(Node node) {
		if (node.next == null)
			return true;
		if (node.data.compareTo(node.next.data) > 0)
			return false;
		return inSortedOrder(node.next);
	}
    
    /**
     * Uses the iterative linear search
     * algorithm to locate a specific
     * element in the list
     * @param element the value to search for
     * @return the location of value in the
     * List or -1 to indicate not found
     * Note that if the List is empty we will
     * consider the element to be not found
     * @postcondition: position of the iterator remains
     * unchanged!
     */
    public int linearSearch(T element) {
    	Node temp = first;
    	int loc = 1;
    	while (temp != null) {
    		if (temp.data.equals(element))
    			return loc;
    		temp = temp.next;
    		loc++;
    	}
    	return -1;
    }
    
    /**
     * Returns the index from 1 to length
     * where value is located in the List
     * by calling the private helper method
     * binarySearch
     * @param value the value to search for
     * @return the index where value is
     * stored from 1 to length, or -1 to
     * indicate not found
     * @precondition isSorted()
     * @postcondition the position of the
     * iterator must remain unchanged!
     * @throws IllegalStateException when the
     * precondition is violated.
     */
    public int binarySearch(T value) throws IllegalStateException {
    	if (!inSortedOrder())
    		throw new IllegalStateException("binarySearch(): List is not in sorted order!");
    	return binarySearch(1, length, value);
    }
   
    /**
     * Searches for the specified value in
     * the List by implementing the recursive
     * binarySearch algorithm
     * @param low the lowest bounds of the search
     * @param high the highest bounds of the search
     * @param value the value to search for
     * @return the index at which value is located
     * or -1 to indicate not found
     * @postcondition the location of the iterator
     * must remain unchanged
     */
    private int binarySearch(int low, int high, T value) {
    	if (high < low)
    		return -1;
    	int mid = low + (high - low)/2;
    	Node temp = first;
    	for (int i = 1; i < mid; i++) {
    		temp = temp.next;
    	}
    	if (temp.data.equals(value))
    		return mid;
    	else if (temp.data.compareTo(value) > 0)
    		return binarySearch(low, mid - 1, value);
    	else
    		return binarySearch(mid + 1, high, value);
    }
    
    /****MUTATORS****/
    
    /**
     * points the iterator to the first Node in the list
     */
    public void pointIterator() {
        iterator = first;
        return;
    }
    
    /**
     * advances the iterator by pointing to the next Node
     * @precondition iterator == null
     * @throws NullPointerException when precondition is violated
     */
    public void advanceIterator() throws NullPointerException {
    	if (iterator == null)
    		throw new NullPointerException("advanceIterator(): Iterator is off end!");
    	iterator = iterator.next;
        return;
    }
    
   /**
    * Places the iterator at first
    * and then iteratively advances
    * it to the specified index
    * no recursion
    * @param index the index where
    * the iterator should be placed
    * @precondition 1 <= index <= length
    * @throws IndexOutOfBoundsException
    * when precondition is violated
    */
   public void advanceToIndex(int index) throws IndexOutOfBoundsException {
       if (index > length)
    	   throw new IndexOutOfBoundsException("advanceToIndex(): Index is out of bound!");
       else {
    	   pointIterator();
    	   for (int i = 1; i < index; i++) {
    		   advanceIterator();
    	   }
       }
       return;
   }
    
    /**
     * points back to the previous Node inside the list
     * @precondition iterator == null
     * @throws NullPointerException when precondition is violated
     */
    public void reverseIterator() throws NullPointerException {
    	if (iterator == null)
    		throw new NullPointerException("reverseIterator(): Iterator is off end!");
    	iterator = iterator.prev;
        return;
    }
   
    /**
     * Creates a new first element
     * @param data the data to insert at the
     * front of the list
     * @postcondition: An element is added to the beginning of the list
     */
    public void addFirst(T data) {
    	if (first == null)
            first = last = new Node(data);
    	else {
            Node N = new Node(data);
            N.next = first;
            first.prev = N;
            first = N;
        }
        length++;
        return;
    }
   
    /**
     * Creates a new last element
     * @param data the data to insert at the
     * end of the list
     * @postcondition: An element is added to the end of the list
     */
    public void addLast(T data) {
    	if (last == null)
            last = first = new Node(data);
    	else {
            Node N = new Node(data);
            last.next = N;
            N.prev = last;
            last = N;
        }
        length++;
        return;
    }
    
    /**
     * creates a new node next to the iterator inside the List
     * @param data
     * @precondition !OffEnd()
     * @throws NullPointerException when the condition is violated
     */
    public void addIterator(T data) throws NullPointerException {
    	if (iterator == null)
    		throw new NullPointerException("addIterator(): Iterator is off end!");
    	if (iterator == last)
    		addLast(data);
    	else {
    		Node N = new Node(data);
    		N.next = iterator.next;
    		iterator.next.prev = N;
    		iterator.next = N;
    		N.prev = iterator;
    		length++;
    	}
        return;
    }
   
    /**
     * removes the element at the front of the list
     * @precondition: List is not empty
     * @postcondition: The first element is removed
     * @throws NoSuchElementException when precondition is violated
     */
    public void removeFirst() throws NoSuchElementException {
    	if (length == 0)
            throw new NoSuchElementException("removeFirst(): Cannot remove from an empty List!");
        else if (length == 1)
            first = last = iterator = null;
        else {
        	if (iterator == first)
        		iterator = null;
            first = first.next;
            first.prev = null;
        }
        length--;
        return;
    }
   
    /**
     * removes the element at the end of the list
     * @precondition: List is not empty
     * @postcondition: The last element is removed
     * @throws NoSuchElementException when precondition is violated
     */
    public void removeLast() throws NoSuchElementException {
    	if (length == 0)
    		throw new NoSuchElementException("removeLast(): Cannot remove from an empty List!");
    	else if (length == 1)
    		last = first = iterator = null;
    	else {
        	if (iterator == last)
        		iterator = null;
            last = last.prev;
            last.next = null;
        }
        length--;
        return;
    }
      
      /**
       * Remove the element currently pointed at by the iterator
       * @precondition: Iterator is not off the end of the List
       * @postcondition: The element currently pointed at by the iterator is removed, iterator points to null
       * @throws NullPointerException when precondition is violated
       */
    public void removeIterator() throws NullPointerException {
    	if (iterator == null)
    		throw new NullPointerException("removeIterator(): Iterator is off end!");
    	else if (iterator == first)
    		removeFirst();
    	else if (iterator == last)
    		removeLast();
    	else {
    		iterator.prev.next = iterator.next;
    		iterator.next.prev = iterator.prev;
    		iterator = null;
    		length--;
    	}
        return;
    }
      
    /**** ADDITIONAL OPERATIONS ****/
   
    /**
     * List with each value on its own line
     * At the end of the List a new line
     * @return the List as a String for display
     */
    @Override public String toString() {
    	String result = "";
        Node temp = first;
        while (temp != null) {
            result += temp.data + " ";
            temp = temp.next;
        }
        return result;
    }
    
    /**
     * Numbered List with each element on a line 
     * At the end of the List a new line
     */
    public void printNumberedList() {
        Node temp = first;
        for (int i = 1; i <= length; i++) {
        	System.out.println(i + ". " + temp.data);
            temp = temp.next;
        }
        System.out.println();
    }
    
    /**
     * Prints a linked list to the console
     * in reverse by calling the private
     * recursive helper method printInReverse
     */
    public void printInReverse() {
    	printInReverse(last);
    }
    
    /**
     * Recursively prints a linked list to the console
     * in reverse order from last to first (no loops)
     * Each element separated by a space
     * Should print a new line after all
     * elements have been displayed
     */   
    private void printInReverse(Node node) {
    	if (node != null) {
    		System.out.print(node.data + " ");
    		printInReverse(node.prev);
    	}
    	else
    		System.out.println();
        return;
    }    
    
}
