package me.noat.sexhack.client.hacks;

import me.noat.sexhack.SexHack;
import me.noat.sexhack.client.guiscreen.settings.Setting;

public
class WurstplusClickGUI extends Module {

    final Setting name_frame_r = create("Name R", "ClickGUINameFrameR", 255, 0, 255);
    final Setting name_frame_g = create("Name G", "ClickGUINameFrameG", 255, 0, 255);
    final Setting name_frame_b = create("Name B", "ClickGUINameFrameB", 255, 0, 255);
    final Setting background_frame_r = create("Background R", "ClickGUIBackgroundFrameR", 0, 0, 255);
    final Setting background_frame_g = create("Background G", "ClickGUIBackgroundFrameG", 0, 0, 255);
    final Setting background_frame_b = create("Background B", "ClickGUIBackgroundFrameB", 0, 0, 255);
    final Setting background_frame_a = create("Background A", "ClickGUIBackgroundFrameA", 151, 0, 255);
    final Setting border_frame_r = create("Border R", "ClickGUIBorderFrameR", 255, 0, 255);
    final Setting border_frame_g = create("Border G", "ClickGUIBorderFrameG", 255, 0, 255);
    final Setting border_frame_b = create("Border B", "ClickGUIBorderFrameB", 255, 0, 255);
    final Setting name_widget_r = create("Name R", "ClickGUINameWidgetR", 255, 0, 255);
    final Setting name_widget_g = create("Name G", "ClickGUINameWidgetG", 255, 0, 255);
    final Setting name_widget_b = create("Name B", "ClickGUINameWidgetB", 255, 0, 255);
    final Setting background_widget_r = create("Background R", "ClickGUIBackgroundWidgetR", 255, 0, 255);
    final Setting background_widget_g = create("Background G", "ClickGUIBackgroundWidgetG", 255, 0, 255);
    final Setting background_widget_b = create("Background B", "ClickGUIBackgroundWidgetB", 255, 0, 255);
    final Setting background_widget_a = create("Background A", "ClickGUIBackgroundWidgetA", 100, 0, 255);
    final Setting border_widget_r = create("Border R", "ClickGUIBorderWidgetR", 255, 0, 255);
    final Setting border_widget_g = create("Border G", "ClickGUIBorderWidgetG", 255, 0, 255);
    final Setting border_widget_b = create("Border B", "ClickGUIBorderWidgetB", 255, 0, 255);
    Setting label_frame = create("info", "ClickGUIInfoFrame", "Frames");
    Setting label_widget = create("info", "ClickGUIInfoWidget", "Widgets");

    public
    WurstplusClickGUI() {
        super(WurstplusCategory.WURSTPLUS_GUI);

        this.name = "GUI";
        this.tag = "GUI";
        this.description = "The main gui";

        set_bind(SexHack.WURSTPLUS_KEY_GUI);
    }

    @Override
    public
    void update() {
        // Update frame colors.
        SexHack.click_gui.theme_frame_name_r = name_frame_r.getValue(1);
        SexHack.click_gui.theme_frame_name_g = name_frame_g.getValue(1);
        SexHack.click_gui.theme_frame_name_b = name_frame_b.getValue(1);

        SexHack.click_gui.theme_frame_background_r = background_frame_r.getValue(1);
        SexHack.click_gui.theme_frame_background_g = background_frame_g.getValue(1);
        SexHack.click_gui.theme_frame_background_b = background_frame_b.getValue(1);
        SexHack.click_gui.theme_frame_background_a = background_frame_a.getValue(1);

        SexHack.click_gui.theme_frame_border_r = border_frame_r.getValue(1);
        SexHack.click_gui.theme_frame_border_g = border_frame_g.getValue(1);
        SexHack.click_gui.theme_frame_border_b = border_frame_b.getValue(1);

        // Update widget colors.
        SexHack.click_gui.theme_widget_name_r = name_widget_r.getValue(1);
        SexHack.click_gui.theme_widget_name_g = name_widget_g.getValue(1);
        SexHack.click_gui.theme_widget_name_b = name_widget_b.getValue(1);

        SexHack.click_gui.theme_widget_background_r = background_widget_r.getValue(1);
        SexHack.click_gui.theme_widget_background_g = background_widget_g.getValue(1);
        SexHack.click_gui.theme_widget_background_b = background_widget_b.getValue(1);
        SexHack.click_gui.theme_widget_background_a = background_widget_a.getValue(1);

        SexHack.click_gui.theme_widget_border_r = border_widget_r.getValue(1);
        SexHack.click_gui.theme_widget_border_g = border_widget_g.getValue(1);
        SexHack.click_gui.theme_widget_border_b = border_widget_b.getValue(1);
    }

    @Override
    public
    void enable() {
        if (mc.world != null && mc.player != null) {
            mc.displayGuiScreen(SexHack.click_gui);
        }
    }

    @Override
    public
    void disable() {
        if (mc.world != null && mc.player != null) {
            mc.displayGuiScreen(null);
        }
    }
}