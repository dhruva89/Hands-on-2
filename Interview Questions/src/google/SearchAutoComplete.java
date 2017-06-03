package google;

import java.util.LinkedList;
import java.util.List;

public class SearchAutoComplete {

	private class TrieNode {
		char val;
		TrieNode[] nextChars;
		boolean isEndOfWord;
		int frequency;

		public TrieNode(char val, boolean isEndOfWord) {
			this.val = val;
			this.isEndOfWord = isEndOfWord;
		}
	}

	private class WordFrequency {
		String word;
		int frequency;
	}

	TrieNode root;

	public SearchAutoComplete() {
		root = new TrieNode('&', false);
	}

	public static void main(String args[]) {

	}

	public List<WordFrequency> search(String prefix) {
		TrieNode cur = root;
		int i = 0;
		if (prefix.length() == 0) {
			return new LinkedList<WordFrequency>();
		}
		while (i < prefix.length() - 1 && cur.nextChars != null) {
			if (cur.nextChars[prefix.charAt(i) - 'a'] == null) {
				return new LinkedList<WordFrequency>();
			}
			i++;
			cur = cur.nextChars[prefix.charAt(i) - 'a'];
		}

		List<WordFrequency> results = new LinkedList<WordFrequency>();
		dfs(cur,results, new WordFrequency());

		for (WordFrequency wordFreq : results) {
			wordFreq.word = prefix + wordFreq.word;
		}
		return results;
	}

	private void dfs(TrieNode cur, List<WordFrequency> results, WordFrequency wordFreq) {
		wordFreq.word += cur.val;
		if(cur.isEndOfWord) {
			wordFreq.frequency = cur.frequency;
			results.add(wordFreq);
			return;
		}
		for(TrieNode neighbor: cur.nextChars) {
			dfs(cur,results,wordFreq);
		}
	}
}
