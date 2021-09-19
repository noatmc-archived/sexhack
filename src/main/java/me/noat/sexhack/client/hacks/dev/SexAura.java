package me.noat.sexhack.client.hacks.dev;

import me.noat.sexhack.client.event.events.WurstplusEventPacket;
import me.noat.sexhack.client.event.events.WurstplusEventRender;
import me.noat.sexhack.client.guiscreen.settings.Setting;
import me.noat.sexhack.client.hacks.Module;
import me.noat.sexhack.client.hacks.WurstplusCategory;
import me.noat.sexhack.client.util.*;
import me.noat.turok.draw.RenderHelp;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.server.SPacketSpawnObject;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

import java.util.Comparator;
import java.util.Objects;

public class SexAura extends Module {
    // settings
    Setting logic = create("Logic", "aslogic", "BRPL", combobox("PLBR", "BRPL"));
    Setting breakCrystal = create("Break", "asbreak", true);
    Setting place = create("Place", "asplace", true);
    Setting cancel = create("Cancel Crystal", "ascancel", true);
    private static EntityPlayer a;
    Setting breakRange = create("Break Range", "asbrange", 6.0f, 0.0f, 6.0f);
    Setting placeRange = create("Place Range", "asrange", 6.0f, 0.0f, 6.0f);
    // cancel crystal system
    @EventHandler
    private final Listener<WurstplusEventPacket.SendPacket> send_listener = new Listener<>(event -> {
        CPacketUseEntity packet;
        if (cancel.get_value(true)) {
            if (event.getStage() == 0 && event.get_packet() instanceof CPacketUseEntity && (packet = (CPacketUseEntity) event.get_packet()).getAction() == CPacketUseEntity.Action.ATTACK && packet.getEntityFromWorld(mc.world) instanceof EntityEnderCrystal) {
                Objects.requireNonNull(packet.getEntityFromWorld(mc.world)).setDead();
                mc.world.removeEntityFromWorld(packet.entityId);
            }
        }
    });
    Setting instant = create("Instant", "asinstant", true);
    Setting minDmg = create("Minimum Dmg", "asmindmg", 6, 0, 36);
    Setting selfDmg = create("Max Self Dmg", "asmaxselfdmg", 8, 0, 36);
    @EventHandler
    private final Listener<WurstplusEventPacket.ReceivePacket> receive = new Listener<>(event -> {
        if (instant.get_value(true)) {
            SPacketSpawnObject packet2;
            if (event.get_packet() instanceof SPacketSpawnObject && (packet2 = (SPacketSpawnObject) event.get_packet()).getType() == 51) {
                CPacketUseEntity predict = new CPacketUseEntity();
                predict.entityId = packet2.getEntityID();
                predict.action = CPacketUseEntity.Action.ATTACK;
                mc.player.connection.sendPacket(predict);
            }
        }
    });
    Setting debug = create("Debug", "asdebug", false);
    private double damage;
    private BlockPos placePos;
    private EntityPlayer e;
    private BlockPos crystalPt2;
    Setting nomultiplace = create("No Multiplace", "asmultiplace", true);
    Setting faceplaceHp = create("Faceplace HP", "asFaceplace", 8, 0, 36);

    public SexAura() {
        super(WurstplusCategory.WURSTPLUS_BETA);

        this.name = "!Auto Sex";
        this.tag = "SexAura";
        this.description = "kills people (if ur good)";
    }

    // disable and reset all the places and crystals
    @Override
    public void disable() {
        placePos = null;
        e = null;
    }

    // make every update do Auto Crystal!
    @Override
    public void update() {
        doAutoCrystal();
    }

    // enable and reset all the places and crystals
    @Override
    public void enable() {
        placePos = null;
        e = null;
    }

    // autocrystal logic
    public void doAutoCrystal() {
        if (logic.in("BRPL")) { // if logic was break place
            if (breakCrystal.get_value(true)) {
                breakCrystal();
            }
            if (place.get_value(true)) {
                placeCrystal();
            }
        }
        if (logic.in("PLBR")) { // if logic was place break
            if (place.get_value(true)) {
                placeCrystal();
            }
            if (breakCrystal.get_value(true)) {
                breakCrystal();
            }
        }
    }

    public static EntityPlayer staticGetTarget() {
        for (Entity entity : mc.world.getLoadedEntityList()) {
            if (!(entity instanceof EntityPlayer)) continue; // check if entity is player
            if (entity == mc.player) continue; // check if the player was you
            if (entity.getDistance(mc.player) >= 20) continue; // check if the player distance
            if (WurstplusFriendUtil.isFriend(entity.getName())) continue; // check if the player was friend
            a = (EntityPlayer) entity; // set player as the target
        }
        return a;
    }

