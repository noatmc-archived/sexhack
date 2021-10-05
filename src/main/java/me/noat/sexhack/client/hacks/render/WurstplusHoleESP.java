package me.noat.sexhack.client.hacks.render;

import me.noat.sexhack.client.event.events.WurstplusEventRender;
import me.noat.sexhack.client.guiscreen.settings.Setting;
import me.noat.sexhack.client.hacks.Module;
import me.noat.sexhack.client.hacks.WurstplusCategory;
import me.noat.sexhack.client.util.WurstplusPair;
import me.noat.turok.draw.RenderHelp;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

// Travis.


public
class WurstplusHoleESP extends Module {

    final Setting mode = create("Mode", "HoleESPMode", "Pretty", combobox("Pretty", "Solid", "Outline"));
    final Setting off_set = create("Height", "HoleESPOffSetSide", 0.2, 0.0, 1.0);
    final Setting range = create("Range", "HoleESPRange", 6, 1, 12);
    final Setting hide_own = create("Hide Own", "HoleESPHideOwn", true);
    final Setting bedrock_enable = create("Bedrock Holes", "HoleESPBedrockHoles", true);
    // WurstplusSetting rgb_b 				= create("RGB Effect", "HoleColorRGBEffect", true);
    final Setting rb = create("R", "HoleESPRb", 0, 0, 255);
    final Setting gb = create("G", "HoleESPGb", 255, 0, 255);
    final Setting bb = create("B", "HoleESPBb", 0, 0, 255);
    final Setting ab = create("A", "HoleESPAb", 50, 0, 255);
    final Setting obsidian_enable = create("Obsidian Holes", "HoleESPObsidianHoles", true);
    // WurstplusSetting rgb_o 				= create("RGB Effect", "HoleColorRGBEffect", true);
    final Setting ro = create("R", "HoleESPRo", 255, 0, 255);
    final Setting go = create("G", "HoleESPGo", 0, 0, 255);
    final Setting bo = create("B", "HoleESPBo", 0, 0, 255);
    final Setting ao = create("A", "HoleESPAo", 50, 0, 255);
    final Setting dual_enable = create("Dual Holes", "HoleESPTwoHoles", false);
    final Setting line_a = create("Outline A", "HoleESPLineOutlineA", 255, 0, 255);
    final ArrayList <WurstplusPair <BlockPos, Boolean>> holes = new ArrayList <>();
    Setting bedrock_view = create("info", "HoleESPbedrock", "Bedrock");
    Setting obsidian_view = create("info", "HoleESPObsidian", "Obsidian");
    Setting dual_view = create("info", "HoleESPDual", "Double");
    boolean outline = false;
    boolean solid = false;
    boolean docking = false;
    int color_r_o;
    int color_g_o;
    int color_b_o;
    int color_r_b;
    int color_g_b;
    int color_b_b;
    int color_r;
    int color_g;
    int color_b;
    int color_a;
    int safe_sides;

    public
    WurstplusHoleESP() {
        super(WurstplusCategory.WURSTPLUS_RENDER);

        this.name = "Hole ESP";
        this.tag = "HoleESP";
        this.description = "lets you know where holes are";
    }

    private static
    BlockPos orientConv(int orient_count) {
        BlockPos converted = null;

        switch (orient_count) {
            case 0:
                //return EnumFacing.DOWN.getDirectionVec();
                converted = new BlockPos(0, -1, 0);
                break;
            case 1:
                //return EnumFacing.NORTH.getDirectionVec();
                converted = new BlockPos(0, 0, -1);
                break;
            case 2:
                //return EnumFacing.EAST.getDirectionVec();
                converted = new BlockPos(1, 0, 0);
                break;
            case 3:
                //return EnumFacing.SOUTH.getDirectionVec();
                converted = new BlockPos(0, 0, 1);
                break;
            case 4:
                //return EnumFacing.WEST.getDirectionVec();
                converted = new BlockPos(-1, 0, 0);
                break;
            case 5:
                converted = new BlockPos(0, 1, 0);
                break;
        }
        return converted;
    }

    private static
    int oppositeIntOrient(int orient_count) {

        int opposite = 0;

        switch (orient_count) {
            case 0:
                opposite = 5;
                break;
            case 1:
                opposite = 3;
                break;
            case 2:
                opposite = 4;
                break;
            case 3:
                opposite = 1;
                break;
            case 4:
                opposite = 2;
                break;
        }
        return opposite;
    }

