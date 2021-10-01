package me.noat.sexhack.client.hacks.dev;

import me.noat.sexhack.client.event.events.WurstplusEventPacket;
import me.noat.sexhack.client.guiscreen.settings.Setting;
import me.noat.sexhack.client.hacks.Module;
import me.noat.sexhack.client.hacks.WurstplusCategory;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;

public class PoopChams extends Module {

    private static final HashMap<EntityOtherPlayerMP, Long> popFakePlayerMap = new HashMap<>();
    @EventHandler
    private final Listener<WurstplusEventPacket.ReceivePacket> packet_event = new Listener<>(event -> {

        if (event.getPacket() instanceof SPacketEntityStatus) {

            SPacketEntityStatus packet = (SPacketEntityStatus) event.getPacket();

            if (packet.getOpCode() == 35) {
                final Entity entity = packet.getEntity(mc.world);
                if (entity instanceof EntityPlayer) {
                    final EntityPlayer player = (EntityPlayer) entity;
                    final EntityOtherPlayerMP fakeEntity = new EntityOtherPlayerMP(mc.world, player.getGameProfile());
                    fakeEntity.copyLocationAndAnglesFrom(player);
                    fakeEntity.rotationYawHead = player.rotationYawHead;
                    fakeEntity.prevRotationYawHead = player.rotationYawHead;
                    fakeEntity.rotationYaw = player.rotationYaw;
                    fakeEntity.prevRotationYaw = player.rotationYaw;
                    fakeEntity.rotationPitch = player.rotationPitch;
                    fakeEntity.prevRotationPitch = player.rotationPitch;
                    fakeEntity.cameraYaw = fakeEntity.rotationYaw;
                    fakeEntity.cameraPitch = fakeEntity.rotationPitch;
                    popFakePlayerMap.put(fakeEntity, System.currentTimeMillis());
                }
            }
        }
    });
    Setting fadeTime = create("FadeTime", "popFadeTime", 3000, 1, 5000);
    Setting fadeSpeed = create("FadeSpeed", "popFadeSpeed", 0.05f, 0.01f, 1.0f);
    Setting fadeMode = create("Fade Mode", "popFadeMode", "Elevator", combobox("Elevator", "Fade", "None"));
    Setting elevatorMode = create("Elevator Mode", "popElevatorMode", "Heaven", combobox("Heaven", "Hell"));
    Setting renderMode = create("Render Mode", "popRenderMode", "Both", combobox("Both", "Textured", "Wireframe"));
    Setting lineWidth = create("Line Width", "popLineWidth", 1f, 0.1f, 3.0f);
    Setting r = create("Red", "popRed", 255, 0, 255);
    Setting g = create("Green", "popGreen", 255, 0, 255);
    Setting b = create("Blue", "popBlue", 255, 0, 255);
    Setting a = create("Alpha", "popAlpha", 127, 0, 255);
    float fade = 1.0f;

    public PoopChams() {
        super(WurstplusCategory.WURSTPLUS_BETA);
        this.name = "PoopChams";
        this.description = "show pop";
        this.tag = "PoopChams";
    }

    @SubscribeEvent
    public void onRenderLast(RenderWorldLastEvent event) {
        for (Map.Entry<EntityOtherPlayerMP, Long> entry : new HashMap<>(popFakePlayerMap).entrySet()) {
            boolean wireFrame;
            boolean textured;
            if (renderMode.in("Both")) {
                wireFrame = true;
                textured = true;
            } else if (renderMode.in("Wireframe")) {
                wireFrame = true;
                textured = false;
            } else {
                wireFrame = false;
                textured = true;
            }
            if (System.currentTimeMillis() - entry.getValue() < (long) fadeTime.getValue(1) && fadeMode.in("Elevator")) {
                if (elevatorMode.in("Heaven")) {
                    entry.getKey().posY += fadeSpeed.getValue(1) * event.getPartialTicks();
                } else {
                    entry.getKey().posY -= fadeSpeed.getValue(1) * event.getPartialTicks();
                }
            } else if (System.currentTimeMillis() - entry.getValue() < (long) fadeTime.getValue(1) && fadeMode.in("Fade")) {
                fade -= fadeSpeed.getValue(1);
            }
            if (System.currentTimeMillis() - entry.getValue() > (long) fadeTime.getValue(1) || fade == 0.0f) {
                popFakePlayerMap.remove(entry.getKey());
                continue;
            }
            GL11.glPushMatrix();
            GL11.glDepthRange(0.01, 1.0f);
            if (wireFrame) {
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
                GL11.glEnable(GL11.GL_ALPHA_TEST);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glLineWidth(lineWidth.getValue(1));
                GL11.glEnable(GL11.GL_LINE_SMOOTH);
                GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
                GL11.glColor4f(r.getValue(1) / 255f, g.getValue(1) / 255f, b.getValue(1) / 255f, fadeMode.in("Fade") ? fade : 1f);
                renderEntityStatic(entry.getKey(), event.getPartialTicks(), true);
                GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_DONT_CARE);
                GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glColor4f(1f, 1f, 1f, 1f);
                GL11.glEnable(GL11.GL_TEXTURE_2D);
            }
            if (textured) {
                GL11.glPushAttrib(GL11.GL_ALL_CLIENT_ATTRIB_BITS);
                GL11.glEnable(GL11.GL_ALPHA_TEST);
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                GL11.glDepthMask(false);
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                GL11.glLineWidth(1.5f);
                GL11.glColor4f(r.getValue(1) / 255f, g.getValue(1) / 255f, b.getValue(1) / 255f, fadeMode.in("Fade") ? fade : a.getValue(1) / 255f);
                renderEntityStatic(entry.getKey(), event.getPartialTicks(), true);
                GL11.glEnable(GL11.GL_DEPTH_TEST);
                GL11.glDepthMask(true);
                GL11.glDisable(GL11.GL_ALPHA_TEST);
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glColor4f(1f, 1f, 1f, 1f);
                GL11.glPopAttrib();
            }
            GL11.glDepthRange(0.0, 1.0f);
            GL11.glPopMatrix();
            fade = 1.0f;
        }
    }

    public void renderEntityStatic(Entity entityIn, float partialTicks, boolean p_188388_3_) {
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
            i = 15728880;
        }

        int j = i % 65536;
        int k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);
        mc.getRenderManager().renderEntity(entityIn, d0 - mc.getRenderManager().viewerPosX, d1 - mc.getRenderManager().viewerPosY, d2 - mc.getRenderManager().viewerPosZ, f, partialTicks, p_188388_3_);
    }
}