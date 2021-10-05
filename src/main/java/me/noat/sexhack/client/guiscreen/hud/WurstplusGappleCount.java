package me.noat.sexhack.client.guiscreen.hud;

import me.noat.sexhack.SexHack;
import me.noat.sexhack.client.guiscreen.render.pinnables.WurstplusPinnable;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;


public
class WurstplusGappleCount extends WurstplusPinnable {
    int gapples = 0;

    public
    WurstplusGappleCount() {
        super("Gapple Count", "GappleCount", 1, 0, 0);
    }

    @Override
    public
    void render() {
        int nl_r = SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").getValue(1);
        int nl_g = SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").getValue(1);
        int nl_b = SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").getValue(1);
        int nl_a = SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").getValue(1);

        if (mc.player != null) {
            if (is_on_gui()) {
                create_rect(0, 0, this.get_width(), this.get_height(), 0, 0, 0, 50);
            }

            GlStateManager.pushMatrix();
            RenderHelper.enableGUIStandardItemLighting();

            gapples = mc.player.inventory.mainInventory.stream().filter(stack -> stack.getItem() == Items.GOLDEN_APPLE).mapToInt(ItemStack::getCount).sum();

            int off = 0;

            for (int i = 0; i < 45; i++) {
                ItemStack stack = mc.player.inventory.getStackInSlot(i);
                ItemStack off_h = mc.player.getHeldItemOffhand();

                if (off_h.getItem() == Items.GOLDEN_APPLE) {
                    off = off_h.getMaxStackSize();
                } else {
                    off = 0;
                }

                if (stack.getItem() == Items.GOLDEN_APPLE) {
                    mc.getRenderItem().renderItemAndEffectIntoGUI(stack, this.get_x(), this.get_y());

                    create_line(Integer.toString(gapples + off), 16 + 2, 16 - get(Integer.toString(gapples + off), "height"), nl_r, nl_g, nl_b, nl_a);
                }
            }

            mc.getRenderItem().zLevel = 0.0f;

            RenderHelper.disableStandardItemLighting();

            GlStateManager.popMatrix();

            this.set_width(16 + get(Integer.toString(gapples) + off, "width") + 2);
            this.set_height(16);
        }
    }
}