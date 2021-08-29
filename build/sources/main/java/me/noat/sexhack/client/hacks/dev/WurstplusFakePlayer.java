package me.noat.sexhack.client.hacks.dev;

import com.mojang.authlib.GameProfile;
import me.noat.sexhack.client.event.events.WurstplusEventPacket;
import me.noat.sexhack.client.guiscreen.settings.Setting;
import me.noat.sexhack.client.hacks.WurstplusCategory;
import me.noat.sexhack.client.hacks.Module;
import me.noat.sexhack.client.util.WurstplusCrystalUtil;
import me.noat.sexhack.client.util.WurstplusMessageUtil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.server.SPacketDestroyEntities;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.potion.PotionEffect;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class WurstplusFakePlayer extends Module {
    
    public WurstplusFakePlayer() {
        super(WurstplusCategory.WURSTPLUS_BETA);

		this.name        = "Fake Player";
		this.tag         = "FakePlayer";
		this.description = "hahahaaha what a noob its in beta ahahahahaha";
    }

    Setting pop = create("Totem Pop", "FakePops", true);

    private EntityOtherPlayerMP fake_player;
    public static ConcurrentHashMap<EntityLivingBase, Integer> totemMap;
    @Override
    protected void enable() {
        
        fake_player = new EntityOtherPlayerMP(mc.world, new GameProfile(UUID.fromString("a07208c2-01e5-4eac-a3cf-a5f5ef2a4700"), "travis"));
        fake_player.copyLocationAndAnglesFrom(mc.player);
        fake_player.rotationYawHead = mc.player.rotationYawHead;
        mc.world.addEntityToWorld(-100, fake_player);

    }

    @EventHandler
    private final Listener<WurstplusEventPacket.ReceivePacket> packetRecieveListener = new Listener<>(event -> {
        if(pop.get_value(true)) {
            if(event.get_packet() instanceof SPacketDestroyEntities) {
                final SPacketDestroyEntities packet = (SPacketDestroyEntities) event.get_packet();

                for(int id : packet.getEntityIDs()) {
                    final Entity entity = mc.world.getEntityByID(id);

                    if(entity instanceof EntityEnderCrystal) {
                        if(entity.getDistanceSq(fake_player) < 144) {
                            final float rawDamage = WurstplusCrystalUtil.calculateDamage(entity.getPositionVector(), fake_player);
                            final float absorption = fake_player.getAbsorptionAmount() - rawDamage;
                            final boolean hasHealthDmg = absorption < 0;
                            final float health = fake_player.getHealth() + absorption;

                            if(hasHealthDmg && health > 0) {
                                fake_player.setHealth(health);
                                fake_player.setAbsorptionAmount(0);
                            } else if(health > 0) {
                                fake_player.setAbsorptionAmount(absorption);
                            } else {
                                fake_player.setHealth(2);
                                fake_player.setAbsorptionAmount(8);
                                fake_player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 5));
                                fake_player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 1));

                                try {
                                    mc.player.connection.handleEntityStatus(new SPacketEntityStatus(fake_player, (byte) 35));
                                } catch (Exception e) {
                                }

                                if(totemMap.containsKey(fake_player)) {
                                    int times = totemMap.get(fake_player) + 1;
                                    WurstplusMessageUtil.send_client_message("#EZMODE - you have just popped your fakeplayer " + times + " times!");
                                    totemMap.remove(fake_player);
                                    totemMap.put(fake_player, times);
                                } else {
                                    WurstplusMessageUtil.send_client_message("#EZMODE - you have just popped your fakeplayer 1 time!");
                                    totemMap.put(fake_player, 1);
                                }
                            }

                            fake_player.hurtTime = 5;
                        }
                    }
                }
            }
        }
    });

    @Override
    protected void disable() {
        try {
            mc.world.removeEntity(fake_player);
        } catch (Exception ignored) {}
    }

}