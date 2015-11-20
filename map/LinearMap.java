package map;

public class LinearMap<K, V> extends AbstractMap<K, V> {

	public LinearMap() {
		super();
	}

	public LinearMap(int x) {
		super(x);
	}

	@Override
	public void put(K key, V value) {
		if (size == map.length)
			resize();
		size++;
		int keyCode = Math.abs(key.hashCode()) % map.length;
		while (true) {
			if (map[keyCode].isEmpty()) {
				map[keyCode].add(new Entry<>(key, value));
				break;
			} else if (++keyCode == map.length)
				keyCode = 0;
			collisions++;
		}
	}

	@Override
	public V remove(K key) {
		int keyCode = Math.abs(key.hashCode()) % map.length;
		for (int d = 0; d < map.length; d++)
			if (map[keyCode].isEmpty())
				return null;
			else if (map[keyCode].get(0).getKey().hashCode() == key.hashCode())
				return map[keyCode].remove(0).getVal();
			else if (++keyCode == map.length)
				keyCode = 0;
		return null;
	}

	@Override
	public boolean contains(K key) {
		int keyCode = Math.abs(key.hashCode()) % map.length;
		for (int d = 0; d < map.length; d++)
			if (map[keyCode].isEmpty())
				return false;
			else if (map[keyCode].get(0).getKey().hashCode() == key.hashCode())
				return true;
			else if (++keyCode == map.length)
				keyCode = 0;
		return false;
	}

	@Override
	public V get(K key) {
		int keyCode = Math.abs(key.hashCode()) % map.length;
		for (int d = 0; d < map.length; d++)
			if (map[keyCode].isEmpty())
				return null;
			else if (map[keyCode].get(0).getKey().hashCode() == key.hashCode())
				return map[keyCode].get(0).getVal();
			else if (++keyCode == map.length)
				keyCode = 0;
		return null;
	}

	@Override
	public long probe(K key) {
		int keyCode = Math.abs(key.hashCode()) % map.length, c = 1;
		for (int d = 0; d < map.length; d++) {
			if (map[keyCode].isEmpty())
				return c;
			else if (map[keyCode].get(0).getKey().hashCode() == key.hashCode())
				return c;
			else if (++keyCode == map.length)
				keyCode = 0;
			c++;
		}
		return c;
	}

}