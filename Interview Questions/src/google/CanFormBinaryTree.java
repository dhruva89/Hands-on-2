package google;

import java.util.ArrayList;
import java.util.HashMap;

public class CanFormBinaryTree {
	private static class TreeNode<T extends Comparable<T>> {
		T data;
		TreeNode<T> left;
		TreeNode<T> right;

		public TreeNode(T data) {
			this.data = data;
			left = null;
			right = null;
		}
	}

	public static void main(String args[]) {
		TreeNode<Integer> t1, t2, t3, t4, t5, t6;
		ArrayList<TreeNode<Integer>> arrayListOfTreeNodes = new ArrayList<TreeNode<Integer>>();
		arrayListOfTreeNodes.add(t1 = new TreeNode<Integer>(3));
		arrayListOfTreeNodes.add(t2 = new TreeNode<Integer>(1));
		arrayListOfTreeNodes.add(t3 = new TreeNode<Integer>(5));
		arrayListOfTreeNodes.add(t4 = new TreeNode<Integer>(2));
		arrayListOfTreeNodes.add(t5 = new TreeNode<Integer>(4));
		arrayListOfTreeNodes.add(t6 = new TreeNode<Integer>(6));

		t1.left = t2;
		t1.right = t3;
		t2.left = t4;
		t3.left = t5;
		t3.right = t6;

		System.out.println(checkIfTree(arrayListOfTreeNodes));
	}

	private static <Y extends Comparable<Y>> boolean checkIfTree(ArrayList<TreeNode<Y>> arrayListOfTreeNodes) {
		HashMap<TreeNode<Y>, Integer> map = new HashMap<TreeNode<Y>, Integer>();
		for (TreeNode<Y> treeNode : arrayListOfTreeNodes) {
			if (!map.containsKey(treeNode)) {
				map.put(treeNode, 0);
			}
			updateCount(treeNode.left, map);
			updateCount(treeNode.right, map);
		}

		TreeNode<Y> root = null;
		if (arrayListOfTreeNodes.size() != map.size() - 1) {
			return false;
		}

		for (TreeNode<Y> treeNode : map.keySet()) {
			if (treeNode == null) {
				continue;
			}

			if (map.get(treeNode) == 0) {
				if (root != null) {
					return false;

				} else {
					root = treeNode;
				}
			} else if (map.get(treeNode) == 1) {
				continue;
			} else {
				return false;
			}
		}
		return true;
	}

	private static <Y extends Comparable<Y>> void updateCount(TreeNode<Y> treeNode, HashMap<TreeNode<Y>, Integer> map) {
		if (map.containsKey(treeNode)) {
			map.put(treeNode, map.get(treeNode) + 1);
		} else {
			map.put(treeNode, 1);
		}
	}
}
