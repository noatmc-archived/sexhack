package me.noat.sexhack.client.hacks.dev;

import me.noat.sexhack.client.guiscreen.settings.Setting;
import me.noat.sexhack.client.hacks.Module;
import me.noat.sexhack.client.hacks.WurstplusCategory;
import me.noat.sexhack.client.util.WP3CrystalUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class SexAura extends Module {
    public SexAura() {
        super(WurstplusCategory.WURSTPLUS_BETA);

        this.name = "!Auto Sex";
        this.tag = "sex";
        this.description = "kills people (if ur good)";
    }

    Setting place = create("Place", "asplace", true);
    Setting breakCrystal = create("Break", "asbreak", true);
    Setting placeRange = create("Range Place", "asrange", 6.0f, 0.0f, 6.0f);
    Setting breakRange = create("Range Break", "asbrange", 6.0f, 0.0f, 6.0f);
    Setting wallRnge = create("Range Wall", "asbrange", 4.0f, 0.0f, 6.0f);
    Setting minDmg = create("Minimum Dmg", "asmindmg", 6, 0, 36);
    Setting selfDmg = create("Max Self Dmg", "asmaxselfdmg", 8, 0, 36);
    private BlockPos placePos;
    private ArrayList popbob = new ArrayList<>();

    @Override
    public void enable() {
        placePos = null;
    }

    public BlockPos getPos() {
        for (Entity entity : mc.world.getLoadedEntityList()) {
            if (entity instanceof EntityPlayer) continue;
            if (entity.getDistanceSq(mc.player) > 20) continue;
            for (BlockPos blocks : WP3CrystalUtil.possiblePlacePositions(placeRange.get_value(1), true, false)) {
                if (WP3CrystalUtil.calculateDamage(blocks, entity, true) > minDmg.get_value(1)) continue;
                if (WP3CrystalUtil.calculateDamage(blocks, mc.player, true) <= selfDmg.get_value(1)) continue;
            }
        }
    }
    @Override
    public void disable() {
        placePos = null;
    }
}
