/*
 * Student Name: John Micah Mercado
 * Student ID Number: N01611717
 * Submission Date: 19 February 2025
 */

import java.util.ArrayList;
import java.util.Scanner;

public class mercado_project1 {

	/**
	 * Option 1. This method counts the number of food items with the word candy and
	 * then returns it.
	 * 
	 * @param list is an array of words containing food items that was input by the
	 *             user.
	 * @return the int count, the number of food items that contains the word candy.
	 */
	public static int countCandy(String[] list) {
		int count = 0; // initialize the count to track candy-related items
		for (int i = 0; i < list.length; i++) {
			// checks if the word contains candy (case-insensitive)
			if (list[i].toLowerCase().contains("candy")) {
				count++; // increments count if "candy" is found
			}
		} // end of for loop
		return count; // return the total count of candy-related items
	}

	/**
	 * Option 2. This method gets all the food items that only has 1 word in it and
	 * then returns it.
	 *
	 * @param list is an array of words containing food items that was input by the
	 *             user.
	 * @return the ArrayList foodItems, the list of food items that only has 1 word
	 *         in it.
	 */
	public static ArrayList<String> getOneWordFoodItems(String[] list) {
		ArrayList<String> foodItems = new ArrayList<String>(); // Create a new ArrayList to store the food items
		// checks if the word has no space in between
		for (int i = 0; i < list.length; i++) {
			if (!list[i].strip().contains(" ")) {
				foodItems.add(list[i].strip()); // add the single-word food item to the list
			}
		} // end of for
		return foodItems; // return the list of food items with only one word
	}

	/**
	 * Option 3. This method gets all the food items that has the 2 or more words in
	 * it and then returns it.
	 *
	 * @param list is an array of words containing food items that was input by the
	 *             user.
	 * @return the ArrayList foodItems, the list of food items that only has 2 or
	 *         more words in it.
	 */
	public static ArrayList<String> getMultiWordFoodItems(String[] list) {
		ArrayList<String> foodItems = new ArrayList<String>(); // create a new list to store food items
		for (int i = 0; i < list.length; i++) {
			// checks if the word contains a space, meaning it has more than 1 word
			if (list[i].strip().contains(" ")) {
				foodItems.add(list[i].strip()); // add the multi-word food item to the list
			}
		} // end of for
		return foodItems; // return the list of multi-word food items
	}

	/**
	 * Option 4. This method gets all the food items that only appear once and then
	 * returns it.
	 *
	 * @param list is an array of words containing food items that was input by the
	 *             user.
	 * @return the ArrayList foodItems, the list of food items that appeared only
	 *         once.
	 */
	public static ArrayList<String> getUniqueFoodItems(String[] list) {
		ArrayList<String> foodItems = new ArrayList<String>(); // create a list to store unique food items
		boolean isUnique = true; // flag to track if the food item is unique
		for (int i = 0; i < list.length; i++) {
			isUnique = true; // reset the flag for each food item
			for (int j = 0; j < list.length; j++) {
				// checks if the food item is the same as any other item in the list
				if (i != j) {
					if (list[i].strip().equalsIgnoreCase(list[j].strip())) {
						isUnique = false; // set flag to false if a duplicate is found (case-insensitive)
					}
				}
			} // end of inner for loop
			if (isUnique) {
				foodItems.add(list[i].strip().toLowerCase()); // add the unique item to the list
			}
		} // end of outer for loop
		return foodItems; // return the list of unique food items
	}

