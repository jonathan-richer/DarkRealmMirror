package io.xeros.content.commands.all;

import java.util.Optional;

import io.xeros.Configuration;
import io.xeros.content.commands.Command;
import io.xeros.model.entity.player.Player;

/**
 * Teleport the player to the mage bank.
 * 
 * @author Emiel
 */
public class News extends Command {

	@Override
	public void execute(Player c, String commandName, String input) {
		c.getPA().sendFrame126(Configuration.DISCORD_INVITE, 12000);
	}

	@Override
	public Optional<String> getDescription() {
		return Optional.of("Check out all of our updates and news on our discord.");
	}

}
