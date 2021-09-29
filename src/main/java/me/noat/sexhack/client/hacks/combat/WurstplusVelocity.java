package me.noat.sexhack.client.hacks.combat;


import me.noat.sexhack.client.event.WurstplusEventCancellable;
import me.noat.sexhack.client.event.events.WurstplusEventEntity;
import me.noat.sexhack.client.event.events.WurstplusEventPacket;
import me.noat.sexhack.client.hacks.Module;
import me.noat.sexhack.client.hacks.WurstplusCategory;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;


public class WurstplusVelocity extends Module {
    @EventHandler
    private final Listener<WurstplusEventPacket.ReceivePacket> damage = new Listener<>(event -> {
        if (event.get_era() == WurstplusEventCancellable.Era.EVENT_PRE) {
            if (event.getPacket() instanceof SPacketEntityVelocity) {
                SPacketEntityVelocity knockback = (SPacketEntityVelocity) event.getPacket();

                if (knockback.getEntityID() == mc.player.getEntityId()) {
                    event.cancel();

                    knockback.motionX *= 0.0f;
                    knockback.motionY *= 0.0f;
                    knockback.motionZ *= 0.0f;
                }
            } else if (event.getPacket() instanceof SPacketExplosion) {
                event.cancel();

                SPacketExplosion knockback = (SPacketExplosion) event.getPacket();

                knockback.motionX *= 0.0f;
                knockback.motionY *= 0.0f;
                knockback.motionZ *= 0.0f;
            }
        }
    });
    @EventHandler
    private final Listener<WurstplusEventEntity.WurstplusEventColision> explosion = new Listener<>(event -> {
        if (event.get_entity() == mc.player) {
            event.cancel();

        }
    });

    public WurstplusVelocity() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);

        this.name = "Velocity";
        this.tag = "Velocity";
        this.description = "No kockback";
    }
}