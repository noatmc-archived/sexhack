package me.noat.sexhack.client.hacks.misc;

import me.noat.sexhack.client.guiscreen.settings.Setting;
import me.noat.sexhack.client.hacks.Module;
import me.noat.sexhack.client.hacks.WurstplusCategory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemExpBottle;


public class WurstplusFastUtil extends Module {

    Setting fast_place = create("Place", "WurstplusFastPlace", false);
    Setting fast_break = create("Break", "WurstplusFastBreak", false);
    Setting crystal = create("Crystal", "WurstplusFastCrystal", false);
    Setting exp = create("EXP", "WurstplusFastExp", true);
    public WurstplusFastUtil() {
        super(WurstplusCategory.WURSTPLUS_MISC);

        this.name = "Fast Util";
        this.tag = "FastUtil";
        this.description = "use things faster";
    }

    @Override
    public void update() {
        Item main = mc.player.getHeldItemMainhand().getItem();
        Item off = mc.player.getHeldItemOffhand().getItem();

        boolean main_exp = main instanceof ItemExpBottle;
        boolean off_exp = off instanceof ItemExpBottle;
        boolean main_cry = main instanceof ItemEndCrystal;
        boolean off_cry = off instanceof ItemEndCrystal;

        if (main_exp | off_exp && exp.getValue(true)) {
            mc.rightClickDelayTimer = 0;
        }

        if (main_cry | off_cry && crystal.getValue(true)) {
            mc.rightClickDelayTimer = 0;
        }

        if (!(main_exp | off_exp | main_cry | off_cry) && fast_place.getValue(true)) {
            mc.rightClickDelayTimer = 0;
        }

        if (fast_break.getValue(true)) {
            mc.playerController.blockHitDelay = 0;
        }
    }
}