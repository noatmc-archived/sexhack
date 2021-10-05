package me.noat.sexhack.client.guiscreen.hud;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.noat.sexhack.SexHack;
import me.noat.sexhack.client.guiscreen.render.WurstplusDraw;
import me.noat.sexhack.client.guiscreen.render.pinnables.WurstplusPinnable;
import me.noat.sexhack.client.util.WurstplusTimeUtil;
import net.minecraft.util.math.MathHelper;


public
class WurstplusUser extends WurstplusPinnable {
    private int scaled_width;

    public
    WurstplusUser() {
        super("User", "User", 1, 0, 0);
    }

    @Override
    public
    void render() {
        updateResolution();
        int nl_r = SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").getValue(1);
        int nl_g = SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").getValue(1);
        int nl_b = SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").getValue(1);
        int nl_a = SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").getValue(1);

        int time = WurstplusTimeUtil.get_hour();
        String line;

        if (time >= 0 && time < 12) {
            line = "Morning, " + ChatFormatting.GOLD + ChatFormatting.BOLD + mc.player.getName() + ChatFormatting.RESET + " you smell good today :)";
        } else if (time >= 12 && time < 16) {
            line = "Afternoon, " + ChatFormatting.GOLD + ChatFormatting.BOLD + mc.player.getName() + ChatFormatting.RESET + " you're looking good today :)";
        } else if (time >= 16 && time < 24) {
            line = "Evening, " + ChatFormatting.GOLD + ChatFormatting.BOLD + mc.player.getName() + ChatFormatting.RESET + " you smell good today :)";
        } else {
            line = "Welcome, " + ChatFormatting.GOLD + ChatFormatting.BOLD + mc.player.getName() + ChatFormatting.RESET + " you're looking fine today :)";
        }

        mc.fontRenderer.drawStringWithShadow(line, scaled_width / 2f - mc.fontRenderer.getStringWidth(line) / 2f, 20f, new WurstplusDraw.TravisColor(nl_r, nl_g, nl_b, nl_a).hex());

        this.set_width(this.get(line, "width") + 2);
        this.set_height(this.get(line, "height") + 2);
    }

    public
    void updateResolution() {
        this.scaled_width = mc.displayWidth;
        int scaled_height = mc.displayHeight;
        int scale_factor = 1;
        final boolean flag = mc.isUnicode();
        int i = mc.gameSettings.guiScale;
        if (i == 0) {
            i = 1000;
        }
        while (scale_factor < i && this.scaled_width / (scale_factor + 1) >= 320 && scaled_height / (scale_factor + 1) >= 240) {
            ++scale_factor;
        }
        if (flag && scale_factor % 2 != 0 && scale_factor != 1) {
            --scale_factor;
        }
        final double scaledWidthD = this.scaled_width / (double) scale_factor;
        final double scaledHeightD = scaled_height / (double) scale_factor;
        this.scaled_width = MathHelper.ceil(scaledWidthD);
        scaled_height = MathHelper.ceil(scaledHeightD);
    }

}