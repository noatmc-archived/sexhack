package me.noat.sexhack.client.command.commands;


import me.noat.sexhack.SexHack;
import me.noat.sexhack.client.command.WurstplusCommand;
import me.noat.sexhack.client.hacks.Module;
import me.noat.sexhack.client.util.WurstplusMessageUtil;


public class WurstplusAlert extends WurstplusCommand {
    public WurstplusAlert() {
        super("alert", "if the module should spam chat or not");
    }

    public boolean get_message(String[] message) {
        String module = "null";
        String state = "null";

        if (message.length > 1) {
            module = message[1];
        }

        if (message.length > 2) {
            state = message[2];
        }

        if (message.length > 3) {
            WurstplusMessageUtil.send_client_error_message(current_prefix() + "t <ModuleName> <True/On/False/Off>");

            return true;
        }

        if (module.equals("null")) {
            WurstplusMessageUtil.send_client_error_message(current_prefix() + "t <ModuleName> <True/On/False/Off>");

            return true;
        }

        if (state.equals("null")) {
            WurstplusMessageUtil.send_client_error_message(current_prefix() + "t <ModuleName> <True/On/False/Off>");

            return true;
        }

        module = module.toLowerCase();
        state = state.toLowerCase();

        Module module_requested = SexHack.get_hack_manager().get_module_with_tag(module);

        if (module_requested == null) {
            WurstplusMessageUtil.send_client_error_message("This module does not exist.");

            return true;
        }

        boolean value = true;

        if (state.equals("true") || state.equals("on")) {
            value = true;
        } else if (state.equals("false") || state.equals("off")) {
            value = false;
        } else {
            WurstplusMessageUtil.send_client_error_message("This value does not exist. <True/On/False/Off>");

            return true;
        }

        module_requested.set_if_can_send_message_toggle(value);

        WurstplusMessageUtil.send_client_message("The actual value of " + module_requested.get_name() + " is " + module_requested.can_send_message_when_toggle() + ".");

        return true;
    }
}