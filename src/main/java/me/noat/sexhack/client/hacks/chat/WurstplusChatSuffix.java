package me.noat.sexhack.client.hacks.chat;

import me.noat.sexhack.SexHack;
import me.noat.sexhack.client.event.events.WurstplusEventPacket;
import me.noat.sexhack.client.guiscreen.settings.Setting;
import me.noat.sexhack.client.hacks.Module;
import me.noat.sexhack.client.hacks.WurstplusCategory;
import me.noat.sexhack.client.util.WurstplusChatSuffixUtil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.network.play.client.CPacketChatMessage;

import java.util.Random;

// Zero alpine manager.


public
class WurstplusChatSuffix extends Module {
    final Setting ignore = create("Ignore", "ChatSuffixIgnore", true);
    final Setting type = create("Type", "ChatSuffixType", "Default", combobox("Default", "Random", "Custom"));
    final String[] random_client_name = {
            "chad",
            "tabott",
            "travis",
            "unco",
            "kiwi",
            "xdolf",
            "eightsixfour",
            "biggus",
            "naughty",
            "jumpy",
            "chae",
            "wurst",
            "buttfuhk"
    };
    final String[] random_client_finish = {
            " plus",
            " epic",
            "god",
            " sex",
            " blue",
            " brown",
            " gay",
            "plus",
            ""
    };
    boolean accept_suffix;
    boolean suffix_default;
    boolean suffix_random;
    boolean suffix_custom;
    @EventHandler
    private final Listener <WurstplusEventPacket.SendPacket> listener = new Listener <>(event -> {
        // If not be the CPacketChatMessage return.
        if (!(event.getPacket() instanceof CPacketChatMessage)) {
            return;
        }

        // Start event suffix.
        accept_suffix = true;

        // Get value.
        boolean ignore_prefix = ignore.getValue(true);

        String message = ((CPacketChatMessage) event.getPacket()).getMessage();

        // If is with some caracther.
        if (message.startsWith("/") && ignore_prefix) accept_suffix = false;
        if (message.startsWith("\\") && ignore_prefix) accept_suffix = false;
        if (message.startsWith("!") && ignore_prefix) accept_suffix = false;
        if (message.startsWith(":") && ignore_prefix) accept_suffix = false;
        if (message.startsWith(";") && ignore_prefix) accept_suffix = false;
        if (message.startsWith(".") && ignore_prefix) accept_suffix = false;
        if (message.startsWith(",") && ignore_prefix) accept_suffix = false;
        if (message.startsWith("@") && ignore_prefix) accept_suffix = false;
        if (message.startsWith("&") && ignore_prefix) accept_suffix = false;
        if (message.startsWith("*") && ignore_prefix) accept_suffix = false;
        if (message.startsWith("$") && ignore_prefix) accept_suffix = false;
        if (message.startsWith("#") && ignore_prefix) accept_suffix = false;
        if (message.startsWith("(") && ignore_prefix) accept_suffix = false;
        if (message.startsWith(")") && ignore_prefix) accept_suffix = false;

        // Compare the values type.
        if (type.in("Default")) {
            suffix_default = true;
            suffix_random = false;
        }

        if (type.in("Random")) {
            suffix_default = false;
            suffix_random = true;
        }

        if (type.in("Custom")) {
            suffix_default = false;
            suffix_custom = true;
        }

        // If accept.
        if (accept_suffix) {
            if (suffix_default) {
                // Just default.
                message += SexHack.WURSTPLUS_SIGN + convert_base("wurstplus two");
            }

            if (suffix_custom) {
                message += SexHack.WURSTPLUS_SIGN + convert_base(WurstplusChatSuffixUtil.get_message());
            }

            if (suffix_random) {
                // Create first the string builder.

                // Convert the base using the TravisFont.
                String suffix_with_randoms = convert_base(random_string(random_client_name)) +
                        convert_base(random_string(random_client_finish));

                message += SexHack.WURSTPLUS_SIGN + suffix_with_randoms;
            }

            // If message 256 string length substring.
            if (message.length() >= 256) {
                message.substring(0, 256);
            }
        }

        // Send the message.
        ((CPacketChatMessage) event.getPacket()).message = message;
    });
    StringBuilder suffix;

    public
    WurstplusChatSuffix() {
        super(WurstplusCategory.WURSTPLUS_CHAT);

        this.name = "Chat Suffix";
        this.tag = "ChatSuffix";
        this.description = "show off how cool u are";
    }

    // Get the random values string.
    public
    String random_string(String[] list) {
        return list[new Random().nextInt(list.length)];
    }

    // Convert the base using the TravisFont.
    public
    String convert_base(String base) {
        return SexHack.smoth(base);
    }

    @Override
    public
    String array_detail() {
        // Update the detail.
        return this.type.get_current_value();
    }
}
