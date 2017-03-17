package facebook;

import java.util.HashMap;
import java.util.Scanner;

public class NKings {
	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		int numberOfCases = in.nextInt();

		for (int i = 0; i < numberOfCases; i++) {
			int N, K;
			N = in.nextInt();
			K = in.nextInt();
			HashMap<Integer, Integer> columnRowMap = new HashMap<Integer, Integer>();
			for (int j = 0; j < K; j++) {
				columnRowMap.put(in.nextInt(), j);
			}
			System.out.println(findWays(N, K, columnRowMap));
		}
		in.close();
	}

	private static int findWays(int N, int row, HashMap<Integer, Integer> columnRowMap) {
		if (row == N) {
			return 1;
		}
		int ways = 0;
		for (int column = 0; column < N; column++) {
			if (kingCanBePlaced(N, columnRowMap, row, column)) {
				columnRowMap.put(column, row);
				ways += findWays(N, row + 1, columnRowMap);
				columnRowMap.remove(column);
			}

		}
		
		return ways;
	}

	private static boolean kingCanBePlaced(int N, HashMap<Integer, Integer> columnRowMap, int row, int column) {
		if (columnRowMap.containsKey(column)) {
			return false;
		}
		if ((column - 1 >= 0) && (row - 1 >= 0) && columnRowMap.containsKey(column - 1)
				&& columnRowMap.get(column - 1) == row - 1) {
			return false;
		}
		if ((column + 1 < N) && (row - 1 >= 0) && columnRowMap.containsKey(column + 1)
				&& columnRowMap.get(column + 1) == row - 1) {
			return false;
		}
		return true;
	}
}
