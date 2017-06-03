package google;

import java.util.ArrayList;

import concepts.BST;
import concepts.BSTNode;

public class PrintPathToLeaves {

	public static void main(String args[]) {
		BST<Integer> bst = new BST<Integer>();
		bst.add(15);
		bst.add(7);
		bst.add(2);
		bst.add(8);
		bst.add(1);
		bst.add(5);
		bst.add(3);
		bst.add(19);
		bst.add(16);
		bst.add(20);

		printPaths(bst);
	}

	private static void printPaths(BST<Integer> bst) {
		if (bst.root != null) {
			ArrayList<StringBuilder> list = new ArrayList<StringBuilder>();
			list.add(new StringBuilder().append(bst.root.data));
			recursePrint(bst.root, 0, 0, list);
		}

	}

	private static void recursePrint(BSTNode<Integer> node, int prevSpaces, int maxSpaces,
			ArrayList<StringBuilder> arrayList) {
		if (node.left == null && node.right == null) {
			for (StringBuilder sb : arrayList) {
				System.out.println(sb.toString());
			}
			System.out.println();
		}

		if (node.left != null) {
			ArrayList<StringBuilder> secondary = deepCopy(arrayList);
			if (prevSpaces - 1 < 0) {
				for (StringBuilder stringBuilder : secondary) {
					stringBuilder.insert(0, "_");
				}
				secondary.add(new StringBuilder().append(node.left.data));
				recursePrint(node.left, 0, maxSpaces + 1, secondary);
			} else {
				StringBuilder spaces = new StringBuilder();
				for (int i = 0; i < prevSpaces - 1; i++) {
					spaces.append("_");
				}
				secondary.add(spaces.append(node.left.data));
				recursePrint(node.left, prevSpaces - 1, maxSpaces, secondary);
			}

		}

		if (node.right != null) {
			StringBuilder spaces = new StringBuilder();
			for (int i = 0; i < prevSpaces + 1; i++) {
				spaces.append("_");
			}
			ArrayList<StringBuilder> tertiary = deepCopy(arrayList);
			tertiary.add(spaces.append(node.right.data));
			recursePrint(node.right, prevSpaces + 1, Math.max(prevSpaces + 1, maxSpaces), tertiary);
		}
	}
	
	public static ArrayList<StringBuilder> deepCopy(ArrayList<StringBuilder> list) {
		ArrayList<StringBuilder> deepCopy = new ArrayList<StringBuilder>();
		for(StringBuilder sb: list){
			deepCopy.add(new StringBuilder(sb));
		}
		return deepCopy;
	}
}
