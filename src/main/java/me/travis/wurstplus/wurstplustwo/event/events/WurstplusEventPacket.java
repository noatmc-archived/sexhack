package me.travis.wurstplus.wurstplustwo.event.events;

import me.travis.wurstplus.wurstplustwo.event.WurstplusEventCancellable;
import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Event;

// External.


public class WurstplusEventPacket extends WurstplusEventCancellable {
	private final Packet packet;
	private int stage;
	
	public WurstplusEventPacket(Packet packet) {
		super();

		this.packet = packet;
		this.stage = stage;
	}

	public Packet get_packet() {
		return this.packet;
	}

	public static class ReceivePacket extends WurstplusEventPacket {
		public ReceivePacket(Packet packet) {
			super(packet);
		}
	}

	public
	int getStage ( ) {
			return this.stage;
	}

	public
	void setStage ( int stage ) {
			this.stage = stage;
	}

	public static class SendPacket extends WurstplusEventPacket {
		public SendPacket(Packet packet) {
			super(packet);
		}
	}
}
