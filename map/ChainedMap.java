package map;

public class ChainedMap<K, V> extends AbstractMap<K, V> {

	public ChainedMap() {
		super();
	}

	public ChainedMap(int size) {
		super(size);
	}

	@Override
	public void put(K key, V value) {
		int hash = findIndex(key);
		if (!map[hash].isEmpty())
			collisions++;
		map[hash].add(new Entry<>(key, value));
		size++;
	}

	@Override
	public V remove(K key) {
		int bucket = findBucket(key);
		if (bucket > -1) {
			size--;
			return map[findIndex(key)].remove(bucket).getVal();
		}
		return null;
	}

	@Override
	public boolean contains(K key) {
		return get(key) != null;
	}

	@Override
	public V get(K key) {
		int bucket = findBucket(key);
		return bucket > -1 ? map[findIndex(key)].get(bucket).getVal() : null;
	}

	@Override
	public long probe(K key) {
		int hash = findIndex(key);
		int c = 0;
		if (map[hash].isEmpty())
			return c;
		for (Entry<K, V> e : map[hash])
			if (e.getKey().hashCode() == key.hashCode())
				return c;
			else
				c++;
		return c;
	}

	private int findIndex(K key) {
		return (int) Math.abs(key.hashCode()) % map.length;
	}

	private int findBucket(K key) {
		int hash = findIndex(key);
		int c = 1;
		if (map[hash].isEmpty())
			return -1;
		for (Entry<K, V> e : map[hash])
			if (e.getKey().hashCode() == key.hashCode())
				return c;
			else
				c++;
		return -1;
	}

}
