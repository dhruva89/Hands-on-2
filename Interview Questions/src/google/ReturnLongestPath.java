package google;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

import concepts.BSTNode;

public class ReturnLongestPath {

	public static class Result {
		LinkedList<LinkedList<Integer>> longestStraightPath;
		LinkedList<LinkedList<Integer>> longestJointPath;

		public Result(LinkedList<LinkedList<Integer>> longestStraightPath,
				LinkedList<LinkedList<Integer>> longestJointPath) {
			super();
			this.longestStraightPath = longestStraightPath;
			this.longestJointPath = longestJointPath;
		}
	}

	public static void main(String args[]) {
		BSTNode<Integer> node1 = new BSTNode<Integer>(1);
		BSTNode<Integer> node2 = new BSTNode<Integer>(2);
		BSTNode<Integer> node3 = new BSTNode<Integer>(3);
		BSTNode<Integer> node4 = new BSTNode<Integer>(4);
		BSTNode<Integer> node5 = new BSTNode<Integer>(5);
		BSTNode<Integer> node6 = new BSTNode<Integer>(6);
		BSTNode<Integer> node7 = new BSTNode<Integer>(7);
		BSTNode<Integer> node8 = new BSTNode<Integer>(8);
		BSTNode<Integer> node9 = new BSTNode<Integer>(9);
		BSTNode<Integer> node10 = new BSTNode<Integer>(10);

		node1.left = node2;
		node1.right = node3;
		node2.left = node4;
		node2.right = node5;
		node4.left = node6;
		node6.left = node7;
		node7.left = node8;
		node5.left = node9;

		Result result = findLongestPath(node1);
		int jointSize = 0;
		if (!result.longestJointPath.isEmpty()) {
			jointSize = result.longestJointPath.getFirst().size();
		}
		int straightSize = 0;
		if (!result.longestStraightPath.isEmpty()) {
			straightSize = result.longestStraightPath.getFirst().size();
		}
		if (jointSize > straightSize) {
			System.out.println(Arrays.toString(result.longestJointPath.toArray()));
		} else if (jointSize < straightSize) {
			System.out.println(Arrays.toString(result.longestStraightPath.toArray()));
		} else {
			System.out.println(Arrays.toString(result.longestJointPath.toArray()));
			System.out.println(Arrays.toString(result.longestStraightPath.toArray()));
		}
	}

	private static Result findLongestPath(BSTNode<Integer> cur) {
		if (cur == null) {
			LinkedList<Integer> emptyList = new LinkedList<Integer>();
			LinkedList<LinkedList<Integer>> emptyListOfLists = new LinkedList<LinkedList<Integer>>();
			emptyListOfLists.add(emptyList);
			return new Result(emptyListOfLists, emptyListOfLists);
		}

		Result left = findLongestPath(cur.left);
		Result right = findLongestPath(cur.right);

		LinkedList<LinkedList<Integer>> longestStraightPathsToReturn;

		int leftStraightSize = 0;
		int rightStraightSize = 0;
		if (!left.longestJointPath.isEmpty()) {
			leftStraightSize = left.longestStraightPath.getFirst().size();
		}
		if (!right.longestJointPath.isEmpty()) {
			rightStraightSize = right.longestStraightPath.getFirst().size();
		}

		if (leftStraightSize > rightStraightSize) {
			longestStraightPathsToReturn = addNode(left.longestStraightPath, cur.data);
		} else if (leftStraightSize < rightStraightSize) {
			longestStraightPathsToReturn = addNode(right.longestStraightPath, cur.data);
		} else {
			longestStraightPathsToReturn = addNode(left.longestStraightPath, cur.data);
			if (leftStraightSize != 0) {
				longestStraightPathsToReturn.addAll(addNode(right.longestStraightPath, cur.data));
			}
		}

		LinkedList<LinkedList<Integer>> longestJointPathsToReturn;

		int leftJointSize = 0;
		int rightJointSize = 0;
		int jointSize = 0;
		if (!left.longestJointPath.isEmpty()) {
			leftJointSize = left.longestJointPath.getFirst().size();
		}
		if (!right.longestJointPath.isEmpty()) {
			rightJointSize = right.longestJointPath.getFirst().size();
		}
		jointSize = leftStraightSize + 1 + rightStraightSize;

		if (!(jointSize < leftJointSize) && !(jointSize < rightJointSize)) {
			LinkedList<LinkedList<Integer>> tempLists = mergeLists(left.longestStraightPath, right.longestStraightPath,
					cur.data);
			if (leftJointSize == jointSize) {
				tempLists.addAll(left.longestJointPath);
			}
			if (rightJointSize == jointSize) {
				tempLists.addAll(right.longestJointPath);
			}
			longestJointPathsToReturn = tempLists;
		} else {
			if (leftJointSize > rightJointSize) {
				longestJointPathsToReturn = left.longestJointPath;
			} else if (leftJointSize < rightJointSize) {
				longestJointPathsToReturn = right.longestJointPath;
			} else {
				longestJointPathsToReturn = left.longestJointPath;
				longestJointPathsToReturn.addAll(right.longestJointPath);
			}
		}

		return new Result(longestStraightPathsToReturn, longestJointPathsToReturn);

	}

	private static LinkedList<LinkedList<Integer>> addNode(LinkedList<LinkedList<Integer>> longestStraightPath,
			Integer data) {
		LinkedList<LinkedList<Integer>> longestStraightPathToReturn = new LinkedList<LinkedList<Integer>>();
		for (LinkedList<Integer> list : longestStraightPath) {
			LinkedList<Integer> clonedList = (LinkedList<Integer>) list.clone();
			clonedList.add(data);
			longestStraightPathToReturn.add(clonedList);
		}

		return longestStraightPathToReturn;
	}

	private static LinkedList<LinkedList<Integer>> mergeLists(LinkedList<LinkedList<Integer>> longestStraightPath,
			LinkedList<LinkedList<Integer>> longestStraightPath2, Integer data) {

		LinkedList<LinkedList<Integer>> mergedLists = new LinkedList<LinkedList<Integer>>();
		for (LinkedList<Integer> list1 : longestStraightPath) {
			for (LinkedList<Integer> list2 : longestStraightPath2) {
				LinkedList<Integer> mergedList = (LinkedList<Integer>) list1.clone();
				mergedList.add(data);
				LinkedList<Integer> clonedList2 = (LinkedList<Integer>) list2.clone();
				Collections.reverse(clonedList2);
				mergedList.addAll(clonedList2);
				mergedLists.add(mergedList);
			}
		}
		return mergedLists;
	}

}
