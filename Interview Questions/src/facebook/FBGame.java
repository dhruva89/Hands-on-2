package facebook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;

public class FBGame {
	private static class Pair implements Comparable<Pair> {
		public Integer val;
		public Integer player;

		public Pair(int val, int player) {
			super();
			this.val = val;
			this.player = player;
		}

		@Override
		public int compareTo(Pair o) {
			return this.val.compareTo(o.val);
		}
	}

	public static void main(String args[]) {
		Scanner scan = new Scanner(System.in);
		int numberOfTestCases = scan.nextInt();
		for (int i = 0; i < numberOfTestCases; i++) {
			int playerCount = scan.nextInt();
			int k = scan.nextInt();
			if (k > playerCount) {
				System.out.println(0);
			}
			ArrayList<Pair> list = new ArrayList<Pair>();
			for (int j = 0; j < playerCount; j++) {
				int listSize = scan.nextInt();
				for (int m = 0; m < listSize; m++) {
					list.add(new Pair(scan.nextInt(), j));
				}
			}
			System.out.println(solve(list, playerCount, k));
		}
		scan.close();
	}

	private static int solve(ArrayList<Pair> list, int playerCount, int k) {
		int answer = 0;
		Collections.sort(list);
		HashSet<Integer> setOfVals = new HashSet<Integer>();
		HashSet<Integer> setOfPlayers = new HashSet<Integer>();
		for (int i = 0; i < list.size(); i++) {
			Pair cur = list.get(i);
			int countLow = 0;
			countLow = setOfPlayers.size() - (setOfPlayers.contains(cur.player) ? 1 : 0);
			if (countLow >= k-1) {
				setOfVals.add(cur.val);
			}
			setOfPlayers.add(cur.player);
		}

		setOfPlayers.clear();

		for (int j = list.size() - 1; j >= 0; j--) {
			Pair cur = list.get(j);
			int countHigh = 0;
			countHigh = setOfPlayers.size() - (setOfPlayers.contains(cur.player) ? 1 : 0);
			if (countHigh >= playerCount-k) {
				if (setOfVals.contains(cur.val)) {
					answer++;
				}
			}
			setOfPlayers.add(cur.player);
		}

		return answer;
	}
}
