package org.qosmiof2.cooker.data;

public enum Fish {
	Shrimp("Shrimp", 317, 7954, 315), Beef("Beef", 2132, 2146, 2142), Chicken(
			"Chicken", 2138, 2144, 2140), Crayfish("Crayfish", 13435, 13437,
			13433), Anchovies("Anchovies", 321, 323, 319), Sardine("Sardine",
			327, 0, 325), Salmon("Salmon", 329, 0, 331), Trout("Trout", 335, 0,
			333), Cod("Cod", 341, 0, 339), Herring("Herring", 345, 0, 347), Pike(
			"Pike", 349, 0, 351), Tuna("Tuna", 359, 0, 361), Lobster("Lobster",
			377, 0, 379), Shark("Shark", 383, 0, 385), Swordfish("Swordfish",
			371, 372, 373);

	private final String name;
	private final int rawId;
	private final int burntId;
	private final int cookedId;

	private Fish(String name, int rawId, int burntId, int cookedId) {
		this.name = name;
		this.rawId = rawId;
		this.burntId = burntId;
		this.cookedId = cookedId;
	}

	public int getRawId() {
		return rawId;
	}

	public String getName() {
		return name;
	}

	public int getBurntId() {
		return burntId;
	}

	public int getCookedId() {
		return cookedId;
	}
}
