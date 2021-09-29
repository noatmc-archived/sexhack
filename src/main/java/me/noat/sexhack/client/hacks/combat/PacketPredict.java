package me.noat.sexhack.client.hacks.combat;

import me.noat.sexhack.client.event.events.WurstplusEventPacket;
import me.noat.sexhack.client.guiscreen.settings.Setting;
import me.noat.sexhack.client.hacks.Module;
import me.noat.sexhack.client.hacks.WurstplusCategory;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.server.SPacketSpawnObject;

public class PacketPredict extends Module {
    public Setting packets = create("Packets", "Packet", 5, 1, 10);
    @EventHandler
    private final Listener<WurstplusEventPacket.ReceivePacket> receive = new Listener<>(event -> {
        SPacketSpawnObject packet2;
        if (event.getPacket() instanceof SPacketSpawnObject && (packet2 = (SPacketSpawnObject) event.getPacket()).getType() == 51) {
            for (int i = 0; i <= this.packets.getValue(1); ) {
                CPacketUseEntity predict = new CPacketUseEntity();
                predict.entityId = packet2.getEntityID();
                predict.action = CPacketUseEntity.Action.ATTACK;
                mc.player.connection.sendPacket(predict);
                i++;
            }
        }
    });

    public PacketPredict() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);

        this.name = "Packet Predict";
        this.tag = "PacketPredict";
        this.description = "mojang spaghetti code abuser 2.0";
    }

    private void packetAttack(int entityId) {

    }
}
