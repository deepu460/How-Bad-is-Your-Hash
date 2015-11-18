package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HBIMY {

	public static List<String> LDS = read("res/Large Data Set.txt"), SSR = read("res/Successful Search Records.txt"),
			USR = read("res/Unsuccessful Search Records.txt");

	public static void main(String[] args) {
		new TestCase("Test Case #16").loadFactor(4, 0.10);
		new Thread(() -> new TestCase("Test Case #1").loadFactor(1, 0.10), "Test Case #1").start();
		new Thread(() -> new TestCase("Test Case #2").loadFactor(1, 0.50), "Test Case #2").start();
		new Thread(() -> new TestCase("Test Case #3").loadFactor(1, 0.80), "Test Case #3").start();
		new Thread(() -> new TestCase("Test Case #4").loadFactor(1, 0.90), "Test Case #4").start();
		new Thread(() -> new TestCase("Test Case #5").loadFactor(1, 0.99), "Test Case #5").start();
		new Thread(() -> new TestCase("Test Case #6").loadFactor(2, 0.10), "Test Case #6").start();
		new Thread(() -> new TestCase("Test Case #7").loadFactor(2, 0.50), "Test Case #7").start();
		new Thread(() -> new TestCase("Test Case #8").loadFactor(2, 0.80), "Test Case #8").start();
		new Thread(() -> new TestCase("Test Case #9").loadFactor(2, 0.90), "Test Case #9").start();
		new Thread(() -> new TestCase("Test Case #10").loadFactor(2, 0.99), "Test Case #10").start();
		new Thread(() -> new TestCase("Test Case #11").loadFactor(3, 0.10), "Test Case #11").start();
		new Thread(() -> new TestCase("Test Case #12").loadFactor(3, 0.50), "Test Case #12").start();
		new Thread(() -> new TestCase("Test Case #13").loadFactor(3, 0.80), "Test Case #13").start();
		new Thread(() -> new TestCase("Test Case #14").loadFactor(3, 0.90), "Test Case #14").start();
		new Thread(() -> new TestCase("Test Case #15").loadFactor(3, 0.99), "Test Case #15").start();
		new Thread(() -> new TestCase("Test Case #16").loadFactor(4, 0.10), "Test Case #16").start();
		new Thread(() -> new TestCase("Test Case #17").loadFactor(4, 0.50), "Test Case #17").start();
		new Thread(() -> new TestCase("Test Case #18").loadFactor(4, 0.80), "Test Case #18").start();
		new Thread(() -> new TestCase("Test Case #19").loadFactor(4, 0.90), "Test Case #19").start();
		new Thread(() -> new TestCase("Test Case #20").loadFactor(4, 0.99), "Test Case #20").start();
		new Thread(() -> new TestCase("Test Case #21").loadFactor(4, 2.00), "Test Case #21").start();
		System.out.println("Begining Test Runs...");
	}

	private static List<String> read(String filename) {
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