package me.noat.sexhack.client.guiscreen.hud;

import me.noat.sexhack.SexHack;
import me.noat.sexhack.client.event.WurstplusEventHandler;
import me.noat.sexhack.client.guiscreen.render.pinnables.WurstplusPinnable;

public
class WurstplusTPS extends WurstplusPinnable {

    public
    WurstplusTPS() {
        super("TPS", "TPS", 1, 0, 0);
    }

    @Override
    public
    void render() {
        int nl_r = SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").getValue(1);
        int nl_g = SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").getValue(1);
        int nl_b = SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").getValue(1);
        int nl_a = SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").getValue(1);

        String line = "TPS: " + getTPS();

        create_line(line, this.docking(1, line), 2, nl_r, nl_g, nl_b, nl_a);

        this.set_width(this.get(line, "width") + 2);
        this.set_height(this.get(line, "height") + 2);
    }

    public
    String getTPS() {
        try {
            int tps = Math.round(WurstplusEventHandler.INSTANCE.get_tick_rate());
            if (tps >= 16) {
                return "\u00A7a" + tps;
            } else if (tps >= 10) {
                return "\u00A73" + tps;
            } else {
                return "\u00A74" + tps;
            }
        } catch (Exception e) {
            return "oh no " + e;
        }
    }

}