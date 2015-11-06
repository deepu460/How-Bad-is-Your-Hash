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
		int index = index(key), c = 1;
		if (map[index] == null)
			map[index] = new ArrayList<>();
		while (!map[index].isEmpty()) {
			c++;
			if (map[(index + Math.abs(c * c)) % map.length] == null)
				map[(index + Math.abs(c * c)) % map.length] = new ArrayList<>();
			System.out.println(Math.abs(c * c) % map.length);
		}
		if (map[(index + Math.abs(c * c)) % map.length] == null)
			map[(index + Math.abs(c * c)) % map.length] = new ArrayList<>();
		map[(index + Math.abs(c * c)) % map.length].add(new Entry<>(key, value));
		size++;
	}

	@Override
	public V remove(K key) {
		if (!contains(key))
			return null;
		size--;
		return map[search(key)].remove(0).getVal();
	}

	@Override
	public boolean contains(K key) {
		return search(key) != -1;
	}

	@Override
	public V get(K key) {
		return map[search(key)].get(0).getVal();
	}

	@Override
	public void clear() {
		for (int i = 0; i < map.length; i++)
			map[i] = new ArrayList<>();
	}

	@Override
	public int size() {
		return size;
	}

	private int search(K key) {
		int index = index(key);
		int c = 1;
		for (int d = 0; d < map.length; d++) {
			c++;
			if (map[(index + Math.abs(c * c)) % map.length] != null)
				if (map[(index + Math.abs(c * c)) % map.length].get(0).getKey().equals(key))
					return (index + Math.abs(c * c)) % map.length;
		}
		return -1;
	}

	private int index(K key) {
		return (int) Math.abs(key.hashCode()) % map.length;
	}

}