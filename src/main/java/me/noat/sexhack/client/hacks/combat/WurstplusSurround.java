package me.noat.sexhack.client.hacks.combat;

import me.noat.sexhack.client.guiscreen.settings.Setting;
import me.noat.sexhack.client.hacks.Module;
import me.noat.sexhack.client.hacks.WurstplusCategory;
import me.noat.sexhack.client.util.WurstplusBlockUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public
class WurstplusSurround extends Module {

    final Setting rotate = create("Rotate", "SurroundSmoth", true);
    final Setting hybrid = create("Hybrid", "SurroundHybrid", true);
    final Setting triggerable = create("Toggle", "SurroundToggle", true);
    final Setting center = create("Center", "SurroundCenter", false);
    final Setting antiStuck = create("Anti Stuck", "SurroundAntiStuck", false);
    final Setting block_head = create("Block Face", "SurroundBlockFace", false);
    final Setting tick_for_place = create("Blocks per tick", "SurroundTickToPlace", 2, 1, 8);
    final Setting tick_timeout = create("Ticks til timeout", "SurroundTicks", 20, 10, 50);
    final Setting swing = create("Swing", "SurroundSwing", "Mainhand", combobox("Mainhand", "Offhand", "Both", "None"));
    final Vec3d[] surround_targets = {
            new Vec3d(1, 0, 0),
            new Vec3d(0, 0, 1),
            new Vec3d(-1, 0, 0),
            new Vec3d(0, 0, -1),
            new Vec3d(1, -1, 0),
            new Vec3d(0, -1, 1),
            new Vec3d(-1, -1, 0),
            new Vec3d(0, -1, -1),
            new Vec3d(0, -1, 0)
    };
    final Vec3d[] surround_targets_face = {
            new Vec3d(1, 1, 0),
            new Vec3d(0, 1, 1),
            new Vec3d(-1, 1, 0),
            new Vec3d(0, 1, -1),
            new Vec3d(1, 0, 0),
            new Vec3d(0, 0, 1),
            new Vec3d(-1, 0, 0),
            new Vec3d(0, 0, -1),
            new Vec3d(1, -1, 0),
            new Vec3d(0, -1, 1),
            new Vec3d(-1, -1, 0),
            new Vec3d(0, -1, -1),
            new Vec3d(0, -1, 0)
    };
    private int y_level = 0;
    private int tick_runs = 0;
    private int offset_step = 0;
    private Vec3d center_block = Vec3d.ZERO;

    public
    WurstplusSurround() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);

        this.name = "Surround";
        this.tag = "Surround";
        this.description = "surround urself with obi and such";
    }

    @Override
    public
    void enable() {
        if (find_in_hotbar() == -1) {
            this.set_disable();
            return;
        }

        if (mc.player != null) {

            y_level = (int) Math.round(mc.player.posY);

            center_block = get_center(mc.player.posX, mc.player.posY, mc.player.posZ);

            if (center.getValue(true)) {
                mc.player.motionX = 0;
                mc.player.motionZ = 0;
            }
        }
    }

    @Override
    public
    void update() {

        if (mc.player != null) {

            if (center_block != Vec3d.ZERO && center.getValue(true)) {

                double x_diff = Math.abs(center_block.x - mc.player.posX);
                double z_diff = Math.abs(center_block.z - mc.player.posZ);

                if (x_diff <= 0.1 && z_diff <= 0.1) {
                    center_block = Vec3d.ZERO;
                } else {
                    double motion_x = center_block.x - mc.player.posX;
                    double motion_z = center_block.z - mc.player.posZ;

                    mc.player.motionX = motion_x / 2;
                    mc.player.motionZ = motion_z / 2;
                }

            }

            if ((int) Math.round(mc.player.posY) != y_level && this.hybrid.getValue(true)) {
                this.set_disable();
                return;
            }

            if (!this.triggerable.getValue(true) && this.tick_runs >= this.tick_timeout.getValue(1)) { // timeout time
                this.tick_runs = 0;
                this.set_disable();
                return;
            }

            int blocks_placed = 0;

            while (blocks_placed < this.tick_for_place.getValue(1)) {

                if (this.offset_step >= (block_head.getValue(true) ? this.surround_targets_face.length : this.surround_targets.length)) {
                    this.offset_step = 0;
                    break;
                }

                BlockPos offsetPos = new BlockPos(block_head.getValue(true) ? this.surround_targets_face[offset_step] : this.surround_targets[offset_step]);
                BlockPos targetPos = new BlockPos(mc.player.getPositionVector()).add(offsetPos.getX(), offsetPos.getY(), offsetPos.getZ());

                boolean try_to_place = mc.world.getBlockState(targetPos).getMaterial().isReplaceable();

                for (Entity entity : mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(targetPos))) {
                    if (entity instanceof EntityItem || entity instanceof EntityXPOrb) continue;
                    try_to_place = false;
                    break;
                }

                if (intersectsWithEntity(targetPos) && antiStuck.getValue(true)) {
                    breaker(targetPos);
                }

                if (try_to_place && WurstplusBlockUtil.placeBlock(targetPos, find_in_hotbar(), rotate.getValue(true), rotate.getValue(true), swing)) {
                    blocks_placed++;
                }

                offset_step++;

            }

            this.tick_runs++;

        }
    }

    private
    boolean intersectsWithEntity(final BlockPos pos) {
        for (final Entity entity : mc.world.loadedEntityList) {
            if (entity.equals(mc.player)) continue;
            if (entity instanceof EntityItem) continue;
            if (new AxisAlignedBB(pos).intersects(entity.getEntityBoundingBox())) return true;
        }
        return false;
    }

    void breaker(BlockPos pos) {
        for (final Entity entity : mc.world.loadedEntityList) {
            if (entity.equals(mc.player)) continue;
            if (entity instanceof EntityPlayer) continue;
            if (entity instanceof EntityItem) continue;
            if (new AxisAlignedBB(pos).intersects(entity.getEntityBoundingBox()))
                mc.player.connection.sendPacket(new CPacketUseEntity(entity));
        }
    }

    private
    int find_in_hotbar() {

        for (int i = 0; i < 9; ++i) {

            final ItemStack stack = mc.player.inventory.getStackInSlot(i);

            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock) {

                final Block block = ((ItemBlock) stack.getItem()).getBlock();

                if (block instanceof BlockEnderChest)
                    return i;

                else if (block instanceof BlockObsidian)
                    return i;

            }
        }
        return -1;
    }

    public
    Vec3d get_center(double posX, double posY, double posZ) {
        double x = Math.floor(posX) + 0.5D;
        double y = Math.floor(posY);
        double z = Math.floor(posZ) + 0.5D;

        return new Vec3d(x, y, z);
    }


}