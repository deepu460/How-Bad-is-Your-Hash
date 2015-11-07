package map;

import java.util.ArrayList;
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
		ran = new Random(key.hashCode());
		int keyCode = Math.abs(key.hashCode()) % map.length;
		while (true)
			if (map[keyCode] == null) {
				map[keyCode] = new ArrayList<Entry<K, V>>();
				map[keyCode].add(new Entry<K, V>(key, value));
				size++;
				break;
			} else
				keyCode = Math.abs(ran.nextInt()) % map.length;
		size++;
	}

	@Override
	public V remove(K key) {
		ran = new Random(Math.abs(key.hashCode()));
		int keyCode = Math.abs(key.hashCode()) % map.length;

		if (contains(key)) {
			size--;
			while (true)
				if (map[keyCode].get(0).getKey().equals(key)) {
					size--;
					return map[keyCode].remove(0).getVal();
				} else
					keyCode = ran.nextInt() % map.length;
		}

		return null;
	}

	@Override
	public boolean contains(K key) {
		ran = new Random(key.hashCode());
		int keyCode = Math.abs(key.hashCode()) % map.length;

		if (map[keyCode] == null)
			return false;

		while (keyCode < map.length)
			if (map[keyCode] == null)
				keyCode++;
			else if (map[keyCode].get(0).getKey().equals(key))
				return true;
			else
				keyCode++;
		return false;
	}

	@Override
	public V get(K key) {
		ran = new Random(key.hashCode());
		int keyCode = Math.abs(key.hashCode()) % map.length;

		if (contains(key))
			while (true)
				if (map[keyCode].get(0).getKey().equals(key)) {
					size--;
					return map[keyCode].get(0).getVal();
				} else
					keyCode = Math.abs(ran.nextInt()) % map.length;
		return null;
	}
}