package me.travis.wurstplus.wurstplustwo.util;

import net.minecraft.util.math.BlockPos;

public class WurstplusCrystalPos {

    private final BlockPos pos;
    private final Double damage;

    public WurstplusCrystalPos(BlockPos pos, Double damage) {
        this.pos = pos;
        this.damage = damage;
    }

    public BlockPos getPos() {
        return pos;
    }

    public Double getDamage() {
        return damage;
    }
}
