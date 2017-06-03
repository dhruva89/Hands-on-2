package google;

import java.util.ArrayList;
import java.util.HashMap;

public class Hello {
	public static class Node {
		Character c;
		Node l, r;

		Node(Character c) {
			this.c = c;
			this.l = null;
			this.r = null;
		}
	}

	public static String GetStringForHyphen(int count) {
		String hyphenString = "";
		for (int i = 0; i < count; i++) {
			hyphenString += "-";
		}
		return hyphenString;
	}

	public static void PrintTree(Node head, int hyphenCnt, int minHyphenCnt, ArrayList<Node> q,
			HashMap<Node, Integer> map) {
		map.put(head, hyphenCnt);
		q.add(head);

		if (head.r == null && head.l == null) {
			for (int i = 0; i < q.size(); i++) {
				Node n = q.get(i);
				String hyphenStr = GetStringForHyphen(map.get(n) - minHyphenCnt);
				System.out.println(hyphenStr + n.c);
			}
			System.out.println("");
			q.remove(q.size() - 1);
			return;
		}
		if (head.l != null) {
			PrintTree(head.l, hyphenCnt - 1, Integer.min(minHyphenCnt, hyphenCnt - 1), q, map);
		}
		if (head.r != null) {
			PrintTree(head.r, hyphenCnt + 1, Integer.min(minHyphenCnt, hyphenCnt + 1), q, map);
		}
		q.remove(q.size() - 1);
	}

	public static void main(String[] args){
		Node head = new Node('A');
		head.l = new Node('B');
		head.r = new Node('C');

		head.l.l = new Node('D');
		head.l.r = new Node('E');
		
		head.r.l = new Node('F');
		head.r.r = new Node('G');

		head.l.l.l = new Node('H');
		head.l.l.r = new Node('I');
		head.l.l.r.l = new Node('J');
		
	
		
		ArrayList<Node> stack = new ArrayList<Node>();
		PrintTree(head, 0, 0, stack, new HashMap<Node, Integer>());

		/*
		              A
		      B                C
		  D       E        F       G
		H   I   J   K    L   M   N   O
		 */
	}
}