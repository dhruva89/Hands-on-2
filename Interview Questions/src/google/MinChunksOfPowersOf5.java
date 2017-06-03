package google;

import java.util.HashMap;

public class MinChunksOfPowersOf5 {
	public static void main(String args[]) {
		String input = "10111001";
		System.out.println(minChunksOfPowersOf5(input, 0, input.length() - 1, new HashMap<String, Integer>()));
	}

	private static int minChunksOfPowersOf5(String input, int start, int end, HashMap<String, Integer> memo) {

		if (isPowerOf5(input, start, end)) {
			return 1;
		}

		String curKey = start + ":" + end;
		if (memo.containsKey(curKey)) {
			memo.get(curKey);
		}
		int min = Integer.MAX_VALUE;

		for (int i = start + 1; i < end; i++) {
			int leftSubString;
			int rightSubString;
			if ((leftSubString = minChunksOfPowersOf5(input, start, i - 1, memo)) != Integer.MAX_VALUE) {
				if ((rightSubString = minChunksOfPowersOf5(input, i, end, memo)) != Integer.MAX_VALUE) {
					min = Math.min(min, leftSubString + rightSubString);
				}
			}
		}
		memo.put(curKey, min);
		return memo.get(curKey);
	}

	public static boolean isPowerOf5(String input, int start, int end) {
		int num = Integer.parseInt(input.substring(start, end + 1), 2);
		double power = Math.log10(num) / Math.log10(5);
		if (power == Math.floor(power) && !Double.isInfinite(power)) {
			return true;
		} else {
			return false;
		}
	}
}
