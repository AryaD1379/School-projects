/**
 * BST.java
 * @author Aryan Dadnam
 * Group Project 22C
 */
import java.io.PrintWriter;
import java.util.NoSuchElementException;

public class BST {
	
    private class Node {
        private Animal data;
        private Node left;
        private Node right;
       
        public Node(Animal data) {
            this.data = data;
            left = null;
            right = null;
        }
    }
   
    private Node root;
   
    /***CONSTRUCTORS***/
   
    /**
     * Default constructor for BST
     * sets root to null
     */
    public BST() {
    	root = null;
    }
   
    /**
     * Copy constructor for BST
     * @param bst the BST to make
     * a copy of
     */
    public BST(BST bst) {
    	if (bst == null)
    		return;
    	else {
    		root = null;
    		copyHelper(bst.root);
    	}
    }
   
    /**
     * Helper method for copy constructor for primary class
     * @param node the node containing
     * data to copy
     */
    private void copyHelper(Node node) {
    	if (node != null) {
    		insertP(node.data);
    		copyHelper(node.left);
    		copyHelper(node.right);
    	}
    }
   
    /***ACCESSORS***/
   
    /**
     * Returns the data stored in the root
     * @precondition !isEmpty()
     * @return the data stored in the root
     * @throws NoSuchElementException when
     * preconditon is violated
     */
    public Animal getRoot() throws NoSuchElementException {
    	if (isEmpty())
    		throw new NoSuchElementException("getRoot(): BST is empty. Cannot get root!");
        return root.data;
    }
   
    /**
     * Determines whether the tree is empty
     * @return whether the tree is empty
     */
    public boolean isEmpty() {
    	return root == null;
    }
   
    /**
     * Returns the current size of the
     * tree (number of nodes)
     * @return the size of the tree
     */
    public int getSize() {
        return getSize(root);
    }
   
    /**
     * Helper method for the getSize method
     * @param node the current node to count
     * @return the size of the tree
     */
    private int getSize(Node node) {
    	if (node == null)
    		return 0;
    	else
    		return (getSize(node.left) + getSize(node.right) + 1);
    }
   
    /**
     * Returns the height of tree by
     * counting edges.
     * @return the height of the tree
     */
    public int getHeight() {
        return getHeight(root);
    }
   
    /**
     * Helper method for getHeight method
     * @param node the current
     * node whose height to count
     * @return the height of the tree
     */
    private int getHeight(Node node) {
    	if (node == null)
    		return -1;
    	if (getHeight(node.left) > getHeight(node.right))
    		return (getHeight(node.left) + 1);
    	else
    		return (getHeight(node.right) + 1);
    }
   
    /**
     * Returns the smallest value in the tree
     * @precondition !isEmpty()
     * @return the smallest value in the tree
     * @throws NoSuchElementException when the
     * precondition is violated
     */
    public Animal findMin() throws NoSuchElementException {
    	if (isEmpty())
    		throw new NoSuchElementException("findMin(): BST is empty. Cannot find min!");
        return findMin(root);
    }
   
    /**
     * Helper method to findMin method
     * @param node the current node to check
     * if it is the smallest
     * @return the smallest value in the tree
     */
    private Animal findMin(Node node) {
    	if (node.left != null)
    		return findMin(node.left);
    	else
    		return node.data;
    }
   
    /**
     * Returns the largest value in the tree
     * @precondition !isEmpty()
     * @return the largest value in the tree
     * @throws NoSuchElementException when the
     * precondition is violated
     */
    public Animal findMax() throws NoSuchElementException{
    	if (isEmpty())
    		throw new NoSuchElementException("findMax(): BST is empty. Cannot find max!");
        return findMax(root);
    }
   
    /**
     * Helper method to findMax method
     * @param node the current node to check
     * if it is the largest
     * @return the largest value in the tree
     */
    private Animal findMax(Node node) {
    	if (node.right != null)
    		return findMax(node.right);
    	else
    		return node.data;
    }
   
    /**
     * Searches for a specified value
     * in the tree
     * @param data the value to search for
     * @return whether the value is stored
     * in the tree
     */
    public String searchN(Animal data) {
    	if (root == null)
            return "No such animal found.";
    	else
            return searchN(data, root);
    }
   
