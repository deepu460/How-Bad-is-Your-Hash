package map;

import java.util.ArrayList;

public class LinearMap<K, V> extends AbstractMap<K, V> {

	private int size = 0;

	public LinearMap() {
		super();
	}

	public LinearMap(int x) {
		super(x);
	}

	@Override
	public void put(K key, V value) {
		int keyCode = Math.abs(key.hashCode()) % map.length;
		while (true) {
			if (map[keyCode] == null) {
				map[keyCode] = new ArrayList<Entry<K, V>>();
				map[keyCode].add(new Entry<K, V>(key, value));
				break;
			} else {
				if (++keyCode == map.length)
					keyCode = 0;
			}
		}

		size++;

	}

	@Override
	public V remove(K key) {
		int keyCode = Math.abs(key.hashCode()) % map.length;
		
		if(contains(key))
		{
		size--;
			while (true) {
				if (map[keyCode].get(0).getKey().equals(key))
					return map[keyCode].remove(0).getVal();
				else {
					if (++keyCode == map.length)
						keyCode = 0;
				}
			}
		}
		return null;
	}

	@Override
	public boolean contains(K key) {
		int keyCode = Math.abs(key.hashCode()) % map.length;
		int startKeyCode = keyCode;

		if (map[startKeyCode] == null)
			return false;

		for (int x = 0; keyCode < map.length; x++) {
			if (map[keyCode] == null)
				keyCode++;
			else if (map[keyCode].get(0).getKey().equals(key))
				return true;
			else {
				keyCode++;
			}
		}
		return false;
	}

	@Override
	public V get(K key) {
		int keyCode = Math.abs(key.hashCode()) % map.length;
		
		if(contains(key))
		{
			while (true) {
				if (map[keyCode].get(0).getKey().equals(key))
					return map[keyCode].get(0).getVal();
				else {
					if (++keyCode == map.length)
						keyCode = 0;
				}
			}
		}
		return null;
	}

	@Override
	public void clear() {
		for (int x = 0; x < map.length; x++) {
			map[x] = new ArrayList<>();
		}
	}

	@Override
	public int size() {
		return size;
	}
}
