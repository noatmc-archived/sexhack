package me.noat.sexhack.client.event.events;

import me.noat.sexhack.client.event.WurstplusEventCancellable;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;

public
class WurstplusEventRenderEntityModel extends WurstplusEventCancellable {
    public final ModelBase modelBase;
    public final Entity entity;
    public final float limbSwing;
    public final float limbSwingAmount;
    public final float age;
    public final float headYaw;
    public final float headPitch;
    public final float scale;
    public final int stage;

    public
    WurstplusEventRenderEntityModel(final int stage, final ModelBase modelBase, final Entity entity, final float limbSwing, final float limbSwingAmount, final float age, final float headYaw, final float headPitch, final float scale) {
        this.stage = stage;
        this.modelBase = modelBase;
        this.entity = entity;
        this.limbSwing = limbSwing;
        this.limbSwingAmount = limbSwingAmount;
        this.age = age;
        this.headYaw = headYaw;
        this.headPitch = headPitch;
        this.scale = scale;
    }
}
