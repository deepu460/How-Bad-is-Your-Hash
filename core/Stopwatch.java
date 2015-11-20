package core;

public class Stopwatch {

	private long start, stop;

	private boolean started, stopped;

	public Stopwatch() {
		super();
	}

	public void start() {
		start = System.nanoTime();
		started = true;
	}

	public void stop() {
		stop = System.nanoTime();
		stopped = true;
	}

	public void clear() {
		started = false;
		stopped = false;
	}

	public String read() {
		if (!started || !stopped)
			throw new RuntimeException("Error: Stopwatch never stopped or started");
		double time = (stop - start) / 1000000.0;
		String prefix = "";
		if (time >= 1000) {
			prefix += ((long) time / 1000) + " sec ";
			time %= 1000;
		}
		return prefix + String.format("%,.2f ms", time);
	}

	public long raw() {
		if (!started || !stopped)
			throw new RuntimeException("Error: Stopwatch never stopped or started");
		return stop - start;
	}

}