    @Override
    public
    void update() {

        color_r_b = rb.getValue(1);
        color_g_b = gb.getValue(1);
        color_b_b = bb.getValue(1);

        color_r_o = ro.getValue(1);
        color_g_o = go.getValue(1);
        color_b_o = bo.getValue(1);

        holes.clear();

        if (mc.player != null || mc.world != null) {
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

            int colapso_range = (int) Math.ceil(range.getValue(1));

            List <BlockPos> spheres = sphere(player_as_blockpos(), colapso_range, colapso_range);

            for (BlockPos pos : spheres) {
                if (!mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR)) {
                    continue;
                }

                if (!mc.world.getBlockState(pos.add(0, 1, 0)).getBlock().equals(Blocks.AIR)) {
                    continue;
                }

                if (!mc.world.getBlockState(pos.add(0, 2, 0)).getBlock().equals(Blocks.AIR)) {
                    continue;
                }

                boolean possible = true;

                safe_sides = 0;

                int air_orient = -1;
                int counter = 0;

                for (BlockPos seems_blocks : new BlockPos[]{
                        new BlockPos(0, -1, 0),
                        new BlockPos(0, 0, -1),
                        new BlockPos(1, 0, 0),
                        new BlockPos(0, 0, 1),
                        new BlockPos(-1, 0, 0)
                }) {
                    Block block = mc.world.getBlockState(pos.add(seems_blocks)).getBlock();

                    if (block != Blocks.BEDROCK && block != Blocks.OBSIDIAN && block != Blocks.ENDER_CHEST && block != Blocks.ANVIL) {
                        possible = false;

                        if (counter == 0) break;

                        if (air_orient != -1) {
                            air_orient = -1;
                            break;
                        }

                        if (block.equals(Blocks.AIR)) {
                            air_orient = counter;
                        } else {
                            break;
                        }
                    }

                    if (block == Blocks.BEDROCK) {
                        safe_sides++;

                    }
                    counter++;
                }

                if (possible) {
                    if (safe_sides == 5) {
                        if (!this.bedrock_enable.getValue(true)) continue;
                        holes.add(new WurstplusPair <>(pos, true));
                    } else {
                        if (!this.obsidian_enable.getValue(true)) continue;
                        holes.add(new WurstplusPair <>(pos, false));
                    }
                    continue;
                }

                if (!this.dual_enable.getValue(true) || air_orient < 0) continue;
                BlockPos second_pos = pos.add(orientConv(air_orient));
                if (checkDual(second_pos, air_orient)) {

                    boolean low_ceiling_hole = mc.world.getBlockState(second_pos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) &&

                            !mc.world.getBlockState(second_pos.add(0, 2, 0)).getBlock().equals(Blocks.AIR);
                    // to avoid rendering the same hole twice

                    if (safe_sides == 8) {
                        holes.add(new WurstplusPair <>(pos, true));
                        if (low_ceiling_hole) holes.add(new WurstplusPair <>(second_pos, true));
                    } else {
                        holes.add(new WurstplusPair <>(pos, false));
                        if (low_ceiling_hole) holes.add(new WurstplusPair <>(second_pos, false));
                    }

                }

            }
        }
    }

    private
    boolean checkDual(BlockPos second_block, int counter) {
        int i = -1;

		/*
			lets check down from second block to not have esp of a dual hole of one space
			missing a bottom block
		*/
        for (BlockPos seems_blocks : new BlockPos[]{
                new BlockPos(0, -1, 0), //Down
                new BlockPos(0, 0, -1), //N
                new BlockPos(1, 0, 0), //E
                new BlockPos(0, 0, 1), //S
                new BlockPos(-1, 0, 0)  //W

        }) {
            i++;
            //skips opposite direction check, since its air


            if (counter == oppositeIntOrient(i)) {
                continue;
            }


            Block block = mc.world.getBlockState(second_block.add(seems_blocks)).getBlock();
            if (block != Blocks.BEDROCK && block != Blocks.OBSIDIAN && block != Blocks.ENDER_CHEST && block != Blocks.ANVIL) {
                return false;
            }

            if (block == Blocks.BEDROCK) {
                safe_sides++;
            }

        }
        return true;
    }

    @Override
    public
    void render(WurstplusEventRender event) {
        float off_set_h;

        if (!holes.isEmpty()) {
            off_set_h = (float) off_set.getValue(1.0);

            for (WurstplusPair <BlockPos, Boolean> hole : holes) {
                if (hole.getValue()) {
                    color_r = color_r_b;
                    color_g = color_g_b;
                    color_b = color_b_b;
                    color_a = ab.getValue(1);
                } else if (!hole.getValue()) {
                    color_r = color_r_o;
                    color_g = color_g_o;
                    color_b = color_b_o;
                    color_a = ao.getValue(1);
                } else continue;

                if (hide_own.getValue(true) && hole.getKey().equals(new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ))) {
                    continue;
                }

                if (solid) {
                    RenderHelp.prepare("quads");
                    RenderHelp.draw_cube(RenderHelp.get_buffer_build(),
                            hole.getKey().getX(), hole.getKey().getY(), hole.getKey().getZ(),
                            1, off_set_h, 1,
                            color_r, color_g, color_b, color_a,
                            "all"
                    );

                    RenderHelp.release();
                }

                if (outline) {
                    RenderHelp.prepare("lines");
                    RenderHelp.draw_cube_line(RenderHelp.get_buffer_build(),
                            hole.getKey().getX(), hole.getKey().getY(), hole.getKey().getZ(),
                            1, off_set_h, 1,
                            color_r, color_g, color_b, line_a.getValue(1),
                            "all"
                    );

                    RenderHelp.release();
                }
            }
        }
    }

    public
    List <BlockPos> sphere(BlockPos pos, float r, int h) {
        boolean hollow = false;
        boolean sphere = true;

        int plus_y = 0;

        List <BlockPos> sphere_block = new ArrayList <>();

        int cx = pos.getX();
        int cy = pos.getY();
        int cz = pos.getZ();

        for (int x = cx - (int) r; x <= cx + r; ++x) {
            for (int z = cz - (int) r; z <= cz + r; ++z) {
                for (int y = sphere ? (cy - (int) r) : cy; y < (sphere ? (cy + r) : ((float) (cy + h))); ++y) {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0);
                    if (dist < r * r && (!hollow || dist >= (r - 1.0f) * (r - 1.0f))) {
                        BlockPos spheres = new BlockPos(x, y + plus_y, z);

                        sphere_block.add(spheres);
                    }
                }
            }
        }

        return sphere_block;
    }

    public
    BlockPos player_as_blockpos() {
        return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
    }
}