	/**
	 * Option 5. This method gets all the food items that appear more than once and
	 * then returns it.
	 *
	 * @param list is an array of words containing food items that was input by the
	 *             user.
	 * @return the ArrayList foodItems, the list of food items that appeared more
	 *         than once.
	 */
	public static ArrayList<String> getNonUniqueFoodItems(String[] list) {
		ArrayList<String> foodItems = new ArrayList<String>(); // create a list to store non-unique food items
		boolean isUnique = true; // flag to track if the food item is non-unique
		for (int i = 0; i < list.length; i++) {
			isUnique = true; // reset the flag for each food item
			for (int j = 0; j < list.length; j++) {
				// checks if the food item is the same as any other item in the list
				if (i != j) {
					if (list[i].strip().equalsIgnoreCase(list[j].strip())) {
						isUnique = false; // set flag to false if a duplicate is found (case-insensitive)
					}
				}
			} // end of inner for loop
			if (!isUnique) {
				foodItems.add(list[i].strip().toLowerCase()); // add the non-unique item to the list
			}
		} // end of outer for loop

		// removes duplicates in the non-unique list
		for (int i = 0; i < foodItems.size(); i++) {
			if (!isUnique) { // reset the flag for each item
				isUnique = true;
				i = 0; // reset loop if flag is not unique
			}
			for (int j = 0; j < foodItems.size(); j++) {
				// checks if the food item is the same as any other in the list
				if (i != j) {
					if (foodItems.get(i).equals(foodItems.get(j))) {
						isUnique = false; // set flag to false if a duplicate is found
					}
				}
			} // end of inner for loop
			if (!isUnique) {
				foodItems.remove(i); // remove the duplicate item from the list
			}
		} // end of second for loop
		return foodItems; // return the list of non-unique food items
	}

	/**
	 * Option 6. This method gets the food item that appears the most frequent and
	 * then returns it. In the event of a tie, food item that first appears latest
	 * in the list gets return.
	 *
	 * @param list is an array of words containing food items that was input by the
	 *             user.
	 * @return the string frequentFoodItem, the most frequent food item.
	 */
	public static String getMostFrequentFoodItem(String[] list) {
		String frequentFoodItem = ""; // stores the most frequent food item
		int mostCount = 0; // tracks the highest frequency count
		int count = 0; // tracks the frequency of each food item
		for (int i = 0; i < list.length; i++) {
			count = 0; // reset the count for each food item
			for (int j = 0; j < list.length; j++) {
				// checks if the current food item matches another item
				if (i != j) {
					if (list[i].strip().equalsIgnoreCase(list[j].strip())) {
						count++; // increment count if a match is found (case-insensitive)
					}
				}
			} // end of inner for loop
				// updates the most frequent food item if current count is higher
			if (count > mostCount) {
				mostCount = count;
				frequentFoodItem = list[i].strip().toLowerCase();
			} else if (count == mostCount) {
				frequentFoodItem = list[i].strip().toLowerCase(); // update in case of tie
			}
		} // end of outer for loop
		return frequentFoodItem; // return the most frequent food item
	}

	/**
	 * Option 7. This method gets the food item that appears the least frequent and
	 * then returns it. In the event of a tie, food item that appears first in the
	 * list gets return.
	 *
	 * @param list is an array of words containing food items that was input by the
	 *             user.
	 * @return the string frequentFoodItem, the least frequent food item.
	 */
	public static String getLeastFrequentFoodItem(String[] list) {
		String frequentFoodItem = ""; // stores the least frequent food item
		int mostDiffCount = 0; // tracks the highest count of differences
		int count = 0; // tracks the frequency of each food item compared to others
		for (int i = 0; i < list.length; i++) {
			count = 0; // reset the count for each food item
			for (int j = 0; j < list.length; j++) {
				// compares the current food item with every other item in the list
				if (i != j) {
					if (!list[i].strip().equalsIgnoreCase(list[j].strip())) {
						count++; // increment count if the items are different (case-insensitive)
					}
				}
			} // end of inner for loop
				// updates the least frequent food item if the current count is higher
			if (count > mostDiffCount) {
				mostDiffCount = count;
				frequentFoodItem = list[i].strip().toLowerCase();
			}
		} // end of outer for loop
		return frequentFoodItem; // return the least frequent food item
	}

