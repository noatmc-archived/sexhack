package me.noat.sexhack.client.hacks.combat;

import me.noat.sexhack.client.event.events.WurstplusEventPacket;
import me.noat.sexhack.client.guiscreen.settings.Setting;
import me.noat.sexhack.client.hacks.WurstplusCategory;
import me.noat.sexhack.client.hacks.Module;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;


public class WurstplusCriticals extends Module {

	public WurstplusCriticals() {
		super(WurstplusCategory.WURSTPLUS_COMBAT);

		this.name        = "Criticals";
		this.tag         = "Criticals";
		this.description = "You can hit with criticals when attack.";
	}

	Setting mode = create("Mode", "CriticalsMode", "Packet", combobox("Packet", "Jump"));

	@EventHandler
	private final Listener<WurstplusEventPacket.SendPacket> listener = new Listener<>(event -> {
		if (event.get_packet() instanceof CPacketUseEntity) {
			CPacketUseEntity event_entity = ((CPacketUseEntity) event.get_packet());

			if (event_entity.getAction() == CPacketUseEntity.Action.ATTACK && mc.player.onGround) {
				if (mode.in("Packet")) {
					mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.1f, mc.player.posZ, false));
					mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
				} else if (mode.in("Jump")) {
					mc.player.jump();
				}
			}
		}
	});

	@Override
	public String array_detail() {
		return mode.get_current_value();
	}
}