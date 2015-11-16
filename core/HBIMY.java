package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import map.ChainedMap;
import map.Map;

public class HBIMY {

	public static long $count = 0;

	public static void main(String[] args) {
		List<String> LRS = read("res/Large Data Set.txt"), SSR = read("res/Successful Search Records.txt"),
				USR = read("res/Unsuccessful Search Records.txt");
		Map<String, Student> map = new ChainedMap<>(50021);
		LRS.parallelStream().map(s -> s.split("\t"))
				.forEachOrdered(s -> map.put(s[0], new Student(s[0], s[1], s[2], s[3])));
		for (String s : SSR)
			map.get(s.split("\t")[0]);
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

}