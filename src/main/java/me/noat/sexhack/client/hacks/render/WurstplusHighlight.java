package me.noat.sexhack.client.hacks.render;

import me.noat.sexhack.client.event.events.WurstplusEventRender;
import me.noat.sexhack.client.guiscreen.settings.Setting;
import me.noat.sexhack.client.hacks.Module;
import me.noat.sexhack.client.hacks.WurstplusCategory;
import me.noat.turok.draw.RenderHelp;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

import java.awt.*;

// Travis.


public
class WurstplusHighlight extends Module {

    final Setting mode = create("Mode", "HighlightMode", "Pretty", combobox("Pretty", "Solid", "Outline"));
    final Setting rgb = create("RGB Effect", "HighlightRGBEffect", true);
    final Setting r = create("R", "HighlightR", 255, 0, 255);
    final Setting g = create("G", "HighlightG", 255, 0, 255);
    final Setting b = create("B", "HighlightB", 255, 0, 255);
    final Setting a = create("A", "HighlightA", 100, 0, 255);
    final Setting l_a = create("Outline A", "HighlightLineA", 255, 0, 255);
    int color_r;
    int color_g;
    int color_b;
    boolean outline = false;
    boolean solid = false;

    public
    WurstplusHighlight() {
        super(WurstplusCategory.WURSTPLUS_RENDER);

        this.name = "Block Highlight";
        this.tag = "BlockHighlight";
        this.description = "see what block ur targeting";
    }

    @Override
    public
    void disable() {
        outline = false;
        solid = false;
    }

    @Override
    public
    void render(WurstplusEventRender event) {
        if (mc.player != null && mc.world != null) {
            float[] tick_color = {
                    (System.currentTimeMillis() % (360 * 32)) / (360f * 32)
            };

            int color_rgb = Color.HSBtoRGB(tick_color[0], 1, 1);

            if (rgb.getValue(true)) {
                color_r = ((color_rgb >> 16) & 0xFF);
                color_g = ((color_rgb >> 8) & 0xFF);
                color_b = (color_rgb & 0xFF);

                r.set_value(color_r);
                g.set_value(color_g);
                b.set_value(color_b);
            } else {
                color_r = r.getValue(1);
                color_g = g.getValue(2);
                color_b = b.getValue(3);
            }

            if (mode.in("Pretty")) {
                outline = true;
                solid = true;
            }

            if (mode.in("Solid")) {
                outline = false;
                solid = true;
            }

            if (mode.in("Outline")) {
                outline = true;
                solid = false;
            }

            RayTraceResult result = mc.objectMouseOver;

            if (result != null) {
                if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
                    BlockPos block_pos = result.getBlockPos();

                    // Solid.
                    if (solid) {
                        RenderHelp.prepare("quads");
                        RenderHelp.draw_cube(block_pos, color_r, color_g, color_b, a.getValue(1), "all");
                        RenderHelp.release();
                    }

                    // Outline.
                    if (outline) {
                        RenderHelp.prepare("lines");
                        RenderHelp.draw_cube_line(block_pos, color_r, color_g, color_b, l_a.getValue(1), "all");
                        RenderHelp.release();
                    }
                }
            }
        }
    }
}