package me.noat.sexhack.client.hacks.movement;

import me.noat.sexhack.client.event.events.WurstplusEventPlayerTravel;
import me.noat.sexhack.client.guiscreen.settings.Setting;
import me.noat.sexhack.client.hacks.Module;
import me.noat.sexhack.client.hacks.WurstplusCategory;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;

public
class ElytraFly extends Module {
    @EventHandler
    private final Listener <WurstplusEventPlayerTravel> onTravel = new Listener <>(event -> {
        if (mc.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == Items.ELYTRA && mc.player.isElytraFlying()) {
            mc.player.capabilities.allowFlying = true;
        } else if (!mc.player.isElytraFlying()) {
            mc.player.capabilities.allowFlying = false;
        }
    });
    Setting speed = create("Speed", "EflySpeed", 1.82f, 0.1f, 10.0f);

    public
    ElytraFly() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);

        this.name = "Elytra Fly";
        this.tag = "EFLY";
        this.description = "we flying";
    }

    @Override
    public
    void disable() {
        mc.player.capabilities.allowFlying = false;
    }

    @Override
    public
    void enable() {
        if (!mc.player.isElytraFlying()) {
            mc.player.jump();
        }
    }
}
