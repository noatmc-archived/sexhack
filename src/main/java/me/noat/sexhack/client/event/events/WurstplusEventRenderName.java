package me.noat.sexhack.client.event.events;

import me.noat.sexhack.client.event.WurstplusEventCancellable;
import net.minecraft.client.entity.AbstractClientPlayer;

public
class WurstplusEventRenderName extends WurstplusEventCancellable {

    public final AbstractClientPlayer Entity;
    public final String Name;
    public final double DistanceSq;
    public double X;
    public double Y;
    public double Z;

    public
    WurstplusEventRenderName(AbstractClientPlayer entityIn, double x, double y, double z, String name, double distanceSq) {
        super();

        Entity = entityIn;
        Name = name;
        DistanceSq = distanceSq;
    }

}