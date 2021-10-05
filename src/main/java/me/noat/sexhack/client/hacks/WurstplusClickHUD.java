package me.noat.sexhack.client.hacks;

import me.noat.sexhack.SexHack;
import me.noat.sexhack.client.guiscreen.settings.Setting;

public
class WurstplusClickHUD extends Module {

    Setting frame_view = create("info", "HUDStringsList", "Strings");
    Setting strings_r = create("Color R", "HUDStringsColorR", 255, 0, 255);
    Setting strings_g = create("Color G", "HUDStringsColorG", 255, 0, 255);
    Setting strings_b = create("Color B", "HUDStringsColorB", 255, 0, 255);
    Setting strings_a = create("Alpha", "HUDStringsColorA", 230, 0, 255);
    Setting compass_scale = create("Compass Scale", "HUDCompassScale", 16, 1, 60);
    Setting arraylist_mode = create("ArrayList", "HUDArrayList", "Free", combobox("Free", "Top R", "Top L", "Bottom R", "Bottom L"));
    Setting show_all_pots = create("All Potions", "HUDAllPotions", false);
    Setting max_player_list = create("Max Players", "HUDMaxPlayers", 24, 1, 64);

    public
    WurstplusClickHUD() {
        super(WurstplusCategory.WURSTPLUS_GUI);

        this.name = "HUD";
        this.tag = "HUD";
        this.description = "gui for pinnables";
    }

    @Override
    public
    void enable() {
        if (mc.world != null && mc.player != null) {
            SexHack.get_hack_manager().get_module_with_tag("GUI").set_active(false);

            SexHack.click_hud.back = false;

            mc.displayGuiScreen(SexHack.click_hud);
        }
    }
}