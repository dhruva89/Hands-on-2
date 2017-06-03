package google;

import java.io.FileNotFoundException;

import concepts.BST;
import concepts.BSTNode;

public class FindBSTMedian {

	public static void main(String args[]) throws FileNotFoundException {
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

		int count = traverseForCount(bst.root);
		if (count % 2 != 0) {
			System.out.println(getNum(count / 2 + 1, bst.root));
		} else {
			System.out.println((getNum(count / 2, bst.root) + getNum(count / 2 + 1, bst.root)) / 2);
		}
	}

	private static double getNum(int i, BSTNode<Integer> root) {
		BSTNode<Integer> cur = root;
		int count = 0;
		int ret = -1;
		while (cur != null) {

			if (cur.left == null) {
				count++;
				if (count == i) {
					ret = cur.data;
				}
				cur = cur.right;
			} else {
				BSTNode<Integer> temp = cur.left;
				while (temp.right != null && temp.right != cur) {
					temp = temp.right;
				}
				if (temp.right == null) {
					temp.right = cur;
					cur = cur.left;
				} else {
					temp.right = null;
					count++;
					if (count == i) {
						ret = cur.data;
					}
					cur = cur.right;
				}
			}
		}
		return ret;
	}

	public static int traverseForCount(BSTNode<Integer> root) {
		BSTNode<Integer> cur = root;
		int count = 0;
		while (cur != null) {
			if (cur.left == null) {
				count++;
				cur = cur.right;
			} else {
				BSTNode<Integer> temp = cur.left;
				while (temp.right != null && temp.right != cur) {
					temp = temp.right;
				}
				if (temp.right == null) {
					temp.right = cur;
					cur = cur.left;
				} else {
					temp.right = null;
					count++;
					cur = cur.right;
				}
			}
		}
		return count;
	}
}
