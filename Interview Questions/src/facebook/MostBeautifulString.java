package facebook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeMap;

import programCreek.IsSubsequence;

public class MostBeautifulString {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine();
		scan.close();

		StringBuilder beautifulString = new StringBuilder();
		int outLen;

		TreeMap<Character, ArrayList<Integer>> inputChars = new TreeMap<Character, ArrayList<Integer>>(
				Collections.reverseOrder());
		for (int i = 0; i < input.length(); i++) {
			char curChar = input.charAt(i);
			if (!inputChars.containsKey(curChar)) {
				inputChars.put(curChar, new ArrayList<Integer>());
			}
			inputChars.get(curChar).add(i);
		}

		outLen = inputChars.size();

		HashSet<Character> used = new HashSet<Character>();

		int pos1 = -1;
		boolean success = false;
		Character key1 = null;
		while (beautifulString.length() != outLen) {
			if (success || key1 == null) {
				key1 = inputChars.firstKey();
			} else {
				key1 = inputChars.higherKey(key1);
			}
			while (key1 != null && used.contains(key1)) {
				key1 = inputChars.higherKey(key1);
			}

			if (key1 == null) {
				continue;
			}

			boolean foundSuitablePos = false;
			int pos3 = pos1;

			LoopForPosition: do {
				for (int pos : inputChars.get(key1)) {
					if (pos > pos3) {
						foundSuitablePos = true;
						pos3 = pos;
						break LoopForPosition;
					}
				}
				key1 = inputChars.higherKey(key1);
			} while (foundSuitablePos == false && key1 != null);

			success = true;
			Character key2 = key1;
			while (((key2 = inputChars.higherKey(key2)) != null)) {
				if (used.contains(key2)) {
					continue;
				}
				boolean foundHigher = false;
				for (int pos2 : inputChars.get(key2)) {
					if (pos2 > pos3) {
						foundHigher = true;
						break;
					}
				}
				if (foundHigher == false) {
					success = false;
					break;
				}
			}
			if (success) {
				beautifulString.append(key1);
				used.add(key1);
				pos1 = pos3;
			}

		}
		System.out.println(beautifulString);
		System.out.println(IsSubsequence.isSubsequence(beautifulString.toString(), input));
		
	}
}