    /**
     * Helper method for the search method
     * @param data the data to search for
     * @param node the current node to check
     * @return whether the data is stored
     * in the tree
     */
    private String searchN(Animal data, Node node) {
    	if (data.getName().equalsIgnoreCase(node.data.getName()))
    		return node.data.toString();
    	else if ( data.getName().compareToIgnoreCase(node.data.getName()) < 0) {
    		if (node.left == null)
    			return "No such animal found.";
    		else
    			return searchN(data, node.left);
    	}
    	else {
    		if (node.right == null)
    			return "No such animal found.";
    		else
    			return searchN(data, node.right);
    	}
    }
    
    
    /**
     * 
     * @param data
     * @return
     */
    public String searchP(Animal data) {
    	if (root == null)
            return "No such animal found.";
    	else
            return searchP(data, root);
    }
   
    /**
     * Helper method for the search method
     * @param data the data to search for
     * @param node the current node to check
     * @return whether the data is stored
     * in the tree
     */
    private String searchP(Animal data, Node node) {
    	if(node != null) {
    	if (data.getPopulate().equals(node.data.getPopulate()))
    		if(node.left != null) {
    			if(!searchP(data, node.left).equals("No such animal found.") && 
    					!searchP(data, node.right).equals("No such animal found.")) {
    			return node.data.toString() + "\n\n" + searchP(data, node.right)
				+"\n\n" + searchP(data, node.left);
    			}
    			else if(searchP(data, node.right).equals("No such animal found.")
    					&& !searchP(data, node.left).equals("No such animal found.")) {
    				return node.data.toString() +
    						"\n\n" + searchP(data, node.left);
    			}
    			else if(searchP(data, node.left).equals("No such animal found.") &&
    					!searchP(data, node.right).equals("No such animal found.")) {
    				return node.data.toString() +"\n\n" + 
    							searchP(data, node.right);
    			}
    			else {
    				return node.data.toString();
    			}
    		} else
    		return node.data.toString();
    	
    	else if (data.getPopulate() < node.data.getPopulate()) {
    		if (node.left == null)
    			return "No such animal found.";
    		else
    			return searchP(data, node.left);
    	}
    	else {
    		if (node.right == null)
    			return "No such animal found.";
    		else
    			return searchP(data, node.right);
    	}
    	}
    	else return "No such animal found.";
    }
    /**
     * Determines whether two trees store
     * identical data in the same structural
     * position in the tree
     * @param o another Object
     * @return whether the two trees are equal
     */
	@Override public boolean equals(Object o) {
    	if (o == this)
    		return true;
    	else if (!(o instanceof BST))
    		return false;
    	else {
    		BST otherBST = (BST) o;
    		return equals(root, otherBST.root);
    	}
    }
   
    /**
     * Helper method for the equals method
     * @param node1 the node of the first bst
     * @param node2 the node of the second bst
     * @return whether the two trees contain
     * identical data stored in the same structural
     * position inside the trees
     */   
    private boolean equals(Node node1, Node node2) {
    	if (node1 == null && node2 == null)
    		return true;
    	else if (node1 != null && node2 != null)
    		return (node2.data.equals(node1.data)
    				&& equals(node1.left, node2.left)
    				&& equals(node1.right, node2.right));
    	return false;
    }
   
    /***MUTATORS***/
   
    /**
     * Inserts a new node in the tree
     * @param data the data to insert
     */
    public void insertN(Animal data) {
    	if (root == null)
            root = new Node(data);
        else
            insertN(data, root);
    }
   
    /**
     * Helper method to insert
     * Inserts a new value in the tree
     * @param data the data to insert
     * @param node the current node in the
     * search for the correct location
     * in which to insert
     */
    private void insertN(Animal data, Node node) {
    	if (data.getName().compareToIgnoreCase(node.data.getName()) <= 0) {
    		if (node.left == null)
    			node.left = new Node(data);
    		else
    			insertN(data, node.left);
    	}
    	else {
    		if (node.right == null)
    			node.right = new Node(data);
    		else
    			insertN(data, node.right);
    	}
    }
    /**
     * Inserts a new node in the tree
     * @param data the data to insert
     */
    public void insertP(Animal data) {
    	if (root == null)
            root = new Node(data);
        else
            insertP(data, root);
    }
   
