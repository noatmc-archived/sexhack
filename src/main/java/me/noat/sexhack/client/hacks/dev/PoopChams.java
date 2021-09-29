package me.noat.sexhack.client.hacks.dev;

import me.noat.sexhack.client.hacks.Module;
import me.noat.sexhack.client.hacks.WurstplusCategory;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;

public class PoopChams extends Module {
    private final HashMap<EntityOtherPlayerMP, Long> popFakePlayerMap = new HashMap();

    public PoopChams() {
        super(WurstplusCategory.WURSTPLUS_BETA);
        this.name = "PoopChams";
        this.tag = "PoopChams";
        this.description = "eh";
    }

    @Override
    public void render() {
        for (Map.Entry<EntityOtherPlayerMP, Long> entry : new HashMap<EntityOtherPlayerMP, Long>(this.popFakePlayerMap).entrySet()) {
            if (System.currentTimeMillis() - entry.getValue() > (long) 127) {
                this.popFakePlayerMap.remove(entry.getKey());
                continue;
            }
            GL11.glPushMatrix();
            GL11.glDepthRange(0.0, 0.01);
            GL11.glDisable(2896);
            GL11.glDisable(3553);
            GL11.glPolygonMode(1032, 6913);
            GL11.glEnable(3008);
            GL11.glEnable(3042);
            GL11.glLineWidth(2);
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            GL11.glColor4f(127 / 255.0f, 127 / 255.0f, 127 / 255.0f, 255);
            mc.getRenderManager().renderEntityStatic(entry.getKey(), 0.0f, false);
            GL11.glHint(3154, 4352);
            GL11.glPolygonMode(1032, 6914);
            GL11.glEnable(2896);
            GL11.glDepthRange(0.0, 1.0);
            GL11.glEnable(3553);
            GL11.glPopMatrix();
        }
    }

}
