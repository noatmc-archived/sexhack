package me.noat.sexhack.mixins;

import me.noat.sexhack.SexHack;
import me.noat.sexhack.client.util.WurstplusCapeUtil;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin (value = {AbstractClientPlayer.class})
public abstract
class WurstplusMixinAbstractClientPlayer {
    @Shadow
    @Nullable
    protected abstract
    NetworkPlayerInfo getPlayerInfo();

    @Inject (method = {"getLocationCape"}, at = {@At (value = "HEAD")}, cancellable = true)
    public
    void getLocationCape(CallbackInfoReturnable <ResourceLocation> callbackInfoReturnable) {

        if (SexHack.get_hack_manager().get_module_with_tag("Capes").is_active()) {
            NetworkPlayerInfo info = this.getPlayerInfo();
            assert info != null;
            if (!WurstplusCapeUtil.is_uuid_valid(info.getGameProfile().getId())) {
                return;
            }
            ResourceLocation r;
            if (SexHack.get_setting_manager().get_setting_with_tag("Capes", "CapeCape").in("Clockwork")) {
                r = new ResourceLocation("custom/clockwork.png");
            } else {
                if (SexHack.get_setting_manager().get_setting_with_tag("Capes", "CapeCape").in("OG")) {
                    r = new ResourceLocation("custom/cape-old.png");
                } else {
                    if (SexHack.get_setting_manager().get_setting_with_tag("Capes", "CapeCape").in("ifarticuhm")) {
                        r = new ResourceLocation("custom/ifarticuhm.png");
                    } else {
                        if (SexHack.get_setting_manager().get_setting_with_tag("Capes", "CapeCape").in("Clockwork MC")) {
                            r = new ResourceLocation("custom/clockworkmc.png");
                        } else {
                            if (SexHack.get_setting_manager().get_setting_with_tag("Capes", "CapeCape").in("Ping Players")) {
                                r = new ResourceLocation("custom/pingplayers.png");
                            } else {
                                if (SexHack.get_setting_manager().get_setting_with_tag("Capes", "CapeCape").in("teejwrld")) {
                                    r = new ResourceLocation("custom/teejwrld.png");
                                } else {
                                    if (SexHack.get_setting_manager().get_setting_with_tag("Capes", "CapeCape").in("cringesyringe")) {
                                        r = new ResourceLocation("custom/cringesyringe.png");
                                    } else {
                                        r = new ResourceLocation("custom/cape.png");
                                    }
                                }
                            }
                        }
                    }
                }
            }

            callbackInfoReturnable.setReturnValue(r);
        }


    }

}
