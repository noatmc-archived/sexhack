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
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.server.SPacketDestroyEntities;
import net.minecraft.network.play.server.SPacketSpawnObject;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

import java.util.Comparator;
import java.util.Objects;

public class SexAura extends Module {
    // settings
    Setting logic = create("Logic", "aslogic", "BRPL", combobox("PLBR", "BRPL"));
    Setting breakCrystal = create("Break", "asbreak", true);
    Setting place = create("Place", "asplace", true);
    Setting cancel = create("Cancel Crystal", "ascancel", true);
    private static EntityPlayer a;
    private final int abc = 0;
    private final int axckx = 0;
    Setting breakRange = create("Break Range", "asbrange", 6.0f, 0.0f, 6.0f);
    Setting placeRange = create("Place Range", "asrange", 6.0f, 0.0f, 6.0f);
    // cancel crystal system
    @EventHandler
    private final Listener<WurstplusEventPacket.SendPacket> send_listener = new Listener<>(event -> {
        CPacketUseEntity packet;
        if (cancel.getValue(true)) {
            if (event.getStage() == 0 && event.get_packet() instanceof CPacketUseEntity && (packet = (CPacketUseEntity) event.get_packet()).getAction() == CPacketUseEntity.Action.ATTACK && packet.getEntityFromWorld(mc.world) instanceof EntityEnderCrystal) {
                Objects.requireNonNull(packet.getEntityFromWorld(mc.world)).setDead();
                mc.world.removeEntityFromWorld(packet.entityId);
            }
        }
    });
    Setting instant = create("Instant", "asinstant", true);
    Setting sequential = create("Sequential", "asSequential", true);
    Setting minDmg = create("Minimum Dmg", "asmindmg", 6, 0, 36);
    Setting selfDmg = create("Max Self Dmg", "asmaxselfdmg", 8, 0, 36);
    @EventHandler
    private final Listener<WurstplusEventPacket.ReceivePacket> receive = new Listener<>(event -> {
        if (instant.getValue(true)) {
            SPacketSpawnObject packet2;
            if (event.get_packet() instanceof SPacketSpawnObject && (packet2 = (SPacketSpawnObject) event.get_packet()).getType() == 51) {
                CPacketUseEntity predict = new CPacketUseEntity();
                predict.entityId = packet2.getEntityID();
                predict.action = CPacketUseEntity.Action.ATTACK;
                mc.player.connection.sendPacket(predict);
            }
        }
        if (sequential.getValue(true)) {
            if (event.get_packet() instanceof SPacketDestroyEntities) {
                final SPacketDestroyEntities sPacket = (SPacketDestroyEntities) event.get_packet();
                for (int id : sPacket.getEntityIDs()) {
                    Entity entity = SexAura.mc.world.getEntityByID(id);
                    if (!(entity instanceof EntityEnderCrystal)) continue;
                    entity.setDead();
                }
            }
        }
    });
    Setting ignoreSelfDmg = create("Ignore Self Damage", "asIgnoreSelfDmg", true);
    Setting silent = create("Silent Switch", "asSilent", true);
    Setting thirteen = create("1.13", "asThirteen", false);
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

    // pery bobo
    public static void placeCrystalOnBlock(BlockPos pos, EnumHand hand, boolean swing, boolean exactHand, boolean silent) {
        RayTraceResult result = mc.world.rayTraceBlocks(new Vec3d(mc.player.posX, mc.player.posY + (double) mc.player.getEyeHeight(), mc.player.posZ), new Vec3d((double) pos.getX() + 0.5, (double) pos.getY() - 0.5, (double) pos.getZ() + 0.5));
        EnumFacing facing = result == null || result.sideHit == null ? EnumFacing.UP : result.sideHit;
        int old = mc.player.inventory.currentItem;
        int crystal = WurstplusPlayerUtil.findHotbarBlock(ItemEndCrystal.class);
        if (hand == EnumHand.MAIN_HAND && silent && crystal != -1 && crystal != mc.player.inventory.currentItem)
            mc.player.connection.sendPacket(new CPacketHeldItemChange(crystal));
        mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(pos, facing, hand, 0.0f, 0.0f, 0.0f));
        if (hand == EnumHand.MAIN_HAND && silent && crystal != -1 && crystal != mc.player.inventory.currentItem)
            mc.player.connection.sendPacket(new CPacketHeldItemChange(old));
        if (swing)
            mc.player.connection.sendPacket(new CPacketAnimation(exactHand ? hand : EnumHand.MAIN_HAND));
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

    // autocrystal logic
    public void doAutoCrystal() {
        if (logic.in("BRPL")) { // if logic was break place
            if (breakCrystal.getValue(true)) {
                breakCrystal();
            }
            if (place.getValue(true)) {
                placeCrystal();
            }
        }
        if (logic.in("PLBR")) { // if logic was place break
            if (place.getValue(true)) {
                placeCrystal();
            }
            if (breakCrystal.getValue(true)) {
                breakCrystal();
            }
        }
    }

    public BlockPos getPos() {
        long bcs = System.currentTimeMillis();
        crystalPt2 = null;
        damage = 0;
        for (Entity entity : mc.world.playerEntities) {
            if (WurstplusFriendUtil.isFriend(entity.getName())) continue; // check if the player was friend
            for (BlockPos blocks : WurstplusCrystalUtil.possiblePlacePositions(placeRange.getValue(1), thirteen.getValue(true), nomultiplace.getValue(false))) {
                final EntityPlayer e = (EntityPlayer) entity;
                if (e == mc.player) continue; // check if the player was you
                if (e.getDistance(mc.player) >= 20) continue; // check if the player distance
                if (e.isDead || e.getHealth() <= 0) continue; // check if target is dead
                int minimum_damage = minDmg.getValue(1);
                if (isFaceplacable(e, faceplaceHp.getValue(1)) || WP3CrystalUtil.getArmourFucker(e, 17)) {
                    minimum_damage = 2;
                }
                double damageToTarget = WurstplusCrystalUtil.calculateDamage(blocks.getX() + 0.5, blocks.getY() + 1, blocks.getZ() + 0.5, e); // set variable for target dmg
                double damageToSelf = WurstplusCrystalUtil.calculateDamage(blocks.getX() + 0.5, blocks.getY() + 1, blocks.getZ() + 0.5, mc.player); // set variable for self dmg
                if (!(damage <= damageToTarget)) continue;
                if (!ignoreSelfDmg.getValue(true)) {
                    if (damageToSelf > selfDmg.getValue(1)) continue;
                } // check self dmg
                if (damageToTarget < minimum_damage) continue; // check min dmg
                if (!(damage <= damageToTarget)) continue;
                if (damageToTarget > damage) {
                    damage = damageToTarget;
                }
                if (debug.getValue(true)) {
                    WurstplusMessageUtil.send_client_message("targetting X: " + blocks.getX() + ", Y: " + blocks.getY() + ", Z: " + blocks.getZ());
                    WurstplusMessageUtil.send_client_message("BlockPos calculation took: " + -(bcs - System.currentTimeMillis()) + "ms");
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
            // place crystal with offhand :3
            placeCrystalOnBlock(placePos, isOffhandCrystal() ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, true, false, silent.getValue(true)); // silent switch
        }
        if (debug.getValue(true)) {
            WurstplusMessageUtil.send_client_message("placing crystal");
        }
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
                .min(Comparator.comparing(c -> mc.player.getDistance(c) < breakRange.getValue(1)))
                .orElse(null);
        if (crystals != null) {
            // attack crystal
            mc.player.connection.sendPacket(new CPacketUseEntity(crystals));
        }
        if (debug.getValue(true)) {
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