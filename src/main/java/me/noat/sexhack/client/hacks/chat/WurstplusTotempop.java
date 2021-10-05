package me.noat.sexhack.client.hacks.chat;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.noat.sexhack.client.event.events.WurstplusEventPacket;
import me.noat.sexhack.client.hacks.Module;
import me.noat.sexhack.client.hacks.WurstplusCategory;
import me.noat.sexhack.client.util.WurstplusFriendUtil;
import me.noat.sexhack.client.util.WurstplusMessageUtil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketEntityStatus;

import java.util.HashMap;


public
class WurstplusTotempop extends Module {

    public static final HashMap <String, Integer> totem_pop_counter = new HashMap <>();
    public static final ChatFormatting red = ChatFormatting.RED;
    public static final ChatFormatting green = ChatFormatting.GREEN;
    public static final ChatFormatting grey = ChatFormatting.GRAY;
    public static final ChatFormatting bold = ChatFormatting.BOLD;
    public static final ChatFormatting reset = ChatFormatting.RESET;
    public static ChatFormatting gold = ChatFormatting.GOLD;
    @EventHandler
    private final Listener <WurstplusEventPacket.ReceivePacket> packet_event = new Listener <>(event -> {

        if (event.getPacket() instanceof SPacketEntityStatus) {

            SPacketEntityStatus packet = (SPacketEntityStatus) event.getPacket();

            if (packet.getOpCode() == 35) {

                Entity entity = packet.getEntity(mc.world);

                int count = 1;

                if (totem_pop_counter.containsKey(entity.getName())) {
                    count = totem_pop_counter.get(entity.getName());
                    totem_pop_counter.put(entity.getName(), ++count);
                } else {
                    totem_pop_counter.put(entity.getName(), count);
                }

                if (entity == mc.player) return;

                if (WurstplusFriendUtil.isFriend(entity.getName())) {
                    WurstplusMessageUtil.client_message_simple(red + "" + bold + " TotemPop " + reset + grey + " > " + reset + "dude, " + bold + green + entity.getName() + reset + " has popped " + bold + count + reset + " totems. you should go help them");
                } else {
                    WurstplusMessageUtil.client_message_simple(red + "" + bold + " TotemPop " + reset + grey + " > " + reset + "dude, " + bold + red + entity.getName() + reset + " has popped " + bold + count + reset + " totems. what a loser");
                }

            }

        }

    });

    public
    WurstplusTotempop() {
        super(WurstplusCategory.WURSTPLUS_CHAT);

        this.name = "Pop Counter";
        this.tag = "TotemPopCounter";
        this.description = "dude idk wurst+ is just outdated";
    }

    @Override
    public
    void update() {

        for (EntityPlayer player : mc.world.playerEntities) {

            if (!totem_pop_counter.containsKey(player.getName())) continue;

            if (player.isDead || player.getHealth() <= 0) {

                int count = totem_pop_counter.get(player.getName());

                totem_pop_counter.remove(player.getName());

                if (player == mc.player) continue;

                if (WurstplusFriendUtil.isFriend(player.getName())) {
                    WurstplusMessageUtil.client_message_simple(red + "" + bold + " TotemPop " + reset + grey + " > " + reset + "dude, " + bold + green + player.getName() + reset + " just fucking DIED after popping " + bold + count + reset + " totems. RIP :pray:");
                } else {
                    WurstplusMessageUtil.client_message_simple(red + "" + bold + " TotemPop " + reset + grey + " > " + reset + "dude, " + bold + red + player.getName() + reset + " just fucking DIED after popping " + bold + count + reset + " totems");
                }

            }

        }

    }

}
