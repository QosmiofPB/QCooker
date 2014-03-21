package org.qosmiof2.cooker.data;

import org.powerbot.script.util.Random;

public enum Location {
	AL_KHARID("Al Kharid", 3274, 3183, 0, (3270 +- 2), (3168 +- Random.nextInt(1, 3)), 0), 
	EDGEVILLE("Edgeville", 3079, 3496, 0, 3094, 3494, 0), 
	COOKS_GUILD("Cooks' Guild", 3145, 3451, 0, 3144, 3450, 0), 
	FALADOR("Falador", 3038, 3364, 0, 3014, 3355, 0),
	VARROCK("Varrock", 3237, 3411, 0, 3253, 3421, 0);

	private Location(String name, int x, int y, int z, int bank_x, int bank_y, int bank_z) {
		this.NAME = name;
		this.X = x;
		this.Y = y;
		this.Z = z;
		this.BANK_X = bank_x;
		this.BANK_Y = bank_y;
		this.BANK_Z = bank_z;
	}

	private String NAME;
	private int X;
	private int Y;
	private int Z;
	private int BANK_X;
	private int BANK_Y;
	private int BANK_Z;

	public int getX() {
		return X;
	}

	public int getY() {
		return Y;
	}

	public int getZ() {
		return Z;
	}

	public String getName() {
		return NAME;
	}
	
	public int getBankX() {
		return BANK_X;
	}

	public int getBankY() {
		return BANK_Y;
	}

	public int getBankZ() {
		return BANK_Z;
	}

}
