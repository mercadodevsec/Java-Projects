import java.util.NoSuchElementException;
import java.util.Scanner;

public class LabProgram8 {
	public static void main(String[] args) {
		Scanner scnr = new Scanner(System.in);
		int val1;
		int val2;
		int val3;
		int max = 0;
		val1 = 0;
		val2 = 0;
		val3 = 0;
		int count = 0;
		try {
			val1 = scnr.nextInt();
			count++;
			max = val1;
			val2 = scnr.nextInt();
			if (val2 > val1) {
				max = val2;
			}
			count++;
			val3 = scnr.nextInt();
			if (val3 > val1 && val3 > val2) {
				max = val3;
			}
			count++;
			System.out.println(max);
		} catch (NoSuchElementException e) {
			if (count > 0) {
				System.out.println(count + " input(s) read:\nMax is " + max);
			} else {
				System.out.println(count + " input(s) read:\nNo max");
			}
		}
		scnr.close();
	}
}
