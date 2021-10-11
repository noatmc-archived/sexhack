package me.noat.sexhack.client.hacks.misc;

import me.noat.sexhack.client.guiscreen.settings.Setting;
import me.noat.sexhack.client.hacks.Module;
import me.noat.sexhack.client.hacks.WurstplusCategory;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;

public class Swing extends Module { // client 2.0.0 because yes
    final Setting swing = create("Swing", "swing", "Offhand", combobox("Main", "Offhand"));
    final Setting speed = create("Speed", "speed", "Fast", combobox("Slow", "Normal", "Fast"));
    final Setting switchSetting = create("Mode", "mode", "1.8", combobox("1.8", "1.9+"));
    public Swing() {
        super("Swing", "Swing", "offhand swing gaming :sunglasses:", WurstplusCategory.WURSTPLUS_MISC);
    }

    @Override
    public void update() {
        if (Swing.fullNullCheck()) {
            return;
        }
        if (this.swing.in("Offhand")) {
            Swing.mc.player.swingingHand = EnumHand.OFF_HAND;
        }
        if (this.swing.in("Main")) {
            Swing.mc.player.swingingHand = EnumHand.MAIN_HAND;
        }
        if (this.switchSetting.in("1.8") && (double)Swing.mc.entityRenderer.itemRenderer.prevEquippedProgressMainHand >= 0.9) {
            Swing.mc.entityRenderer.itemRenderer.equippedProgressMainHand = 1.0f;
            Swing.mc.entityRenderer.itemRenderer.itemStackMainHand = Swing.mc.player.getHeldItemMainhand();
        }
        if (this.speed.in("Slow")) {
            Swing.mc.player.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 10));
            Swing.mc.player.removePotionEffect(MobEffects.HASTE);
        } else if (this.speed.in("Normal")) {
            Swing.mc.player.removePotionEffect(MobEffects.MINING_FATIGUE);
            Swing.mc.player.removePotionEffect(MobEffects.HASTE);
        } else if (this.speed.in("Fast")) {
            Swing.mc.player.removePotionEffect(MobEffects.MINING_FATIGUE);
            Swing.mc.player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 10));
        }
    }

    @Override
    public void disable() {
        Swing.mc.player.removePotionEffect(MobEffects.MINING_FATIGUE);
        Swing.mc.player.removePotionEffect(MobEffects.HASTE);
    }
}
