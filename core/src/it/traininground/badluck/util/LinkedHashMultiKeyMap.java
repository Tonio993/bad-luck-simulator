package it.traininground.badluck.util;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LinkedHashMultiKeyMap<K, V> extends AbstractMap<List<K>, V> implements Map<List<K>, V> {

	private int size = 0;
	
	private Node<K, V> root = new Node<>();
	private Map<V, Integer> values = new HashMap<>();

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public boolean containsKey(Object keyObject) {
		Node<K, V> node = getNode(keyObject);
		return node != null && node.containsValue();
	}

	@Override
	public boolean containsValue(Object value) {
		return values.containsKey(value);
	}

	@Override
	public V get(Object keyObject) {
		Node<K, V> node = getNode(keyObject);
		return node != null ? node.value : null;
	}

	@Override
	public V put(List<K> keySequence, V value) {
		return addNode(keySequence, value);
	}
	
	@SuppressWarnings("unchecked")
	public V put(V value, K ...keySequence) {
		return addNode(Arrays.asList(keySequence), value);
	}

	@Override
	public V remove(Object key) {
		return removeNode(key);
	}

	@Override
	public void putAll(Map<? extends List<K>, ? extends V> m) {
		for (Entry<? extends List<K>, ? extends V> entry : m.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public void clear() {
		root.clearChildren();
	}

	@Override
	public Set<List<K>> keySet() {
		return keySet(new LinkedHashSet<List<K>>(), new LinkedList<K>(), root);
	}
	
	private Set<List<K>> keySet(Set<List<K>> set, LinkedList<K> sequence, Node<K, V> node) {
		if (node != root) {
			sequence.add(node.key);
		}
		if (node.containsValue()) {
			set.add(new LinkedList<>(sequence));
		}
		for (Node<K, V> child : node.children.values()) {
			keySet(set, sequence, child);
		}
		if (node != root) {
			sequence.removeLast();
		}
		return set;
	}

	@Override
	public Collection<V> values() {
		return Collections.unmodifiableSet(values.keySet());
	}

	@Override
	public Set<Entry<List<K>, V>> entrySet() {
		return entrySet(new LinkedHashSet<Entry<List<K>, V>>(), new LinkedList<K>(), root);
	}

	private Set<Entry<List<K>, V>> entrySet(Set<Entry<List<K>, V>> set, LinkedList<K> sequence, Node<K, V> node) {
		if (node != root) {
			sequence.add(node.key);
		}
		if (node.containsValue()) {
			set.add(new AbstractMap.SimpleEntry<>(new LinkedList<>(sequence), node.value));
		}
		for (Node<K, V> child : node.children.values()) {
			entrySet(set, sequence, child);
		}
		if (node != root) {
			sequence.removeLast();
		}
		return set;
	}

	@SuppressWarnings("unchecked")
	private List<K> getKeySequence(Object keyObject) {
		if (!(keyObject instanceof List)) {
			return null;
		}
		return (List<K>) keyObject;
	}
	
	private Node<K, V> getNode(Object keyObject) {
		List<K> keySequence = getKeySequence(keyObject);
		return keySequence != null ? getNodeFromSequence(keySequence) : null;
	}
		
	private Node<K, V> getNodeFromSequence(List<K> keySequence) {
		Node<K, V> curr = root;
		Node<K, V> next = null;
		for (K key : keySequence) {
			if ((next = curr.getChild(key)) == null) {
				return null;
			}
			curr = next;
		}
		return curr;
	}
	
	private V addNode(List<K> keySequence, V value) {
		Node<K, V> curr = root;
		Node<K, V> next = null;
		boolean exists = true;
		for (K key : keySequence) {
			if (exists && (next = curr.getChild(key)) == null) {
				exists = false;
			}
			if (!exists) {
				next = new Node<>(curr, key);
				curr.setChild(next);
			}
			curr = next;
		}
		V previousValue = curr.value;
		curr.value = value;
		addValue(value);
		if (previousValue != null && !previousValue.equals(value)) {
			removeValue(previousValue);
		} else {
			size++;
		}
		return previousValue;
	}
	
	private V removeNode(Object key) {
		Node<K, V> node = getNode(getKeySequence(key));
		if (node == null) {
			return null;
		}
		if (node.isLeaf()) {
			node.parent.removeChild(node.key);
		}
		removeValue(node.value);
		size--;
		return node.value;
	}
	
	private void addValue(V value) {
		values.compute(value, (k, v) -> v == null ? 1 : v + 1);
	}
	
	private void removeValue(V value) {
		values.compute(value, (k, v) -> v == 1 ? null : v - 1);
	}

	private static class Node<K, V> {
		final Node<K, V> parent;
		HashMap<K, Node<K, V>> children;

		final K key;
		V value;
		
		Node() {
			this(null, null, null);
		}
		
		Node(Node<K, V> parent, K key) {
			this(parent, key, null);
		}
		
		Node(Node<K, V> parent, K key, V value) {
			this.parent = parent;
			children = new HashMap<>();

			this.key = key;
			this.value = value;
		}

		boolean containsValue() {
			return value != null;
		}
		
		boolean isLeaf() {
			return children.isEmpty();
		}
		
		Node<K, V> getChild(Object key) {
			return children.get(key);
		}
		
		boolean setChild(Node<K, V> node) {
			if (children.containsKey(node.key)) {
				if (children.get(node.key).value.equals(node.value)) {
					return false;
				} else {
					children.get(node.key).value = node.value;
					return true;
				}
			}
			children.put(node.key, node);
			return true;
		}
		
		boolean removeChild(K key) {
			return children.remove(key) != null;
		}
		
		void clearChildren() {
			children.clear();
		}

		@Override
		public String toString() {
			return "Node [parent=" + parent + ", children=" + children + ", key=" + key + ", value=" + value + "]";
		}
	}

}
