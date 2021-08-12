package me.noat.sexhack.client.event.events;

import me.noat.sexhack.client.event.WurstplusEventCancellable;
import net.minecraft.util.EnumHand;

public class WurstplusEventSwing extends WurstplusEventCancellable {
    
    public EnumHand hand;

    public WurstplusEventSwing(EnumHand hand) {
        super();
        this.hand = hand;
    }

}