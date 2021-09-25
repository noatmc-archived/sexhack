package me.noat.sexhack.client.guiscreen;

import me.noat.sexhack.SexHack;
import me.noat.sexhack.client.guiscreen.render.pinnables.WurstplusFrame;
import me.noat.sexhack.client.guiscreen.render.pinnables.WurstplusPinnableButton;
import net.minecraft.client.gui.GuiScreen;


public class WurstplusHUD extends GuiScreen {
    private final WurstplusFrame frame;
    public boolean on_gui;
    public boolean back;
    private int frame_height;

    public WurstplusHUD() {
        this.frame = new WurstplusFrame("Wurst+2 HUD", "WurstplusHUD", 40, 40);
        this.back = false;
        this.on_gui = false;
    }

    public WurstplusFrame get_frame_hud() {
        return this.frame;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void initGui() {
        this.on_gui = true;

        WurstplusFrame.nc_r = SexHack.get_setting_manager().get_setting_with_tag("GUI", "ClickGUINameFrameR").getValue(1);
        WurstplusFrame.nc_g = SexHack.get_setting_manager().get_setting_with_tag("GUI", "ClickGUINameFrameG").getValue(1);
        WurstplusFrame.nc_b = SexHack.get_setting_manager().get_setting_with_tag("GUI", "ClickGUINameFrameB").getValue(1);

        WurstplusFrame.bg_r = SexHack.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBackgroundFrameR").getValue(1);
        WurstplusFrame.bg_g = SexHack.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBackgroundFrameG").getValue(1);
        WurstplusFrame.bg_b = SexHack.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBackgroundFrameB").getValue(1);
        WurstplusFrame.bg_a = SexHack.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBackgroundFrameA").getValue(1);

        WurstplusFrame.bd_r = SexHack.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBorderFrameR").getValue(1);
        WurstplusFrame.bd_g = SexHack.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBorderFrameG").getValue(1);
        WurstplusFrame.bd_b = SexHack.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBorderFrameB").getValue(1);
        WurstplusFrame.bd_a = 0;

        WurstplusFrame.bdw_r = SexHack.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBorderWidgetR").getValue(1);
        WurstplusFrame.bdw_g = SexHack.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBorderWidgetG").getValue(1);
        WurstplusFrame.bdw_b = SexHack.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBorderWidgetB").getValue(1);
        WurstplusFrame.bdw_a = 255;

        WurstplusPinnableButton.nc_r = SexHack.get_setting_manager().get_setting_with_tag("GUI", "ClickGUINameWidgetR").getValue(1);
        WurstplusPinnableButton.nc_g = SexHack.get_setting_manager().get_setting_with_tag("GUI", "ClickGUINameWidgetG").getValue(1);
        WurstplusPinnableButton.nc_b = SexHack.get_setting_manager().get_setting_with_tag("GUI", "ClickGUINameWidgetB").getValue(1);

        WurstplusPinnableButton.bg_r = SexHack.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBackgroundWidgetR").getValue(1);
        WurstplusPinnableButton.bg_g = SexHack.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBackgroundWidgetG").getValue(1);
        WurstplusPinnableButton.bg_b = SexHack.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBackgroundWidgetB").getValue(1);
        WurstplusPinnableButton.bg_a = SexHack.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBackgroundWidgetA").getValue(1);

        WurstplusPinnableButton.bd_r = SexHack.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBorderWidgetR").getValue(1);
        WurstplusPinnableButton.bd_g = SexHack.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBorderWidgetG").getValue(1);
        WurstplusPinnableButton.bd_b = SexHack.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBorderWidgetB").getValue(1);
    }

    @Override
    public void onGuiClosed() {
        if (this.back) {
            SexHack.get_hack_manager().get_module_with_tag("GUI").set_active(true);
            SexHack.get_hack_manager().get_module_with_tag("HUD").set_active(false);
        } else {
            SexHack.get_hack_manager().get_module_with_tag("HUD").set_active(false);
            SexHack.get_hack_manager().get_module_with_tag("GUI").set_active(false);
        }

        this.on_gui = false;

        SexHack.get_config_manager().save_settings();
    }

    @Override
    protected void mouseClicked(int mx, int my, int mouse) {
        this.frame.mouse(mx, my, mouse);

        if (mouse == 0) {
            if (this.frame.motion(mx, my) && this.frame.can()) {
                this.frame.set_move(true);

                this.frame.set_move_x(mx - this.frame.get_x());
                this.frame.set_move_y(my - this.frame.get_y());
            }
        }
    }

    @Override
    protected void mouseReleased(int mx, int my, int state) {
        this.frame.release(mx, my, state);

        this.frame.set_move(false);
    }

    @Override
    public void drawScreen(int mx, int my, float tick) {
        this.drawDefaultBackground();

        this.frame.render(mx, my, 2);
    }
}