package me.noat.sexhack.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Objects;

public class CircleRenderUtil {
    private static final Minecraft mc = Minecraft.getMinecraft();
    public static ICamera camera = new Frustum();

    public static void drawCircle(BlockPos pos , float radius, Color colour){
        //IBlockState iblockstate = CircleRenderUtil.mc.world.getBlockState(new BlockPos(x, y, z));
        //AxisAlignedBB bb = iblockstate.getSelectedBoundingBox(CircleRenderUtil.mc.world, new BlockPos(x, y, z)).offset(-interpPos.x, -interpPos.y, -interpPos.z);
        AxisAlignedBB bb = new AxisAlignedBB((double) pos.getX() - CircleRenderUtil.mc.getRenderManager().viewerPosX, (double) pos.getY() - CircleRenderUtil.mc.getRenderManager().viewerPosY,
                (double) pos.getZ() - CircleRenderUtil.mc.getRenderManager().viewerPosZ,
                (double) (pos.getX() + 1) - CircleRenderUtil.mc.getRenderManager().viewerPosX,
                (double) (pos.getY() + 1) - CircleRenderUtil.mc.getRenderManager().viewerPosY, (double) (pos.getZ() + 1) - CircleRenderUtil.mc.getRenderManager().viewerPosZ);
        camera.setPosition(Objects.requireNonNull(CircleRenderUtil.mc.getRenderViewEntity()).posX, CircleRenderUtil.mc.getRenderViewEntity().posY, CircleRenderUtil.mc.getRenderViewEntity().posZ);
        if (camera.isBoundingBoxInFrustum(new AxisAlignedBB(bb.minX + CircleRenderUtil.mc.getRenderManager().viewerPosX, bb.minY + CircleRenderUtil.mc.getRenderManager().viewerPosY, bb.minZ + CircleRenderUtil.mc.getRenderManager().viewerPosZ, bb.maxX + CircleRenderUtil.mc.getRenderManager().viewerPosX, bb.maxY + CircleRenderUtil.mc.getRenderManager().viewerPosY, bb.maxZ + CircleRenderUtil.mc.getRenderManager().viewerPosZ))) {
            drawCircleVertices(bb, radius, colour);
        }
    }

    public static void drawColumn(float x, float y, float z, float radius, Color colour, int amount, double height){
        double Hincrement = height/amount;
        float Rincrement = (radius/amount) * (float) height;


        BlockPos pos = new BlockPos(x, y, z);
        AxisAlignedBB bb = new AxisAlignedBB((double) pos.getX() - CircleRenderUtil.mc.getRenderManager().viewerPosX, (double) pos.getY() - CircleRenderUtil.mc.getRenderManager().viewerPosY,
                (double) pos.getZ() - CircleRenderUtil.mc.getRenderManager().viewerPosZ,
                (double) (pos.getX() + 1) - CircleRenderUtil.mc.getRenderManager().viewerPosX,
                (double) (pos.getY() + 1) - CircleRenderUtil.mc.getRenderManager().viewerPosY, (double) (pos.getZ() + 1) - CircleRenderUtil.mc.getRenderManager().viewerPosZ);
        camera.setPosition(Objects.requireNonNull(CircleRenderUtil.mc.getRenderViewEntity()).posX, CircleRenderUtil.mc.getRenderViewEntity().posY, CircleRenderUtil.mc.getRenderViewEntity().posZ);

        if (camera.isBoundingBoxInFrustum(new AxisAlignedBB(bb.minX + CircleRenderUtil.mc.getRenderManager().viewerPosX, bb.minY + CircleRenderUtil.mc.getRenderManager().viewerPosY, bb.minZ + CircleRenderUtil.mc.getRenderManager().viewerPosZ, bb.maxX + CircleRenderUtil.mc.getRenderManager().viewerPosX, bb.maxY + CircleRenderUtil.mc.getRenderManager().viewerPosY, bb.maxZ + CircleRenderUtil.mc.getRenderManager().viewerPosZ))) {
            for (int i =0; i<=amount;i++) {
                bb = new AxisAlignedBB(bb.minX, bb.minY + Hincrement*i , bb.minZ, bb.maxX, bb.maxY+ Hincrement*i, bb.maxZ);
                drawCircleVertices(bb, Rincrement*i, colour);
            }
        }
    }

    public static void drawCircleVertices(AxisAlignedBB bb, float radius, Color colour){
        float r = (float) colour.getRed() / 255.0f;
        float g = (float) colour.getGreen() / 255.0f;
        float b = (float) colour.getBlue() / 255.0f;
        float a = (float) colour.getAlpha() / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glLineWidth(1f);
        for (int i = 0; i < 360; i++) {
            buffer.begin(3, DefaultVertexFormats.POSITION_COLOR);
            buffer.pos(bb.getCenter().x + (Math.sin((i * 3.1415926D / 180)) * radius), bb.minY, bb.getCenter().z + (Math.cos((i * 3.1415926D / 180)) * radius)).color(r, g, b, a).endVertex();
            buffer.pos(bb.getCenter().x + (Math.sin(((i + 1) * 3.1415926D / 180)) * radius), bb.minY, bb.getCenter().z + (Math.cos(((i + 1) * 3.1415926D / 180)) * radius)).color(r, g, b, a).endVertex();
            tessellator.draw();
        }
        GL11.glDisable(2848);
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
}
