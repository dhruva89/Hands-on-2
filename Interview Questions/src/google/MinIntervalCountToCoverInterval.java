package google;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class MinIntervalCountToCoverInterval {
	static class Interval {
		Integer sT;
		Integer eT;

		Interval(int sT, int eT) {
			this.sT = sT;
			this.eT = eT;
		}

		@Override
		public String toString() {
			return "[" + sT + ", " + eT + "]";
		}
	}

	public static ArrayList<Interval> findMinInterval(ArrayList<Interval> list, Interval target) {
		HashSet<Integer> set = new HashSet<Integer>();
		for (Interval i : list) {
			set.add(i.sT);
			set.add(i.eT);
		}
		set.add(target.sT);
		set.add(target.eT);

		Integer[] arr = new Integer[set.size()];
		arr = set.toArray(arr);

		Arrays.sort(arr);

		HashMap<Integer, Interval> bestIntervalMap = new HashMap<Integer, Interval>();
		for (Interval i : list) {
			int startIndex = Arrays.binarySearch(arr, i.sT);
			if (startIndex < 0) {
				startIndex = -startIndex - 1;
			}

			int endIndex = Arrays.binarySearch(arr, i.eT);
			if (endIndex < 0) {
				endIndex = -endIndex - 1;
			}

			for (int j = startIndex; j <= endIndex; j++) {
				if (!bestIntervalMap.containsKey(arr[j])) {
					bestIntervalMap.put(arr[j], i);
				} else if (bestIntervalMap.get(arr[j]).eT < i.eT) {
					bestIntervalMap.put(arr[j], i);

				}
			}

		}
		int cur = target.sT;
		ArrayList<Interval> result = new ArrayList<Interval>();
		while (cur < target.eT) {
			if (!bestIntervalMap.containsKey(cur)) {
				return null;
			} else {
				result.add(bestIntervalMap.get(cur));
				cur = bestIntervalMap.get(cur).eT;
			}
		}
		return result;

	}

	static void PrintList(ArrayList<Interval> list) {
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				System.out.print(list.get(i).toString() + ((i == list.size() - 1) ? "" : ", "));
			}
			System.out.println();
		} else {
			System.out.println("Not possible");
		}
	}

	public static void main(String[] args) {
		ArrayList<Interval> list = new ArrayList<Interval>();
		list.add(new Interval(-1, 9));
		list.add(new Interval(1, 10));
		list.add(new Interval(0, 3));
		list.add(new Interval(9, 10));
		list.add(new Interval(3, 14));
		list.add(new Interval(2, 9));
		list.add(new Interval(10, 16));
		PrintList(findMinInterval(list, new Interval(2, 15)));
	}
}