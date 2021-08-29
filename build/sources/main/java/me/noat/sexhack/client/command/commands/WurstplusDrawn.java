package me.noat.sexhack.client.command.commands;

import me.noat.sexhack.SexHack;
import me.noat.sexhack.client.command.WurstplusCommand;
import me.noat.sexhack.client.hacks.Module;
import me.noat.sexhack.client.util.WurstplusDrawnUtil;
import me.noat.sexhack.client.util.WurstplusMessageUtil;

import java.util.List;

public class WurstplusDrawn extends WurstplusCommand {
    
    public WurstplusDrawn() {
        super("drawn", "Hide elements of the array list");
    }

    public boolean get_message(String[] message) {

        if (message.length == 1) {
            WurstplusMessageUtil.send_client_error_message("module name needed");

            return true;
        }

        if (message.length == 2) {

            if (is_module(message[1])) {
                WurstplusDrawnUtil.add_remove_item(message[1]);
                SexHack.get_config_manager().save_settings();
            } else {
                WurstplusMessageUtil.send_client_error_message("cannot find module by name: " + message[1]);
            }
            return true;

        }

        return false;
    
    }

    public boolean is_module(String s) {

        List<Module> modules = SexHack.get_hack_manager().get_array_hacks();

        for (Module module : modules) {
            if (module.get_tag().equalsIgnoreCase(s)) {
                return true;
            }
        }

        return false;

    }

}