import java.util.Scanner;

public class ExamReview1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);

		String line = in.nextLine();

		printReverseChars(line);
		printReverseWords(line);
		printWordStats(line);
		printNumberOfWordsWithVowels(line);

		in.close();
	}

	public static void printReverseChars(String line) {

		for (int i = line.length() - 1; i >= 0; i--) {

			if (line.charAt(i) != ' ') {
				System.out.print(line.charAt(i));
			}
		}
		System.out.println();

	}

	public static void printReverseWords(String line) {

		String[] wordsArray = line.split(" ");

		for (int i = wordsArray.length - 1; i >= 0; i--) {
			if (i == 0) {
				System.out.print(wordsArray[i]);
			} else {
				System.out.print(wordsArray[i] + " ");
			}
		}
		System.out.println();
	}

	public static void printWordStats(String line) {

		int odd = 0;
		int even = 0;
		String[] wordsArray = line.split(" ");
		for (int i = 0; i < wordsArray.length; i++) {
			if (i % 2 == 0) {
				even++;
			} else {
				odd++;
			}
		}
		System.out.println("Even: " + even + " Odd: " + odd);
	}

	public static void printNumberOfWordsWithVowels(String line) {
		int count = 0;
		for (int i = 0; i < line.length(); i++) {
			if ("AEIOUaeiou".indexOf(line.charAt(i)) != -1) {
				count++;
			}

		}
		System.out.println("Number of vowels: " + count);
		int index = "CANDYcandy".indexOf("candy");
		System.out.println(index);
		index = "CANDYcandy".indexOf("CANDY");
		System.out.println(index);
	}
}
