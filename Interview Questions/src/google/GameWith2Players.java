package google;

import java.util.HashMap;

public class GameWith2Players {
	private static class Result {
		int coinsCollectedByA;
		int coinsCollectedByB;

		public Result(int a, int b) {
			coinsCollectedByA = a;
			coinsCollectedByB = b;
		}
	}

	public static void main(String args[]) {
		int[] arr = { 1, 5, 3, 1 };
		Result result = playGame(arr, 0, arr.length - 1, true, new HashMap<String, Result>());
		System.out.println(result.coinsCollectedByA);
	}

	private static Result playGame(int[] arr, int start, int end, boolean isPlayerA, HashMap<String, Result> memo) {
		if (start > end) {
			return new Result(0, 0);
		}

		String curKey = start + ":" + end + ":" + isPlayerA;
		if (memo.containsKey(curKey)) {
			return memo.get(curKey);
		}

		Result left = playGame(arr, start + 1, end, !isPlayerA, memo);
		int leftPotCoins = arr[start];
		Result right = playGame(arr, start, end - 1, !isPlayerA, memo);
		int rightPotCoins = arr[end];

		memo.put(curKey, maximize(left, right, leftPotCoins, rightPotCoins, isPlayerA));
		return memo.get(curKey);
	}

	private static Result maximize(Result left, Result right, int leftPotCoins, int rightPotCoins, boolean isPlayerA) {
		int coinsCollectedByPlayerFromLeft = (isPlayerA ? left.coinsCollectedByA : left.coinsCollectedByB);
		int coinsCollectedByOtherPlayerFromLeft = (!isPlayerA ? left.coinsCollectedByA : left.coinsCollectedByB);
		int coinsCollectedByPlayerFromRight = (isPlayerA ? right.coinsCollectedByA : right.coinsCollectedByB);
		int coinsCollectedByOtherPlayerFromRight = (!isPlayerA ? right.coinsCollectedByA : right.coinsCollectedByB);
		int choosingLeft = leftPotCoins + coinsCollectedByPlayerFromLeft;
		int choosingRight = rightPotCoins + coinsCollectedByPlayerFromRight;
		if (choosingLeft > choosingRight) {
			return new Result(isPlayerA ? choosingLeft : coinsCollectedByOtherPlayerFromLeft,
					!isPlayerA ? choosingLeft : coinsCollectedByOtherPlayerFromLeft);
		} else {
			return new Result(isPlayerA ? choosingRight : coinsCollectedByOtherPlayerFromRight,
					!isPlayerA ? choosingRight : coinsCollectedByOtherPlayerFromRight);
		}
	}
}
