package google;

import java.util.HashMap;

public class LongestSubstringWithMDisctinctCharacters {
	public static void main(String args[]) {
		String input = "aababaabababccccccddddddddddddddddddd";
		int m = 2;

		System.out.println(findSubstring(input, m));
	}

	private static int findSubstring(String input, int m) {
		if (input.length() == 0 || m == 0) {
			return 0;
		}
		int maxLength = 0;
		int end = 0;
		int curLength = 0;
		HashMap<Character, Integer> charLastOccurence = new HashMap<Character, Integer>();
		while (end != input.length()) {
			char curChar = input.charAt(end);
			if (charLastOccurence.containsKey(curChar)) {
				curLength++;
			} else {
				if (charLastOccurence.keySet().size() < m) {
					curLength++;
				} else {
					int earliestCharOccurence = Integer.MAX_VALUE;
					char earliestChar = '-';
					for (char cur : charLastOccurence.keySet()) {
						if (charLastOccurence.get(cur) < earliestCharOccurence) {
							earliestCharOccurence = charLastOccurence.get(cur);
							earliestChar = cur;
						}
					}
					if (earliestChar != '-') {
						curLength = end - earliestCharOccurence;
						charLastOccurence.remove(earliestChar);
					}
				}
			}
			maxLength = Math.max(maxLength, curLength);
			charLastOccurence.put(curChar, end);
			end++;
		}
		return maxLength;
	}
}
