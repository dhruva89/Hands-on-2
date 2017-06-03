package google;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class FormStringInDictionaryByRemovingMinChars {

	public static void main(String args[]) {
		HashSet<String> dictionary = new HashSet<String>();
		dictionary.add("Word");
		dictionary.add("Bee");
		dictionary.add("Cat");
		dictionary.add("Dog");
		dictionary.add("Elephant");
		dictionary.add("Father");
		dictionary.add("Goat");
		dictionary.add("High");
		dictionary.add("Ice");
		dictionary.add("Joker");
		dictionary.add("Kite");

		String input = "Applebee";

		System.out.println(findString(input, dictionary));

	}

	private static String findString(String input, HashSet<String> dictionary) {
		HashMap<Character, ArrayList<Integer>> charOccurences = new HashMap<Character, ArrayList<Integer>>();
		for (int i = 0; i < input.length(); i++) {
			Character curChar = Character.toLowerCase(input.charAt(i));
			if (!charOccurences.containsKey(curChar)) {
				charOccurences.put(curChar, new ArrayList<Integer>());
			}
			charOccurences.get(curChar).add(i);
		}

		String result = null;
		wordLoop: for (String wordInDict : dictionary) {
			int prevIndex = -1;
			for (int i = 0; i < wordInDict.length(); i++) {
				Character curCharOfWordInDict = Character.toLowerCase(wordInDict.charAt(i));
				int occurence;
				if (charOccurences.containsKey(curCharOfWordInDict)
						&& (occurence = search(prevIndex + 1, charOccurences.get(curCharOfWordInDict))) != -1) {
					prevIndex = occurence;
				} else {
					continue wordLoop;
				}
			}
			if (result == null || result.length() < wordInDict.length()) {
				result = wordInDict;
			}
		}
		return result;
	}

	private static int search(int nextIndex, ArrayList<Integer> occurences) {
		int start = 0;
		int end = occurences.size() - 1;
		int higherVal = -1;

		while (start <= end) {
			int mid = (start + end) / 2;
			int midVal = occurences.get(mid);
			if (midVal == nextIndex) {
				return midVal;
			} else if (midVal < nextIndex) {
				start = mid + 1;
			} else {
				higherVal = midVal;
				end = mid - 1;
			}
		}
		return higherVal;
	}
}
