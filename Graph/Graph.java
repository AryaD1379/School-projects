/**
 * Graph.java
 * @author Aryan Dadnam
 * CIS 22C, Lab 8
 */

import java.util.ArrayList;

public class Graph {
    private int vertices;
    private int edges; 
    private ArrayList<List<Integer> > adj;
    private ArrayList<Character> color;
    private ArrayList<Integer> distance;
    private ArrayList<Integer> parent;
    
    /**Constructors and Destructors*/

  /**
   * initializes an empty graph, with n vertices
   * and 0 edges
   * @param n the number of vertices in the graph
   */
    public Graph(int n) {
            vertices = n;
            edges = 0;
            adj = new ArrayList<List<Integer>>();
            color = new ArrayList<Character>();
            distance = new ArrayList<Integer>();
            parent = new ArrayList<Integer>();
            for (int i = 0; i <= n; i++) {
            	adj.add(new List<Integer>());
            	color.add('W');
            	distance.add(-1);
            	parent.add(null);
            }
    }
    
    
    /*** Accessors ***/
    
  /**
   * Returns the number of edges in the graph
   * @return the number of edges
   */
    public int getNumEdges() {
        return edges;
    }
    
    /**
     * Returns the number of vertices in the graph
     * @return the number of vertices
     */
    public int getNumVertices() {
        return vertices;
    }
    
    /**
     * returns whether the graph is empty (no vertices)
     * @return whether the graph is empty
     */
    public boolean isEmpty() {
        return vertices == 0;
    }

    /**
     * Returns the value of the distance[v]
     * @param v a vertex in the graph
     * @precondition 0 < v <= vertices
     * @return the distance of vertex v
     * @throws IndexOutOfBoundsException when 
     * the precondition is violated
     */
    public Integer getDistance(Integer v) throws IndexOutOfBoundsException{
    	if(v <= 0 || v >= vertices) {
    		throw new IndexOutOfBoundsException("getDistance(): invalid vertex.");
    	}
        return distance.get(v);
    }
    
    /**
     * Returns the value of the parent[v]
     * @param v a vertex in the graph
     * @precondition 0 < v <= vertices
     * @return the parent of vertex v
     * @throws IndexOutOfBoundsException when 
     * the precondition is violated
     */
    public Integer getParent(Integer v) throws IndexOutOfBoundsException {
    	if(v <= 0 || v >= vertices) {
    		throw new IndexOutOfBoundsException("getParent(): invalid vertex.");
    	}
        return parent.get(v);
    }
    
    /**
     * Returns the value of the color[v]
     * @param v a vertex in the graph
     * @precondition 0 < v <= vertices
     * @return the color of vertex v
     * @throws IndexOutOfBoundsException when 
     * the precondition is violated
     */
    public Character getColor(Integer v) throws IndexOutOfBoundsException {
    	if(v <= 0 || v >= vertices) {
    		throw new IndexOutOfBoundsException("getColor(): invalid vertex.");
    	}
        return color.get(v);
    }
    
    /**
     * Returns the List stored at index v
     * @param v a vertex in the graph
     * @return the adjacency List a v
     * @precondition 0 < v <= vertices
     * @throws IndexOutOfBoundsException  when 
     * the precondition is violated
     */
    public List<Integer> getAdjacencyList(Integer v) throws IndexOutOfBoundsException {
    	if(v <= 0 || v > vertices) {
    		throw new IndexOutOfBoundsException("getAdjacencyList(): invalid vertex.");
    	}
        return adj.get(v);
    }
    
    /*** Manipulation Procedures ***/
    
    /**
     * Inserts vertex v into the adjacency list of vertex u
     * (i.e. inserts v into the list at index u)
     * @precondition, 0 < u, v <= vertices
     * @throws IndexOutOfBounds exception when the precondition
     * is violated
     * @postcondition a sorted adjacency list
     */
    public void addDirectedEdge(Integer u, Integer v) throws IndexOutOfBoundsException {
    	if(u <= 0 || v <= 0 || u > vertices || v > vertices) {
    		throw new IndexOutOfBoundsException("invalid!");
    	}
    	adj.get(u).addLast(v);
    	edges++;
    }
    
    /**
     * Inserts vertex v into the adjacency list of vertex u
     * (i.e. inserts v into the list at index u)
     * and inserts u into the adjacent vertex list of v
     * @precondition, 0 < u, v <= vertices
     * @postcondition a sorted adjacency list
     */
    public void addUndirectedEdge(Integer u, Integer v) {
    	if(u <= 0 || v <= 0 || u > vertices || v > vertices) {
    		throw new IndexOutOfBoundsException("invalid!");
    	}
    	adj.get(u).addLast(v);
    	adj.get(v).addLast(u);
    	edges++;
    }
    
    /*** Additional Operations ***/
    
    /**
     * Creates a String representation of the Graph
     * Prints the adjacency list of each vertex in the graph,
     * vertex: <space separated list of adjacent vertices>
     */
    @Override public String toString() {
        String result = "";
        for (int i = 1; i <= vertices; i++) {
        	result += i + ": " + getAdjacencyList(i) + "\n"; 
        }
        return result;
    }
    
    
    
    /**
     * Prints the current values in the parallel ArrayLists
     * after executing BFS. First prints the heading:
     * v <tab> c <tab> p <tab> d
     * Then, prints out this information for each vertex in the graph
     * Note that this method is intended purely to help you debug your code
     */
    public void printBFS() {
        System.out.println("v\t" +"c\t" + "p\t" + "d");
        for (int i = 1; i <= vertices; i++) {
        	System.out.println(i + "\t" +color.get(i) + "\t" + 
        			parent.get(i) + "\t"  + distance.get(i));
        }
    }
    
    /**
     * Performs breath first search on this Graph give a source vertex
     * @param source
     * @precondition graph must not be empty
     * @precondition source is a vertex in the graph
     * @throws IllegalStateException if the graph is empty
     * @throws IndexOutOfBoundsException when vertex is
     * outside the bounds of the graph
     */

    public void BFS(Integer source) throws IllegalStateException, IndexOutOfBoundsException {
    	if(source < 0 || vertices <= source) {
    		throw new IndexOutOfBoundsException("BFS(): Invalid source.");
    	}
    	else if(isEmpty()) {
    		throw new IllegalStateException("BFS(): Graph is empty.");
    	}
    	else {
    		for(int i = 1; i <= vertices; i++) {
    			color.set(i, 'W');
    			parent.set(i, null);
    			distance.set(i, -1);
    		}
    		color.set(source, 'G');
    		distance.set(source, 0);
    		List<Integer> Que = new List<Integer>();
    		Que.addLast(source);
    		while(!Que.isEmpty()) {
    			int x = Que.getFirst();
    			Que.removeFirst();
    			adj.get(x).pointIterator();
    			
    			for(int y = 1; y <= adj.get(x).getLength(); y++) {
    				if(color.get(adj.get(x).getIterator()).equals('W')){
    					color.set(adj.get(x).getIterator(),'G');
    					distance.set(adj.get(x).getIterator(), distance.get(x) + 1);
    					parent.set(adj.get(x).getIterator(), x);
    					Que.addLast(adj.get(x).getIterator());
    				}
    				adj.get(x).advanceIterator();
    			}
    			color.set(x, 'B');		
    		}
    	}
    }
    
}