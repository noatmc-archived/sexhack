package me.travis.wurstplus.wurstplustwo.hacks.combat;

import me.travis.wurstplus.wurstplustwo.hacks.combat.WurstplusAutoCrystalNew;
import net.minecraft.network.play.server.*;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketUseEntity;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.*;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventEntityRemoved;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventMotionUpdate;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRender;
import me.travis.wurstplus.wurstplustwo.event.events.*;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;

public class WurstplusGodModule extends WurstplusHack {

    public WurstplusGodModule() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);

        this.name        = "God Module";
        this.tag         = "godmodule";
        this.description = "funny exploit :^)";
    }

    WurstplusSetting attacks = create("Attacks", "GAttacks", 1, 0, 20);

    @EventHandler
    private final Listener<WurstplusEventPacket.SendPacket> send_listener = new Listener<>(event -> {
      if (event.get_packet() instanceof CPacketPlayerTryUseItemOnBlock) {
        CPacketUseEntity attack = new CPacketUseEntity ( );
        attack.entityId = WurstplusAutoCrystalNew.weDoALittleTrolling().getEntityId();
        attack.action = CPacketUseEntity.Action.ATTACK;
        mc.player.connection.sendPacket ( attack );
      }
    }
}
