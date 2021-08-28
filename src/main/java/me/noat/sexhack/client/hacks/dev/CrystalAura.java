package me.noat.sexhack.client.hacks.dev;

import me.noat.sexhack.client.hacks.WurstplusCategory;
import me.noat.sexhack.client.hacks.Module;
import me.noat.sexhack.client.util.WurstplusBlockUtil;
import me.noat.sexhack.client.util.WurstplusCrystalUtil;
import me.noat.sexhack.client.util.WurstplusFriendUtil;
import me.noat.sexhack.client.util.WurstplusTimer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class CrystalAura extends Module {
    public CrystalAura() {
        super(WurstplusCategory.WURSTPLUS_BETA);
        this.name        = "CrystalAura";
        this.tag         = "woohoo";
        this.description = ":^)";
    }

    int minDmg = 3;
    double blockgameAtomicBombDamage;
    double selfDmg;
    BlockPos damage_block;
    WurstplusTimer timerPlace;

    public BlockPos getBlockForCrystalAura() {
        List<BlockPos> blocks = WurstplusCrystalUtil.possiblePlacePositions((float) 6, false, true);

        for (Entity player : mc.world.playerEntities) {

            if (WurstplusFriendUtil.isFriend(player.getName())) continue;

            for (BlockPos block : blocks) {

                if (player == mc.player || !(player instanceof EntityPlayer)) continue;

                if (player.getDistance(mc.player) >= 11) continue;

                blockgameAtomicBombDamage = WurstplusCrystalUtil.calculateDamage(block.getX() + 0.5, (double) block.getY() + 1, (double) block.getZ() + 0.5, player);
                selfDmg = WurstplusCrystalUtil.calculateDamage(block.getX() + 0.5, (double) block.getY() + 1, (double) block.getZ() + 0.5, mc.player);

                if (blockgameAtomicBombDamage > minDmg) continue;
                if (blockgameAtomicBombDamage > selfDmg) continue;

                return block = damage_block;
            }
            blocks.clear();
        }
        return damage_block;
    }

    public void update() {
        do_ca();
    }

    public void do_ca() {
        if (timerPlace.passed(50)) {
            place_crystal();
            timerPlace.reset();
        }
    }
    public void place_crystal() {
        BlockPos crystalPlacePos = getBlockForCrystalAura();
        WurstplusBlockUtil.placeCrystalOnBlock(crystalPlacePos, EnumHand.OFF_HAND);
    }
}
