package me.noat.sexhack.client.hacks.movement;

import me.noat.sexhack.client.event.events.WurstplusEventPlayerTravel;
import me.noat.sexhack.client.guiscreen.settings.Setting;
import me.noat.sexhack.client.hacks.Module;
import me.noat.sexhack.client.hacks.WurstplusCategory;
import me.noat.sexhack.client.util.WurstplusMathUtil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;

public class ElytraFly extends Module {
    Setting speed = create("Speed", "EflySpeed", 1.82f, 0.1f, 10.0f);
    @EventHandler
    private final Listener<WurstplusEventPlayerTravel> onTravel = new Listener<>(event -> {
        if (mc.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == Items.ELYTRA && mc.player.isElytraFlying()) {
            HandleControlMode(event);
        }
    });

    public ElytraFly() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);

        this.name = "Elytra Fly";
        this.tag = "EFLY";
        this.description = "we flying";
    }

    @Override
    public void enable() {
        if (!mc.player.isElytraFlying()) {
            mc.player.jump();
        }
    }

    private void HandleControlMode(WurstplusEventPlayerTravel p_Event) {
        final double[] dir = WurstplusMathUtil.directionSpeed(speed.get_value(1));

        if (mc.player.movementInput.moveStrafe != 0 || mc.player.movementInput.moveForward != 0) {
            mc.player.motionX = dir[0];
            mc.player.motionZ = dir[1];

            mc.player.motionX -= (mc.player.motionX * (Math.abs(mc.player.rotationPitch) + 90) / 90) - mc.player.motionX;
            mc.player.motionZ -= (mc.player.motionZ * (Math.abs(mc.player.rotationPitch) + 90) / 90) - mc.player.motionZ;
        } else {
            mc.player.motionX = 0;
            mc.player.motionZ = 0;
        }

        if (mc.player.movementInput.jump) {
            mc.player.motionY = (WurstplusMathUtil.degToRad(90));
        }

        if (mc.player.movementInput.sneak) {
            mc.player.motionY = (-WurstplusMathUtil.degToRad(90));
        }


        mc.player.prevLimbSwingAmount = 0;
        mc.player.limbSwingAmount = 0;
        mc.player.limbSwing = 0;
        p_Event.cancel();
    }
}
