package me.noat.turok;

// Draw.

import me.noat.turok.draw.GL;
import me.noat.turok.task.Font;

// Task.

/**
 * @author me
 * <p>
 * Created by me.
 * 27/04/20.
 */
public
class Turok {
    private final String tag;

    private Font font_manager;

    public
    Turok(String tag) {
        this.tag = tag;
    }

    public
    void resize(int x, int y, float size) {
        GL.resize(x, y, size);
    }

    public
    void resize(int x, int y, float size, String tag) {
        GL.resize(x, y, size, "end");
    }

    public
    Font get_font_manager() {
        return this.font_manager;
    }
}