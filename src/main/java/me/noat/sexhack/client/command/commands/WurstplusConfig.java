package me.noat.sexhack.client.command.commands;

import me.noat.sexhack.SexHack;
import me.noat.sexhack.client.command.WurstplusCommand;
import me.noat.sexhack.client.util.WurstplusMessageUtil;

public
class WurstplusConfig extends WurstplusCommand {

    public
    WurstplusConfig() {
        super("config", "changes which config is loaded");
    }

    public
    boolean get_message(String[] message) {

        if (message.length == 1) {
            WurstplusMessageUtil.send_client_error_message("config needed");
            return true;
        } else if (message.length == 2) {
            String config = message[1];
            if (SexHack.get_config_manager().set_active_config_folder(config + "/")) {
                WurstplusMessageUtil.send_client_message("new config folder set as " + config);
            } else {
                WurstplusMessageUtil.send_client_error_message("cannot set folder to " + config);
            }
            return true;
        } else {
            WurstplusMessageUtil.send_client_error_message("config path may only be one word");
            return true;
        }
    }

}
