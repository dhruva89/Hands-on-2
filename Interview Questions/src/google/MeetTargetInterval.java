package google;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class MeetTargetInterval {
	private static class Interval {
		public int start;
		public int end;

		public Interval(int start, int end) {
			this.start = start;
			this.end = end;
		}
	}

	public static void main(String args[]) {
		LinkedList<Interval> list = new LinkedList<Interval>();
		list.add(new Interval(-1, 9));
		list.add(new Interval(1, 10));
		list.add(new Interval(0, 3));
		list.add(new Interval(9, 10));
		list.add(new Interval(3, 14));
		list.add(new Interval(2, 9));
		list.add(new Interval(10, 16));

		list.sort(new Comparator<Interval>() {

			@Override
			public int compare(Interval arg0, Interval arg1) {
				return Integer.compare(arg0.start, arg1.start);
			}
		});

		Interval target = new Interval(2, 15);
		LinkedHashMap<Interval, Boolean> map = new LinkedHashMap<Interval, Boolean>();
		for (Interval i : list) {
			map.put(i, false);
		}
		System.out.println(findMinNumberOfIntervalsToReachTarget(target, map, 0));

	}

	public static int findMinNumberOfIntervalsToReachTarget(Interval target,
			LinkedHashMap<Interval, Boolean> originalIntervals, int count) {
		if (target.start > target.end) {

			return count;

		}
		int min = Integer.MAX_VALUE;
		for (Interval i : originalIntervals.keySet()) {
			if (originalIntervals.get(i) == false && i.start <= target.start) {
				originalIntervals.put(i, true);
				min = Math.min(min, findMinNumberOfIntervalsToReachTarget(new Interval(i.end, target.end),
						originalIntervals, count + 1));
				originalIntervals.put(i, false);
			}
		}
		return min;
	}

}
