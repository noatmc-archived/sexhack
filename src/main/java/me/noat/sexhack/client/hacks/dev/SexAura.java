package me.noat.sexhack.client.hacks.dev;

import me.noat.sexhack.client.event.events.WurstplusEventPacket;
import me.noat.sexhack.client.guiscreen.settings.Setting;
import me.noat.sexhack.client.hacks.Module;
import me.noat.sexhack.client.hacks.WurstplusCategory;
import me.noat.sexhack.client.util.WurstplusBlockUtil;
import me.noat.sexhack.client.util.WurstplusCrystalUtil;
import me.noat.sexhack.client.util.WurstplusFriendUtil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

import java.util.Comparator;
import java.util.Objects;

public class SexAura extends Module {
    public SexAura() {
        super(WurstplusCategory.WURSTPLUS_BETA);

        this.name = "!Auto Sex";
        this.tag = "sex";
        this.description = "kills people (if ur good)";
    }

    @EventHandler
    private final Listener<WurstplusEventPacket.SendPacket> send_listener = new Listener<>(event -> {
        CPacketUseEntity packet;
        if (event.getStage() == 0 && event.get_packet() instanceof CPacketUseEntity && (packet = (CPacketUseEntity) event.get_packet()).getAction() == CPacketUseEntity.Action.ATTACK && packet.getEntityFromWorld(mc.world) instanceof EntityEnderCrystal) {
            Objects.requireNonNull(packet.getEntityFromWorld(mc.world)).setDead();
            mc.world.removeEntityFromWorld(packet.entityId);
        }
    });
    Setting place = create("Place", "asplace", true);
    Setting breakCrystal = create("Break", "asbreak", true);
    Setting logic = create("Logic", "aslogic", "BRPL", combobox("PLBR", "BRPL"));
    Setting placeRange = create("Place Range", "asrange", 6.0f, 0.0f, 6.0f);
    Setting minDmg = create("Minimum Dmg", "asmindmg", 6, 0, 36);
    Setting selfDmg = create("Max Self Dmg", "asmaxselfdmg", 8, 0, 36);
    private BlockPos placePos;
    Setting breakRange = create("Break Range", "asbrange", 6.0f, 0.0f, 6.0f);
    private EntityPlayer e;
    private BlockPos crystalPt2;

    @Override
    public void disable() {
        placePos = null;
        e = null;
    }

    @Override
    public void update() {
        doAutoCrystal();
    }

    @Override
    public void enable() {
        placePos = null;
        e = null;
    }

    public void doAutoCrystal() {
        if (logic.in("PLBR")) {
            if (place.get_value(true)) {
                placeCrystal();
            }
            if (breakCrystal.get_value(true)) {
                breakCrystal();
            }
        }
        if (logic.in("PLBR")) {
            if (place.get_value(true)) {
                placeCrystal();
            }
            if (breakCrystal.get_value(true)) {
                breakCrystal();
            }
        }
    }

    public EntityPlayer getTarget() {
        for (Entity entity : mc.world.getLoadedEntityList()) {
            if (!(entity instanceof EntityPlayer)) continue;
            if (entity == mc.player) continue;
            if (WurstplusFriendUtil.isFriend(entity.getName())) continue;
            e = (EntityPlayer) entity;
        }
        return e;
    }

    public BlockPos getPos() {
        EntityPlayer target = getTarget();
        for (BlockPos blocks : WurstplusCrystalUtil.possiblePlacePositions(placeRange.get_value(1), true, false)) {
            BlockPos crystal = new BlockPos(blocks.getX() + 0.5, blocks.getY() + 1, blocks.getZ() + 0.5);
            double damageToTarget = WurstplusCrystalUtil.calculateDamage(crystal.getX() + 0.5, crystal.getY() + 1, crystal.getZ() + 0.5, target);
            double damageToSelf = WurstplusCrystalUtil.calculateDamage(crystal.getX() + 0.5, crystal.getY() + 1, crystal.getZ() + 0.5, mc.player);
            if (damageToTarget < minDmg.get_value(1)) continue;
            if (target.isDead || target.getHealth() <= 0) continue;
            crystalPt2 = blocks;
        }
        return crystalPt2;
    }

    public void placeCrystal() {
        placePos = getPos();
        if (placePos != null) {
            WurstplusBlockUtil.placeCrystalOnBlock(placePos, EnumHand.OFF_HAND);
        }
    }

    public void breakCrystal() {
        EntityEnderCrystal crystals = mc.world.getLoadedEntityList().stream()
                .filter(entity -> entity instanceof EntityEnderCrystal)
                .filter(entity -> mc.player.canEntityBeSeen(entity))
                .map(entity -> (EntityEnderCrystal) entity)
                .min(Comparator.comparing(c -> mc.player.getDistance(c) < breakRange.get_value(1)))
                .orElse(null);
        if (crystals != null) {
            mc.player.connection.sendPacket(new CPacketUseEntity(crystals));
        }
    }
}
