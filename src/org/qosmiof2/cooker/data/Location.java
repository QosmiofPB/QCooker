package org.qosmiof2.cooker.data;

import org.powerbot.script.Tile;

public enum Location {
	AL_KHARID("Al Kharid", new Tile(3274, 3183), new Tile(3270, 3168)), 
	EDGEVILLE("Edgeville", new Tile(3079, 3496), new Tile(3094, 3494)), 
	COOKS_GUILD("Cooks' Guild", new Tile(3145, 3451), new Tile(3144, 3450)), 
	FALADOR("Falador", new Tile(3038, 3364), new Tile(3014, 3355)),
	VARROCK("Varrock", new Tile(3237, 3411), new Tile(3253, 3421));

	private Location(String name, Tile rangeTile, Tile bankTile) {
		this.NAME = name;
		this.BANK_TILE = bankTile;
		this.RANGE_TILE = rangeTile;
	}

	private String NAME;
	private Tile BANK_TILE;
	private Tile RANGE_TILE;

	public Tile getRangeTile() {
		return RANGE_TILE;
	}

	public Tile getBankTile(){
		return BANK_TILE;
	}
	
	public String getName() {
		return NAME;
	}


}
