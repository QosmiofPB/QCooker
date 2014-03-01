package org.qosmiof2.cooker.data;

public enum Fish {
	Beef("Beef", 2132, 2146),
	Chicken("Chicken", 2138, 2144),
	CRAYFISH("Crayfish", 13435, 13437),
	SHRIMP("Shrimp", 317, 7954),
	ANCHOVIES("Anchovies", 321, 323),
	SARDINE("Sardine", 327, 0),
	SALMON("Salmon", 331, 0),
	TROUT("Trout", 335, 0),
	COD("Cod", 341, 0),
	HERRING("Herring", 345, 0),
	PIKE("Pike", 349, 0),
	TUNA("Tuna", 359, 0),
	LOBSTER("Lobster", 377, 0),
	SHARK("Shark", 383, 0),
	SWORDFISH("Swordfish", 371, 372);

	private final String NAME;
	private final int RAW_ID;
	private final int BURNT_ID;

	private Fish(String name, int raw_id, int burnt_id) {
		this.NAME = name;
		this.RAW_ID = raw_id;
		this.BURNT_ID = burnt_id;
	}

	public int getRawId() {
		return RAW_ID;
	}

	public String getName() {
		return NAME;
	}

	public int getBURNT_ID() {
		return BURNT_ID;
	}
}
