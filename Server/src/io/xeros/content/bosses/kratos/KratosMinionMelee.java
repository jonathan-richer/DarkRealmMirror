package io.xeros.content.bosses.kratos;

import io.xeros.content.combat.npc.NPCAutoAttack;
import io.xeros.content.combat.npc.NPCAutoAttackBuilder;
import io.xeros.content.combat.npc.NPCCombatAttack;
import io.xeros.model.Animation;
import io.xeros.model.CombatType;

import java.util.function.Function;

public class KratosMinionMelee implements Function<KratosNpc, NPCAutoAttack> {

    @Override
    public NPCAutoAttack apply(KratosNpc nightmare) {
        return new NPCAutoAttackBuilder()
                .setAnimation(new Animation(7018))
                .setCombatType(CombatType.MELEE)
                .setMaxHit(13)
                .setHitDelay(2)
                .setAttackDelay(4)
                .setDistanceRequiredForAttack(1)
                .setPrayerProtectionPercentage(new Function<NPCCombatAttack, Double>() {
                    @Override
                    public Double apply(NPCCombatAttack npcCombatAttack) {
                        return 0.2d;
                    }
                })
                .createNPCAutoAttack();
    }


}