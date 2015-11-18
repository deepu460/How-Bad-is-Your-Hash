package core;

import map.ChainedMap;
import map.LinearMap;
import map.Map;
import map.QuadraticMap;
import map.RandomMap;

public class TestCase {

	private String name;

	public TestCase(String name) {
		this.name = name;
	}

	public void loadFactor(int mapType, double load) {
		double loadFactor = 1 / load;
		double loadSize = 50000 * loadFactor;
		int size = (int) (loadSize);
		switch (mapType) {
		case 1:
			calculations(new LinearMap<>(size));
			break;

		case 2:
			calculations(new QuadraticMap<>(size));
			break;

		case 3:
			calculations(new RandomMap<>(size));
			break;

		case 4:
			calculations(new ChainedMap<>(size));
			break;
		}
	}

	private void calculations(Map<String, Student> map) {
		Stopwatch sw = new Stopwatch();
		String temp = name + ": \n";
		sw.start();
		HBIMY.LDS.parallelStream().map(s -> s.split("\t"))
				.forEachOrdered(s -> map.put(s[0], new Student(s[0], s[1], s[2], s[3])));
		sw.stop();
		temp += "Time to add data into map: " + sw.read() + "\n\t";
		temp += String.format("Collisions: %,d\n\t", map.collisions());
		sw.clear();
		sw.start();
		for (String s : HBIMY.SSR)
			map.get(s);
		sw.stop();
		temp += "Successful Search Time: " + sw.read() + "\n\t";
		sw.clear();
		sw.start();
		for (String s : HBIMY.USR)
			map.get(s);
		sw.stop();
		temp += "Unsuccessful Search Time: " + sw.read();

		System.out.println(temp);
	}
}
