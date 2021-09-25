package me.noat.sexhack.client.hacks.render;

import me.noat.sexhack.client.guiscreen.settings.Setting;
import me.noat.sexhack.client.hacks.Module;
import me.noat.sexhack.client.hacks.WurstplusCategory;
import me.noat.sexhack.client.hacks.dev.SexAura;
import me.noat.sexhack.client.util.WurstplusRenderUtil;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

import java.awt.*;

public class TargetHUD extends Module {
    Setting x = create("X", "X", 0, 0, 500);
    Setting y = create("Y", "Y", 0, 0, 500);

    public TargetHUD() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.name = "TargetHUD";
        this.tag = "TargetHUD";
        this.description = "deez nuts";
    }

    @Override
    public void render() {
        EntityPlayer target = SexAura.staticGetTarget();
        if (target != null) {
            int xOffset = 39;
            for (int i = target.inventory.armorInventory.size(); i > 0; i--) {
                final ItemStack stack2 = target.inventory.armorInventory.get(i - 1);
                final ItemStack armourStack = stack2.copy();
                if (armourStack.hasEffect() && (armourStack.getItem() instanceof ItemTool || armourStack.getItem() instanceof ItemArmor)) {
                    armourStack.stackSize = 1;
                }
                WurstplusNameTags.renderStatic(armourStack, x.getValue(1) + 39);
                xOffset = xOffset + 4;
            }
            mc.fontRenderer.drawStringWithShadow(target.getName() + " | " + target.getHealth(), x.getValue(1) + 39, y.getValue(1) + 10, new Color(255, 255, 255, 255).hashCode());
            WurstplusRenderUtil.drawRectangleCorrectly(x.getValue(1), y.getValue(1), 150, 80, new Color(0, 0, 0, 60).hashCode());
            GuiInventory.drawEntityOnScreen(x.getValue(1) + 20, y.getValue(1) + 70, 35, 0, 0, target);
            WurstplusRenderUtil.drawRectangleCorrectly(x.getValue(1) + 39, y.getValue(1) + 70, 104, 8, new Color(0, 0, 0, 145).hashCode());
            WurstplusRenderUtil.drawRectangleCorrectly(x.getValue(1) + 41, y.getValue(1) + 72, Math.round(target.getHealth()) * 5, 4, new Color(0, 255, 0, 255).hashCode());
        }
    }
}
