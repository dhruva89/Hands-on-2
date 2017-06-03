package google;

import java.util.Arrays;

//https://www.careercup.com/question?id=5662358396469248

public class CombinationSum {
	public static void main(String args[]) {
		int target = 47;
		int[] arr = {7, 20};
		boolean[][] memo = new boolean[target + 1][arr.length];
		for (int i = 0; i < memo.length; i++) {
			Arrays.fill(memo[i], false);
		}
		for (int j = 0; j < memo[0].length; j++) {
			memo[0][j] = true;
		}
		System.out.println(getWhetherCombinedSumExists(target, arr, memo, arr.length - 1));
	}

	private static boolean getWhetherCombinedSumExists(int target, int[] arr, boolean[][] memo, int arrIndex) {
		if(target == 0) {
			return true;
		}
		
		if (arrIndex == -1) {
			return false;
		}
		
		if (memo[target][arrIndex] == true) {
			return true;
		}

		int curFactor = arr[arrIndex];
		boolean result = false;
		if (arrIndex >= 0) {

			for (int i = target / curFactor; i >= 0; i--) {
				result = result || getWhetherCombinedSumExists(target - (i * curFactor), arr, memo, arrIndex - 1);
			}
		}
		return result;
	}
}
