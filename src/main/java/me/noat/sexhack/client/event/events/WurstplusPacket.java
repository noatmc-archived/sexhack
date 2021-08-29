package me.noat.sexhack.client.event.events;

import me.noat.sexhack.client.event.WurstplusEventStage;
import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

public
class WurstplusPacket
        extends WurstplusEventStage {
    private final Packet<?> packet;

    public WurstplusPacket(int stage, Packet<?> packet) {
        super(stage);
        this.packet = packet;
    }

    public <T extends Packet<?>> T getPacket() {
        return (T) this.packet;
    }

    @Cancelable
    public static
    class Send
            extends WurstplusPacket {
        public Send(int stage, Packet<?> packet) {
            super(stage, packet);
        }
    }

    @Cancelable
    public static
    class Receive
            extends WurstplusPacket {
        public Receive(int stage, Packet<?> packet) {
            super(stage, packet);
        }
    }
}
