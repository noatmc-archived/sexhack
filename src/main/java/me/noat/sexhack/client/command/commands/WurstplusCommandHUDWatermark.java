package me.noat.sexhack.client.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.noat.sexhack.SexHack;
import me.noat.sexhack.client.command.WurstplusCommand;
import me.noat.sexhack.client.util.WurstplusMessageUtil;
import me.noat.sexhack.client.util.WurstplusWatermarkUtil;

public class WurstplusCommandHUDWatermark extends WurstplusCommand {

    public WurstplusCommandHUDWatermark() {
        super("watermark", "change wurstplus hud watermark thing");
    }

    public boolean get_message(String[] message) {

        if (message.length == 1) {
            WurstplusMessageUtil.send_client_error_message("message needed");
            return true;
        }

        if (message.length >= 2) {
            StringBuilder wm = new StringBuilder();
            boolean flag = true;
            for (String word : message) {
                if (flag) {
                    flag = false;
                    continue;
                }
                wm.append(word).append(" ");
            }
            WurstplusWatermarkUtil.set_message(wm.toString());
            WurstplusMessageUtil.send_client_message("watermark message changed to " + ChatFormatting.BOLD + wm);
            SexHack.get_config_manager().save_settings();
            return true;
        }

        return false;

    }

}
