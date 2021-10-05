package me.noat.sexhack.client.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.noat.sexhack.SexHack;
import me.noat.sexhack.client.command.WurstplusCommand;
import me.noat.sexhack.client.util.WurstplusChatSuffixUtil;
import me.noat.sexhack.client.util.WurstplusMessageUtil;

public
class WurstplusChatSuffix extends WurstplusCommand {

    public
    WurstplusChatSuffix() {
        super("chatsuffix", "set chatsuffix");
    }

    public
    boolean get_message(String[] message) {

        if (message.length == 1) {
            WurstplusMessageUtil.send_client_error_message("no chat suffix found, try putting a message");
            return true;
        }

        if (message.length >= 2) {
            StringBuilder csuffix = new StringBuilder();
            boolean flag = true;
            for (String word : message) {
                if (flag) {
                    flag = false;
                    continue;
                }
                csuffix.append(word).append(" ");
            }
            WurstplusChatSuffixUtil.set_message(csuffix.toString());
            WurstplusMessageUtil.send_client_message("chatsuffix change to " + ChatFormatting.BOLD + csuffix);
            SexHack.get_config_manager().save_settings();
            return true;
        }

        return false;

    }

}
