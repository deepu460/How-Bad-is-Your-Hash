package map;

public class Entry<K, V> {

	public static boolean goodHash = true;

	private K k;

	private V v;

	public Entry(K k, V v) {
		this.k = k;
		this.v = v;
	}

	public K getKey() {
		return k;
	}

	public V getVal() {
		return v;
	}

	@Override
	public int hashCode() {
		if (goodHash) {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((k == null) ? 0 : k.hashCode());
			return result;
		}
		return k.getClass().hashCode() & k.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entry<?, ?> other = (Entry<?, ?>) obj;
		if (k == null) {
			if (other.getKey() != null)
				return false;
		} else if (hashCode() != other.hashCode())
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "<" + k + ", " + v + ">";
	}

}