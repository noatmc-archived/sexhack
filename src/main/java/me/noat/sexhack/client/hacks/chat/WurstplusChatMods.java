package me.noat.sexhack.client.hacks.chat;


import com.mojang.realmsclient.gui.ChatFormatting;
import me.noat.sexhack.client.event.events.WurstplusEventPacket;
import me.noat.sexhack.client.guiscreen.settings.Setting;
import me.noat.sexhack.client.hacks.Module;
import me.noat.sexhack.client.hacks.WurstplusCategory;
import me.noat.sexhack.client.util.WurstplusMessageUtil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.util.text.TextComponentString;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class WurstplusChatMods extends Module {

    Setting timestamps = create("Timestamps", "ChatModsTimeStamps", true);

    // GuiNewChat nc = new GuiNewChat(mc);
    Setting dateformat = create("Date Format", "ChatModsDateFormat", "24HR", combobox("24HR", "12HR"));
    Setting name_highlight = create("Name Highlight", "ChatModsNameHighlight", true);
    @EventHandler
    private final Listener<WurstplusEventPacket.ReceivePacket> PacketEvent = new Listener<>(event -> {

        if (event.get_packet() instanceof SPacketChat) {

            final SPacketChat packet = (SPacketChat) event.get_packet();

            if (packet.getChatComponent() instanceof TextComponentString) {
                final TextComponentString component = (TextComponentString) packet.getChatComponent();

                if (timestamps.getValue(true)) {

                    String date = "";

                    if (dateformat.in("12HR")) {
                        date = new SimpleDateFormat("h:mm a").format(new Date());
                    }

                    if (dateformat.in("24HR")) {
                        date = new SimpleDateFormat("k:mm").format(new Date());

                    }

                    component.text = "\2477[" + date + "]\247r " + component.text;

                }

                String text = component.getFormattedText();

                if (text.contains("combat for")) return;

                if (name_highlight.getValue(true) && mc.player != null) {

                    if (text.toLowerCase().contains(mc.player.getName().toLowerCase())) {

                        text = text.replaceAll("(?i)" + mc.player.getName(), ChatFormatting.GOLD + mc.player.getName() + ChatFormatting.RESET);

                    }

                }

                event.cancel();

                WurstplusMessageUtil.client_message(text);

            }
        }
    });

    public WurstplusChatMods() {
        super(WurstplusCategory.WURSTPLUS_CHAT);

        this.name = "Chat Modifications";
        this.tag = "ChatModifications";
        this.description = "this breaks things";
    }

}