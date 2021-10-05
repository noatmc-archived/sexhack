package me.noat.sexhack.client.guiscreen;

import me.noat.sexhack.SexHack;
import me.noat.sexhack.client.guiscreen.render.components.WurstplusFrame;
import me.noat.sexhack.client.hacks.WurstplusCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.ArrayList;

// Hacks.
// TO DO: use witchcraft to make click gui scrollable.

public
class WurstplusGUI extends GuiScreen {
    public final int theme_frame_name_a = 0;
    public final int theme_widget_name_a = 0;
    private final Minecraft mc = Minecraft.getMinecraft();
    private final ArrayList <WurstplusFrame> frame;
    // Frame.
    public int theme_frame_name_r = 0;
    public int theme_frame_name_g = 0;
    public int theme_frame_name_b = 0;
    public int theme_frame_background_r = 0;
    public int theme_frame_background_g = 0;
    public int theme_frame_background_b = 0;
    public int theme_frame_background_a = 0;
    public int theme_frame_border_r = 0;
    public int theme_frame_border_g = 0;
    public int theme_frame_border_b = 0;
    // Module.
    public int theme_widget_name_r = 0;
    public int theme_widget_name_g = 0;
    public int theme_widget_name_b = 0;
    public int theme_widget_background_r = 0;
    public int theme_widget_background_g = 0;
    public int theme_widget_background_b = 0;
    public int theme_widget_background_a = 0;
    public int theme_widget_border_r = 0;
    public int theme_widget_border_g = 0;
    public int theme_widget_border_b = 0;
    private WurstplusFrame current;

    public
    WurstplusGUI() {
        this.frame = new ArrayList <>();
        int frame_x = 10;

        boolean event_start = true;
        boolean event_finish = false;

        for (WurstplusCategory categorys : WurstplusCategory.values()) {
            if (categorys.is_hidden()) {
                continue;
            }

            WurstplusFrame frames = new WurstplusFrame(categorys);

            frames.set_x(frame_x);

            this.frame.add(frames);

            frame_x += frames.get_width() + 5;

            this.current = frames;
        }
    }

    @Override
    public
    boolean doesGuiPauseGame() {
        return false;
    }


    @Override
    public
    void onGuiClosed() {
        SexHack.get_hack_manager().get_module_with_tag("GUI").set_active(false);

        SexHack.get_config_manager().save_settings();
    }

    @Override
    protected
    void keyTyped(char char_, int key) {
        for (WurstplusFrame frame : this.frame) {
            frame.bind(char_, key);

            if (key == SexHack.WURSTPLUS_KEY_GUI_ESCAPE && !frame.is_binding()) {
                mc.displayGuiScreen(null);
            }

            if (key == Keyboard.KEY_DOWN || key == 200) {
                frame.set_y(frame.get_y() - 1);
            }

            if (key == Keyboard.KEY_UP || key == 208) {
                frame.set_y(frame.get_y() + 1);
            }

        }
    }

    public
    void handleMouseInput() throws IOException {
        if (Mouse.getEventDWheel() > 0) {
            for (WurstplusFrame frames : this.frame) {
                frames.set_y(frames.get_y() + 10);
            }
        }
        if (Mouse.getEventDWheel() < 0) {
            for (WurstplusFrame frames : this.frame) {
                frames.set_y(frames.get_y() - 10);
            }
        }

        super.handleMouseInput();
    }

    @Override
    protected
    void mouseClicked(int mx, int my, int mouse) {
        for (WurstplusFrame frames : this.frame) {
            frames.mouse(mx, my, mouse);

            // If left click.
            if (mouse == 0) {
                if (frames.motion(mx, my) && frames.can()) {
                    frames.does_button_for_do_widgets_can(false);

                    this.current = frames;

                    this.current.set_move(true);

                    this.current.set_move_x(mx - this.current.get_x());
                    this.current.set_move_y(my - this.current.get_y());
                }
            }
        }
    }

    @Override
    protected
    void mouseReleased(int mx, int my, int state) {
        for (WurstplusFrame frames : this.frame) {
            frames.does_button_for_do_widgets_can(true);
            frames.mouse_release(mx, my, state);
            frames.set_move(false);
        }

        set_current(this.current);
    }

    @Override
    public
    void drawScreen(int mx, int my, float tick) {
        this.drawDefaultBackground();

        for (WurstplusFrame frames : this.frame) {
            frames.render(mx, my);
        }
    }

    public
    WurstplusFrame get_current() {
        return this.current;
    }

    public
    void set_current(WurstplusFrame current) {
        this.frame.remove(current);
        this.frame.add(current);
    }

    public
    ArrayList <WurstplusFrame> get_array_frames() {
        return this.frame;
    }

    public
    WurstplusFrame get_frame_with_tag(String tag) {
        WurstplusFrame frame_requested = null;

        for (WurstplusFrame frames : get_array_frames()) {
            if (frames.get_tag().equals(tag)) {
                frame_requested = frames;
            }
        }

        return frame_requested;
    }

}
