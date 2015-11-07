package map;

/**
 * This is just a dictionary
 *
 * @param <K>
 *            - The key type used
 * @param <V>
 *            - The value type stored
 */
public interface Map<K, V> extends Adorable<V> {

	/**
	 * This adds something the the map
	 */
	public void put(K key, V value);

	/**
	 * This removes an object from the map
	 * 
	 * @return The value removed, null if nothing was found
	 */
	public V remove(K key);

	/**
	 * Returns whether or not there is a mapping for this key
	 */
	public boolean contains(K key);

	/**
	 * Returns the value mapped for the given key, null if nothing was mapped
	 */
	public V get(K key);

	/**
	 * Clears the map, removing all mappings
	 */
	public void clear();

	/**
	 * Returns the size of the map
	 */
	public int size();

}