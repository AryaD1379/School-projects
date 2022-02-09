/**
 * SFZoo.java
 * @author Aryan Dadnam
 * Group Project 22C
 */
import java.io.*;
import java.util.*;
import java.util.Scanner;

public class SFZoo {
	public SFZoo() {};
	public static void main (String [] args) {
		SFZoo zoo = new SFZoo();
		Hash hashSort = new Hash(50);
		BST alphaSort = new BST();
		BST popSort = new BST();
		Scanner input = new Scanner(System.in);
		boolean fileOK = false;
		String filename;
		
		System.out.println("Welcome to San Francisco Zoo!\n");
		System.out.print("Enter the name of a compatible file containing Zoo animal data!: ");
		do {
			filename = input.nextLine();
			fileOK = zoo.checkFile(filename);
			if(!fileOK) 
				System.out.print("Please enter a valid file name: ");
		} while (!fileOK);
		
		zoo.createDatabases(hashSort, alphaSort, popSort, filename);
		
		// Scanner input = new Scanner(System.in);
		// Hash hashSort = h1;
		// BST alphaSort = a1;
		// BST popSort = p1;
		 Long population = 0L;
		 Integer amount = 0;
		 String choice = "", name = "", color = "", family = "";
		 String prompt = "\n\nPlease select from one of the following options:\n"
					+ "\nA. Add an animal" 
					+ "\nR. Remove an animal" 
					+ "\nS. Search for an animal" 
					+ "\nD. Display all animals" 
					+ "\nP. Print animals to text file"
					+ "\nX. Exit\n";
		 
		 while(!choice.equalsIgnoreCase("X")) {
			 System.out.print(prompt + "\n\nEnter your choice: ");
			 choice = input.nextLine();
			
		 	 if(choice.equalsIgnoreCase("D")) {
				 System.out.print("\nDisplaying all animals!");
				 System.out.print("\nHow would you like all your data displayed?");
					 System.out.print(
							 "\n\na. Display all animals"
							 + "\nb. Display by Name"
							 + "\nc. Display by Population"
							 + "\n\nEnter your choice: ");
					 choice = input.nextLine();
					
					 System.out.print("\n\n");
					 if(choice.equalsIgnoreCase("a")) {
						 System.out.print("Displaying unsorted data:\n\n");
						 hashSort.printTable();
					 } else if (choice.equalsIgnoreCase("b")) {
						 System.out.print("Displaying data sorted alphabetically by name:\n\n");
						 alphaSort.inOrderPrint();
					 } else if (choice.equalsIgnoreCase("c")){
						 System.out.print("Displaying data sorted by population size:\n\n");
						 popSort.inOrderPrint();
					 } else {
						 System.out.print("Invalid input. Displaying data sorted alphabetically by name:\n\n");
						 alphaSort.inOrderPrint();
					 }
				
			} else if (choice.equalsIgnoreCase("S")) {
					System.out.print("\nSearch for an animal!");
					System.out.print("\nHow would you like to search for data?");
					System.out.print(
						   "\n\na. Search by name"
							+"\nb. Search by population");
					System.out.print("\n\nEnter your choice: ");
					choice = input.nextLine();
					
					if(choice.equalsIgnoreCase("a")) {
						System.out.print("\nEnter name: ");
						name = input.nextLine().toUpperCase();
						Animal tempAnimal = new Animal(name, 0L, 0, "", "");
						System.out.print("\n" + alphaSort.searchN(tempAnimal));
					} else if(choice.equalsIgnoreCase("b")) {
						try {
							System.out.print("\nEnter global population as a number (no punctuation please!): ");
							population = Long.parseLong(input.nextLine());
							Animal tempAnimal2 = new Animal("", population, 0, "", "");
							System.out.print("\n" + popSort.searchP(tempAnimal2));
						} catch (NumberFormatException e) {
							System.out.println(e.toString());
							System.out.println("\nInvalid choice. Please try again.");
						}
					} else {
						System.out.println("\nInvalid choice. Please try again.");
					}
					
			} else if(choice.equalsIgnoreCase("A")) {
					boolean intMatch = false, longMatch = false;
					System.out.print("\nAdding an animal!");
					System.out.print("\nEnter the animal's name: ");
					name = input.nextLine().toUpperCase();
					
					while(!longMatch) {
						try {
							System.out.print("Enter the global population as a number(no punctuation please!): ");
							population = Long.parseLong(input.nextLine());
							longMatch = true;
						} catch (NumberFormatException e){
							System.out.println("Input error. Please try again!");
						}
					}
					while (!intMatch) {
						try {
							System.out.print("Enter the current # of this animal in Zoo: ");
							amount = Integer.parseInt(input.nextLine());
							intMatch = true;
						} catch (InputMismatchException e) {
							System.out.println("Input error. Please try again!");
						}
					}
					
					System.out.print("Enter the color(s): ");
					color = input.nextLine().toUpperCase();
					System.out.print("Enter the family classification: ");
					family = input.nextLine().toUpperCase();
				
					Animal tempAnimal = new Animal(name, population, amount, color, family);
					hashSort.insert(tempAnimal);
					alphaSort.insertN(tempAnimal);
					popSort.insertP(tempAnimal);
					System.out.print("\n" + name.toUpperCase() + " has been added!");
					
			} else if(choice.equalsIgnoreCase("R")) {
					boolean longMatch = false;
					System.out.print("\nRemoving an animal!");
					System.out.print("\nEnter the name of the animal: ");
					name = input.nextLine().toUpperCase();
					System.out.print("Enter the global population of the animal as a whole number (no punctuation please!): ");
					while(!longMatch) {
						try {
						population = Long.parseLong(input.nextLine());
						longMatch = true;
						} catch (NumberFormatException e) {
							System.out.println("Input error. Please enter a whole number! (no punctuation please!)");
						}
					}
					System.out.print("\nRemoving " + name + "...");
					Animal tempAnimal = new Animal(name, population, amount, color, family);
					try {
						hashSort.remove(tempAnimal);
						alphaSort.removeN(tempAnimal);
						popSort.removeP(tempAnimal);
						System.out.print("\n" + name + " has been removed.");
					} catch(NoSuchElementException e) {
						System.out.print("\n\nCouldn't find animal: " + name + " in database!");
					}
					
			} else if(choice.equalsIgnoreCase("P")) {
				String filename2 = "Zoo2.txt";
				System.out.println("Printing the animals inside Zoo to file: " + filename2 + "!");
				
				System.out.print("\nHow would you like this file sorted?\n");
				System.out.println("a. Print sorted by name:");
				System.out.println("b. Print sorted by population:\n");
				System.out.print("Enter your choice: ");
				choice = input.nextLine();
				
				if(choice.equalsIgnoreCase("a")) {	
					try {
					PrintWriter write = new PrintWriter(filename2);
					alphaSort.printFolder(write);
					write.close();
					} catch (IOException e) {
						System.out.print("Error. Unable to print to file: " + filename2);
					}
					System.out.print("Successfully printed data to: " + filename2);
				}
				else if(choice.equalsIgnoreCase("b")) {
					try {
					PrintWriter write = new PrintWriter(filename2);
					popSort.printFolder(write);
					write.close();
					} catch (IOException e) {
						System.out.print("Error. Unable to print to file: " + filename2);
					}
					System.out.print("Successfully printed data to: " + filename2);
				}
				else {
					System.out.println("\nInvalid choice. Please try again.");
				}
				
		   } else if(choice.equalsIgnoreCase("X")) {
					System.out.print("\nGoodbye!\nThank You for visiting the San Francisco Zoo! ");
					
		   } else {
				System.out.print("\nInvalid choice! Please choose from given options.");
		   }	
				
		}//end of while
			input.close();
	}
	
	public boolean checkFile(String name) {
		File fileTest = new File(name);
		return fileTest.exists();
	}
	
	public void createDatabases(Hash h, BST a, BST p, String filename) {
		File file = null;
		Scanner input = null;
		try {
			file = new File(filename);
			input = new Scanner(file);
			while (input.hasNextLine()) {
				String name = input.nextLine();
				Long population = Long.parseLong(input.nextLine());
				Integer amount = Integer.parseInt(input.nextLine());
				String color = input.nextLine();
				String family = input.nextLine();
				Animal animal = new Animal(name, population, amount, color, family);
				if(input.hasNextLine()) {
					input.nextLine();
				}
				h.insert(animal);
				a.insertN(animal);	//CHANGE TO "INSERTN"
				p.insertP(animal);	//CHANGE TO "INSERTP"	
			}
		} catch(IOException e) {
			System.out.println (e.toString());
		}
	}
}