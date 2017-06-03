package google;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class IsBipartite {
	public static class UndirectedGraphNode {
		int label;
		List<UndirectedGraphNode> neighbors;

		UndirectedGraphNode(int x) {
			label = x;
			neighbors = new ArrayList<UndirectedGraphNode>();
		}
	}

	public static class UndirectedGraph {
		protected HashMap<Integer, UndirectedGraphNode> nodes;

		public UndirectedGraph(UndirectedGraphNode... nodes) {
			this.nodes = new HashMap<Integer, UndirectedGraphNode>();
			for (UndirectedGraphNode node : nodes) {
				this.nodes.put(node.label,node);
			}
		}

		public UndirectedGraph() {
			this.nodes = new HashMap<Integer, UndirectedGraphNode>();
		}
		
		public boolean contains(int NodeID){
			return nodes.containsKey(NodeID);
		}
	}

	public static void main(String args[]) {
		UndirectedGraphNode n1 = new UndirectedGraphNode(1);
		UndirectedGraphNode n2 = new UndirectedGraphNode(2);
		UndirectedGraphNode n3 = new UndirectedGraphNode(3);
		UndirectedGraphNode n4 = new UndirectedGraphNode(4);
		UndirectedGraphNode n5 = new UndirectedGraphNode(5);
		UndirectedGraphNode n6 = new UndirectedGraphNode(6);
		

		n1.neighbors.add(n2);
		n1.neighbors.add(n3);
		n1.neighbors.add(n4);

		n2.neighbors.add(n1);
		n3.neighbors.add(n1);
		n4.neighbors.add(n1);

		n2.neighbors.add(n5);
		n3.neighbors.add(n5);

		n5.neighbors.add(n2);
		n5.neighbors.add(n3);

		n3.neighbors.add(n6);

		n6.neighbors.add(n3);

		UndirectedGraph undirectedGraph = new UndirectedGraph(n1, n2, n3, n4, n5, n6);

		System.out.println(isBipartite(undirectedGraph));

	}

	public static boolean isBipartite(UndirectedGraph undirectedGraph) {
		boolean result = true;
		HashSet<UndirectedGraphNode> visited = new HashSet<UndirectedGraphNode>();
		for (int node : undirectedGraph.nodes.keySet()) {
			if (!visited.contains(undirectedGraph.nodes.get(node))) {
				HashSet<UndirectedGraphNode> set1 = new HashSet<UndirectedGraphNode>();
				HashSet<UndirectedGraphNode> set2 = new HashSet<UndirectedGraphNode>();
				HashSet<UndirectedGraphNode> curSet = set1;
				result = result && recurseFindBipartite(curSet, undirectedGraph.nodes.get(node), set1, set2);
				visited.addAll(set1);
				visited.addAll(set2);
			}
		}
		return result;

	}

	private static boolean recurseFindBipartite(HashSet<UndirectedGraphNode> curSet, UndirectedGraphNode curNode,
			HashSet<UndirectedGraphNode> set1, HashSet<UndirectedGraphNode> set2) {
		HashSet<UndirectedGraphNode> otherSet = (curSet == set1 ? set2 : set1);
		if (otherSet.contains(curNode)) {
			return false;
		}
		if (curSet.contains(curNode)) {
			return true;
		} else {
			curSet.add(curNode);
		}
		boolean result = true;
		for (UndirectedGraphNode neighbor : curNode.neighbors) {
			result = result && recurseFindBipartite(otherSet, neighbor, set1, set2);
		}
		return result;
	}
}
