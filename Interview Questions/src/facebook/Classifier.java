package facebook;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class bundles Objects by their field's category.
 * 
 * @param <T>
 *            the Object to be stored
 */
public class Classifier<T> {

	/**
	 * Any Field implementing this Interface can become a filter
	 * {@link Category} for the {@link Classifier}.
	 */
	public interface Category {
	}

	/**
	 * Abstract Tree node type. Can {@link Node#add(List, Object)},
	 * {@link Node#search(List)} and {@link Node#count(List)} some Items.
	 */
	private abstract class Node {
		protected long count; // Made private

		public Node() {
			super();
			this.count = 0;
		}

		public abstract void add(List<Class<? extends Category>> classHierarchy, T item);

		protected abstract List<T> search(List<? extends Category> criteria);

		protected abstract long count(List<? extends Category> criteria);
	}

	/**
	 * Tree Branch. Does not store anything, all data is in a {@link Leaf}
	 * further down.
	 */
	private class Branch extends Node {

		protected final Class<? extends Category> classType; // Made private
		private Map<Category, Node> children = new HashMap<>(); // Made private,
																// made general
																// (map)

		public Branch(Class<? extends Category> class1) {
			super();
			this.classType = class1;
		}

		public Branch(List<Class<? extends Category>> classHierarchy, T item) {
			super();
			this.classType = classHierarchy.get(0);
			List<Class<? extends Category>> downstreamCategories = new ArrayList<>(classHierarchy);
			downstreamCategories.remove(0);
			Category fieldValue = getRelevantField(classType, item);
			final Node child;
			if (downstreamCategories.isEmpty()) {
				child = new Leaf(item);
			} else {
				child = new Branch(downstreamCategories, item);
			}
			children.put(fieldValue, child);
			count++;
		}

		public void add(List<Class<? extends Category>> classHierarchy, T item) {
			Class<? extends Category> filterCat = classHierarchy.remove(0);
			Category field = getRelevantField(filterCat, item);
			if (children.containsKey(field)) {
				children.get(field).add(classHierarchy, item);
			} else {
				if (classHierarchy.isEmpty()) { // No more classifiers to apply
					children.put(field, new Leaf(item));
				} else {
					children.put(field, new Branch(classHierarchy, item));
				}
			}
			count++;
		}

		protected List<T> search(List<? extends Category> criteria) {
			// Locate a criteria of the class that this node handles
			for (Category criterion : criteria) {
				if (this.classType.isInstance(criterion)) {
					// This node handles this Criteria Class
					Node matchingChild = children.get(criterion);
					if (matchingChild != null) {
						// This criteria *instance* is known: we can dive down
						// the tree
						List<? extends Category> remainingCriteria = new ArrayList<>(criteria);
						remainingCriteria.remove(criterion);
						return matchingChild.search(remainingCriteria);
					} else {
						// The criteria *instance* has no matching key. There
						// can be no results down this node
						return new ArrayList<>();
					}
				}
			}
			// This node does not filter any requested criteria class. All
			// subnodes are equally valid.
			// Let's merge the result of all the subnodes!
			return children.values().stream().flatMap(node -> node.search(criteria).stream())
					.collect(Collectors.toList());
		}

		protected long count(List<? extends Category> criteria) {
			// Locate a criteria of the class that this node handles
			for (Category criterion : criteria) {
				if (this.classType.isInstance(criterion)) {
					// This node handles this Criteria Class
					Node matchingChild = children.get(criterion);
					if (matchingChild != null) {
						// This criteria *instance* is known: we can dive down
						// the tree
						List<? extends Category> remainingCriteria = new ArrayList<>(criteria);
						remainingCriteria.remove(criterion);
						return matchingChild.count(remainingCriteria);
					} else {
						// The criteria *instance* has no matching key. There
						// can be no results down this node
						return 0;
					}
				}
			}
			// This node does not filter any requested criteria class. All
			// subnodes are equally valid.
			// Let's add the result of all the subnodes!
			return children.values().stream().collect(Collectors.summingLong(node -> node.count(criteria)));
		}
	}

	/** Tree Leaf. Stores all objects sharing the same Categories. */
	private class Leaf extends Node {
		List<T> storedItems = new ArrayList<>();

		public Leaf(T item) {
			super();
			storedItems.add(item);
		}

		public void add(List<Class<? extends Category>> classHierarchy, T item) {
			storedItems.add(item);
		}

