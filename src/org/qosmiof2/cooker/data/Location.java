package org.qosmiof2.cooker.data;

public enum Location {
	AL_KHARID("Al Kharid", 3274, 3183, 0),
	EDGEVILLE("Edgeville", 3079, 3496, 0);
	
	private Location(String name, int x, int y, int z) {
		this.NAME = name;
		this.X = x;
		this.Y = y;
		this.Z = z;
	}
	private String NAME;
	private int X;
	private int Y;
	private int Z;
	
	public int getX() {
		return X;
	}
	public int getY() {
		return Y;
	}
	public int getZ() {
		return Z;
	}
	public String getNAME() {
		return NAME;
	}
	
	

}
