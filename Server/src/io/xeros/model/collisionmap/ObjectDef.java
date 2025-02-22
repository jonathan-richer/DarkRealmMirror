package io.xeros.model.collisionmap;

import io.xeros.Server;

import java.io.IOException;

public final class ObjectDef {



	public static ObjectDef getObjectDef(int id) {
		if (id > streamIndices.length) {
			//System.out.println("Object id out of range: " + id);
			id = streamIndices.length - 1;
		}

		for (int j = 0; j < 20; j++) {
			if (cache[j].type == id) {
				return cache[j];
			}
		}

		cacheIndex = (cacheIndex + 1) % 20;
		ObjectDef class46 = cache[cacheIndex];

		if (id > streamIndices.length - 1 || id < 0) {
			return null;
		}

		stream.currentOffset = streamIndices[id];

		class46.type = id;
		class46.setDefaults();
		class46.readValues(stream);

		return class46;
	}

	private void setDefaults() {
		anIntArray773 = null;
		anIntArray776 = null;
		name = null;
		description = null;
		modifiedModelColors = null;
		originalModelColors = null;
		xLength = 1;
		yLength = 1;
		aBoolean767 = true;
		aBoolean757 = true;
		hasActions = false;
		aBoolean762 = false;
		aBoolean769 = false;
		aBoolean764 = false;
		anInt781 = -1;
		anInt775 = 16;
		aByte737 = 0;
		aByte742 = 0;
		actions = null;
		anInt746 = -1;
		anInt758 = -1;
		aBoolean751 = false;
		aBoolean779 = true;
		anInt748 = 128;
		anInt772 = 128;
		anInt740 = 128;
		anInt768 = 0;
		anInt738 = 0;
		anInt745 = 0;
		anInt783 = 0;
		aBoolean736 = false;
		aBoolean766 = false;
		anInt760 = -1;
		anInt774 = -1;
		anInt749 = -1;
		childrenIDs = null;
	}

	static ByteStreamExt stream;

	private static int[] streamIndices;

	public static void loadConfig() throws IOException {
		stream = new ByteStreamExt(getBuffer("loc.dat"));
		ByteStreamExt stream = new ByteStreamExt(getBuffer("loc.idx"));
		int objects = stream.readUnsignedWord();
		System.out.println("Total objects: " + objects);
		totalObjects = objects;
		streamIndices = new int[objects];
		int i = 2;
		for (int j = 0; j < objects; j++) {
			streamIndices[j] = i;
			i += stream.readUnsignedWord();
		}
		cache = new ObjectDef[20];
		for (int k = 0; k < 20; k++) {
			cache[k] = new ObjectDef();
		}
		System.out.println(objects + " Object definitions loaded.");
	}

