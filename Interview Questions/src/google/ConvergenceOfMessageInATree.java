package google;

import java.util.Arrays;
import java.util.Collections;

import concepts.GraphNode;

public class ConvergenceOfMessageInATree {
	public static void main(String args[]) {
		GraphNode<Integer> node1 = new GraphNode<Integer>(1);
		GraphNode<Integer> node2 = new GraphNode<Integer>(2);
		GraphNode<Integer> node3 = new GraphNode<Integer>(3);
		GraphNode<Integer> node4 = new GraphNode<Integer>(4);
		GraphNode<Integer> node5 = new GraphNode<Integer>(5);
		GraphNode<Integer> node6 = new GraphNode<Integer>(6);
		GraphNode<Integer> node7 = new GraphNode<Integer>(7);
		GraphNode<Integer> node8 = new GraphNode<Integer>(8);
		GraphNode<Integer> node9 = new GraphNode<Integer>(9);

		node1.children.add(node2);
		node1.children.add(node3);

		node2.children.add(node4);
		node2.children.add(node5);
		node2.children.add(node6);
		node2.children.add(node7);

		node3.children.add(node8);
		node3.children.add(node9);

		System.out.println(findTime(node1));
	}

	private static int findTime(GraphNode<Integer> cur) {

		Integer[] childValues = new Integer[cur.children.size()];
		int i = 0;
		for (GraphNode<Integer> child : cur.children) {
			childValues[i] = findTime(child);
			i++;
		}
		Arrays.sort(childValues, Collections.reverseOrder());
		int max = 0;
		for (int j = 0; j < childValues.length; j++) {
			max = Math.max(max, childValues[j] + j + 1);
		}
		return max;
	}
}
