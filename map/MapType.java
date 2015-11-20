package map;

public enum MapType {

	// Linear, Quadratic, Random, and Chained respectively
	L() {
		@Override
		public String toString() {
			return "Linear Map";
		}
	},
	Q() {
		@Override
		public String toString() {
			return "Quadratic Map";
		}
	},
	R() {
		@Override
		public String toString() {
			return "Random Map";
		}
	},
	C() {
		@Override
		public String toString() {
			return "Chained Map";
		}
	};

	// Useful for looping through the various map types
	public static final MapType[] TYPES = { L, Q, R, C };

}