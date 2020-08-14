package it.traininground.badluck.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class LinkedHashMultiSet<T> implements Set<List<T>> {

	private int size = 0;
	
	private Node<T> root = new Node<>();

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public boolean add(List<T> sequence) {
		return addNode(sequence);
	}
	
	@SuppressWarnings("unchecked")
	public boolean addSequence(T... sequence) {
		return addNode(Arrays.asList(sequence));
	}
	
	@SuppressWarnings("unchecked")
	public boolean add(T... sequence) {
		return addNode(Arrays.asList(sequence));
	}
	
	@Override
	public boolean remove(Object sequence) {
		return removeNode(sequence);
	}

	@Override
	public void clear() {
		root.clearChildren();
	}

	@SuppressWarnings("unchecked")
	private List<T> getSequence(Object object) {
		if (!(object instanceof List)) {
			return null;
		}
		return (List<T>) object;
	}
	
	private Node<T> getNode(Object object) {
		List<T> sequence = getSequence(object);
		return sequence != null ? getNodeFromSequence(sequence) : null;
	}
		
	private Node<T> getNodeFromSequence(List<T> sequence) {
		Node<T> curr = root;
		Node<T> next = null;
		for (T key : sequence) {
			if ((next = curr.getChild(key)) == null) {
				return null;
			}
			curr = next;
		}
		return curr;
	}
	
	private boolean addNode(List<T> sequence) {
		Node<T> curr = root;
		Node<T> next = null;
		boolean exists = true;
		for (T key : sequence) {
			if (exists && (next = curr.getChild(key)) == null) {
				exists = false;
			}
			if (!exists) {
				next = new Node<>(curr, key, false);
				curr.addChild(next);
			}
			curr = next;
		}
		curr.isValue = true;
		if (!exists) {
			size++;
			return true;
		}
		return false;
	}
	
	private boolean removeNode(Object objectSequence) {
		Node<T> node = getNode(getSequence(objectSequence));
		if (node == null) {
			return false;
		}
		if (node.isLeaf()) {
			node.parent.removeChild(node.value);
		}
		size--;
		return true;
	}
	
	@Override
	public boolean contains(Object objectSequence) {
		List<T> sequence = getSequence(objectSequence);
		Node<T> curr = root;
		for (T key : sequence) {
			if ((curr = curr.getChild(key)) == null) {
				return false;
			}
		}
		return curr.isValue;
	}
	
	@SuppressWarnings("unchecked")
	public boolean containsSequence(T... sequence) {
		return contains(Arrays.asList(sequence));
	}

	@Override
	public Iterator<List<T>> iterator() {
		return new InnerIterator();
	}

	@Override
	public Object[] toArray() {
		return null;
	}

	@Override
	public <T2> T2[] toArray(T2[] a) {
		return null;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object obj : c) {
			if (!contains(obj)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends List<T>> c) {
		boolean changes = false;
		for (List<T> sequence : c) {
			changes |= add(sequence);
		}
		return changes;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		boolean changes = false;
		for (Object obj : c) {
			if (!contains(obj)) {
				changes |= remove(obj);
			}
		}
		return changes;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean changes = false;
		for (Object obj : c) {
			changes |= remove(obj);
		}
		return changes;
	}

	private static class Node<T> {
		final Node<T> parent;
		HashMap<T, Node<T>> children;
	
		boolean isValue;
		T value;
		
		Node() {
			this(null, null, false);
		}
		
		Node(Node<T> parent, T value, boolean isValue) {
			this.parent = parent;
			children = new HashMap<>();
			this.isValue = isValue;
			this.value = value;
		}
	
		boolean isLeaf() {
			return children.isEmpty();
		}
		
		Node<T> getChild(Object key) {
			return children.get(key);
		}
		
		boolean addChild(Node<T> node) {
			if (children.containsKey(node.value)) {
				return false;
			} else {
				children.put(node.value, node);
				return true;
			}
		}
		
		boolean removeChild(T value) {
			return children.remove(value) != null;
		}
		
		void clearChildren() {
			children.clear();
		}
	
		@Override
		public String toString() {
			return "Node [parent=" + parent + ", children=" + children + ", value=" + value + "]";
		}
	}
	
	class InnerIterator implements Iterator<List<T>> {
		
		boolean hasNext = true;
		
		LinkedList<T> nodeQueue = new LinkedList<>();
		LinkedList<Iterator<Node<T>>> iteratorQueue = new LinkedList<>();
		
		List<T> currValue;
		T nextValue;

		InnerIterator() {
			iteratorQueue.add(root.children.values().iterator());
			nextStep();
		}
		
		private void nextStep() {
			Iterator<Node<T>> it;
			while (!iteratorQueue.isEmpty()) {
				it = iteratorQueue.getLast();
				if (!it.hasNext()) {
					iteratorQueue.removeLast();
					if (iteratorQueue.isEmpty()) {
						break;
					}
					nodeQueue.removeLast();
					continue;
				}
				Node<T> curr = it.next();
				nodeQueue.add(curr.value);
				iteratorQueue.add(curr.children.values().iterator());
				if (curr.isValue) {
					break;
				}
			}
			if (iteratorQueue.isEmpty()) {
				hasNext = false;
			}
		}

		@Override
		public boolean hasNext() {
			return hasNext;
		}

		@Override
		public List<T> next() {
			List<T> next = new ArrayList<>(nodeQueue); 
			nextStep();
			return next;
		}
		
	}

}
