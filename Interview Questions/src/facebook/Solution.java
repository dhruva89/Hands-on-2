package facebook;

import java.util.Scanner;

public class Solution {

	public static class Node {
		String val = "";
		Node[] children;
		boolean terminate = true;

		public Node(String val) {
			super();
			this.val = val;
			this.children = new Node[56];
			this.terminate = true;
			;
		}

	}

	public static void main(String args[]) throws Exception {
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine();
		scan.close();
		constructSuffixTree(input);
	}

	private static void constructSuffixTree(String input) {
		Node root = new Node("*");
		for (int i = 0; i < input.length(); i++) {
			char cur = input.charAt(i);
			for(Node child : root.children) {
				while(child.terminate == false) {
					child = 
				}
				child.val = child.val + cur;
			}
			if(root.children[convert(cur)] == null) {
				root.children[convert(cur)] = new Node(""+cur);
			} else {
				Node newNode = new Node(root.children[convert(cur)].val.substring(1));
				root.children[convert(cur)].terminate = false;
				root.children[convert(cur)].val = root.children[convert(cur)].val.substring(0, 1);
			}
		}
	}

	private static int convert(char c) {
		if (Character.isUpperCase(c)) {
			return 25 + ((int) c - (int) 'A');
		} else {
			return ((int) c - (int) 'a');
		}
	}
}