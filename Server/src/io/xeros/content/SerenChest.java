package io.xeros.content;

import io.xeros.Server;
import io.xeros.content.event.eventcalendar.EventChallenge;
import io.xeros.content.item.lootable.LootRarity;
import io.xeros.content.item.lootable.Lootable;
import io.xeros.model.definitions.ItemDef;
import io.xeros.model.entity.player.Player;
import io.xeros.model.entity.player.PlayerHandler;
import io.xeros.model.entity.player.Right;
import io.xeros.model.items.GameItem;
import io.xeros.model.items.ItemAssistant;
import io.xeros.util.Misc;
import io.xeros.util.discord.Discord;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The seren chest.
 *
 * @author C.T for koranes
 *
 */


public class SerenChest implements Lootable {

	public static int KEY = 6792;
	public static final int ANIMATION = 881;

	private static final Map<LootRarity, List<GameItem>> items = new HashMap<>();

	static {
		items.put(LootRarity.COMMON, Arrays.asList(

				new GameItem(21046, 4),//15% chest rate tomb

				new GameItem(995, 240000), //coins
				new GameItem(2996, 35),//pkp tickets
				new GameItem(11230, 12),//dragon darts
				new GameItem(20849, 32),//dragon thrownaxe
				new GameItem(22804, 35),//dragon knife
				new GameItem(537, 5 + Misc.random(30)),//dragon bones
				new GameItem(1306, 3),//dragon longsword
				new GameItem(1080, 6),//rune platelegs
				new GameItem(1128, 6),//rune platebody
				new GameItem(4087, 1),//dragon platelegs
				new GameItem(4585, 1),//dragon plateskirt
				new GameItem(4151, 1),//whip
				new GameItem(23804, 1),//imbuedifier
				new GameItem(11232, 50 + Misc.random(10)),//dragon darts
				new GameItem(11840),//dragon boots
				new GameItem(6889),//mages book
				new GameItem(2364, 100),//runite bar
				new GameItem(1514, 100),// magic logs
				new GameItem(1632, 50),//uncut dragonstone

				new GameItem(995, 240000), //coins
				new GameItem(2996, 35),//pkp tickets
				new GameItem(11230, 12),//dragon darts
				new GameItem(20849, 32),//dragon thrownaxe
				new GameItem(22804, 35),//dragon knife
				new GameItem(537, 5 + Misc.random(30)),//dragon bones
				new GameItem(1306, 3),//dragon longsword
				new GameItem(1080, 6),//rune platelegs
				new GameItem(1128, 6),//rune platebody
				new GameItem(4087, 1),//dragon platelegs
				new GameItem(4585, 1),//dragon plateskirt
				new GameItem(4151, 1),//whip
				new GameItem(23804, 1),//imbuedifier
				new GameItem(11232, 50 + Misc.random(10)),//dragon darts
				new GameItem(11840),//dragon boots
				new GameItem(6889),//mages book
				new GameItem(2364, 100),//runite bar
				new GameItem(1514, 100),// magic logs
				new GameItem(1632, 50),//uncut dragonstone

				new GameItem(995, 240000), //coins
				new GameItem(2996, 35),//pkp tickets
				new GameItem(11230, 12),//dragon darts
				new GameItem(20849, 32),//dragon thrownaxe
				new GameItem(22804, 35),//dragon knife
				new GameItem(537, 5 + Misc.random(30)),//dragon bones
				new GameItem(1306, 3),//dragon longsword
				new GameItem(1080, 6),//rune platelegs
				new GameItem(1128, 6),//rune platebody
				new GameItem(4087, 1),//dragon platelegs
				new GameItem(4585, 1),//dragon plateskirt
				new GameItem(4151, 1),//whip
				new GameItem(23804, 1),//imbuedifier
				new GameItem(11232, 50 + Misc.random(10)),//dragon darts
				new GameItem(11840),//dragon boots
				new GameItem(6889),//mages book
				new GameItem(2364, 100),//runite bar
				new GameItem(1514, 100),// magic logs
				new GameItem(1632, 50),//uncut dragonstone

				new GameItem(21547, 1),//small foe bone
				new GameItem(21547, 1),//small foe bone
				new GameItem(21547, 1),//small foe bone
				new GameItem(21547, 1),//small foe bone
				new GameItem(21549, 1),//medium foe bone
				new GameItem(21549, 1),//medium foe bone
				new GameItem(21549, 1),//medium foe bone
				new GameItem(21551, 1),//large foe bone
				new GameItem(21551, 1),//large foe bone
				new GameItem(21553, 1),//rare foe bone

				new GameItem(24291),//dagon hai top
				new GameItem(24294),//dagon hai bottom
				new GameItem(24288),//dagon hai hat
				new GameItem(19707)//eternal glory
		));

		items.put(LootRarity.RARE, Arrays.asList(
				new GameItem(24291),//dagon hai top
				new GameItem(24294),//dagon hai bottom            //Common
				new GameItem(24288),//dagon hai hat

				new GameItem(24291),//dagon hai top
				new GameItem(24294),//dagon hai bottom            //Common
				new GameItem(24288),//dagon hai hat

				new GameItem(24291),//dagon hai top
				new GameItem(24294),//dagon hai bottom            //Common
				new GameItem(24288),//dagon hai hat

				new GameItem(24291),//dagon hai top
				new GameItem(24294),//dagon hai bottom            //Common
				new GameItem(24288),//dagon hai hat

				new GameItem(24291),//dagon hai top
				new GameItem(24294),//dagon hai bottom            //Common
				new GameItem(24288),//dagon hai hat

				new GameItem(24291),//dagon hai top
				new GameItem(24294),//dagon hai bottom            //Common
				new GameItem(24288),//dagon hai hat

				new GameItem(24291),//dagon hai top
				new GameItem(24294),//dagon hai bottom            //Common
				new GameItem(24288),//dagon hai hat


				new GameItem(22638, 1),
				new GameItem(22641, 1),
				new GameItem(22644, 1),
				new GameItem(22650, 1),
				new GameItem(22653, 1),
				new GameItem(22656, 1),
				new GameItem(24419, 1),
				new GameItem(24417, 1),
				new GameItem(24420, 1),
				new GameItem(24421, 1)));
	}

