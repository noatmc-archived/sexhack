package me.noat.sexhack.client.guiscreen.hud;

import me.noat.sexhack.client.guiscreen.render.pinnables.WurstplusPinnable;
import me.noat.sexhack.client.util.WurstplusFriendUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;

public class TargetHUD extends WurstplusPinnable {
    private static final Minecraft mc = Minecraft.getMinecraft();
    private EntityPlayer target = null;
    public TargetHUD() {
        super("TargetHUD", "TargetHUD", 1, 0, 0);
    }
    @Override
    public void render() {
        GlStateManager.pushMatrix();
        RenderHelper.enableGUIStandardItemLighting();

        create_rect(0, 0, this.get_width(), this.get_height(), 0, 0, 0, 60);

        this.set_width(16 * 9);
        this.set_height(16 * 3);
        try {
            target = getClosestEnemy();
            GuiInventory.drawEntityOnScreen(this.get_width() + 10, this.get_height() + 5, 25, 0.0f, 0.0f, target);
        } catch (Exception ignored) {
        }
    }

    public static
    EntityPlayer getClosestEnemy ( ) {
        EntityPlayer closestPlayer = null;
        for (EntityPlayer player : TargetHUD.mc.world.playerEntities) {
            if ( player == TargetHUD.mc.player || WurstplusFriendUtil.isFriend(player.getName()) ) continue;
            if ( closestPlayer == null ) {
                closestPlayer = player;
                continue;
            }
            if ( ! ( TargetHUD.mc.player.getDistanceSq ( player ) < TargetHUD.mc.player.getDistanceSq ( closestPlayer ) ) )
                continue;
            closestPlayer = player;
        }
        return closestPlayer;
    }
}
