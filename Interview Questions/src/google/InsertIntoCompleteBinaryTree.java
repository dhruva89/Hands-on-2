package google;

import concepts.BSTNode;

public class InsertIntoCompleteBinaryTree {

	public enum Result {
		foundPos, inserted, notFound
	};

	public static void main(String args[]) {
		BSTNode<Integer> root = new BSTNode<Integer>(1);
		root.left = new BSTNode<Integer>(2);
		root.right = new BSTNode<Integer>(3);
		root.left.left = new BSTNode<Integer>(4);
		root.left.right = new BSTNode<Integer>(5);

		int maxHeight = findHeight(root, 0);

		insert(6, 0, maxHeight, root);
		System.out.println(root.right.left);
	}

	private static Result insert(int elem, int height, int maxHeight, BSTNode<Integer> cur) {
		if (cur == null) {
			if (height < maxHeight) {
				return Result.foundPos;
			} else {
				return Result.notFound;
			}
		}
		Result res;
		if ((res = insert(elem, height + 1, maxHeight, cur.left)) == Result.foundPos) {
			cur.left = new BSTNode<Integer>(6);
			return Result.inserted;
		} else if (res == Result.inserted) {
			return res;
		} else if ((res = insert(elem, height + 1, maxHeight, cur.right)) == Result.foundPos) {
			cur.right = new BSTNode<Integer>(6);
			return Result.inserted;
		} else {
			return res;
		}
	}

	private static int findHeight(BSTNode<Integer> cur, int height) {
		if (cur == null) {
			return height;
		} else {
			return findHeight(cur.left, height + 1);
		}
	}
}
