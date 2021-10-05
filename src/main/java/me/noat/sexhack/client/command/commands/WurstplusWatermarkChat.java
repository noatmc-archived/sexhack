package me.noat.sexhack.client.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.noat.sexhack.SexHack;
import me.noat.sexhack.client.command.WurstplusCommand;
import me.noat.sexhack.client.util.WurstplusMessageUtil;
import me.noat.sexhack.client.util.WurstplusWatermarkUtil;

import static me.noat.sexhack.client.util.WurstplusMessageUtil.r;

public
class WurstplusWatermarkChat extends WurstplusCommand {

    public
    WurstplusWatermarkChat() {
        super("watermarkchat", "le skid");
    }

    public
    boolean get_message(String[] message) {

        if (message.length == 1) {
            WurstplusMessageUtil.send_client_error_message("no watermark found, try putting a message");
            return true;
        }

        if (message.length >= 2) {
            StringBuilder watermark = new StringBuilder();
            boolean flag = true;
            for (String word : message) {
                if (flag) {
                    flag = false;
                    continue;
                }
                watermark.append(word).append(" ");
            }
            WurstplusWatermarkUtil.set_message(watermark.toString());
            WurstplusMessageUtil.send_client_message("chat's watermark change to " + ChatFormatting.BOLD + watermark);
            WurstplusMessageUtil.opener = WurstplusMessageUtil.g + watermark.toString() + ChatFormatting.GRAY + " > " + r;
            SexHack.get_config_manager().save_settings();
            return true;
        }

        return false;

    }

}
