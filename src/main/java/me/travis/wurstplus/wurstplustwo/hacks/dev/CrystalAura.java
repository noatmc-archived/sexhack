package me.travis.wurstplus.wurstplustwo.hacks.dev;

import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusCrystalUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class CrystalAura extends WurstplusHack {
    public CrystalAura() {
        super(WurstplusCategory.WURSTPLUS_BETA);
        this.name        = "CrystalAura";
        this.tag         = "woohoo";
        this.description = ":^)";
    }

    int minDmg = 3;
    double blockgameAtomicBombDamage;
    double selfDmg;

    public void getBlockForCrystalAura() {
        List<BlockPos> blocks = WurstplusCrystalUtil.possiblePlacePositions((float) 6, false, true);

        for (Entity player : mc.world.playerEntities) {

            if (WurstplusFriendUtil.isFriend(player.getName())) continue;

            for (BlockPos block : blocks) {

                if (player == mc.player || !(player instanceof EntityPlayer)) continue;

                if (player.getDistance(mc.player) >= 11) continue;

                blockgameAtomicBombDamage = WurstplusCrystalUtil.calculateDamage(block.getX() + 0.5, (double) block.getY() + 1, (double) block.getZ() + 0.5, player);
                selfDmg = WurstplusCrystalUtil.calculateDamage(block.getX() + 0.5, (double) block.getY() + 1, (double) block.getZ() + 0.5, mc.player);
            }
        }
    }
}
