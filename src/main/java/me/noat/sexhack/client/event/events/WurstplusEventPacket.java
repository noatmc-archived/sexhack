package me.noat.sexhack.client.event.events;

import me.noat.sexhack.client.event.WurstplusEventCancellable;
import net.minecraft.network.Packet;

// External.


public class WurstplusEventPacket extends WurstplusEventCancellable {
    private final Packet packet;
    private int stage;

    public WurstplusEventPacket(Packet packet) {
        super();

        this.packet = packet;
        this.stage = stage;
    }

    public Packet getPacket() {
        return this.packet;
    }

    public int getStage() {
        return this.stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public static class ReceivePacket extends WurstplusEventPacket {
        public ReceivePacket(Packet packet) {
            super(packet);
        }
    }

    public static class SendPacket extends WurstplusEventPacket {
        public SendPacket(Packet packet) {
            super(packet);
        }
    }
}
