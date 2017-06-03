package google;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LargestNumberDivisibleBy3 {

	public static void main(String args[]) {
		int[] input = { 3, 1, 1, 2};
		System.out.println(getHigestNumDivisibleByThree(input));
	}

	private static int getHigestNumDivisibleByThree(int[] arr) {

		int sum = 0;
		StringBuilder str = new StringBuilder();
		Arrays.sort(arr);

		for (int i = arr.length; i > 0; i--) {
			sum = sum + arr[i - 1];
			str.append(arr[i - 1]);
		}

		int remainder = sum % 3;
		if (remainder == 0)
			return Integer.parseInt(str.toString());

		ArrayList<Integer> input = new ArrayList<Integer>(IntStream.of(arr).boxed().collect(Collectors.toList()));

		boolean found = false;
		for (int num : arr) {
			if (num % 3 == remainder) {
				input.remove(num);
				found = true;
				break;
			}
		}

		if (found) {
			return getNumFromArrayList(input);
		}

		int count = 0;
		int notRemainder = remainder == 1 ? 2 : 1;
		for (int i =0; i<input.size();) {
			if (input.get(i) % 3 == notRemainder && count < 2) {
				input.remove(i);
				count++;
			} else {
				i++;
			}
		}
		
		return getNumFromArrayList(input);
	}

	private static int getNumFromArrayList(ArrayList<Integer> input) {
		int returnVal = 0;
		int multiplier = 1;
		for (int i = 0; i < input.size(); i++) {
			returnVal += (multiplier * input.get(i));
			multiplier *= 10;
		}
		return returnVal;
	}
}