	/**
	 * Option 8. This method counts the number of food items that contains a mustard
	 * and then returns it while also updating the list by putting all the food
	 * items with mustard in the front of the list.
	 *
	 * @param list is an array of words containing food items that was input by the
	 *             user.
	 * @return the int count, the number of food items that contains a mustard.
	 */
	public static int countMustardsInList(String[] list) {
		int count = 0; // initialize count to track the mustard items
		for (int i = 0; i < list.length; i++) {
			// checks if the food item contains mustard (case-insensitive)
			if (list[i].toLowerCase().contains("mustard")) {
				count++; // increment count if mustard is found
			}
		} // end of first for loop

		String[] foodItems = new String[count]; // create a new array to store mustard-related items
		for (int i = 0, j = 0; i < list.length; i++) {
			// checks if the food item contains mustard
			if (list[i].toLowerCase().contains("mustard")) {
				foodItems[j] = list[i].toLowerCase().strip(); // add mustard item to new array
				j++; // increment index for the mustard items array
			}
		} // end of second for loop

		// update the original list by moving the mustard items to the front
		for (int i = 0; i < foodItems.length; i++) {
			list[i] = foodItems[i]; // replace the original list items with mustard items
		} // end of third for loop

		return count; // return the total count of mustard-related items
	}

	/**
	 * Option 9. This method gets the new list from the user input.
	 *
	 * @param scn is the scanner for user input.
	 * @return the new list.
	 */
	public static String[] getNewList(Scanner scn) {
		System.out.print("Enter new list: "); // prompt user for input
		String input = scn.nextLine(); // read user input
		String[] foodItems = input.split(","); // split the input into an array
		return foodItems; // return the new list
	}

	/**
	 * Prints the menu.
	 */
	public static void printMenu() {
		System.out.println(); // print a new line for formatting
		System.out.println("What do you want to do?");
		System.out.println("1 = Count Candy");
		System.out.println("2 = Get One Word Food Items");
		System.out.println("3 = Get Multi-Word Food Items");
		System.out.println("4 = Get Unique Food Items");
		System.out.println("5 = Get Non-Unique Food Items");
		System.out.println("6 = Get Most Frequent Food Item");
		System.out.println("7 = Get Least Frequent Food Item");
		System.out.println("8 = Count Mustards");
		System.out.println("9 = Enter New List");
		System.out.println("0 = Quit");
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		String[] foodItems = getNewList(scn); // get the new list from user input
		printMenu(); // display the menu
		int menuChoice = scn.nextInt();
		while (menuChoice != 0) { // loop until user chooses to exit
			switch (menuChoice) {
			case 1:
				System.out.println("Number of Candy: " + countCandy(foodItems)); // display count of candy items
				printMenu();
				menuChoice = scn.nextInt();
				break;
			case 2:
				for (String foodItem : getOneWordFoodItems(foodItems)) { // display one-word food items
					System.out.println(foodItem);
				}
				printMenu();
				menuChoice = scn.nextInt();
				break;
			case 3:
				for (String foodItem : getMultiWordFoodItems(foodItems)) { // display multi-word food items
					System.out.println(foodItem);
				}
				printMenu();
				menuChoice = scn.nextInt();
				break;
			case 4:
				for (String foodItem : getUniqueFoodItems(foodItems)) { // display unique food items
					System.out.println(foodItem);
				}
				printMenu();
				menuChoice = scn.nextInt();
				break;
			case 5:
				for (String foodItem : getNonUniqueFoodItems(foodItems)) { // display non-unique food items
					System.out.println(foodItem);
				}
				printMenu();
				menuChoice = scn.nextInt();
				break;
			case 6:
				System.out.println(getMostFrequentFoodItem(foodItems)); // display the most frequent food item
				printMenu();
				menuChoice = scn.nextInt();
				break;
			case 7:
				System.out.println(getLeastFrequentFoodItem(foodItems)); // display the least frequent food item
				printMenu();
				menuChoice = scn.nextInt();
				break;
			case 8:
				String[] temp = foodItems.clone(); // store a temporary array for later use
				int count = countMustardsInList(foodItems); // display food items with mustard
				for (int i = 0; i < count; i++) {
					System.out.println(foodItems[i]);
				}
				foodItems = temp; // revert back the list to its original list
				printMenu();
				menuChoice = scn.nextInt();
				break;
			case 9:
				scn = new Scanner(System.in); // reset the scanner for new input
				foodItems = getNewList(scn); // get the new list from user input
				printMenu();
				menuChoice = scn.nextInt();
				break;
			default:
				System.out.println("Wrong input. Pick again"); // handle invalid input
				menuChoice = scn.nextInt();
				break;
			}
		}
		System.out.println("Goodbye!"); // exit message
		scn.close(); // close the scanner
	}

}