    // get target for autocrystal (nearest entity tbh)
    public EntityPlayer getTarget() {
        for (Entity entity : mc.world.playerEntities) {
            if (!(entity instanceof EntityPlayer)) continue; // check if entity is player
            if (entity == mc.player) continue; // check if the player was you
            if (WurstplusFriendUtil.isFriend(entity.getName())) continue; // check if the player was friend
            if (entity.getDistance(mc.player) >= 20) continue; // check if the player distance
            e = (EntityPlayer) entity; // set player as the target
        }
        return e;
    }

    public BlockPos getPos() {
        damage = 0;
        for (Entity entity : mc.world.playerEntities) {
            if (WurstplusFriendUtil.isFriend(entity.getName())) continue; // check if the player was friend
            for (BlockPos blocks : WurstplusCrystalUtil.possiblePlacePositions(placeRange.get_value(1), false, nomultiplace.get_value(false))) {
                final EntityPlayer e = (EntityPlayer) entity;
                if (e == mc.player) continue; // check if the player was you
                if (e.getDistance(mc.player) >= 20) continue; // check if the player distance
                if (e.isDead || e.getHealth() <= 0) continue; // check if target is dead
                int minimum_damage = minDmg.get_value(1);
                double damageToTarget = WurstplusCrystalUtil.calculateDamage(blocks.getX() + 0.5, blocks.getY() + 1, blocks.getZ() + 0.5, e); // set variable for target dmg
                double damageToSelf = WurstplusCrystalUtil.calculateDamage(blocks.getX() + 0.5, blocks.getY() + 1, blocks.getZ() + 0.5, mc.player); // set variable for self dmg
                if (!(damage <= damageToTarget)) continue;
                if (damageToSelf > selfDmg.get_value(1)) continue; // check self dmg
                if (damageToTarget < minimum_damage) continue; // check min dmg
                if (damageToTarget > damage) {
                    damage = damageToTarget;
                }
                crystalPt2 = blocks; // set the variable
            }
        }
        return crystalPt2;
    }

    public boolean isOffhandCrystal() {
        return (mc.player.getHeldItemOffhand().item == Items.END_CRYSTAL);
    }

    public void placeCrystal() {
        placePos = getPos(); // get position from getPos() function
        if (placePos != null) {
            WurstplusBlockUtil.placeCrystalOnBlock(placePos, isOffhandCrystal() ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND); // place crystal with offhand :3
        }
        if (debug.get_value(true)) {
            WurstplusMessageUtil.send_client_message("placing crystal");
        }
        placePos = null;
    }

    public boolean isFaceplacable(EntityPlayer e, int hp) {
        return e.getHealth() + e.getAbsorptionAmount() <= hp;
    }

    public void breakCrystal() {
        // check for good crystals!
        EntityEnderCrystal crystals = mc.world.getLoadedEntityList().stream()
                .filter(entity -> entity instanceof EntityEnderCrystal)
                .filter(entity -> mc.player.canEntityBeSeen(entity))
                .map(entity -> (EntityEnderCrystal) entity)
                .min(Comparator.comparing(c -> mc.player.getDistance(c) < breakRange.get_value(1)))
                .orElse(null);
        if (crystals != null) {
            // attack crystal
            mc.player.connection.sendPacket(new CPacketUseEntity(crystals));
        }
        if (debug.get_value(true)) {
            WurstplusMessageUtil.send_client_message("breaking crystal");
        }
    }


    // render, pasted from wurst+ 2's ac!
    @Override
    public void render(WurstplusEventRender e) {
        BlockPos render_block = getPos();
        if (render_block != null) {
            RenderHelp.prepare("quads");
            RenderHelp.draw_cube(RenderHelp.get_buffer_build(),
                    render_block.getX(), render_block.getY(), render_block.getZ(),
                    1, 1, 1,
                    255, 255, 255, 57,
                    "all"
            );
            RenderHelp.release();
            WurstplusRenderUtil.drawText(render_block, ((Math.floor(this.damage) == this.damage) ? Integer.valueOf((int) this.damage) : String.format("%.1f", this.damage)) + "");
        }
    }

    @Override
    public String array_detail() {
        EntityPlayer a = getTarget();
        return (a != null) ? a.getName() + " | " + mc.player.getDistance(a) : "None";
    }
}