	public static byte[] getBuffer(String s) {
		try {
			java.io.File f = new java.io.File(Server.getDataDirectory() + "/mapdata/" + s);
			if (!f.exists())
				return null;
			byte[] buffer = new byte[(int) f.length()];
			try (java.io.DataInputStream dis = new java.io.DataInputStream(new java.io.FileInputStream(f))) {
				dis.readFully(buffer);
				dis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return buffer;
		} catch (Exception e) {
		}
		return null;
	}

	/*
	 * private void readValues(ByteStreamExt buffer) { int i = -1; label0: do {
	 * int opcode; do { opcode = buffer.readUnsignedByte(); if (opcode == 0)
	 * break label0; if (opcode == 1) { int modelLength =
	 * buffer.readUnsignedByte(); anIntArray773 = new int[modelLength];
	 * anIntArray776 = new int[modelLength]; for(int i2 = 0; i2 < modelLength;
	 * i2++) { anIntArray773[i2] = buffer.readUnsignedWord(); anIntArray776[i2]
	 * = buffer.readUnsignedByte(); } } else if (opcode == 2) name =
	 * buffer.readString(); else if (opcode == 3) description =
	 * buffer.readBytes(); else if (opcode == 5) { int length =
	 * buffer.readUnsignedByte(); anIntArray776 = null; anIntArray773 = new
	 * int[length]; for (int l1 = 0; l1 < length; l1++) anIntArray773[l1] =
	 * buffer.readUnsignedWord(); } else if (opcode == 14) anInt744 =
	 * buffer.readUnsignedByte(); else if (opcode == 15) anInt761 =
	 * buffer.readUnsignedByte(); else if (opcode == 17) aBoolean767 = false;
	 * else if (opcode == 18) aBoolean757 = false; else if (opcode == 19) { i =
	 * buffer.readUnsignedByte(); if (i == 1) hasActions = true; } else if
	 * (opcode == 21) aBoolean762 = true; else if (opcode == 22) aBoolean769 =
	 * true; else if (opcode == 23) aBoolean764 = true; else if (opcode == 24) {
	 * anInt781 = buffer.readUnsignedWord(); if (anInt781 == 65535) anInt781 =
	 * -1; } else if (opcode == 28) anInt775 = buffer.readUnsignedByte(); else
	 * if (opcode == 29) aByte737 = buffer.readSignedByte(); else if (opcode ==
	 * 39) aByte742 = buffer.readSignedByte(); else if (opcode >= 30 && opcode <
	 * 39) { if (actions == null) actions = new String[10]; actions[opcode - 30]
	 * = buffer.readString(); if (actions[opcode -
	 * 30].equalsIgnoreCase("hidden")) actions[opcode - 30] = null; } else if
	 * (opcode == 40) { int i1 = buffer.readUnsignedByte(); originalModelColors
	 * = new int[i1]; modifiedModelColors = new int[i1]; for (int i2 = 0; i2 <
	 * i1; i2++) { modifiedModelColors[i2] = buffer.readUnsignedWord();
	 * originalModelColors[i2] = buffer.readUnsignedWord(); } } else if (opcode
	 * == 60) anInt746 = buffer.readUnsignedWord(); else if (opcode == 62)
	 * aBoolean751 = true; else if (opcode == 64) aBoolean779 = false; else if
	 * (opcode == 65) anInt748 = stream.readUnsignedWord(); else if (opcode ==
	 * 66) anInt772 = stream.readUnsignedWord(); else if (opcode == 67) anInt740
	 * = stream.readUnsignedWord(); else if (opcode == 68) anInt758 =
	 * buffer.readUnsignedWord(); else if (opcode == 69) anInt768 =
	 * buffer.readUnsignedByte(); else if (opcode == 70) anInt738 =
	 * buffer.readSignedWord(); else if (opcode == 71) anInt745 =
	 * buffer.readSignedWord(); else if (opcode == 72) anInt783 =
	 * buffer.readSignedWord(); else if (opcode == 73) aBoolean736 = true; else
	 * if (opcode == 74) { aBoolean766 = true; } else { if (opcode != 75)
	 * continue; anInt760 = buffer.readUnsignedByte(); } continue label0; }
	 * while (opcode != 77); anInt774 = buffer.readUnsignedWord(); if (anInt774
	 * == 65535) anInt774 = -1; anInt749 = buffer.readUnsignedWord(); if
	 * (anInt749 == 65535) anInt749 = -1; int j1 = buffer.readUnsignedByte();
	 * childrenIDs = new int[j1 + 1]; for (int j2 = 0; j2 <= j1; j2++) {
	 * childrenIDs[j2] = buffer.readUnsignedWord(); if (childrenIDs[j2] ==
	 * 65535) childrenIDs[j2] = -1; }
	 *
	 * } while (true); if (i == -1) { hasActions = anIntArray773 != null &&
	 * (anIntArray776 == null || anIntArray776[0] == 10); if (actions != null)
	 * hasActions = true; } if (aBoolean766) { aBoolean767 = false; aBoolean757
	 * = false; } if (anInt760 == -1) anInt760 = aBoolean767 ? 1 : 0; }
	 */

	private static int objects = 0;
	private int opcode61;
	private String opcode150;
	private boolean field3621;
	private void readValues(ByteStreamExt buffer) {
		int flag = -1;
		do {
			int type = stream.readUnsignedByte();
			if (type == 0)
				break;
			if (type == 1) {
				int len = stream.readUnsignedByte();
				if (len > 0) {
					if (anIntArray773 == null || lowMem) {
						anIntArray776 = new int[len];
						anIntArray773 = new int[len];
						for (int k1 = 0; k1 < len; k1++) {
							anIntArray773[k1] = stream.readUnsignedWord();
							anIntArray776[k1] = stream.readUnsignedByte();
						}
					} else {
						stream.currentOffset += len * 3;
					}
				}
			} else if (type == 2)
				name = stream.readString();
			else if (type == 3)
				description = String.valueOf(stream.readBytes());
			else if (type == 5) {
				int len = stream.readUnsignedByte();
				if (len > 0) {
					if (anIntArray773 == null || lowMem) {
						anIntArray776 = null;
						anIntArray773 = new int[len];
						for (int l1 = 0; l1 < len; l1++)
							anIntArray773[l1] = stream.readUnsignedWord();
					} else {
						stream.currentOffset += len * 2;
					}
				}
			} else if (type == 14)
				//objectSizeX = stream.readUnsignedByte();
				xLength = buffer.readUnsignedByte();
			else if (type == 15)
			//	objectSizeY = stream.readUnsignedByte();
				yLength = buffer.readUnsignedByte();
			else if (type == 17)
				aBoolean767 = false;
			else if (type == 18)
				aBoolean757 = false;
			else if (type == 19)
				hasActions = (stream.readUnsignedByte() == 1);
			else if (type == 21)
				aBoolean762 = true;
			else if (type == 22) {
			} else if (type == 23)
				aBoolean764 = true;
			else if (type == 24) {
				anInt781 = stream.readUnsignedWord();
				if (anInt781 == 65535)
					anInt781 = -1;
			} else if (type == 28)
				anInt775 = stream.readUnsignedByte();
			else if (type == 29)
				stream.readSignedByte();
			else if (type == 39)
				stream.readSignedByte();
			else if (type >= 30 && type < 39) {
				if (actions == null)
					actions = new String[5];
				actions[type - 30] = stream.readString();
				if (actions[type - 30].equalsIgnoreCase("hidden"))
					actions[type - 30] = null;
			} else if (type == 40) {
				int i1 = stream.readUnsignedByte();
				modifiedModelColors = new int[i1];
				originalModelColors = new int[i1];
				for (int i2 = 0; i2 < i1; i2++) {
					modifiedModelColors[i2] = stream.readUnsignedWord();
					originalModelColors[i2] = stream.readUnsignedWord();
				}

			} else if (type == 61)
				opcode61 = stream.readUnsignedWord();
			else if (type == 62)
				aBoolean751 = true;
			else if (type == 64){
				aBoolean779 = false;
			} else if (type == 65)
				stream.readUnsignedWord();
			else if (type == 66)
				stream.readUnsignedWord();
			else if (type == 67)
				stream.readUnsignedWord();
			else if (type == 68)
				anInt758 = stream.readUnsignedWord();
			else if (type == 69)
				anInt768 = stream.readUnsignedByte();
			else if (type == 70)
				stream.readSignedWord();
			else if (type == 71)
				stream.readSignedWord();
			else if (type == 72)
				stream.readSignedWord();
			else if (type == 73)
				aBoolean736 = true;
			else if (type == 74)
				aBoolean766 = true;
			else if (type == 75)
				anInt760 = stream.readUnsignedByte();
			else if (type == 77|| type == 92) {
				anInt774 = stream.readUnsignedWord();
				if (anInt774 == 65535)
					anInt774 = -1;
				anInt749 = stream.readUnsignedWord();
				if (anInt749 == 65535)
					anInt749 = -1;
				int var3 = -1;
				if(type == 92) {
					var3 = stream.readUnsignedWord();
				}
				int j1 = stream.readUnsignedByte();
				childrenIDs = new int[j1 + 2];
				for (int j2 = 0; j2 <= j1; j2++) {
					childrenIDs[j2] = stream.readUnsignedWord();
					if (childrenIDs[j2] == 65535)
						childrenIDs[j2] = -1;
				}
				childrenIDs[j1 + 1] = var3;
			} else if(type == 78){
				stream.skip(3);
			} else if(type == 79) {
				stream.skip(5);
				int count = stream.readSignedByte();
				stream.skip(2 * count);
			} else if(type == 81) {
				stream.skip(1);//Clip type?
			} else if (type == 82) {
				anInt746 = stream.readUnsignedWord();//AreaType
			} else if(type == 89) {
				field3621 = false;
			} else if (type == 249) {//94
				opcode150 = stream.readString();
			}
		} while (true);
		if (flag == -1 && name != "null" && name != null) {
			hasActions = anIntArray773 != null && (anIntArray776 == null || anIntArray776[0] == 10);
			if (actions != null)
				hasActions = true;
		}
		if (aBoolean766) {
			aBoolean767 = false;
			aBoolean757 = false;
		}
		if (anInt760 == -1)
			anInt760 = aBoolean767 ? 1 : 0;
	}

	private ObjectDef() {
		type = -1;
	}

	public boolean hasActions() {
		return hasActions;
	}

	public boolean hasName() {
		return name != null && name.length() > 1;
	}

	public boolean solid() {
		return aBoolean779;
	}

	public int xLength() {
		return xLength;
	}

	public int yLength() {
		return yLength;
	}

	public boolean aBoolean767() {
		return aBoolean767;
	}

	private boolean aBoolean766;
	public boolean aBoolean736;
	public String name;
	public int xLength;
	public int anInt746;
	private int[] originalModelColors;
	public int anInt749;
	public static boolean lowMem;
	public int type;
	public boolean aBoolean757;
	public int anInt758;
	public int[] childrenIDs;
	public int yLength;
	public boolean aBoolean762;
	public boolean aBoolean764;
	public boolean aBoolean767;
	public int anInt768;
	private static int cacheIndex;
	private int[] anIntArray773;
	public int anInt774;
	public int anInt775;
	public int anInt738;
	public int anInt740; // Width
	public int anInt745;
	public int anInt783;
	public int anInt772; // Height
	private int[] anIntArray776;
	public int anInt760;
	public String description;
	public boolean hasActions;
	public boolean aBoolean779;
	public byte aByte737;
	public byte aByte742;
	public boolean aBoolean769;
	public int anInt748; // Thickness
	public boolean aBoolean751;
	public int anInt781;
	public static int totalObjects;
	private static ObjectDef[] cache;
	private int[] modifiedModelColors;
	public String[] actions;

}
