package map;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractMap<K, V> implements Map<K, V> {

	private static final int DEFAULT_SIZE = 127;

	/**
	 * How much larger the array grows with each call to <code>resize()</code>
	 */
	public final double GROWTH_FACTOR = 1.1;

	/**
	 * This array stores the values in this map
	 */
	protected List<Entry<K, V>>[] map;

	/**
	 * The amount of keys mapped in this map
	 */
	protected int size;

	/**
	 * The amount of collisions that occured in this map
	 */
	protected long collisions;

	

	private class HashIterator implements Adored<V> {

		List<Entry<K, V>>[] map;

		private int c, t;

		public HashIterator(AbstractMap<K, V> map) {
			super();
			this.map = map.map;
			c = 0;
			t = 0;
		}

		@Override
		public boolean hasNext() {
			if (map[c] == null) {
				t = 0;
				while (map[c] != null)
					if (++c >= map.length)
						return false;
			}

			while (true) {
				if (c >= map.length)
					return false;
				if (map[c] != null)
					if (t < map[c].size())
						return true;
				c++;
				t = 0;
			}
		}

		@Override
		public V next() {
			if (!hasNext())
				return null;
			while (true)
				if (t < map[c].size())
					return map[c].get(t++).getVal();
				else {
					c++;
					t = 0;
				}
		}

	}

	public AbstractMap() {
		this(DEFAULT_SIZE);
	}

	@SuppressWarnings("unchecked")
	public AbstractMap(int size) {
		super();
		this.size = 0;
		map = new List[!prime(size) ? nextPrime(size) : size];
		clear();
	}

	@Override
	public Iterator<V> iterator() {
		return new HashIterator(this);
	}

	@Override
	public void clear() {
		size = 0;
		collisions = 0;
		for (int i = 0; i < map.length; i++)
			map[i] = new LinkedList<>();
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public long collisions() {
		return collisions;
	}

	/**
	 * Resize the array to the next largest prime number
	 */
	@SuppressWarnings("unchecked")
	protected void resize() {
		List<Entry<K, V>>[] t = new List[map.length];
		for (int i = 0; i < map.length; i++)
			t[i] = map[i];
		map = new List[nextPrime((int) (Math.round(map.length * GROWTH_FACTOR) + 1))];
		size = 0;
		collisions = 0;
		for (List<Entry<K, V>> l : t)
			if (l != null)
				for (Entry<K, V> e : l)
					if (e != null)
						put(e.getKey(), e.getVal());
	}

	private int nextPrime(int s) {
		int candidate = s;
		while (!prime(candidate++))
			;
		return --candidate;
	}

	// Lazily copied off stackoverflow...
	private boolean prime(int n) {
		if (n < 2)
			return false;
		if (n == 2 || n == 3 || n == 5)
			return true;
		if (n % 2 == 0 || n % 3 == 0 || n % 5 == 0)
			return false;
		int sqrtN = (int) Math.sqrt(n) + 1;
		for (int i = 6; i <= sqrtN; i += 6)
			if (n % (i - 1) == 0 || n % (i + 1) == 0)
				return false;
		return true;
	}

}
