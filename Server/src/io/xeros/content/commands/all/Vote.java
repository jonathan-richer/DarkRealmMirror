package io.xeros.content.commands.all;

import java.util.Optional;

import io.xeros.Configuration;
import io.xeros.content.commands.Command;
import io.xeros.content.vote_panel.VotePanelInterface;
import io.xeros.model.entity.player.Player;


public class Vote extends Command {

	@Override
	public void execute(Player c, String commandName, String input) {
		c.getPA().sendFrame126(Configuration.VOTE_LINK + c.getLoginNameLower(), 12000);
	}

	@Override
	public Optional<String> getDescription() {
		return Optional.of("Opens a web page where you can vote for rewards");
	}

}
