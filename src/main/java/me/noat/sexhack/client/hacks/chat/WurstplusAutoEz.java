package me.noat.sexhack.client.hacks.chat;

import me.noat.sexhack.client.event.events.WurstplusEventPacket;
import me.noat.sexhack.client.guiscreen.settings.Setting;
import me.noat.sexhack.client.hacks.Module;
import me.noat.sexhack.client.hacks.WurstplusCategory;
import me.noat.sexhack.client.util.WurstplusEzMessageUtil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class WurstplusAutoEz extends Module {

    private static final ConcurrentHashMap targeted_players = new ConcurrentHashMap();
    int delay_count = 0;

    Setting discord = create("Discord", "EzDiscord", false);
    Setting custom = create("Custom", "EzCustom", false);
    @EventHandler
    private final Listener<WurstplusEventPacket.SendPacket> send_listener = new Listener<>(event -> {

        if (mc.player == null) return;

        if (event.getPacket() instanceof CPacketUseEntity) {
            CPacketUseEntity cPacketUseEntity = (CPacketUseEntity) event.getPacket();
            if (cPacketUseEntity.getAction().equals(CPacketUseEntity.Action.ATTACK)) {
                Entity target_entity = cPacketUseEntity.getEntityFromWorld(mc.world);
                if (target_entity instanceof EntityPlayer) {
                    add_target(target_entity.getName());
                }
            }
        }

    });
    @EventHandler
    private final Listener<LivingDeathEvent> living_death_listener = new Listener<>(event -> {

        if (mc.player == null) return;

        EntityLivingBase e = event.getEntityLiving();
        if (e == null) return;

        if (e instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) e;

            if (player.getHealth() <= 0) {
                if (targeted_players.containsKey(player.getName())) {
                    announce(player.getName());
                }
            }
        }

    });

    public WurstplusAutoEz() {
        super(WurstplusCategory.WURSTPLUS_CHAT);

        this.name = "Auto Ez";
        this.tag = "AutoEz";
        this.description = "you just got nae nae'd by wurst+... 2";
    }

    public static void add_target(String name) {
        if (!Objects.equals(name, mc.player.getName())) {
            targeted_players.put(name, 20);
        }
    }

    @Override
    public void update() {

        for (Entity entity : mc.world.getLoadedEntityList()) {
            if (entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entity;
                if (player.getHealth() <= 0) {
                    if (targeted_players.containsKey(player.getName())) {
                        announce(player.getName());
                    }
                }
            }
        }

        targeted_players.forEach((name, timeout) -> {
            if ((int) timeout <= 0) {
                targeted_players.remove(name);
            } else {
                targeted_players.put(name, (int) timeout - 1);
            }

        });

        delay_count++;

    }

    public void announce(String name) {
        if (delay_count < 150) {
            return;
        }
        delay_count = 0;
        targeted_players.remove(name);
        String message = "";
        if (custom.getValue(true)) {
            message += WurstplusEzMessageUtil.get_message().replace("[", "").replace("]", "");
        } else {
            message += "you just got nae nae'd by wurst+2";
        }
        if (discord.getValue(true)) {
            message += " - discord.gg/wurst";
        }
        mc.player.connection.sendPacket(new CPacketChatMessage(message));
    }

}