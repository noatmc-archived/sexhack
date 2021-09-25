package me.noat.sexhack.client.hacks.movement;

import me.noat.sexhack.client.guiscreen.settings.Setting;
import me.noat.sexhack.client.hacks.Module;
import me.noat.sexhack.client.hacks.WurstplusCategory;

public class WurstplusSprint extends Module {

    Setting rage = create("Rage", "SprintRage", true);

    public WurstplusSprint() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);

        this.name = "Sprint";
        this.tag = "Sprint";
        this.description = "ZOOOOOOOOM";
    }

    @Override
    public void update() {

        if (mc.player == null) return;

        if (rage.getValue(true) && (mc.player.moveForward != 0 || mc.player.moveStrafing != 0)) {
            mc.player.setSprinting(true);
        } else mc.player.setSprinting(mc.player.moveForward > 0 || mc.player.moveStrafing > 0);

    }


}