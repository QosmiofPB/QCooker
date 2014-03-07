package org.qosmiof2.cooker.data;

public enum Fish {
	SHRIMP("Shrimp", 317, 7954, 315),
	BEEF("Beef", 2132, 2146, 2142),
	CHICKEN("Chicken", 2138, 2144, 2140),
	CRAYFISH("Crayfish", 13435, 13437, 13433),
	ANCHOVIES("Anchovies", 321, 323, 319),
	SARDINE("Sardine", 327, 0, 325),
	SALMON("Salmon", 329, 0, 331),
	TROUT("Trout", 335, 0, 333),
	COD("Cod", 341, 0, 339),
	HERRING("Herring", 345, 0, 347),
	PIKE("Pike", 349, 0, 351),
	TUNA("Tuna", 359, 0, 361),
	LOBSTER("Lobster", 377, 0, 379),
	SHARK("Shark", 383, 0, 385),
	SWORDFISH("Swordfish", 371, 372, 373);

	private final String NAME;
	private final int RAW_ID;
	private final int BURNT_ID;
	private final int COOKED_ID;

	private Fish(String name, int raw_id, int burnt_id, int cooked_id) {
		this.NAME = name;
		this.RAW_ID = raw_id;
		this.BURNT_ID = burnt_id;
		this.COOKED_ID = cooked_id;
	}

	public int getRawId() {
		return RAW_ID;
	}

	public String getName() {
		return NAME;
	}

	public int getBurntId() {
		return BURNT_ID;
	}

	public int getCookedId() {
		return COOKED_ID;
	}
}
