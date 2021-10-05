package me.noat.sexhack.client.guiscreen.hud;

import com.google.common.collect.Lists;
import me.noat.sexhack.SexHack;
import me.noat.sexhack.client.guiscreen.render.pinnables.WurstplusPinnable;
import me.noat.sexhack.client.hacks.Module;
import me.noat.sexhack.client.util.WurstplusDrawnUtil;
import net.minecraft.util.math.MathHelper;

import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public
class WurstplusArrayList extends WurstplusPinnable {
    boolean flag = true;
    private int count;
    private int scaled_width;
    private int scaled_height;
    private int nl_r;
    private int nl_g;
    private int nl_b;

    public
    WurstplusArrayList() {
        super("Array List", "ArrayList", 1, 0, 0);
    }

    public static
    Color alphaStep(Color color, int index, int count) {
        float[] hsb = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        float brightness = Math.abs(((float) (System.currentTimeMillis() % 2000L) / 1000.0F + (float) index / (float) count * 2.0F) % 2.0F - 1.0F);
        brightness = 0.5F + 0.5F * brightness;
        hsb[2] = brightness % 2.0F;
        return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
    }

    @Override
    public
    void render() {
        nl_r = SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").getValue(1);
        nl_g = SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").getValue(1);
        nl_b = SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").getValue(1);
        int nl_a = SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").getValue(1);
        updateResolution();
        int position_update_y = 2;

        List <Module> pretty_modules = SexHack.get_hack_manager().get_array_active_hacks().stream()
                .sorted(Comparator.comparing(modules -> get(modules.array_detail() == null ? modules.get_tag() : modules.get_tag() + SexHack.g + " [" + SexHack.r + modules.array_detail() + SexHack.g + "]" + SexHack.r, "width")))
                .collect(Collectors.toList());

        int count = 0;

        if (SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDArrayList").in("Top R") || SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDArrayList").in("Top L")) {
            pretty_modules = Lists.reverse(pretty_modules);
        }

        for (Module modules : pretty_modules) {

            flag = true;

            if (modules.get_category().get_tag().equals("WurstplusGUI")) {
                continue;
            }

            for (String s : WurstplusDrawnUtil.hidden_tags) {
                if (modules.get_tag().equalsIgnoreCase(s)) {
                    flag = false;
                    break;
                }
                if (!flag) break;
            }

            if (flag) {
                String module_name = (
                        modules.array_detail() == null ? modules.get_tag() :
                                modules.get_tag() + SexHack.g + " [" + SexHack.r + modules.array_detail() + SexHack.g + "]" + SexHack.r
                );

                if (SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDArrayList").in("Free")) {
                    createLine(module_name, this.docking(2, module_name), position_update_y, generateColor(modules));

                    position_update_y += get(module_name, "height") + 2;

                    if (get(module_name, "width") > this.get_width()) {
                        this.set_width(get(module_name, "width") + 2);
                    }

                    this.set_height(position_update_y);
                } else {
                    if (SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDArrayList").in("Top R")) {
                        mc.fontRenderer.drawStringWithShadow(module_name, scaled_width - 2 - mc.fontRenderer.getStringWidth(module_name), 3 + count * 10, generateColor(modules));
                        count++;
                    }
                    if (SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDArrayList").in("Top L")) {
                        mc.fontRenderer.drawStringWithShadow(module_name, 2, 3 + count * 10, generateColor(modules));
                        count++;
                    }
                    if (SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDArrayList").in("Bottom R")) {
                        mc.fontRenderer.drawStringWithShadow(module_name, scaled_width - 2 - mc.fontRenderer.getStringWidth(module_name), scaled_height - (count * 10), generateColor(modules));
                        count++;
                    }
                    if (SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDArrayList").in("Bottom L")) {
                        mc.fontRenderer.drawStringWithShadow(module_name, 2, scaled_height - (count * 10), generateColor(modules));
                        count++;
                    }
                }


            }
        }
    }

    public
    int generateColor(Module mod) {
        return alphaStep(new Color(nl_r, nl_g, nl_b), 50, (count * 2) + 10).getRGB();
    }

    public
    void updateResolution() {
        this.scaled_width = mc.displayWidth;
        this.scaled_height = mc.displayHeight;
        int scale_factor = 1;
        final boolean flag = mc.isUnicode();
        int i = mc.gameSettings.guiScale;
        if (i == 0) {
            i = 1000;
        }
        while (scale_factor < i && this.scaled_width / (scale_factor + 1) >= 320 && this.scaled_height / (scale_factor + 1) >= 240) {
            ++scale_factor;
        }
        if (flag && scale_factor % 2 != 0 && scale_factor != 1) {
            --scale_factor;
        }
        final double scaledWidthD = this.scaled_width / (double) scale_factor;
        final double scaledHeightD = this.scaled_height / (double) scale_factor;
        this.scaled_width = MathHelper.ceil(scaledWidthD);
        this.scaled_height = MathHelper.ceil(scaledHeightD);
    }
}