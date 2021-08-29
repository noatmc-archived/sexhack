package me.noat.sexhack.client.event;

import me.noat.sexhack.client.manager.WurstplusCommandManager;
import me.noat.sexhack.client.manager.WurstplusEventManager;
import net.minecraftforge.common.MinecraftForge;


public class WurstplusEventRegister {
	public static void register_command_manager(WurstplusCommandManager manager) {
		MinecraftForge.EVENT_BUS.register(manager);
	}

	public static void register_module_manager(WurstplusEventManager manager) {
		MinecraftForge.EVENT_BUS.register(manager);
	}
}