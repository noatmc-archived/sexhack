package me.travis.wurstplus.wurstplustwo.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.command.WurstplusCommand;
import me.travis.wurstplus.wurstplustwo.util.WurstplusChatSuffixUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;

public class WurstplusChatSuffix extends WurstplusCommand {

    public WurstplusChatSuffix() {
        super("chatsuffix", "set chatsuffix");
    }

    public boolean get_message(String[] message) {

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
            WurstplusMessageUtil.send_client_message("chatsuffix change to " + ChatFormatting.BOLD + csuffix.toString());
            Wurstplus.get_config_manager().save_settings();
            return true;
        }

        return false;

    }

}
