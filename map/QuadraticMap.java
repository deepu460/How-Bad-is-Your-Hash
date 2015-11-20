package map;

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
		int index = index(key), c = 0;
		while (!map[index].isEmpty()) {
			index += Math.abs(index + c * c++);
			index %= map.length;
			collisions++;
		}
		map[index].add(new Entry<>(key, value));
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
		int index = index(key);
		int c = 1, f = 1;
		if (map[index].isEmpty())
			return f;
		if (map[index].get(0).getKey().hashCode() == key.hashCode())
			return f;
		for (int d = 0; d < map.length; d++) {
			index += Math.abs(index + c * c++) % map.length;
			index %= map.length;
			if (map[index].isEmpty())
				return f;
			if (map[index].get(0).getKey().hashCode() == key.hashCode())
				return f;
			f++;
		}
		return f;
	}

	private int search(K key) {
		int index = index(key);
		int c = 1;
		if (map[index].isEmpty())
			return -1;
		if (map[index].get(0).getKey().hashCode() == key.hashCode())
			return index;
		for (int d = 0; d < map.length; d++) {
			index += Math.abs(index + c * c++) % map.length;
			index %= map.length;
			if (map[index].isEmpty())
				return -1;
			if (map[index].get(0).getKey().hashCode() == key.hashCode())
				return index;
		}
		return -1;
	}

	private int index(K key) {
		return (int) Math.abs(key.hashCode()) % map.length;
	}

}