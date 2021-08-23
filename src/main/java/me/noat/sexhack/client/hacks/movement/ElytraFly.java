package me.noat.sexhack.client.hacks.movement;

import me.noat.sexhack.client.event.events.WurstplusEventMove;
import me.noat.sexhack.client.event.events.WurstplusEventPlayerTravel;
import me.noat.sexhack.client.guiscreen.settings.WurstplusSetting;
import me.noat.sexhack.client.hacks.WurstplusCategory;
import me.noat.sexhack.client.hacks.WurstplusHack;
import me.noat.sexhack.client.util.WurstplusMathUtil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;

public class ElytraFly extends WurstplusHack {
    WurstplusSetting speed = create("Speed", "EflySpeed", 1.82f, 0.1f, 10.0f);
    public ElytraFly() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);

        this.name        = "Elytra Fly";
        this.tag         = "EFLY";
        this.description = "we flying";
    }

    @EventHandler
    private Listener<WurstplusEventPlayerTravel> onTravel = new Listener<>(event -> {
        if (mc.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == Items.ELYTRA && mc.player.isElytraFlying()) {
            HandleControlMode(event);
        }
    });

    private void HandleControlMode(WurstplusEventPlayerTravel p_Event)
    {
        final double[] dir = WurstplusMathUtil.directionSpeed(speed.get_value(1));

        if (mc.player.movementInput.moveStrafe != 0 || mc.player.movementInput.moveForward != 0)
        {
            mc.player.motionX = dir[0];
            mc.player.motionZ = dir[1];

            mc.player.motionX -= (mc.player.motionX*(Math.abs(mc.player.rotationPitch)+90)/90) - mc.player.motionX;
            mc.player.motionZ -= (mc.player.motionZ*(Math.abs(mc.player.rotationPitch)+90)/90) - mc.player.motionZ;
        }
        else
        {
            mc.player.motionX = 0;
            mc.player.motionZ = 0;
        }

        mc.player.motionY = (-WurstplusMathUtil.degToRad(mc.player.rotationPitch)) * mc.player.movementInput.moveForward;


        mc.player.prevLimbSwingAmount = 0;
        mc.player.limbSwingAmount = 0;
        mc.player.limbSwing = 0;
        p_Event.cancel();
    }
}
