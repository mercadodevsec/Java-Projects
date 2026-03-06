import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class Sentences {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner console = new Scanner(System.in);
		System.out.print("Input file: ");
		String inputFileName = console.nextLine();
		Scanner in = new Scanner(new File(inputFileName));
		console.close();
		PrintStream out = new PrintStream("sentences.txt");

		while (in.hasNext()) {
			String word = in.next();
			char last = word.charAt(word.length() - 1);
			if (last == '.' || last == '?' || last == '!') {
				out.printf(word + "\n");
			} else {
				out.printf(word + " ");
			}
		}

		in.close();
		out.close();
	}
}