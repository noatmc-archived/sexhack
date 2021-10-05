package me.noat.sexhack.client.event.events;

import me.noat.sexhack.client.event.WurstplusEventCancellable;
import net.minecraft.entity.Entity;

public
class WurstplusEventEntityRemoved extends WurstplusEventCancellable {

    private final Entity entity;

    public
    WurstplusEventEntityRemoved(Entity entity) {
        this.entity = entity;
    }

    public
    Entity get_entity() {
        return this.entity;
    }

}