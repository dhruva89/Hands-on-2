package google;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import concepts.BST;

public class FindLeafNodesInPreOrderTraversal {
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

		ArrayList<Integer> preOrderArr = new ArrayList<Integer>();
		bst.preOrderTraversalForArray(preOrderArr);
		System.out.println(leaves(preOrderArr.stream().mapToInt(i -> i).toArray()));
	}

	public static List<Integer> leaves(int[] arr){
		if(arr == null || arr.length == 0){
			return Collections.<Integer>emptyList();
		}
		List<Integer> result = new ArrayList<Integer>();
		Stack<Integer> st = new Stack<Integer>();
		for(int i = 0; i < arr.length; i++){
			Integer l = null;
			boolean leafFound = false;
			while(!st.isEmpty() && arr[i] > st.peek()){
				if(l != null && !leafFound){
					result.add(l);
					leafFound = true;
				}
				l = st.pop();
			}
			st.push(arr[i]);
		}
		result.add(st.pop());
		return result;
	}

}
