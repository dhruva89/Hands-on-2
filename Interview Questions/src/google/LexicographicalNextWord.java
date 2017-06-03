package google;

import java.util.NavigableSet;
import java.util.Scanner;
import java.util.TreeSet;

public class LexicographicalNextWord {
	public static void main(String args[]) {
		NavigableSet<String> words = new TreeSet<String>();
		Scanner input = new Scanner(System.in);
		System.out.println("Ënter number of words: ");
		int numberOfWords = input.nextInt();
		for (int i = 0; i < numberOfWords; i++) {
			words.add(input.next());
		}

		System.out.println("Ënter input word: ");
		String word = input.next();

		String out = null;
		if (!words.contains(word)) {
			out = words.ceiling(word);
		} else {
			out = words.higher(word);
		}
		System.out.println(out == null ? "Error" : out);
	}
}
