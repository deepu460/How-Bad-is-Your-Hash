package core;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Stopwatch {

	private Instant start, now;

	private List<Instant> laps;

	public Stopwatch() {
		super();
		laps = new ArrayList<>();
	}

	public void start() {
		start = Instant.now();
	}

	public void stop() {
		now = Instant.now();
	}

	public void lap() {
		laps.add(Instant.now());
	}

	public void clear() {
		start = null;
		now = null;
		laps.clear();
	}

	public String[] readLaps() {
		if (now == null)
			throw new RuntimeException("Error: Stopwatch never stopped");
		List<String> l = new ArrayList<>();
		for (Instant i : laps) {
			Duration d = Duration.between(i, now);
			l.add(String.format("%s s %s ns", d.getSeconds(), d.getNano()));
		}
		return l.toArray(new String[l.size()]);
	}

	public String read() {
		if (now == null || start == null)
			throw new RuntimeException("Error: Stopwatch never stopped or started");
		Duration d = Duration.between(start, now);
		return String.format("%,d s %,d ns", d.getSeconds(), d.getNano());
	}

}