		protected List<T> search(List<? extends Category> criteria) {
			return storedItems;
		}

		@Override
		protected long count(List<? extends Category> criteria) {
			return count; // everyone here is a match
		}
	}

	private Node root; // Initialized at first insertion
	final List<Class<? extends Category>> classOrder;

	/**
	 * Builds a Classifier, using fields of the provided Category classes. The
	 * parameters order defines the classification priority.<br>
	 * <i>Note: Only the first Field of the class will be used in the
	 * classification.</i>
	 */
	public Classifier(Class<? extends Category>... classes) {
		super();
		classOrder = new ArrayList<Class<? extends Category>>();
		for (Class<? extends Category> toAdd : classes) {
			classOrder.add(toAdd);
		}
	}

	/**
	 * Builds a Classifier, using fields of the provided Category classes. The
	 * {@link List} order defines the classification priority.<br>
	 * <i>Note: Only the first Field of the class will be used in the
	 * classification.</i>
	 */
	public Classifier(List<Class<? extends Category>> classOrder) {
		super();
		this.classOrder = new ArrayList<>(classOrder);
	}

	/**
	 * Creates a Classifier instance containing the same data, but with swapped
	 * classification order.
	 */
	public Classifier<T> reorder(int index1, int index2) {
		if (index1 < 0 || index2 < 0 || index1 >= classOrder.size() || index2 >= classOrder.size()
				|| index1 == index2) {
			throw new IllegalArgumentException("The swapped indices (" + index1 + ", " + index2
					+ ") must be different, and each in the range [0, " + (classOrder.size() - 1) + "]");
		}
		List<Class<? extends Category>> newClassOrder = new ArrayList<>(this.classOrder); // Don't
																							// use
																							// clone,
																							// Oracle
																							// messed
																							// this
																							// method
																							// up.
																							// This
																							// is
																							// a
																							// shallow
																							// copy,
																							// make
																							// it
																							// clear
		// Much clearer if you use source data when swapping, not the data
		// you're currently modifying
		newClassOrder.set(index1, classOrder.get(index2));
		newClassOrder.set(index2, classOrder.get(index1));
		Classifier<T> reorderedClassifier = new Classifier<T>(newClassOrder);
		if (root != null) {
			// Very bad solution (TEMPORARY): take all elements, and insert them
			// in the new classifier
			// (a much better solution would be to reorganize the Nodes tree in
			// place, and not return a new Classifier object)
			// But tree reordering are tricky, and I was lazy
			List<T> contents = root.search(new ArrayList<>()); // returns
																// EVERYTHING
			for (T elem : contents) {
				reorderedClassifier.insert(elem);
			}
		}
		return reorderedClassifier;
	}

	/** Inserts an Item in this Classifier. */
	public void insert(T item) {
		if (item == null) {
			throw new IllegalArgumentException("The input Item must not be null");
		}
		if (root == null) {
			root = new Branch(classOrder.get(0));
		}
		root.add(new ArrayList<>(classOrder), item); // Copy the classOrder
														// it'll be mutated
														// there
	}

	/** Returns all items that correspond the input category filters. */
	public List<T> search(Category... searchCriteria) {
		if (root == null) {
			return new ArrayList<>();
		} else {
			List<? extends Category> criteriaAsList = searchCriteria == null ? new ArrayList<>()
					: Arrays.asList(searchCriteria);
			return root.search(criteriaAsList);
		}
	}

	/** Counts all items that correspond the input category filters. */
	public long count(Category... searchCriteria) {
		if (root == null || searchCriteria == null) {
			return 0;
		} else {
			List<? extends Category> criteriaAsList = Arrays.asList(searchCriteria);
			return root.count(criteriaAsList);
		}
	}

	/** Counts all items. */
	public long total() {
		return root == null ? 0 : root.count;
	}

	private Category getRelevantField(Class<? extends Category> classType, T item) { // Was
																						// BAD!
																						// hardcoded=
																						// not
																						// extensible.
																						// Instead,
																						// I
																						// used
																						// reflection
		Field[] fields = item.getClass().getFields();
		for (Field field : fields) {
			Class<?> fieldClass = field.getType();
			if (fieldClass.equals(classType)) {
				try {
					return (Category) field.get(item);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					// Very bad code! Handle it with a proper logger please
					e.printStackTrace();
				}
			}
		}
		throw new IllegalArgumentException(
				"The input item " + item + " does not contain a Field of type " + classType.getTypeName());
	}
}