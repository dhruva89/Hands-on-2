package google;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;

public class SmallestWindowContainingAllWordsInQuery {
	public static class Range implements Comparable<Range> {
		int start;
		int end;
		Integer size;

		public Range(int start, int end) {
			super();
			this.start = start;
			this.end = end;
			size = end - start + 1;
		}

		@Override
		public String toString() {
			return start + " " + end;
		}

		@Override
		public int compareTo(Range arg0) {
			return this.size.compareTo(arg0.size);
		}
	}

	public static void main(String args[]) {
		HashSet<String> query = new HashSet<String>();
		String input = "This is a test string. Insert word. Insert word file. Insert String. Insert works. I hope this test works well instead of an actual file. I'm not really sure if this would make a good test";
		query.add("test");
		query.add("word");
		query.add("works");
		query.add("file");

		System.out.println(findSmallestWindow(input, query));
	}

	private static Range findSmallestWindow(String input, HashSet<String> query) {
		HashMap<String, ArrayList<Integer>> queryStringOccurences = new HashMap<String, ArrayList<Integer>>();
		Iterator<String> it = query.iterator();
		while (it.hasNext()) {
			queryStringOccurences.put(it.next().toLowerCase(), new ArrayList<Integer>());
		}

		String word = "";
		int wordCount = 0;
		for (int i = 0; i < input.length(); i++) {
			if (word != "" && (input.charAt(i) == ' ' || input.charAt(i) == ',' || input.charAt(i) == '.')) {
				wordCount++;
				if (queryStringOccurences.containsKey(word.toLowerCase())) {
					queryStringOccurences.get(word.toLowerCase()).add(wordCount);
				}
				word = "";
			} else if (Character.isAlphabetic(input.charAt(i))) {
				word += input.charAt(i);
			}
		}

		wordCount++;
		if (queryStringOccurences.containsKey(word.toLowerCase())) {
			queryStringOccurences.get(word.toLowerCase()).add(wordCount);
		}

		int size = Integer.MAX_VALUE;
		String minOccurWord = null;
		boolean noOccurence = false;
		for (String cur : queryStringOccurences.keySet()) {
			if (queryStringOccurences.get(cur).isEmpty()) {
				noOccurence = true;
				break;
			}
			if (size > queryStringOccurences.get(cur).size()) {
				size = queryStringOccurences.get(cur).size();
				minOccurWord = cur;
			}
		}

		if (noOccurence) {
			return null;
		}

		PriorityQueue<Range> finalRanges = new PriorityQueue<Range>();
		for (int occurence1 : queryStringOccurences.get(minOccurWord)) {
			PriorityQueue<Range> ranges = new PriorityQueue<Range>();
			ranges.add(new Range(occurence1, occurence1));
			for (String cur : queryStringOccurences.keySet()) {
				if (cur.equals(minOccurWord)) {
					continue;
				}
				PriorityQueue<Range> newRanges = new PriorityQueue<Range>();
				for (Range range : ranges) {
					int left = -1;
					int right = Integer.MAX_VALUE;
					boolean foundInBetween = false;
					for (int occurence2 : queryStringOccurences.get(cur)) {
						if (range.start < occurence2 && range.end > occurence2) {
							foundInBetween = true;
							break;
						}
						if (range.start - occurence2 > 0 && (range.start - occurence2) < (range.start - left)) {
							left = occurence2;
						}

						if (occurence2 - range.end > 0 && (occurence2 - range.end) < (right - range.end)) {
							right = occurence2;
						}
					}
					if (!foundInBetween) {
						if (left != -1) {
							newRanges.add(new Range(left, range.end));
						}

						if (right != Integer.MAX_VALUE) {
							newRanges.add(new Range(range.start, right));
						}
					} else {
						newRanges.add(new Range(range.start, range.end));
					}
				}
				ranges = newRanges;
			}
			finalRanges.add(ranges.peek());
		}
		return finalRanges.peek();
	}
}