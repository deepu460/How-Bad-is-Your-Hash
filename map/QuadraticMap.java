package map;

import java.util.ArrayList;

public class QuadraticMap<K, V> extends AbstractMap<K, V> {

	public QuadraticMap() {
		super();
	}

	public QuadraticMap(int size) {
		super(size);
	}

	@Override
	public void put(K key, V value) {
		if (size == map.length)
			resize();
		int index = index(key), c = 0, z = index;
		while (!(map[z] = map[z] == null ? new ArrayList<>() : map[z]).isEmpty())
			z = (index + Math.abs(c * c++)) % map.length;
		map[z].add(new Entry<>(key, value));
		size++;
	}

	@Override
	public V remove(K key) {
		int z = search(key);
		if (z != -1)
			return null;
		size--;
		return map[z].remove(0).getVal();
	}

	@Override
	public boolean contains(K key) {
		return search(key) != -1;
	}

	@Override
	public V get(K key) {
		return map[search(key)].get(0).getVal();
	}

	private int search(K key) {
		int index = index(key);
		int c = 1;
		for (int d = 0; d < map.length; d++) {
			int z = (index + Math.abs(c * c++)) % map.length;
			if (map[z] != null)
				for (Entry<K, V> e : map[z])
					if (e.getKey().equals(key))
						return z;
		}
		return -1;
	}

	private int index(K key) {
		return (int) Math.abs(key.hashCode()) % map.length;
	}

}