package me.noat.sexhack.client.hacks.render;

import me.noat.sexhack.client.event.events.WurstplusEventRender;
import me.noat.sexhack.client.guiscreen.settings.Setting;
import me.noat.sexhack.client.hacks.Module;
import me.noat.sexhack.client.hacks.WurstplusCategory;
import me.noat.sexhack.client.util.WurstplusEntityUtil;
import me.noat.turok.draw.RenderHelp;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public
class WurstplusCityEsp extends Module {

    final Setting endcrystal_mode = create("EndCrystal", "CityEndCrystal", false);
    final Setting mode = create("Mode", "CityMode", "Pretty", combobox("Pretty", "Solid", "Outline"));
    final Setting off_set = create("Height", "CityOffSetSide", 0.2, 0.0, 1.0);
    final Setting range = create("Range", "CityRange", 6, 1, 12);
    final Setting r = create("R", "CityR", 0, 0, 255);
    final Setting g = create("G", "CityG", 255, 0, 255);
    final Setting b = create("B", "CityB", 0, 0, 255);
    final Setting a = create("A", "CityA", 50, 0, 255);
    final List <BlockPos> blocks = new ArrayList <>();
    boolean outline = false;
    boolean solid = false;

    public
    WurstplusCityEsp() {
        super(WurstplusCategory.WURSTPLUS_RENDER);

        this.name = "CityESP";
        this.tag = "City ESP";
        this.description = "jumpy isnt gonna be happy about this";

    }

    @Override
    public
    void update() {
        blocks.clear();
        for (EntityPlayer player : mc.world.playerEntities) {
            if (mc.player.getDistance(player) > range.getValue(1) || mc.player == player) continue;

            BlockPos p = WurstplusEntityUtil.is_cityable(player, endcrystal_mode.getValue(true));

            if (p != null) {
                blocks.add(p);
            }
        }
    }

    @Override
    public
    void render(WurstplusEventRender event) {

        float off_set_h = (float) off_set.getValue(1.0);

        for (BlockPos pos : blocks) {

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

            if (solid) {
                RenderHelp.prepare("quads");
                RenderHelp.draw_cube(RenderHelp.get_buffer_build(),
                        pos.getX(), pos.getY(), pos.getZ(),
                        1, off_set_h, 1,
                        r.getValue(1), g.getValue(1), b.getValue(1), a.getValue(1),
                        "all"
                );

                RenderHelp.release();
            }


            if (outline) {
                RenderHelp.prepare("lines");
                RenderHelp.draw_cube_line(RenderHelp.get_buffer_build(),
                        pos.getX(), pos.getY(), pos.getZ(),
                        1, off_set_h, 1,
                        r.getValue(1), g.getValue(1), b.getValue(1), a.getValue(1),
                        "all"
                );

                RenderHelp.release();
            }
        }
    }
}


