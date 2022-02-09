/**
* Hash.java
 * @author Aryan Dadnam
 * Group Project 22C
*/
import java.util.ArrayList;

public class Hash{

	private int numElements;
	private ArrayList< List > Table;
	
	/**
	* Constructor for the Hash.java
	* class. Initializes the Table to
	* be sized according to value passed
	* in as a parameter
	* Inserts size empty Lists into the
	* table. Sets numElements to 0
	* @param size the table size
	*/
	public Hash(int size) {
		numElements = 0;
		Table = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			Table.add(new List());
		}
	}
	
	/**Accessors*/
	
	/**
	* Returns the hash value in the Table
	* for a given key by taking modulus
	* of the hashCode value for that key
	* and the size of the table
	* @param t the key
	* @return the index in the Table
	*/
	private int hash(Animal t) {
		int code = t.hashCode();
	    return code % Table.size();
	}
	
	/**
	* Counts the number of keys at this index
	* @param index the index in the Table
	* @precondition 0 <= index < Table.length
	* @return the count of keys at this index
	* @throws IndexOutOfBoundsException
	*/
	public int countBucket(int index) throws IndexOutOfBoundsException{
		if(index < 0 || index >= Table.size()) {
			throw new IndexOutOfBoundsException("countBucket(): invalid index. index is Out of Bounds!");
		}
	    return Table.get(index).getLength();
	}
	
	/**
	* Returns total number of keys in the Table
	* @return total number of keys
	*/
	public int getNumElements() {
	    return numElements;
	}
	
	/**
	* Searches for a specified key in the Table
	* @param t the key to search for
	* @return whether the key is in the Table
	*/
	public boolean search(Animal t) {
		int bucket = hash(t);
		if (Table.get(bucket).linearSearch(t) == -1)
			return false;
		return true;
	}
	
	
	/**Manipulation Procedures*/
	
	/**
	* Inserts a new key in the Table
	* calls the hash method to determine placement
	* @param t the key to insert
	*/
	public void insert(Animal t) { 
		int bucket = hash(t);
		Table.get(bucket).addLast(t);
		numElements++;
	} 
	
	
	/**
	* removes the key t from the Table
	* calls the hash method on the key to
	* determine correct placement
	* has no effect if t is not in
	* the Table
	* @param t the key to remove
	*/
	public void remove(Animal t) {
		int bucket = hash(t);
		int pos = Table.get(bucket).linearSearch(t);
		if(Table.get(bucket).linearSearch(t) != -1) {
			Table.get(bucket).advanceToIndex(pos);
			Table.get(bucket).removeIterator();
			numElements--;
		}
	}
	
	/**Additional Methods*/
	
	/**
	* Prints the first key at each bucket
	* along with a count of the total keys
	* with the message "+ <count> -1 more 
	* at this bucket." Each bucket separated
	* with two blank lines. When the bucket is 
	* empty, prints the message "This bucket
	* is empty." followed by two blank lines
	*/
	public void printTable(){
		for (int i = 0; i < Table.size(); i++) {
				Table.get(i).pointIterator();
				while(Table.get(i).offEnd() == false) {
					System.out.println(Table.get(i).getIterator().toString() + "\n");
					Table.get(i).advanceIterator();
				}
		}
			
	}
	
}