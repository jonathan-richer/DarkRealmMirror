package io.xeros.content.minigames.toa.rooms;

import io.xeros.content.instances.InstancedArea;
import io.xeros.content.minigames.toa.ToaConstants;
import io.xeros.content.minigames.toa.ToaRoom;
import io.xeros.content.minigames.toa.bosses.Nylocas;
import io.xeros.model.collisionmap.WorldObject;
import io.xeros.model.entity.player.Boundary;
import io.xeros.model.entity.player.Player;
import io.xeros.model.entity.player.Position;
import io.xeros.model.world.objects.GlobalObject;

public class RoomThreeNylocas extends ToaRoom {

    @Override
    public Nylocas spawn(InstancedArea instancedArea) {
        return new Nylocas(instancedArea);
    }

    @Override
    public Position getPlayerSpawnPosition() {
        return new Position(3296, 4283);
    }

    @Override
    public boolean handleClickObject(Player player, WorldObject worldObject, int option) {
        return false;
    }

    @Override
    public void handleClickBossGate(Player player, WorldObject worldObject) {
        if (player.getY() >= 4256) {
            player.getPA().movePlayer(player.getX(), 4254, player.getHeight());
        } else {
            player.getPA().movePlayer(player.getX(), 4256, player.getHeight());
        }
    }

    @Override
    public boolean isRoomComplete(InstancedArea instancedArea) {
        return instancedArea.getNpcs().isEmpty();
    }

    @Override
    public Boundary getBoundary() {
        return ToaConstants.APMEKEN1_BOSS_ROOM_BOUNDARY;
    }

    @Override
    public Position getDeathPosition() {
        return new Position(3295, 4256, 0);
    }

    @Override
    public Position getFightStartPosition() {
        return new Position(3295, 4254, 0);
    }

    @Override
    public GlobalObject getFoodChestPosition() {
        return getFoodChest(new Position(3303, 4274, 0), 3);
    }
}
