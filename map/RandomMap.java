package map;

import java.util.ArrayList;
import java.util.Random;

import core.Stopwatch;

public class RandomMap<K, V> extends AbstractMap<K, V> {

	private Random ran;

	public RandomMap() {
		super();
	}

	public RandomMap(int x) {
		super(x);
	}

	@Override
	public void put(K key, V value) {
		if (size == map.length)
			resize();
		ran = new Random(key.hashCode());
		int keyCode = Math.abs(key.hashCode()) % map.length, z = keyCode;
		while (!(map[z] = map[z] == null ? new ArrayList<>() : map[z]).isEmpty())
			z = Math.abs(ran.nextInt()) % map.length;
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
		int z = search(key);
		return z == -1 ? null : map[search(key)].get(0).getVal();
	}

	private int search(K key) {
		int index = Math.abs(key.hashCode()) % map.length, z = index;
		ran = new Random(key.hashCode());
		for (int d = 0; d < map.length; d++) {
			if (map[z] != null)
				for (Entry<K, V> e : map[z])
					if (e.getKey().equals(key))
						return z;
			z = Math.abs(ran.nextInt()) % map.length;
		}
		return -1;
	}

}