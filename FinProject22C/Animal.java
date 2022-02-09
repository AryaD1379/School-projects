/**
 * Animal.java
 * @author Aryan Dadnam
 * Group Project 22C
 */
public class Animal{
	private String name;	//unique key, Name of animal (ex:Turtle)
	private Long population; //secondary key,# of population in wild
	private int amount;		//# in zoo
	private String color;   //primary color of animal
	private String family; //Type of animal in animal family;
	
	//Constructor
	
	public Animal(String name, Long population,
		int amount, String color, String family) {
		this.name = name;
		this.population = population;
		this.amount = amount;
		this.color = color;
		this.family = family;
	}
	
	//Getters
	
	public String getName() {
		return name;
	}
	public Long getPopulate() {
		return population;
	}
	public int getAmount() {
		return amount;
	}
	public String getColor() {
		return color;
	}
	public String getfamily() {
		return family;
	}
	
	//Setters
	
	public void setName(String name) {
		this.name = name;
	}
	public void setPopulate(Long population) {
		this.population = population;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public void setfamily(String family) {
		this.family = family;
	}
	
	//Other operations
	@Override public String toString() {
		String result = "Name: "+ name		
			+"\nPopulation in Wild: "+ population
			+"\nAmount in Zoo: "+ amount
			+"\nPrimary Color: "+ color
			+"\nType of species in Animal family: "+ family;
		return result;
	}
	 @Override public int hashCode() {
	    	String key = name;
	    	int sum = 0;
	    	for(int i = 0; i < key.length(); i++) {
	    		sum += (int) key.charAt(i);
	    	}
	        return sum;
	    }
}