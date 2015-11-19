package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import map.ChainedMap;
import map.LinearMap;
import map.Map;
import map.QuadraticMap;
import map.RandomMap;

public class HBIMY {

	private static PrintWriter out = null;

	private static List<String> LDS, SSR, USR;

	static {
		try {
			out = new PrintWriter(new File("res/log.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		LDS = new LinkedList<>();
		SSR = new LinkedList<>();
		USR = new LinkedList<>();
		Stopwatch watch = new Stopwatch();

		// Open LDS
		watch.start();
		read(LDS, "res/Large Data Set.txt");
		watch.stop();
		println("Finished Reading LDS in " + watch.read());
		watch.clear();
		// Open SSR
		watch.start();
		read(SSR, "res/Successful Search Records.txt");
		watch.stop();
		println("Finished Reading SSR in " + watch.read());
		watch.clear();
		// Open USR
		watch.start();
		read(USR, "res/Unsuccessful Search Records.txt");
		watch.stop();
		println("Finished Reading USR in " + watch.read());
		watch.clear();

		// Begin Testing
		double[] loads = { 0.10, 0.50, 0.80, 0.90, 0.99 };
		for (int i = 1; i < 5; i++)
			for (double d : loads)
				test(i, d);
		test(4, 2.00);

		// Make sure all output is actually written to logs
		out.flush();
	}

	private static void test(int type, double load) {
		Stopwatch watch = new Stopwatch();
		int size = (int) Math.round(50000 / load);
		Map<String, Student> map;
		switch (type) {
		case 1:
			map = new LinearMap<>(size);
			break;
		case 2:
			map = new QuadraticMap<>(size);
			break;
		case 3:
			map = new RandomMap<>(size);
			break;
		case 4:
			map = new ChainedMap<>(size);
			break;
		default:
			throw new RuntimeException("Invalid type " + type);
		}
		watch.start();
		for (String s : LDS) {
			String[] a = s.split("\t");
			map.put(a[0] + " " + a[1], new Student(a[0] + " " + a[1], a[2], a[3], a[4]));
		}
		watch.stop();
		println("Mapping "
				+ (type == 1 ? "linear map" : type == 2 ? "quadratic map" : type == 3 ? "random map" : "chained map")
				+ " took " + watch.read());
		println("\tload factor -> " + load);
		println(String.format("\tsize -> %,d", map.size()));
		println(String.format("\tcollisions -> %,d", map.collisions()));
		watch.clear();
		watch.start();
		for (String s : SSR)
			map.get(s);
		watch.stop();
		println("\tSSR -> " + watch.read());
		watch.clear();
		watch.start();
		for (String s : USR)
			map.get(s);
		watch.stop();
		println("\tUSR -> " + watch.read());
	}

	/**
	 * This method prints a string to the console as well as a log file so that
	 * we can retrieve output later.
	 */
	private static void println(String s) {
		System.out.println(s);
		out.println(s);
	}

	/**
	 * This method copies all of the lines in a file into a list of strings
	 */
	private static void read(List<String> list, String filename) throws FileNotFoundException {
		Scanner s = new Scanner(new File(filename));
		while (s.hasNextLine())
			list.add(s.nextLine());
		s.close();
	}

}