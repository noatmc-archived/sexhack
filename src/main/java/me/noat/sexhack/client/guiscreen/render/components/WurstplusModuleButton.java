package me.noat.sexhack.client.guiscreen.render.components;

import me.noat.sexhack.SexHack;
import me.noat.sexhack.client.guiscreen.render.WurstplusDraw;
import me.noat.sexhack.client.guiscreen.render.components.widgets.*;
import me.noat.sexhack.client.guiscreen.settings.Setting;
import me.noat.sexhack.client.hacks.Module;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.util.ArrayList;

// Hacks.


public class WurstplusModuleButton {
    public int settings_height;
    private final Module module;
    private final WurstplusFrame master;
    private final ArrayList<WurstplusAbstractWidget> widget;
    private final String module_name;
    private boolean opened;
    private int x;
    private int y;
    private int width;
    private int height;
    private int opened_height;
    private int save_y;
    private final WurstplusDraw font = new WurstplusDraw(1);
    private final int border_a = 200;
    private final int border_size = 1;
    private final int master_height_cache;
    private int count;

    public WurstplusModuleButton(Module module, WurstplusFrame master) {
        /**
         * A value to save the y. When move the frame the save_y does the work.
         * @param save_y;
         **/

        this.module = module;
        this.master = master;

        this.widget = new ArrayList();

        this.module_name = module.get_name();

        this.x = 0;
        this.y = 0;

        this.width = font.get_string_width(module.get_name()) + 5;
        this.height = font.get_string_height();

        this.opened_height = this.height;

        this.save_y = 0;

        this.opened = false;

        this.master_height_cache = master.get_height();

        this.settings_height = this.y + 10;

        this.count = 0;

        for (Setting settings : SexHack.get_setting_manager().get_settings_with_hack(module)) {
            if (settings.get_type().equals("button")) {
                this.widget.add(new WurstplusButton(master, this, settings.get_tag(), this.settings_height));

                this.settings_height += 13;

                this.count++;
            }

            if (settings.get_type().equals("combobox")) {
                this.widget.add(new WurstplusCombobox(master, this, settings.get_tag(), this.settings_height));

                this.settings_height += 13;

                this.count++;
            }

            if (settings.get_type().equals("label")) {
                this.widget.add(new WurstplusLabel(master, this, settings.get_tag(), this.settings_height));

                this.settings_height += 13;

                this.count++;
            }

            if (settings.get_type().equals("doubleslider") || settings.get_type().equals("integerslider")) {
                this.widget.add(new WurstplusSlider(master, this, settings.get_tag(), this.settings_height));

                this.settings_height += 13;

                this.count++;
            }
        }

        int size = SexHack.get_setting_manager().get_settings_with_hack(module).size();

        if (this.count >= size) {
            this.widget.add(new WurstplusButtonBind(master, this, "bind", this.settings_height));

            this.settings_height += 13;
        }
    }

    public Module get_module() {
        return this.module;
    }

    public WurstplusFrame get_master() {
        return this.master;
    }

    public void set_pressed(boolean value) {
        this.module.set_active(value);
    }

    public boolean get_state() {
        return this.module.is_active();
    }

    public int get_settings_height() {
        return this.settings_height + 9;
    }

    public int get_cache_height() {
        return this.master_height_cache;
    }

    public int get_width() {
        return this.width;
    }

    public void set_width(int width) {
        this.width = width;
    }

    public int get_height() {
        return this.height;
    }

    public void set_height(int height) {
        this.height = height;
    }

    public int get_x() {
        return this.x;
    }

    public void set_x(int x) {
        this.x = x;
    }

    public int get_y() {
        return this.y;
    }

    public void set_y(int y) {
        this.y = y;
    }

    public int get_save_y() {
        return this.save_y + 6;
    }

    public boolean is_open() {
        return this.opened;
    }

    public void set_open(boolean value) {
        this.opened = value;
    }

