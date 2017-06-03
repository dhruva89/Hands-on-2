package google;

public class Count1sInSubMatrix {
	public static void main(String args[]) {
		int[][] input = { { 1, 1, 0, 1, 1 }, { 1, 0, 0, 0, 1 }, { 0, 0, 0, 1, 1 }, { 1, 0, 1, 0, 1 } };

		int[][] preprocessData = new int[input.length][input[0].length];
		performPreprocessing(input, preprocessData);
		int numberOf1s = performQuery(preprocessData, 0, 1, 4, 3);
		if (!(numberOf1s == Integer.MIN_VALUE)) {
			System.out.println(numberOf1s);
		} else {
			System.out.println("Wrong input");
		}
	}

	private static int performQuery(int[][] preprocessData, int startX, int startY, int length, int width) {
		if (length == 0 || width == 0 || startX + length - 1 >= preprocessData.length
				|| startY + width - 1 >= preprocessData[0].length) {
			return Integer.MIN_VALUE;
		}
		int totalMatrix = preprocessData[startX + length - 1][startY + width - 1];
		int upperMatrix = startX > 0 ? preprocessData[startX - 1][startY + width - 1] : 0;
		int leftMatrix = startY > 0 ? preprocessData[startX + length - 1][startY - 1] : 0;
		int diagMatrix = startX > 0 && startY > 0 ? preprocessData[startX - 1][startY - 1] : 0;

		return totalMatrix + diagMatrix - upperMatrix - leftMatrix;
	}

	private static void performPreprocessing(int[][] input, int[][] preprocessData) {
		for (int i = 0; i < input.length; i++) {
			for (int j = 0; j < input[0].length; j++) {
				int up = i > 0 ? preprocessData[i - 1][j] : 0;
				int left = j > 0 ? preprocessData[i][j - 1] : 0;
				int diag = i > 0 && j > 0 ? preprocessData[i - 1][j - 1] : 0;
				preprocessData[i][j] = up + left - diag + (input[i][j] == 1 ? 1 : 0);
			}
		}
	}
}
