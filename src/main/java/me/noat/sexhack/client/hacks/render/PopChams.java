
package me.noat.sexhack.client.hacks.render;

import me.noat.sexhack.client.event.events.WurstplusEventPacket;
import me.noat.sexhack.client.guiscreen.settings.Setting;
import me.noat.sexhack.client.hacks.Module;
import me.noat.sexhack.client.hacks.WurstplusCategory;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;

public class PopChams
        extends Module {
    public final Setting r = create("Red", "ColorRed", 255, 0, 255);
    public final Setting g = create("Green", "ColorGreen", 255, 0, 255);
    public final Setting b = create("Blue", "ColorBlue", 255, 0, 255);
    private final Setting lineWidth = create("Line Width", "LineWidth", 1.0f, 0.1f, 3.0f);
    private final HashMap<EntityOtherPlayerMP, Long> popFakePlayerMap = new HashMap();
    @EventHandler
    private final Listener<WurstplusEventPacket.ReceivePacket> receive_listener = new Listener<>(event -> {
        if (event.get_packet() instanceof SPacketEntityStatus) {

            SPacketEntityStatus packet = (SPacketEntityStatus) event.get_packet();

            if (packet.getOpCode() == 35) {
                Entity entity = packet.getEntity(mc.world);
                if (PopChams.mc.world.getEntityByID(entity.getEntityId()) != null && (entity = PopChams.mc.world.getEntityByID(entity.getEntityId())) instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) entity;
                    EntityOtherPlayerMP fakeEntity = new EntityOtherPlayerMP(PopChams.mc.world, player.getGameProfile());
                    fakeEntity.copyLocationAndAnglesFrom(player);
                    fakeEntity.rotationYawHead = player.rotationYawHead;
                    fakeEntity.prevRotationYawHead = player.rotationYawHead;
                    fakeEntity.rotationYaw = player.rotationYaw;
                    fakeEntity.prevRotationYaw = player.rotationYaw;
                    fakeEntity.rotationPitch = player.rotationPitch;
                    fakeEntity.prevRotationPitch = player.rotationPitch;
                    fakeEntity.cameraYaw = fakeEntity.rotationYaw;
                    fakeEntity.cameraPitch = fakeEntity.rotationPitch;
                    this.popFakePlayerMap.put(fakeEntity, System.currentTimeMillis());
                }
            }
        }
    });
    public Setting fadeTime = create("FadeTime", "Fade", 3000.0f, 100.0f, 5000.0f);

    public PopChams() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.name = "Pop Chams";
        this.tag = "PopChams";
        this.description = "popchams uwu";
    }

    public void render(RenderWorldLastEvent event) {
        for (Map.Entry<EntityOtherPlayerMP, Long> entry : new HashMap<EntityOtherPlayerMP, Long>(this.popFakePlayerMap).entrySet()) {
            if (System.currentTimeMillis() - entry.getValue() > (long) this.fadeTime.get_value(1)) {
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
            GL11.glLineWidth(this.lineWidth.get_value(1));
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            this.renderEntity(entry.getKey(), event.getPartialTicks(), false);
            GL11.glHint(3154, 4352);
            GL11.glPolygonMode(1032, 6914);
            GL11.glEnable(2896);
            GL11.glDepthRange(0.0, 1.0);
            GL11.glEnable(3553);
            GL11.glPopMatrix();
        }
    }

    private void glColor() {
        GL11.glColor4f((float) r.get_value(1) / 255.0f, (float) g.get_value(1) / 255.0f, (float) b.get_value(1) / 255.0f, 60);
    }

    public void renderEntity(Entity entityIn, float partialTicks, boolean p_188388_3_) {
        if (entityIn.ticksExisted == 0) {
            entityIn.lastTickPosX = entityIn.posX;
            entityIn.lastTickPosY = entityIn.posY;
            entityIn.lastTickPosZ = entityIn.posZ;
        }
        double d0 = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * (double) partialTicks;
        double d1 = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * (double) partialTicks;
        double d2 = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * (double) partialTicks;
        float f = entityIn.prevRotationYaw + (entityIn.rotationYaw - entityIn.prevRotationYaw) * partialTicks;
        int i = entityIn.getBrightnessForRender();
        if (entityIn.isBurning()) {
            i = 0xF000F0;
        }
        int j = i % 65536;
        int k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);
        this.glColor();
        mc.getRenderManager().renderEntity(entityIn, d0 - PopChams.mc.getRenderManager().viewerPosX, d1 - PopChams.mc.getRenderManager().viewerPosY, d2 - PopChams.mc.getRenderManager().viewerPosZ, f, partialTicks, p_188388_3_);
    }
}