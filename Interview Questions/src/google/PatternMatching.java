package google;

import java.util.LinkedList;

public class PatternMatching {
	public static class Point {
		int x;
		int y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public static Point getMiddlePoint(Point a, Point b) {
			return new Point((a.x + b.x) / 2, (a.y + b.y) / 2);
		}
	}

	public class PatternPoint {
		private Point location;

		private boolean selected;

		public PatternPoint(Point location) {
			this.location = location;
			selected = false;
		}

		public boolean isSelected() {
			return selected;
		}

		public void setSelected(boolean value) {
			selected = value;
		}

		public Point getLocation() {
			return location;
		}
	}

	PatternPoint[] patternPointGrid = new PatternPoint[9];
	LinkedList<Integer> pattern = new LinkedList<Integer>();
	LinkedList<Integer> workingPattern = new LinkedList<Integer>();
	int patternPointRadius;

	public PatternMatching(Point upperLeft, Point upperRight, Point lowerLeft, Point lowerRight) {
		patternPointGrid[0] = new PatternPoint(upperLeft);
		patternPointGrid[1] = new PatternPoint(Point.getMiddlePoint(upperLeft, upperRight));
		patternPointGrid[2] = new PatternPoint(upperRight);
		patternPointGrid[3] = new PatternPoint(Point.getMiddlePoint(upperLeft, lowerLeft));
		patternPointGrid[4] = new PatternPoint(Point.getMiddlePoint(upperLeft, lowerRight));
		patternPointGrid[5] = new PatternPoint(Point.getMiddlePoint(upperRight, lowerRight));
		patternPointGrid[6] = new PatternPoint(lowerLeft);
		patternPointGrid[7] = new PatternPoint(Point.getMiddlePoint(lowerLeft, lowerRight));
		patternPointGrid[8] = new PatternPoint(lowerRight);
	}

	public boolean evaluatePattern() {
		boolean retValue;
		if (workingPattern.equals(pattern)) {
			retValue = true;
		} else {
			retValue = false;
		}
		workingPattern = new LinkedList<Integer>();
		clearSelection();
		return retValue;
	}

	public void setPattern() {
		if (workingPattern.size() > 2) {
			pattern = workingPattern;
		}
		workingPattern = new LinkedList<Integer>();
		clearSelection();
	}

	public void clearSelection() {
		for (int i = 0; i < patternPointGrid.length; i++) {
			patternPointGrid[i].setSelected(false);
		}
	}

	public void checkPointsAndAssign(Point cur) {
		for (int i = 0; i < patternPointGrid.length; i++) {
			if (isInRadius(cur, patternPointGrid[i])) {
				patternPointGrid[i].setSelected(true);
				workingPattern.add(i);
			}
		}
	}

	private boolean isInRadius(Point cur, PatternPoint patternPoint) {
		if (Math.sqrt(Math.pow(patternPoint.getLocation().x - cur.x, 2)
				+ Math.pow(patternPoint.getLocation().y - cur.y, 2)) <= patternPointRadius) {

			return true;
		}
		return false;
	}
}
