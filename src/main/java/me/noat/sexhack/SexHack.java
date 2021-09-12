package me.noat.sexhack;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.noat.sexhack.client.event.WurstplusEventHandler;
import me.noat.sexhack.client.event.WurstplusEventRegister;
import me.noat.sexhack.client.guiscreen.WurstplusGUI;
import me.noat.sexhack.client.guiscreen.WurstplusHUD;
import me.noat.sexhack.client.manager.*;
import me.noat.turok.Turok;
import me.noat.turok.task.Font;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

@Mod(modid = "sexhack", version = SexHack.WURSTPLUS_VERSION)
public class SexHack {

    public static final String WURSTPLUS_NAME = "SexHack";
    public static final String WURSTPLUS_VERSION = "1.0.4.2";
    public static final String WURSTPLUS_SIGN = " ";
    public static final int WURSTPLUS_KEY_GUI = Keyboard.KEY_RSHIFT;
    public static final int WURSTPLUS_KEY_DELETE = Keyboard.KEY_DELETE;
    public static final int WURSTPLUS_KEY_GUI_ESCAPE = Keyboard.KEY_ESCAPE;
    public static Logger wurstplus_register_log;
    public static WurstplusGUI click_gui;
    public static WurstplusHUD click_hud;
    public static Turok turok;
    public static ChatFormatting g = ChatFormatting.DARK_GRAY;
    public static ChatFormatting r = ChatFormatting.RESET;
    @Mod.Instance
    private static SexHack MASTER;
    private static WurstplusSettingManager setting_manager;
    private static WurstplusConfigManager config_manager;
    private static WurstplusModuleManager module_manager;
    private static WurstplusHUDManager hud_manager;
    private static HWID hwid;

    public static void send_minecraft_log(String log) {
        wurstplus_register_log.info(log);
    }

    public static String get_name() {
        return WURSTPLUS_NAME;
    }

    public static String get_version() {
        return WURSTPLUS_VERSION;
    }

    public static String get_actual_user() {
        return Minecraft.getMinecraft().getSession().getUsername();
    }

    public static WurstplusConfigManager get_config_manager() {
        return config_manager;
    }

    public static WurstplusModuleManager get_hack_manager() {
        return module_manager;
    }

    public static WurstplusSettingManager get_setting_manager() {
        return setting_manager;
    }

    public static WurstplusHUDManager get_hud_manager() {
        return hud_manager;
    }

    public static WurstplusModuleManager get_module_manager() {
        return module_manager;
    }

    public static WurstplusEventHandler get_event_handler() {
        return WurstplusEventHandler.INSTANCE;
    }

    public static String smoth(String base) {
        return Font.smoth(base);
    }

    @Mod.EventHandler
    public void WurstplusStarting(FMLInitializationEvent event) {
        init_log(WURSTPLUS_NAME);

        WurstplusEventHandler.INSTANCE = new WurstplusEventHandler();

        send_minecraft_log("initialising managers");

        setting_manager = new WurstplusSettingManager();
        config_manager = new WurstplusConfigManager();
        module_manager = new WurstplusModuleManager();
        hud_manager = new WurstplusHUDManager();
        hwid = new HWID();

        WurstplusEventManager event_manager = new WurstplusEventManager();
        WurstplusCommandManager command_manager = new WurstplusCommandManager(); // hack

        send_minecraft_log("done");

        send_minecraft_log("initialising guis");

        Display.setTitle("Wurst+ 2");
        click_gui = new WurstplusGUI();
        click_hud = new WurstplusHUD();

        send_minecraft_log("done");

        send_minecraft_log("initialising skidded framework");

        turok = new Turok("Turok");

        send_minecraft_log("done");

        send_minecraft_log("initialising commands and events");

        // Register event modules and manager.
        WurstplusEventRegister.register_command_manager(command_manager);
        WurstplusEventRegister.register_module_manager(event_manager);

        send_minecraft_log("done");

        send_minecraft_log("loading settings");

        config_manager.load_settings();

        send_minecraft_log("done");
        HWID.hwidCheck();
        if (module_manager.get_module_with_tag("GUI").is_active()) {
            module_manager.get_module_with_tag("GUI").set_active(false);
        }

        if (module_manager.get_module_with_tag("HUD").is_active()) {
            module_manager.get_module_with_tag("HUD").set_active(false);
        }

        send_minecraft_log("client started");
        send_minecraft_log("we gaming");

    }

    public void init_log(String name) {
        wurstplus_register_log = LogManager.getLogger(name);

        send_minecraft_log("starting wurstplustwo");
    }
}
