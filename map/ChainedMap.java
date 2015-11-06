package map;

import java.util.LinkedList;

import core.HBIMY;

public class ChainedMap<K, V> extends AbstractMap<K, V> {

	public ChainedMap() {
		super();
	}

	public ChainedMap(int size) {
		super(size);
	}

	public void put(K key, V value) {
		if (size > map.length - 1)
			resize();
		int hash = findIndex(key);
		if (map[hash] == null)
			map[hash] = new LinkedList<>();
		map[hash].add(new Entry<>(key, value));
		if (map[hash].size() != 1)
			HBIMY.$count++;
		size++;
	}

	public V remove(K key) {
		int bucket = findBucket(key);
		if (bucket > -1) {
			size--;
			return map[findIndex(key)].remove(bucket).getVal();
		}
		return null;
	}

	public boolean contains(K key) {
		return get(key) != null;
	}

	public V get(K key) {
		int bucket = findBucket(key);
		return bucket > -1 ? map[findIndex(key)].get(bucket).getVal() : null;
	}

	private int findIndex(K key) {
		return (int) Math.abs(key.hashCode()) % map.length;
	}

	private int findBucket(K key) {
		int hash = findIndex(key);
		int c = 0;
		if (map[hash] == null)
			return -1;
		for (Entry<K, V> e : map[hash])
			if (e.getKey().hashCode() == key.hashCode())
				return c;
			else
				c++;
		return -1;
	}
}
