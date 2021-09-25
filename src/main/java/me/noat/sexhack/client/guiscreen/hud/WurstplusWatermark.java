package me.noat.sexhack.client.guiscreen.hud;

import me.noat.sexhack.SexHack;
import me.noat.sexhack.client.guiscreen.render.pinnables.WurstplusPinnable;


public class WurstplusWatermark extends WurstplusPinnable {
    public WurstplusWatermark() {
        super("Watermark", "Watermark", 1, 0, 0);
    }

    @Override
    public void render() {
        int nl_r = SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").getValue(1);
        int nl_g = SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").getValue(1);
        int nl_b = SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").getValue(1);
        int nl_a = SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").getValue(1);

        String line = "SexHack " + SexHack.r + "v" + SexHack.get_version();

        create_line(line, this.docking(1, line), 2, nl_r, nl_g, nl_b, nl_a);

        this.set_width(this.get(line, "width") + 2);
        this.set_height(this.get(line, "height") + 2);
    }
}
