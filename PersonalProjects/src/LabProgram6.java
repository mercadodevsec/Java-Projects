import java.util.Scanner;

public class LabProgram6 {
	public static void main(String[] args) {
		Scanner scnr = new Scanner(System.in);
		String[] names = { "Ryley", "Edan", "Reagan", "Henry", "Caius", "Jane", "Guto", "Sonya", "Tyrese", "Johnny" };
		int index;

		index = scnr.nextInt();
		try {
			System.out.println("Name: " + names[index]);
		} catch (ArrayIndexOutOfBoundsException e) {
			if (index >= names.length) {
				System.out
						.println("Exception! " + e.getMessage() + "\nThe closest name is: " + names[names.length - 1]);
			} else {
				System.out.println("Exception! " + e.getMessage() + "\nThe closest name is: " + names[0]);
			}
		}
		scnr.close();
	}
}
