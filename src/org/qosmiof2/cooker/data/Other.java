package org.qosmiof2.cooker.data;

public enum Other {
	
	UncookedPizza("Uncooked pizza", 2287, 1982, 2283, 1985);
	
	private String NAME;
	private int ID;
	private int TOMATO_ID, PIZZA_BASE_ID, CHEESE_ID;
	
	private Other(String name, int id, int tomatoId, int pizzaBaseId, int cheeseId) {
		this.NAME = name;
		this.ID = id;
		this.TOMATO_ID = tomatoId;
		this.PIZZA_BASE_ID = pizzaBaseId;
		this.CHEESE_ID = cheeseId;
	}
	
	public String getName() {
		return NAME;
	}
	public int getId() {
		return ID;
	}

	public int getTomatoId() {
		return TOMATO_ID;
	}

	public int getPizzaBaseId() {
		return PIZZA_BASE_ID;
	}

	public int getCheeseId() {
		return CHEESE_ID;
	}
	
	
}
