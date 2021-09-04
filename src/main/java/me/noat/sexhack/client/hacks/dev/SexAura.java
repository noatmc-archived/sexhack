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
import net.minecraft.network.play.client.CPacketUseEntity;
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
    Setting minDmg = create("Minimum Dmg", "asmindmg", 6, 0, 36);
    Setting selfDmg = create("Max Self Dmg", "asmaxselfdmg", 8, 0, 36);
    Setting debug = create("Debug", "asdebug", false);
    private double damage;
    private BlockPos placePos;
    private EntityPlayer e;
    private BlockPos crystalPt2;
    private boolean placing;
    private boolean breaking;

    public SexAura() {
        super(WurstplusCategory.WURSTPLUS_BETA);

        this.name = "!Auto Sex";
        this.tag = "yes";
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
            if (breakCrystal.get_value(true) && !breaking) {
                breakCrystal();
                WurstplusMessageUtil.send_client_message("breaking crystal");
            }
            if (place.get_value(true) && !placing) {
                placeCrystal();
                WurstplusMessageUtil.send_client_message("placing crystal");
            }
        }
        if (logic.in("PLBR")) { // if logic was place break
            if (place.get_value(true) && !placing) {
                placeCrystal();
            }
            if (breakCrystal.get_value(true) && !breaking) {
                breakCrystal();
            }
        }
    }

    // get target for autocrystal (nearest entity tbh)
    public EntityPlayer getTarget() {
        for (Entity entity : mc.world.getLoadedEntityList()) {
            if (!(entity instanceof EntityPlayer)) continue; // check if entity is player
            if (entity == mc.player) continue; // check if the player was you
            if (WurstplusFriendUtil.isFriend(entity.getName())) continue; // check if the player was friend
            e = (EntityPlayer) entity; // set player as the target
        }
        return e;
    }

    public BlockPos getPos() {
        EntityPlayer target = getTarget(); // pull data from getting target function
        for (BlockPos blocks : WurstplusCrystalUtil.possiblePlacePositions(placeRange.get_value(1), true, false)) {
            double damageToTarget = WurstplusCrystalUtil.calculateDamage(blocks.getX() + 0.5, blocks.getY() + 1, blocks.getZ() + 0.5, target); // set variable for target dmg
            double damageToSelf = WurstplusCrystalUtil.calculateDamage(blocks.getX() + 0.5, blocks.getY() + 1, blocks.getZ() + 0.5, mc.player); // set variable for self dmg
            if (damageToSelf > selfDmg.get_value(1)) continue; // check self dmg
            if (damageToTarget < minDmg.get_value(1)) continue; // check min dmg
            if (target.isDead || target.getHealth() <= 0) continue; // check if target is dead
            damage = damageToTarget;
            crystalPt2 = blocks; // set the variable
        }
        return crystalPt2;
    }

    public void placeCrystal() {
        placing = true;
        placePos = getPos(); // get position from getPos() function
        if (placePos != null) {
            WurstplusBlockUtil.placeCrystalOnBlock(placePos, EnumHand.OFF_HAND); // place crystal with offhand :3
        }
        if (debug.get_value(true)) {
            WurstplusMessageUtil.send_client_message("placing crystal");
        }
        placing = false;
    }

    public void breakCrystal() {
        breaking = true;
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
        breaking = false;
    }


    // render, pasted from wurst+ 2's ac!
    @Override
    public void render(WurstplusEventRender e) {
        BlockPos render_block = getPos();
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

    @Override
    public String array_detail() {
        EntityPlayer a = getTarget();
        return (a != null) ? a.getName() + " | " + mc.player.getDistance(a) : "None";
    }
}