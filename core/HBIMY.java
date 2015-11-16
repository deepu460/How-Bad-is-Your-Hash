package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import map.ChainedMap;
import map.LinearMap;
import map.Map;
import map.QuadraticMap;
import map.RandomMap;

public class HBIMY {

	public static long $count = 0;
	private static Map<String, Student> map;
	private static Stopwatch sw = new Stopwatch();

	public static void main(String[] args) {
		loadFactor(1, 0.10);
		loadFactor(1, 0.50);
		loadFactor(1, 0.80);
		loadFactor(1, 0.90);
		loadFactor(1, 0.99);
		loadFactor(2, 0.10);
		loadFactor(2, 0.50);
		loadFactor(2, 0.80);
		loadFactor(2, 0.90);
		loadFactor(2, 0.99);
		loadFactor(3, 0.10);
		loadFactor(3, 0.50);
		loadFactor(3, 0.80);
		loadFactor(3, 0.90);
		loadFactor(3, 0.99);
		loadFactor(4, 0.10);
		loadFactor(4, 0.50);
		loadFactor(4, 0.80);
		loadFactor(4, 0.90);
		loadFactor(4, 0.99);
		loadFactor(4, 2.00);
	}

	public static List<String> read(String filename) {
		Scanner s = null;
		List<String> list = new ArrayList<>();
		try {
			s = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (s.hasNextLine())
			list.add(s.nextLine());
		return list;
	}
	
	public static void loadFactor(int mapType, double load)
	{
		double loadFactor = 1 / load;
		double loadSize = 50000 * loadFactor;
		int size = (int)(loadSize);
		switch(mapType)
		{
		case 1:
			map = new LinearMap<>(size);
			calculations();
			break;
			
		case 2:
			map = new QuadraticMap<>(size);
			calculations();
			break;
			
		case 3:
			map = new RandomMap<>(size);
			calculations();
			break;
			
		case 4:
			map = new ChainedMap<>(size);
			calculations();
			break;
		}
	}
	
	public static void calculations()
	{
		String temp = "";
		List<String> LRS = read("res/Large Data Set.txt"), SSR = read("res/Successful Search Records.txt"),
				USR = read("res/Unsuccessful Search Records.txt");
		sw.start();
		LRS.parallelStream().map(s -> s.split("\t"))
				.forEachOrdered(s -> map.put(s[0], new Student(s[0], s[1], s[2], s[3])));
		sw.stop();
		temp += "Time to add data into map: " + sw.read() + "\n\t";
		sw.clear();
		sw.start();
		for(String s : SSR)
		{
			map.get(s);
		}
		sw.stop();
		temp += "Successful Search Time: " + sw.read() + "\n\t";
		sw.clear();
		sw.start();
		for(String s : USR)
		{
			map.get(s);
		}
		sw.stop();
		temp += "Unsuccessful Search Time: " + sw.read();
		
		System.out.println(temp);
	}

}