	private static GameItem randomChestRewardsYoutube(Player c, int chance) {
		int random = Misc.random(chance);
		int rareChance = 980;
		if (c.getItems().playerHasItem(21046)) {
			rareChance = 975;
			c.getItems().deleteItem(21046, 1);
			c.sendMessage("@red@You sacrifice your @cya@tablet @red@for an increased drop rate." );
			c.getEventCalendar().progress(EventChallenge.USE_X_CHEST_RATE_INCREASE_TABLETS, 1);
		}
		List<GameItem> itemList;
		if (random <= rareChance) {
			itemList = items.get(LootRarity.COMMON);
			GameItem chooseRandomItem = Misc.getRandomItem(itemList);
			return chooseRandomItem;
		} else {
			itemList = items.get(LootRarity.RARE);
			GameItem chooseRandomItem = Misc.getRandomItem(itemList);
			ItemDef def = ItemDef.forId(chooseRandomItem.getId());
			//if (!c.getDisplayName().equalsIgnoreCase("thimble") && !c.getDisplayName().equalsIgnoreCase("top hat")) {
				PlayerHandler.executeGlobalMessage("<col=990000>" + c.getLoginName() + "@bla@ has just received a <col=990000>" + def.getName() + " from the seren chest.");
		//	}
			return chooseRandomItem;
		}
	}

	private static GameItem randomChestRewards(Player c, int chance) {
		int random = Misc.random(chance);
		int rareChance = 997;
		if (c.getItems().playerHasItem(21046)) {
			rareChance = 996;
			c.getItems().deleteItem(21046, 1);
			c.sendMessage("@red@You sacrifice your @cya@tablet @red@for an increased drop rate." );
			c.getEventCalendar().progress(EventChallenge.USE_X_CHEST_RATE_INCREASE_TABLETS, 1);
		}
		List<GameItem> itemList;
		if (random <= rareChance) {
			itemList = items.get(LootRarity.COMMON);
			GameItem chooseRandomItem = Misc.getRandomItem(itemList);
			return chooseRandomItem;
		} else {
			itemList = items.get(LootRarity.RARE);
			GameItem chooseRandomItem = Misc.getRandomItem(itemList);
			ItemDef def = ItemDef.forId(chooseRandomItem.getId());
			//if (!c.getDisplayName().equalsIgnoreCase("thimble") && !c.getDisplayName().equalsIgnoreCase("top hat")) {
			PlayerHandler.executeGlobalMessage("@bla@[<col=7f0000>SEREN@bla@] <col=990000>" + c.getDisplayName() + "@bla@ has just received a <col=990000>" + def.getName() + ".");
			Discord.writeDropsSyncMessage(""+ c.getLoginName() + " has received: a " + def.getName() +" from the Seren Chest!");
			//}
			return chooseRandomItem;
		}
	}

	@Override
	public Map<LootRarity, List<GameItem>> getLoot() {
		return items;
	}

	@Override
	public void roll(Player c) {
		int pkpbonus = Misc.random(5) + 10;
		if (c.getItems().playerHasItem(KEY)) {
			c.getItems().deleteItem(KEY, 1);
			c.startAnimation(ANIMATION);
			c.pkp += pkpbonus;
			if (c.getRights().isOrInherits(Right.YOUTUBER)) {
				GameItem reward = randomChestRewards(c, 1000);
				if (!c.getItems().addItem(reward.getId(), reward.getAmount())) {
					Server.itemHandler.createGroundItem(c, reward.getId(), c.getX(), c.getY(), c.heightLevel, reward.getAmount());
				}
				c.sendMessage("@blu@You also receive @red@" + pkpbonus + " @blu@pkp as a bonus for killing seren.");
			} else {
				c.sendMessage("@blu@The chest is locked, it won't budge!");
			}
			if (!(c.getRights().isOrInherits(Right.YOUTUBER))) {
				GameItem reward = randomChestRewards(c, 1000);
				if (!c.getItems().addItem(reward.getId(), reward.getAmount())) {
					Server.itemHandler.createGroundItem(c, reward.getId(), c.getX(), c.getY(), c.heightLevel, reward.getAmount());
				}
				c.sendMessage("@blu@You also receive @red@" + pkpbonus + " @blu@pkp as a bonus for killing seren.");
			} else {
				c.sendMessage("@blu@The chest is locked, it won't budge!");
			}}
	}
}
