package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import map.ChainedMap;
import map.LinearMap;
import map.Map;
import map.MapType;
import map.QuadraticMap;
import map.RandomMap;

public class HBIMY {

	private static final double[] LOADS = { 0.10, 0.50, 0.80, 0.90, 0.99 };

	private static PrintWriter out;

	private static List<String> LDS, SSR, USR;

	private static boolean quiet = true;

	private static List<TrialData> data;

	static {
		try {
			out = new PrintWriter(new File("res/log.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// Init lists
		LDS = new LinkedList<>();
		SSR = new LinkedList<>();
		USR = new LinkedList<>();
		data = new ArrayList<>();
	}

	public static void main(String[] args) throws Exception {
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
		// Warm up the JVM & let it optimize code
		println("\nOptimizing Code...\n");
		watch.start();
		for (int i = 0; i < 5; i++)
			trial();
		watch.stop();
		println("Completed Optimizations in " + watch.read() + "\n");
		watch.clear();
		// Run the actual run
		watch.start();
		quiet = false;
		for (int i = 1; i <= 3; i++) {
			println("Trial " + i + ": ");
			trial();
			println();
		}
		watch.stop();
		println("Program complete in " + watch.read() + "\n");
		// Begin printing stats
		stats();
		// Make sure all output is actually written to logs
		out.flush();
	}

	private static void trial() {
		Stopwatch watch = new Stopwatch();
		// Begin timing the entire thing
		watch.start();

		// Begin Testing

		for (MapType t : MapType.TYPES) {
			for (double d : LOADS)
				test(t, d);
			if (!quiet)
				println();
		}
		test(MapType.C, 2.00);

		// Print overall run time
		watch.stop();
		if (!quiet)
			println("\nSimulation Completed in " + watch.read() + ".");
	}

	/**
	 * Prints all stats in general, including each individual map type.
	 */
	private static void stats() {
		for (MapType t : MapType.TYPES)
			statsFor(t);
		println("Overall: ");
		printf("\tAverage construction time -> %s%n",
				Stopwatch.parse((long) data.parallelStream().mapToLong(i -> i.getTime()).average().getAsDouble()));
		printf("\tAverage collisions -> %,.2f%n",
				data.parallelStream().mapToLong(i -> i.getCollisions()).average().getAsDouble());
		printf("\tAverage SSR time -> %s%n",
				Stopwatch.parse((long) data.parallelStream().mapToLong(i -> i.getSsr()).average().getAsDouble()));
		printf("\tAverage USR time -> %s%n%n",
				Stopwatch.parse((long) data.parallelStream().mapToLong(i -> i.getUsr()).average().getAsDouble()));
	}

	/**
	 * Prints stats for one type of map
	 */
	private static void statsFor(MapType type) {
		List<TrialData> list = data.parallelStream().filter(i -> i.getType() == type).collect(Collectors.toList());
		println(type + ": ");
		printf("\tAverage construction time -> %s%n",
				Stopwatch.parse((long) list.parallelStream().mapToLong(i -> i.getTime()).average().getAsDouble()));
		printf("\tAverage collisions -> %,.2f%n",
				list.parallelStream().mapToLong(i -> i.getCollisions()).average().getAsDouble());
		printf("\tAverage SSR time -> %s%n",
				Stopwatch.parse((long) list.parallelStream().mapToLong(i -> i.getSsr()).average().getAsDouble()));
		printf("\tAverage USR time -> %s%n%n",
				Stopwatch.parse((long) list.parallelStream().mapToLong(i -> i.getUsr()).average().getAsDouble()));
	}

	private static void test(MapType type, double load) {
		Stopwatch watch = new Stopwatch();
		int size = (int) Math.round(50000 / load);
		long time = 0, ssr = 0, usr = 0;
		Map<String, Student> map;
		switch (type) {
		case L:
			map = new LinearMap<>(size);
			break;
		case Q:
			map = new QuadraticMap<>(size);
			break;
		case R:
			map = new RandomMap<>(size);
			break;
		case C:
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
		if (!quiet) {
			println("Mapping " + type + " took " + watch.read());
			printf("\tavg insertion time -> %,d ns%n", watch.raw() / 50000);
			printf("\tload factor -> %,.2f%n", load);
			printf("\tsize -> %,d%n", map.size());
			printf("\tcollisions -> %,d%n", map.collisions());
			printf("\tcollisions vs Insertions: %,.2f%%%n", map.collisions() / 500.0);
			if (type == MapType.C)
				printf("\tavg. List Length -> %,.2f%n", 50000.0 / (50000 - map.collisions()));
			time = watch.raw();
		}
		watch.clear();
		long probes_ssr = 0;
		watch.start();
		for (String s : SSR)
			probes_ssr += map.probe(s);
		watch.stop();
		if (!quiet) {
			println("\tSSR -> " + watch.read());
			printf("\tavg. Probes -> %,.2f%n", probes_ssr / 1000.0);
			ssr = watch.raw();
		}
		watch.clear();
		long probes_usr = 0;
		watch.start();
		for (String s : USR)
			probes_usr += map.probe(s);
		watch.stop();
		if (!quiet) {
			println("\tUSR -> " + watch.read());
			printf("\tavg. Probes -> %,.2f%n", probes_usr / 1000.0);
			usr = watch.raw();
		}
		watch.clear();
		if (!quiet)
			data.add(new TrialData(type, load, LDS.size(), map.collisions(), time, ssr, usr, probes_ssr / 1000,
					probes_usr / 1000));
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
	 * This method prints a string to the console as well as a log file so that
	 * we can retrieve output later.
	 */
	private static void println() {
		println("");
	}

	private static void printf(String s, Object... args) {
		System.out.printf(s, args);
		out.printf(s, args);
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