package google;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class SplitUsersIntoGroups {
	public static void main(String args[]) {
		int[][] friendRelations = { { 1, 2 }, { 2, 3 }, { 3, 4 } };

		HashMap<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();

		for (int i = 0; i < friendRelations.length; i++) {

			checkAndPut(friendRelations[i][0], friendRelations[i][1], map);
			checkAndPut(friendRelations[i][1], friendRelations[i][0], map);
		}

		HashSet<Integer> visited = new HashSet<Integer>();

		boolean result = true;
		for (int cur : map.keySet()) {

			if (!visited.contains(cur)) {
				HashSet<Integer> group1 = new HashSet<Integer>();
				HashSet<Integer> group2 = new HashSet<Integer>();
				boolean curGrp1 = true;
				result = result && recurseFindGroupable(group1, group2, curGrp1, cur, map);
				visited.addAll(group1);
				visited.addAll(group2);
			}

		}

		System.out.println(result);

	}

	private static boolean recurseFindGroupable(HashSet<Integer> group1, HashSet<Integer> group2, boolean curGrp1,
			int cur, HashMap<Integer, ArrayList<Integer>> map) {
		HashSet<Integer> curSet = curGrp1 ? group1 : group2;
		HashSet<Integer> otherSet = curGrp1 ? group2 : group1;
		boolean result = true;
		if (curSet.contains(cur)) {
			return true;
		} else if (otherSet.contains(cur)) {
			return false;
		} else {
			curSet.add(cur);
		}
		for (int user : map.get(cur)) {
			result = result && recurseFindGroupable(group1, group2, !curGrp1, user, map);
		}
		return result;
	}

	public static void checkAndPut(int first, int second, HashMap<Integer, ArrayList<Integer>> map) {
		if (map.containsKey(first)) {
			map.get(first).add(second);
		} else {
			ArrayList<Integer> list = new ArrayList<Integer>();
			list.add(second);
			map.put(first, list);
		}
	}
}
