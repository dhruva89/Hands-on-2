package snapchat;

class Throttler {
	private CircularLinkedList queries;

	public Throttler(int queriesPerSec) {
		queries = new CircularLinkedList(queriesPerSec);
	}

	public boolean allowAccess() {
		if (queries.peek() == 0) {
			queries.add(System.currentTimeMillis());
			return true;
		} else if ((System.currentTimeMillis() - queries.peek()) >= 1000) {
			queries.add(System.currentTimeMillis());
			return true;
		} else {
			return false;
		}
	}

	private class CircularLinkedList {
		private class Node {
			public long timeOfQuery;
			public Node next;
		}

		private Node cur;

		public CircularLinkedList(int size) { // size > 0
			Node start = new Node();
			Node cur = start;
			for (int i = 0; i < size - 1; i++) {
				cur.next = new Node();
				cur = cur.next;
			}
			cur.next = start;
		}

		public long peek() {
			return cur.timeOfQuery;
		}

		public void add(long input) {
			cur.timeOfQuery = input;
			cur = cur.next;
		}
	}
}