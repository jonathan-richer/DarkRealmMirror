package com.client;

import java.util.ArrayList;
import java.util.List;

import com.client.definitions.AnimationDefinition;
import com.client.definitions.ItemDefinition;
import com.client.definitions.NpcDefinition;
import com.client.definitions.GraphicsDefinition;

public final class Player extends Entity {

	@Override
	public Model getRotatedModel() {
		if (!visible)
			return null;
		Model model = method452();
		if (model == null)
			return null;
		super.height = model.modelHeight;
		model.fits_on_single_square = true;
		if (aBoolean1699)
			return model;
		if (super.anInt1520 != -1 && super.anInt1521 != -1) {
			GraphicsDefinition spotAnim = GraphicsDefinition.cache[super.anInt1520];
			Model model_2 = spotAnim.getModel();
			if (model_2 != null) {
				Model model_3 = new Model(true, Class36.method532(super.anInt1521), false, model_2);
				model_3.method475(0, -super.anInt1524, 0);
				model_3.method469();
				model_3.method470(spotAnim.aAnimation_407.anIntArray353[super.anInt1521]);
				model_3.faceGroups = null;
				model_3.vertexGroups = null;
				if (spotAnim.anInt410 != 128 || spotAnim.anInt411 != 128)
					model_3.method478(spotAnim.anInt410, spotAnim.anInt410,
							spotAnim.anInt411);
				// model_3.method479(64 + spotAnim.anInt413, 850 +
				// spotAnim.anInt414, -30, -50, -30, true);
				model_3.method479(84 + spotAnim.anInt413,
						1550 + spotAnim.anInt414, -50, -110, -50, true);
				Model aclass30_sub2_sub4_sub6_1s[] = { model, model_3 };
				model = new Model(aclass30_sub2_sub4_sub6_1s);
			}
		}
		if (aModel_1714 != null) {
			if (Client.loopCycle >= anInt1708)
				aModel_1714 = null;
			if (Client.loopCycle >= anInt1707 && Client.loopCycle < anInt1708) {
				Model model_1 = aModel_1714;
				model_1.method475(anInt1711 - super.x, anInt1712 - anInt1709,
						anInt1713 - super.y);
				if (super.getTurnDirection() == 512) {
					model_1.method473();
					model_1.method473();
					model_1.method473();
				} else if (super.getTurnDirection() == 1024) {
					model_1.method473();
					model_1.method473();
				} else if (super.getTurnDirection() == 1536)
					model_1.method473();
				Model aclass30_sub2_sub4_sub6s[] = { model, model_1 };
				model = new Model(aclass30_sub2_sub4_sub6s);
				if (super.getTurnDirection() == 512)
					model_1.method473();
				else if (super.getTurnDirection() == 1024) {
					model_1.method473();
					model_1.method473();
				} else if (super.getTurnDirection() == 1536) {
					model_1.method473();
					model_1.method473();
					model_1.method473();
				}
				model_1.method475(super.x - anInt1711, anInt1709 - anInt1712,
						super.y - anInt1713);
			}
		}
		model.fits_on_single_square = true;
		return model;
	}
	public int usedItemID;
	public String title;
	public String titleColor;

	public void updatePlayer(Stream stream) {
		stream.currentOffset = 0;
		anInt1702 = stream.readUnsignedByte();
		title = stream.readString();
		titleColor = stream.readString();
		healthState = stream.readUnsignedByte();
		headIcon = stream.readUnsignedByte();
		skullIcon = stream.readUnsignedByte();
		desc = null;
		team = 0;
		for (int j = 0; j < 12; j++) {
			int k = stream.readUnsignedByte();
			if (k == 0) {
				equipment[j] = 0;
				continue;
			}
			int i1 = stream.readUnsignedByte();
			equipment[j] = (k << 8) + i1;
			if (j == 0 && equipment[0] == 65535) {
				desc = NpcDefinition.forID(stream.readUShort());
				break;
			}
			if (equipment[j] >= 512 && equipment[j] - 512 < ItemDefinition.totalItems) {
				int l1 = ItemDefinition.forID(equipment[j] - 512).team;
				if (l1 != 0)
					team = l1;
			}
		}

		for (int l = 0; l < 5; l++) {
			int j1 = stream.readUnsignedByte();
			if (j1 < 0 || j1 >= Client.anIntArrayArray1003[l].length)
				j1 = 0;
			anIntArray1700[l] = j1;
		}

		super.anInt1511 = stream.readUShort();
		if (super.anInt1511 == 65535)
			super.anInt1511 = -1;
		super.anInt1512 = stream.readUShort();
		if (super.anInt1512 == 65535)
			super.anInt1512 = -1;
		super.anInt1554 = stream.readUShort();
		if (super.anInt1554 == 65535)
			super.anInt1554 = -1;
		super.anInt1555 = stream.readUShort();
		if (super.anInt1555 == 65535)
			super.anInt1555 = -1;
		super.anInt1556 = stream.readUShort();
		if (super.anInt1556 == 65535)
			super.anInt1556 = -1;
		super.anInt1557 = stream.readUShort();
		if (super.anInt1557 == 65535)
			super.anInt1557 = -1;
		super.anInt1505 = stream.readUShort();
		if (super.anInt1505 == 65535)
			super.anInt1505 = -1;
		displayName = stream.readString();
		visible = stream.readUnsignedByte() == 0;
		combatLevel = stream.readUnsignedByte();
		rights = PlayerRights.readRightsFromPacket(stream).getRight();
		displayedRights = PlayerRights.getDisplayedRights(rights);
		skill = stream.readUShort();
		aLong1718 = 0L;
		for (int k1 = 0; k1 < 12; k1++) {
			aLong1718 <<= 4;
			if (equipment[k1] >= 256)
				aLong1718 += equipment[k1] - 256;
		}

		if (equipment[0] >= 256)
			aLong1718 += equipment[0] - 256 >> 4;
		if (equipment[1] >= 256)
			aLong1718 += equipment[1] - 256 >> 8;
		for (int i2 = 0; i2 < 5; i2++) {
			aLong1718 <<= 3;
			aLong1718 += anIntArray1700[i2];
		}

		aLong1718 <<= 1;
		aLong1718 += anInt1702;
	}

