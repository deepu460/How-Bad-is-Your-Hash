package map;

import java.util.Random;

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
		int keyCode = Math.abs(key.hashCode()) % map.length, z = 0, k = keyCode;
		while (!map[k].isEmpty()) {
			z = Math.abs(ran.nextInt()) % map.length;
			k = keyCode + z;
			if (k >= map.length)
				k -= map.length;
			collisions++;
		}
		map[k].add(new Entry<>(key, value));
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
		return z == -1 ? null : map[z].get(0).getVal();
	}

	@Override
	public long probe(K key) {
		int hash = key.hashCode();
		int index = Math.abs(key.hashCode()) % map.length, z = 0, k = index, f = 1;
		ran = new Random(key.hashCode());
		for (int d = 0; d < map.length; d++) {
			if (map[k].isEmpty())
				return f;
			if (map[k].get(0).getKey().hashCode() == hash)
				return f;
			z = Math.abs(ran.nextInt()) % map.length;
			k = index + z;
			if (k >= map.length)
				k -= map.length;
			f++;
		}
		return f;
	}

	private int search(K key) {
		int hash = key.hashCode();
		int index = Math.abs(key.hashCode()) % map.length, z = 0, k = index;
		ran = new Random(key.hashCode());
		for (int d = 0; d < map.length; d++) {
			if (map[k].isEmpty())
				return -1;
			if (map[k].get(0).getKey().hashCode() == hash)
				return k;
			z = Math.abs(ran.nextInt()) % map.length;
			k = index + z;
			if (k >= map.length)
				k -= map.length;
		}
		return -1;
	}

}