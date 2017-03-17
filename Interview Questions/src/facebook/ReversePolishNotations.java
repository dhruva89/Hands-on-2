package facebook;

import java.util.Scanner;

public class ReversePolishNotations {
	public static void main(String args[]) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		for (int t = 0; t < n; t++) {
			String testcase = scan.next();
			int xCount = 0;
			int replaceCount = 0;
			int deleteCount = 0;
			int insertCount = 0;
			for (int i = 0; i < testcase.length(); i++) {
				if (testcase.charAt(i) == 'x')
					xCount++;
				else {
					if (xCount > 1) {
						xCount--;
					} else if (xCount == 1) {
						if (deleteCount > 0) {
							replaceCount++;
							deleteCount--;
						} else {
							if (i == testcase.length() - 1) {
								insertCount++;
							} else {
								deleteCount++;
							}
						}
					} else {
						if (deleteCount > 1) {
							deleteCount = deleteCount - 2;
							replaceCount = replaceCount + 2;
							xCount = 1;

						} else {
							deleteCount++;
						}

					}
				}

			}
			while (xCount > 1) {
				if (xCount > 2) {
					replaceCount++;
					xCount = xCount - 2;
				}
				if (xCount == 2) {
					insertCount++;
					xCount--;
				}

			}
			System.out.println(deleteCount + replaceCount + insertCount);
		}
		scan.close();
	}

}