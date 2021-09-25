package me.noat.sexhack.client.hacks.dev;

import com.mojang.authlib.GameProfile;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.noat.sexhack.client.event.events.WurstplusEventPacket;
import me.noat.sexhack.client.guiscreen.settings.Setting;
import me.noat.sexhack.client.hacks.Module;
import me.noat.sexhack.client.hacks.WurstplusCategory;
import me.noat.sexhack.client.util.WurstplusCrystalUtil;
import me.noat.sexhack.client.util.WurstplusMessageUtil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.UUID;

public class WurstplusFakePlayer
        extends Module {
    public Setting pops = create("Totem Pops", "Pop", true);
    public EntityOtherPlayerMP fakePlayer;
    private int i = 0;

    public WurstplusFakePlayer() {
        super(WurstplusCategory.WURSTPLUS_BETA);
        this.name = "Fake Player";
        this.tag = "fakeplayer";
        this.description = "pasted";
    }

    @Override
    public void enable() {
        i = 0;
        if (WurstplusFakePlayer.mc.world == null || WurstplusFakePlayer.mc.player == null) {
            this.disable();
        } else {
            UUID playerUUID = WurstplusFakePlayer.mc.player.getUniqueID();
            this.fakePlayer = new EntityOtherPlayerMP(WurstplusFakePlayer.mc.world, new GameProfile(UUID.fromString(playerUUID.toString()), "noatmc"));
            this.fakePlayer.copyLocationAndAnglesFrom(WurstplusFakePlayer.mc.player);
            this.fakePlayer.inventory.copyInventory(WurstplusFakePlayer.mc.player.inventory);
            fakePlayer.hurtTime = 5;
            WurstplusFakePlayer.mc.world.addEntityToWorld(-7777, this.fakePlayer);
            WurstplusMessageUtil.send_client_message("Added a " + ChatFormatting.GREEN + "fake player" + ChatFormatting.RESET + " to your world.");
        }
    }

    @EventHandler
    private final Listener<WurstplusEventPacket.ReceivePacket> receivePacketListener = new Listener<>(event -> {
        double damage;
        SPacketExplosion explosion;
        if (this.fakePlayer == null) {
            return;
        }
        if (event.get_packet() instanceof SPacketExplosion && this.fakePlayer.getDistance((explosion = (SPacketExplosion) event.get_packet()).getX(), explosion.getY(), explosion.getZ()) <= 15.0 && (damage = WurstplusCrystalUtil.calculateDamage(explosion.getX(), explosion.getY(), explosion.getZ(), this.fakePlayer)) > 0.0 && this.pops.getValue(true)) {
            this.fakePlayer.setHealth((float) ((double) this.fakePlayer.getHealth() - MathHelper.clamp(damage, 0.0, 999.0)));
        }
    });

    @SubscribeEvent
    public void update() {
        if (this.pops.getValue(true) && this.fakePlayer != null) {
            this.fakePlayer.inventory.offHandInventory.set(0, new ItemStack(Items.TOTEM_OF_UNDYING));
            if (this.fakePlayer.getHealth() <= 0.0f) {
                this.fakePop(this.fakePlayer);
                this.fakePlayer.setHealth(20.0f);
                WurstplusMessageUtil.send_client_message("You have popped noatmc " + i + " times!");
                i++;
                fakePlayer.hurtTime = 5;
            }
        }
    }

    @Override
    public void disable() {
        i = 0;
        if (this.fakePlayer != null && WurstplusFakePlayer.mc.world != null) {
            WurstplusFakePlayer.mc.world.removeEntityFromWorld(-7777);
            WurstplusMessageUtil.send_client_message("Removed a " + ChatFormatting.RED + "fake player" + ChatFormatting.RESET + " from your world.");
            this.fakePlayer = null;
        }
    }

    public void fakePop(Entity entity) {
        WurstplusFakePlayer.mc.effectRenderer.emitParticleAtEntity(entity, EnumParticleTypes.TOTEM, 30);
        WurstplusFakePlayer.mc.world.playSound(entity.posX, entity.posY, entity.posZ, SoundEvents.ITEM_TOTEM_USE, entity.getSoundCategory(), 1.0f, 1.0f, false);
    }
}