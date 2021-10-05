package me.noat.sexhack.client.command.commands;

import me.noat.sexhack.SexHack;
import me.noat.sexhack.client.command.WurstplusCommand;
import me.noat.sexhack.client.hacks.Module;
import me.noat.sexhack.client.manager.WurstplusCommandManager;
import me.noat.sexhack.client.util.WurstplusMessageUtil;

public
class WurstplusToggle extends WurstplusCommand {
    public
    WurstplusToggle() {
        super("t", "turn on and off stuffs");
    }

    public
    boolean get_message(String[] message) {
        String module = "null";

        if (message.length > 1) {
            module = message[1];
        }

        if (message.length > 2) {
            WurstplusMessageUtil.send_client_error_message(current_prefix() + "t <ModuleNameNoSpace>");

            return true;
        }

        if (module.equals("null")) {
            WurstplusMessageUtil.send_client_error_message(WurstplusCommandManager.get_prefix() + "t <ModuleNameNoSpace>");

            return true;
        }

        Module module_requested = SexHack.get_module_manager().get_module_with_tag(module);

        if (module_requested != null) {
            module_requested.toggle();

            WurstplusMessageUtil.send_client_message("[" + module_requested.get_tag() + "] - Toggled to " + module_requested.is_active() + ".");
        } else {
            WurstplusMessageUtil.send_client_error_message("Module does not exist.");
        }

        return true;
    }
}