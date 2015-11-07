package core;

import static java.lang.System.out;

import java.io.File;
import java.util.Scanner;

import map.LinearMap;
import map.Map;

public class WhoAmI {

	public static void main(String[] args) throws Exception {
		// Read + Map out students
		Scanner s = null;
		s = new Scanner(new File("res/students.txt"));
		Map<String, Student> map = new LinearMap<>(5);
		long z = System.currentTimeMillis();
		while (s.hasNextLine()) {
			String[] a = s.nextLine().split("\t");
			map.put(a[0], new Student(a[0], a[1], a[2], a[3]));
		}
		long x = System.currentTimeMillis();
		out.printf("Loaded students in %,d ms%n", x - z);
		out.printf("Avg Loading time is %,.2f ms%n", (x - z) / (double) map.size());
		s.close();
		s = new Scanner(System.in);
		// Greeting Message
		out.println("\nStudent Database Explorer Alpha V0.1");
		out.println("Type \"help\" for help");
		// Menu loop
		while (true) {
			out.print("> ");
			String a[] = s.nextLine().split(" ");
			long t = System.currentTimeMillis();
			if (a[0].equals("exit"))
				break;
			else if (a[0].equals("help")) {
				out.println("\"find [name] | (filter - optional)\" - finds someone's data");
				out.println("\"print all\" - prints all students in directory");
				out.println("\"add\" - Adds a student");
				out.println("\"remove [name]\" - removes a student");
				out.println("\"exit\" - closes program");
				out.println("\"size\" - size of student directory");
			} // Prints out size of students in directory
			else if (a[0].equals("size"))
				out.println("There are " + map.size() + " students...");
			// Adds a student
			else if (a[0].equals("add")) {
				String name, email, phone;
				out.print("Name?> ");
				name = s.nextLine();
				out.print("Phone?>");
				phone = s.nextLine();
				out.print("Email?> ");
				email = s.nextLine();
				out.print("SSN?> ");
				map.put(name, new Student(name, phone, email, s.nextLine()));
			} else if (a.length > 1)
				// Searches for a person w/ filters
				if (a[0].equals("find")) {
					String l = "";
					for (int i = 1; i < a.length; i++)
						if (a[i].equals("|"))
							break;
						else
							l += a[i] + " ";
					l = l.trim();
					if (map.contains(l)) {
						out.println("Found match: ");
						if (a[a.length - 2].equals("|"))
							switch (a[a.length - 1]) {
							// Email filters
							case "mail":
							case "email":
								out.println(map.get(l).getEmail());
								break;
							// Phone # filters
							case "phone":
							case "number":
								out.println(map.get(l).getNumber());
								break;
							// SSN filters
							case "SSN":
							case "ssn":
							case "ID":
							case "id":
								out.println(map.get(l).getId());
								break;
							// Name filter
							case "name":
								out.println(map.get(l).getName());
								break;
							// Invalid filters
							default:
								out.println("Invalid Filter!");
								break;
							}
						else
							out.println(map.get(l));
					} else
						out.println("No matches for " + l);

				} // Removes a student
				else if (a[0].equals("remove")) {
					String l = "";
					for (int i = 1; i < a.length; i++)
						if (a[i].equals("|"))
							break;
						else
							l += a[i] + " ";
					l = l.trim();
					if (map.contains(l)) {
						out.println("Removing " + l);
						map.remove(l);
					} else
						out.println("Can't find " + l);
				} // Prints off all students
				else if (a[0].equals("print") && a[1].equals("all"))
					map.forEach(h -> out.println(h));
			out.printf("Completed Operation in %,d ms%n", System.currentTimeMillis() - t);
		}
		s.close();
	}
}