	public Model method452() {
		if (desc != null) {
			int j = -1;
			if (super.anim >= 0 && super.anInt1529 == 0)
				j = AnimationDefinition.anims[super.anim].anIntArray353[super.animFrameIndex];
			else if (super.anInt1517 >= 0)
				j = AnimationDefinition.anims[super.anInt1517].anIntArray353[super.anInt1518];
			Model model = desc.method164(-1, j, null);
			return model;
		}
		long l = aLong1718;
		int k = -1;
		int i1 = -1;
		int j1 = -1;
		int k1 = -1;
		if (super.anim >= 0 && super.anInt1529 == 0) {
			AnimationDefinition animation = AnimationDefinition.anims[super.anim];
			k = animation.anIntArray353[super.animFrameIndex];
			if (super.anInt1517 >= 0 && super.anInt1517 != super.anInt1511)
				i1 = AnimationDefinition.anims[super.anInt1517].anIntArray353[super.anInt1518];
			if (animation.anInt360 >= 0) {
				j1 = animation.anInt360;
				l += j1 - equipment[5] << 40;
			}
			if (animation.anInt361 >= 0) {
				k1 = animation.anInt361;
				l += k1 - equipment[3] << 48;
			}
		} else if (super.anInt1517 >= 0)
			k = AnimationDefinition.anims[super.anInt1517].anIntArray353[super.anInt1518];
		Model model_1 = (Model) mruNodes.insertFromCache(l);
		if (model_1 == null) {
			boolean flag = false;
			for (int i2 = 0; i2 < 12; i2++) {
				int k2 = equipment[i2];
				if (k1 >= 0 && i2 == 3)
					k2 = k1;
				if (j1 >= 0 && i2 == 5)
					k2 = j1;
				if (k2 >= 256 && k2 < 512 && !IDK.cache[k2 - 256].method537())
					flag = true;
				if (k2 >= 512 && !ItemDefinition.forID(k2 - 512).method195(anInt1702))
					flag = true;
			}

			if (flag) {
				if (aLong1697 != -1L)
					model_1 = (Model) mruNodes.insertFromCache(aLong1697);
				if (model_1 == null)
					return null;
			}
		}
		if (model_1 == null) {
			Model aclass30_sub2_sub4_sub6s[] = new Model[12];
			int j2 = 0;
			for (int l2 = 0; l2 < 12; l2++) {
				int i3 = equipment[l2];
				if (k1 >= 0 && l2 == 3)
					i3 = k1;
				if (j1 >= 0 && l2 == 5)
					i3 = j1;
				if (i3 >= 256 && i3 < 512) {
					Model model_3 = IDK.cache[i3 - 256].method538();
					if (model_3 != null)
						aclass30_sub2_sub4_sub6s[j2++] = model_3;
				}
				if (i3 >= 512) {
					Model model_4 = ItemDefinition.forID(i3 - 512)
							.method196(anInt1702);
					if (model_4 != null)
						aclass30_sub2_sub4_sub6s[j2++] = model_4;
				}
			}

			model_1 = new Model(j2, aclass30_sub2_sub4_sub6s);
			for (int j3 = 0; j3 < 5; j3++)
				if (anIntArray1700[j3] != 0) {
					model_1.replaceColor(Client.anIntArrayArray1003[j3][0],
							Client.anIntArrayArray1003[j3][anIntArray1700[j3]]);
					if (j3 == 1)
						model_1.replaceColor(Client.anIntArray1204[0],
								Client.anIntArray1204[anIntArray1700[j3]]);
				}

			model_1.method469();
			model_1.light(64, 850, -30, -50, -30, true);
			//model_1.method479(84, 1000, -90, -580, -90, true);
			mruNodes.removeFromCache(model_1, l);
			aLong1697 = l;
		}
		if (aBoolean1699)
			return model_1;
		Model model_2 = Model.EMPTY_MODEL;
		model_2.method464(model_1, Class36.method532(k) & Class36.method532(i1));
		if (k != -1 && i1 != -1)
			model_2.method471(AnimationDefinition.anims[super.anim].anIntArray357, i1, k);
		else if (k != -1)
			model_2.method470(k);
		model_2.calculateDistances();
		model_2.faceGroups = null;
		model_2.vertexGroups = null;
		return model_2;
	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	public int privelage;

	public boolean isAdminRights() {
		return hasRights(PlayerRights.ADMINISTRATOR)
				|| hasRights(PlayerRights.OWNER)
				|| hasRights(PlayerRights.GAME_DEVELOPER);
	}

	public boolean hasRightsOtherThan(PlayerRights playerRight) {
		return PlayerRights.hasRightsOtherThan(rights, playerRight);
	}

	public boolean hasRights(PlayerRights playerRights) {
		return PlayerRights.hasRights(rights, playerRights);
	}

	public boolean hasRightsLevel(int rightsId) {
		return PlayerRights.hasRightsLevel(rights, rightsId);
	}

	public boolean hasRightsBetween(int low, int high) {
		return PlayerRights.hasRightsBetween(rights, low, high);
	}

	public Model method453() {
		if (!visible)
			return null;
		if (desc != null)
			return desc.method160();
		boolean flag = false;
		for (int i = 0; i < 12; i++) {
			int j = equipment[i];
			if (j >= 256 && j < 512 && !IDK.cache[j - 256].method539())
				flag = true;
			if (j >= 512 && !ItemDefinition.forID(j - 512).method192(anInt1702))
				flag = true;
		}

		if (flag)
			return null;
		Model aclass30_sub2_sub4_sub6s[] = new Model[12];
		int k = 0;
		for (int l = 0; l < 12; l++) {
			int i1 = equipment[l];
			if (i1 >= 256 && i1 < 512) {
				Model model_1 = IDK.cache[i1 - 256].method540();
				if (model_1 != null)
					aclass30_sub2_sub4_sub6s[k++] = model_1;
			}
			if (i1 >= 512) {
				Model model_2 = ItemDefinition.forID(i1 - 512).method194(anInt1702);
				if (model_2 != null)
					aclass30_sub2_sub4_sub6s[k++] = model_2;
			}
		}

		Model model = new Model(k, aclass30_sub2_sub4_sub6s);
		for (int j1 = 0; j1 < 5; j1++)
			if (anIntArray1700[j1] != 0) {
				model.replaceColor(Client.anIntArrayArray1003[j1][0],
						Client.anIntArrayArray1003[j1][anIntArray1700[j1]]);
				if (j1 == 1)
					model.replaceColor(Client.anIntArray1204[0],
							Client.anIntArray1204[anIntArray1700[j1]]);
			}

		return model;
	}

	Player() {
		aLong1697 = -1L;
		aBoolean1699 = false;
		anIntArray1700 = new int[5];
		visible = false;
		equipment = new int[12];
	}

	public boolean inFlowerPokerArea() {
		int x = getAbsoluteX();
		int y = getAbsoluteY();
		//return x >= 3109 && y >= 3504 && x <= 3121 && y <= 3515;
		return x >= 3107 && y >= 3485 && x <= 3114 && y <= 3498;
	}

	public boolean inFlowerPokerChatProximity() {
		int x = getAbsoluteX();
		int y = getAbsoluteY();
		//return x >= 3106 && y >= 3502 && x <= 3123 && y <= 3517;
		return x >= 3108 && y >= 3487 && x <= 3112 && y <= 3497;
	}

	public PlayerRights[] getRights() {
		return rights;
	}

	public List<PlayerRights> getDisplayedRights() {
		return displayedRights;
	}

	public int getHealthState() {
		return healthState;
	}

	private PlayerRights[] rights = new PlayerRights[] {PlayerRights.PLAYER};
	private List<PlayerRights> displayedRights = new ArrayList<>();
	private long aLong1697;
	public NpcDefinition desc;
	boolean aBoolean1699;
	final int[] anIntArray1700;
	public int team;
	private int anInt1702;
	public String displayName;
	static MRUNodes mruNodes = new MRUNodes(260);
	public int combatLevel;
	public int headIcon;
	public int skullIcon;
	public int hintIcon;
	public int anInt1707;
	int anInt1708;
	int anInt1709;
	boolean visible;
	int anInt1711;
	int anInt1712;
	int anInt1713;
	Model aModel_1714;
	public final int[] equipment;
	private long aLong1718;
	int anInt1719;
	int anInt1720;
	int anInt1721;
	int anInt1722;
	int skill;
	private int healthState;

}
