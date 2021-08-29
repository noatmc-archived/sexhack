package me.noat.sexhack.client.hacks.combat;

import me.noat.sexhack.client.guiscreen.settings.Setting;
import me.noat.sexhack.client.hacks.Module;
import me.noat.sexhack.client.hacks.WurstplusCategory;
import me.noat.sexhack.client.util.WurstplusBlockInteractHelper;
import me.noat.sexhack.client.util.WurstplusBlockInteractHelper.ValidResult;
import me.noat.sexhack.client.util.WurstplusMessageUtil;
import me.noat.sexhack.client.util.WurstplusPlayerUtil;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;

public class WurstplusWebfill extends Module {

    private final ArrayList<BlockPos> holes = new ArrayList <> ( );
    Setting web_toggle = create("Toggle", "WebFillToggle", true);
    Setting web_rotate = create("Rotate", "WebFillRotate", true);
    Setting web_range = create("Range", "WebFillRange", 4, 1, 6);
    private boolean sneak;

    public WurstplusWebfill() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);

        this.name = "Web Fill";
        this.tag = "WebFill";
        this.description = "its like hole fill, but more annoying";
    }

    @Override
    public void enable() {
        find_new_holes();
    }

    @Override
    public void disable() {
        holes.clear();
    }

    @Override
    public void update() {

        if (holes.isEmpty()) {

            if (!web_toggle.get_value(true)) {

                this.set_disable();
                WurstplusMessageUtil.toggle_message(this);
                return;

            } else {
                find_new_holes();
            }

        }

        BlockPos pos_to_fill = null;

        for (BlockPos pos : new ArrayList <> ( holes )) {

            if (pos == null) continue;

            WurstplusBlockInteractHelper.ValidResult result = WurstplusBlockInteractHelper.valid(pos);

            if (result != ValidResult.Ok) {

                holes.remove(pos);
                continue;

            }

            pos_to_fill = pos;
            break;

        }

        int obi_slot = find_in_hotbar();

        if (pos_to_fill != null && obi_slot != -1) {

            int last_slot = mc.player.inventory.currentItem;
            mc.player.inventory.currentItem = obi_slot;
            mc.playerController.updateController();

            if (place_blocks(pos_to_fill)) {
                holes.remove(pos_to_fill);
            }

            mc.player.inventory.currentItem = last_slot;

        }

    }

    public void find_new_holes() {

        holes.clear();

        for (BlockPos pos : WurstplusBlockInteractHelper.getSphere(WurstplusPlayerUtil.GetLocalPlayerPosFloored(), web_range.get_value(1), web_range.get_value(1), false, true, 0)) {

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

                    break;
                }

            }

            if (possible) {
                holes.add(pos);
            }

        }

    }

    private int find_in_hotbar() {

        for (int i = 0; i < 9; ++i) {

            final ItemStack stack = mc.player.inventory.getStackInSlot(i);

            if (stack.getItem() == Item.getItemById(30)) {
                return i;
            }

        }
        return -1;
    }

    private boolean place_blocks(BlockPos pos) {

        if (!mc.world.getBlockState(pos).getMaterial().isReplaceable()) {
            return false;
        }

        if (!WurstplusBlockInteractHelper.checkForNeighbours(pos)) {
            return false;
        }

        for (EnumFacing side : EnumFacing.values()) {

            BlockPos neighbor = pos.offset(side);

            EnumFacing side2 = side.getOpposite();

            if (!WurstplusBlockInteractHelper.canBeClicked(neighbor)) continue;

            if (WurstplusBlockInteractHelper.blackList.contains( mc.world.getBlockState(neighbor).getBlock() )) {
                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
                sneak = true;
            }

            Vec3d hitVec = new Vec3d(neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5));

            if (web_rotate.get_value(true)) {
                WurstplusBlockInteractHelper.faceVectorPacketInstant(hitVec);
            }

            mc.playerController.processRightClickBlock(mc.player, mc.world, neighbor, side2, hitVec, EnumHand.MAIN_HAND);
            mc.player.swingArm(EnumHand.MAIN_HAND);

            return true;
        }

        return false;

    }

}
