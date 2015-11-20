package core;

import map.MapType;

public class TrialData {

	private MapType type;

	private double load;

	private long size, collisions, time, ssr, usr;

	private long probes_ssr, probes_usr;

	public TrialData(MapType type, double load, long size, long collisions, long time, long ssr, long usr,
			long probes_ssr, long probes_usr) {
		super();
		this.type = type;
		this.load = load;
		this.size = size;
		this.collisions = collisions;
		this.time = time;
		this.ssr = ssr;
		this.usr = usr;
		this.probes_ssr = probes_ssr;
		this.probes_usr = probes_usr;
	}

	public MapType getType() {
		return type;
	}

	public double getLoad() {
		return load;
	}

	public long getSize() {
		return size;
	}

	public long getCollisions() {
		return collisions;
	}

	public long getTime() {
		return time;
	}

	public long getSsr() {
		return ssr;
	}

	public long getUsr() {
		return usr;
	}

	public long getProbes_ssr() {
		return probes_ssr;
	}

	public long getProbes_usr() {
		return probes_usr;
	}

	@Override
	public String toString() {
		return "TrialData [type=" + type + ", load=" + load + ", size=" + size + ", collisions=" + collisions
				+ ", time=" + time + ", ssr=" + ssr + ", usr=" + usr + "]";
	}

}