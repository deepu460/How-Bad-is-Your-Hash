package map;

public interface Map<K, V> extends Adorable<V> {
	public void put(K key, V value);

	public V remove(K key);

	public boolean contains(K key);

	public V get(K key);

	public void clear();

	public int size();
}
