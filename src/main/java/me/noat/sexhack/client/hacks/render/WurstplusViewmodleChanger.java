package me.noat.sexhack.client.hacks.render;


import me.noat.sexhack.client.guiscreen.settings.Setting;
import me.noat.sexhack.client.hacks.Module;
import me.noat.sexhack.client.hacks.WurstplusCategory;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WurstplusViewmodleChanger extends Module {
    Setting custom_fov = create("FOV", "FOVSlider", 130, 110, 170);
    Setting items = create("Items", "FOVItems", false);
    Setting viewmodle_fov = create("Items FOV", "ItemsFOVSlider", 130, 110, 170);
    Setting normal_offset = create("Offset", "FOVOffset", true);
    Setting offset = create("Offset Main", "FOVOffsetMain", 0.7, 0.0, 1.0);
    Setting offset_x = create("Offset X", "FOVOffsetX", 0.0, -1.0, 1.0);
    Setting offset_y = create("Offset Y", "FOVOffsetY", 0.0, -1.0, 1.0);
    Setting main_x = create("Main X", "FOVMainX", 0.0, -1.0, 1.0);
    Setting main_y = create("Main Y", "FOVMainY", 0.0, -1.0, 1.0);
    private float fov;


    public WurstplusViewmodleChanger() {
        super(WurstplusCategory.WURSTPLUS_RENDER);

        this.name = "Custom Viewmodel";
        this.tag = "CustomViewmodel";
        this.description = "anti chad";
    }

    @Override
    protected void enable() {
        fov = mc.gameSettings.fovSetting;
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    protected void disable() {
        mc.gameSettings.fovSetting = fov;
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    @Override
    public void update() {
        mc.gameSettings.fovSetting = custom_fov.get_value(1);
    }

    @SubscribeEvent
    public void fov_event(final EntityViewRenderEvent.FOVModifier m) {
        if (items.get_value(true))
            m.setFOV(viewmodle_fov.get_value(1));
    }

}