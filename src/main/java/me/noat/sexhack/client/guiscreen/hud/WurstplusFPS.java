package me.noat.sexhack.client.guiscreen.hud;


import me.noat.sexhack.SexHack;
import me.noat.sexhack.client.guiscreen.render.pinnables.WurstplusPinnable;
import net.minecraft.client.Minecraft;

public
class WurstplusFPS extends WurstplusPinnable {

    public
    WurstplusFPS() {
        super("Fps", "Fps", 1, 0, 0);
    }

    @Override
    public
    void render() {
        int nl_r = SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").getValue(1);
        int nl_g = SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").getValue(1);
        int nl_b = SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").getValue(1);
        int nl_a = SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").getValue(1);

        String line = "FPS: " + get_fps();

        create_line(line, this.docking(1, line), 2, nl_r, nl_g, nl_b, nl_a);

        this.set_width(this.get(line, "width") + 2);
        this.set_height(this.get(line, "height") + 2);
    }

    public
    String get_fps() {
        int fps = Minecraft.getDebugFPS();
        if (fps >= 60) {
            return "\u00A7a" + fps;
        } else if (fps >= 30) {
            return "\u00A73" + fps;
        } else {
            return "\u00A74" + fps;
        }
    }

}