package google;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class FindPath {

	public static class Point {
		int x;
		int y;

		public Point(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}

		@Override
		public boolean equals(Object o) {

			if (o == this)
				return true;
			if (!(o instanceof Point)) {
				return false;
			}
			Point point = (Point) o;
			return x == point.x && y == point.y;
		}

		@Override
		public int hashCode() {
			return Objects.hash(x, y);
		}
	}

	public static void main(String args[]) {
		char[][] board2 = { { '0', '2', '1', '1', '1' }, { '0', '1', 'a', '0', 'A' }, { '0', '1', '0', '0', '3' },
				{ '0', '1', '0', '0', '1' }, { '0', '1', '1', '1', '1' } };

		List<int[]> solution = solve(board2);
		for (int[] pos : solution) {
			System.out.println(Arrays.toString(pos));
		}
	}

	public static List<int[]> solve(char[][] board) {
		Point start = null;
		Point goal = null;
		HashSet<Point> visited = new HashSet<Point>();

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] == '2') {
					start = new Point(i, j);
				} else if (board[i][j] == '3') {
					goal = new Point(i, j);
				}
			}
		}

		return solve(start, board, visited, goal, new LinkedList<int[]>(), new HashSet<Character>());
	}

	private static List<int[]> solve(Point cur, char[][] board, HashSet<Point> visited, Point goal,
			LinkedList<int[]> solution, HashSet<Character> keys) {

		if (cur.equals(goal)) {
			return (LinkedList<int[]>) solution.clone();
		}

		if (Character.isLowerCase(board[cur.x][cur.y])) {
			keys.add(board[cur.x][cur.y]);
		}

		visited.add(new Point(cur.x,cur.y));

		int i = cur.x;
		int j = cur.y;

		solution.add(new int[] { i, j });

		List<int[]> up = null;
		List<int[]> down = null;
		List<int[]> left = null;
		List<int[]> right = null;

		if (i - 1 >= 0 && board[i - 1][j] != '0' && !visited.contains(new Point(i - 1, j))
				&& isOpenableIfDoor(board, i - 1, j, keys)) {
			cur.x -= 1;
			up = solve(cur, board, visited, goal, solution, keys);
			cur.x += 1;
		}
		if (i + 1 < board.length && board[i + 1][j] != '0' && !visited.contains(new Point(i + 1, j))
				&& isOpenableIfDoor(board, i + 1, j, keys)) {
			cur.x += 1;
			down = solve(cur, board, visited, goal, solution, keys);
			cur.x -= 1;
		}
		if (j - 1 >= 0 && board[i][j - 1] != '0' && !visited.contains(new Point(i, j - 1))
				&& isOpenableIfDoor(board, i, j - 1, keys)) {
			cur.y -= 1;
			left = solve(cur, board, visited, goal, solution, keys);
			cur.y += 1;
		}
		if (j + 1 < board[0].length && board[i][j + 1] != '0' && !visited.contains(new Point(i, j + 1))
				&& isOpenableIfDoor(board, i, j + 1, keys)) {
			cur.y += 1;
			right = solve(cur, board, visited, goal, solution, keys);
			cur.y -= 1;
		}

		List<List<int[]>> res = new LinkedList<List<int[]>>();
		res.add(up);
		res.add(down);
		res.add(left);
		res.add(right);
		Collections.sort(res, new Comparator<List<int[]>>() {

			@Override
			public int compare(List<int[]> arg0, List<int[]> arg1) {
				if (arg0 != null && arg1 != null) {
					return Integer.compare(arg0.size(), arg1.size());
				} else if (arg0 != null) {
					return -1;
				} else if (arg1 != null) {
					return 1;
				} else {
					return 0;
				}
			}
		});
		if (Character.isLowerCase(board[cur.x][cur.y])) {
			keys.remove(board[cur.x][cur.y]);
		}
		solution.removeLast();
		visited.remove(new Point(i, j));
		return res.get(0);
	}

	private static boolean isOpenableIfDoor(char[][] board, int i, int j, HashSet<Character> keys) {
		if (Character.isUpperCase(board[i][j])) {
			if (keys.contains(Character.toLowerCase(board[i][j]))) {
				return true;
			}
			return false;
		}
		return true;
	}
}
