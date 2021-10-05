package me.noat.sexhack.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public
class WurstplusOnlineFriends {

    public static final List <Entity> entities = new ArrayList <>();

    static public
    List <Entity> getFriends() {
        entities.clear();
        entities.addAll(Minecraft.getMinecraft().world.playerEntities.stream().filter(entityPlayer -> WurstplusFriendUtil.isFriend(entityPlayer.getName())).collect(Collectors.toList()));

        return entities;
    }

}