    public boolean is_binding() {
        boolean value_requested = false;

        for (WurstplusAbstractWidget widgets : this.widget) {
            if (widgets.is_binding()) {
                value_requested = true;
            }
        }

        return value_requested;
    }

    public boolean motion(int mx, int my) {
        return mx >= get_x() && my >= get_save_y() && mx <= get_x() + get_width() && my <= get_save_y() + get_height();
    }

    public void does_widgets_can(boolean can) {
        for (WurstplusAbstractWidget widgets : this.widget) {
            widgets.does_can(can);
        }
    }

    public void bind(char char_, int key) {
        for (WurstplusAbstractWidget widgets : this.widget) {
            widgets.bind(char_, key);
        }
    }

    public void mouse(int mx, int my, int mouse) {
        for (WurstplusAbstractWidget widgets : this.widget) {
            widgets.mouse(mx, my, mouse);
        }

        if (mouse == 0) {
            if (motion(mx, my)) {
                this.master.does_can(false);

                set_pressed(!get_state());
            }
        }

        if (mouse == 1) {
            if (motion(mx, my)) {
                this.master.does_can(false);

                set_open(!is_open());

                this.master.refresh_frame(this, 0);
            }
        }
    }

    public void button_release(int mx, int my, int mouse) {
        for (WurstplusAbstractWidget widgets : this.widget) {
            widgets.release(mx, my, mouse);
        }

        this.master.does_can(true);
    }

    public void render(int mx, int my, int separe) {
        set_width(this.master.get_width() - separe);

        this.save_y = this.y + this.master.get_y() - 10;

        int nm_r = SexHack.click_gui.theme_widget_name_r;
        int nm_g = SexHack.click_gui.theme_widget_name_g;
        int nm_b = SexHack.click_gui.theme_widget_name_b;
        int nm_a = SexHack.click_gui.theme_widget_name_a;

        int bg_r = SexHack.click_gui.theme_widget_background_r;
        int bg_g = SexHack.click_gui.theme_widget_background_g;
        int bg_b = SexHack.click_gui.theme_widget_background_b;
        int bg_a = SexHack.click_gui.theme_widget_background_a;

        int bd_r = SexHack.click_gui.theme_widget_border_r;
        int bd_g = SexHack.click_gui.theme_widget_border_g;
        int bd_b = SexHack.click_gui.theme_widget_border_b;

        if (this.module.is_active()) {
            WurstplusDraw.draw_rect(this.x - 2, this.save_y - 3, this.x + this.width, this.save_y + this.height + 4, bg_r, bg_g, bg_b, bg_a);

            WurstplusDraw.draw_string(this.module_name, this.x + separe, this.save_y, 255, 255, 255, 255);
        } else {
            WurstplusDraw.draw_string(this.module_name, this.x + separe, this.save_y, 177, 177, 177, 255);
        }

        for (WurstplusAbstractWidget widgets : this.widget) {
            widgets.set_x(get_x());

            boolean is_passing_in_widget = this.opened && widgets.motion_pass(mx, my);

            if (motion(mx, my) || is_passing_in_widget) {
                WurstplusDraw.draw_rect(this.master.get_x() - 1, this.save_y, this.master.get_width() + 1, this.opened_height, bd_r, bd_g, bd_b, border_a, this.border_size, "right-left");
            }

            if (!opened) {
                Minecraft.getMinecraft().fontRenderer.drawString("+", this.get_x() + this.get_width() - 10, this.save_y, new Color(255, 255, 255, 255).hashCode());
            }

            if (this.opened) {
                Minecraft.getMinecraft().fontRenderer.drawString("-", this.get_x() + this.get_width() - 10, this.save_y, new Color(255, 255, 255, 255).hashCode());
                this.opened_height = this.height + this.settings_height - 10;

                widgets.render(get_save_y(), separe, mx, my);
            } else {
                this.opened_height = this.height;
            }
        }
    }
}