    /**
     * Helper method to insert
     * Inserts a new value in the tree
     * @param data the data to insert
     * @param node the current node in the
     * search for the correct location
     * in which to insert
     */
    private void insertP(Animal data, Node node) {
    	if (data.getPopulate() <= node.data.getPopulate()) {
    		if (node.left == null)
    			node.left = new Node(data);
    		else
    			insertP(data, node.left);
    	}
    	else {
    		if (node.right == null)
    			node.right = new Node(data);
    		else
    			insertP(data, node.right);
    	}
    }
   
    /**
     * Removes a value from the BST
     * @param data the value to remove
     * @precondition !isEmpty()
     * @precondition the data is located in the tree
     * @throws NoSuchElementException when the
     * precondition is violated
     */
    public void removeN(Animal data) throws NoSuchElementException{
    	if (isEmpty())
    		throw new NoSuchElementException("remove(): BST is empty. Cannot remove!");
    	else if (searchN(data, root).equals("No such animal found."))
    		throw new NoSuchElementException("remove(): Element is not located in BST. Cannot remove!");
    	root = removeN(data, root);
    }
   
    /**
     * Helper method to the remove method
     * @param data the data to remove
     * @param node the current node
     * @return an updated reference variable
     */
    private Node removeN(Animal data, Node node) {
    	if (node == null)
    		return node;
    	else if (data.getName().compareToIgnoreCase(node.data.getName()) < 0)
    		node.left = removeN(data, node.left);
    	else if (data.getName().compareToIgnoreCase(node.data.getName()) > 0)
    		node.right = removeN(data, node.right);
    	else {
    		if (node.left == null && node.right == null)
    			node = null;
    		else if (node.left != null && node.right == null)
    			node = node.left;
    		else if (node.right != null && node.left == null)
    			node = node.right;
    		else {
    			Animal successor = findMin(node.right);
    			node.data = successor;
    			node.right = removeN(successor, node.right);
    		}
    	}
        return node;
    }
    /**
     * Removes a value from the BST
     * @param data the value to remove
     * @precondition !isEmpty()
     * @precondition the data is located in the tree
     * @throws NoSuchElementException when the
     * precondition is violated
     */
    public void removeP(Animal data) throws NoSuchElementException{
    	if (isEmpty())
    		throw new NoSuchElementException("remove(): BST is empty. Cannot remove!");
    	else if (searchP(data, root).equals("No such animal found."))
    		throw new NoSuchElementException("remove(): Element is not located in BST. Cannot remove!");
    	root = removeP(data, root);
    }
   
    /**
     * Helper method to the remove method
     * @param data the data to remove
     * @param node the current node
     * @return an updated reference variable
     */
    private Node removeP(Animal data, Node node) {
    	if (node == null)
    		return node;
    	else if (data.getPopulate().compareTo(node.data.getPopulate()) <= 0
    			&& !data.getName().equalsIgnoreCase(node.data.getName()))
    		node.left = removeP(data, node.left);
    	else if (data.getPopulate().compareTo(node.data.getPopulate()) > 0
    			&& !node.data.getName().equalsIgnoreCase(data.getName()))
    		node.right = removeP(data, node.right);
    	else {
    		if (node.left == null && node.right == null)
    			node = null;
    		else if (node.left != null && node.right == null)
    			node = node.left;
    		else if (node.right != null && node.left == null)
    			node = node.right;
    		else {
    			Animal successor = findMin(node.right);
    			node.data = successor;
    			node.right = removeP(successor, node.right);
    		}
    	}
        return node;
    }
       
    /***ADDITIONAL OPERATIONS***/
 
    /**
     * Prints the data in sorted order
     * to the console
     */
    public void inOrderPrint() {
    	inOrderPrint(root);
    	System.out.println();
    }
   
    /**
     * Helper method to inOrderPrint method
     * Prints the data in sorted order
     * to the console
     */
    private void inOrderPrint(Node node) {
    	if (node != null) {
    		inOrderPrint(node.left);
    		System.out.print(node.data + " \n\n");
    		inOrderPrint(node.right);
    	}
    }
  
    public void printFolder(PrintWriter in) {
    	printFolder(root, in);
    }
    private void printFolder(Node node, PrintWriter in) {
    	if(node != null) {
    		printFolder(node.left, in);
    		in.println(node.data.getName() +"\n" +
    		node.data.getPopulate() + "\n" +
    				node.data.getAmount() + "\n" +
    				node.data.getColor() + "\n" +
    				node.data.getfamily() + "\n");
    		printFolder(node.right, in);
    	}
    }
}