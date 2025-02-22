package io.xeros.content.minigames.toa.party;

import io.xeros.content.party.PartyInterface;
import io.xeros.content.party.PlayerParty;
import io.xeros.model.entity.player.Player;
import io.xeros.util.Misc;

public class ToaParty extends PlayerParty {

    public static final String TYPE = "ToA Party";

    public ToaParty() {
        super(TYPE, 5);
    }

    @Override
    public boolean canJoin(Player invitedBy, Player invited) {
        if (invited.getPA().calculateTotalLevel() < invited.getMode().getTotalLevelForToa()) {
            invited.sendStatement("You need " + Misc.insertCommas(invited.getMode().getTotalLevelForToa()) + " total level to compete.");
            invitedBy.sendMessage(invited.getDisplayNameFormatted() + " doesn't have the requirements to compete.");
            return false;
        }

        return true;
    }

    @Override
    public void onJoin(Player player) {
        PartyInterface.refreshOnJoinOrLeave(player, this);
    }

    @Override
    public void onLeave(Player player) {
        PartyInterface.refreshOnJoinOrLeave(player, this);
    }
}
