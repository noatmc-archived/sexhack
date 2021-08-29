package me.noat.sexhack.client.guiscreen.hud;


import me.noat.sexhack.SexHack;
import me.noat.sexhack.client.guiscreen.render.WurstplusDraw;
import me.noat.sexhack.client.guiscreen.render.pinnables.WurstplusPinnable;
import me.noat.sexhack.client.util.WurstplusMathUtil;

public class WurstplusCompass extends WurstplusPinnable {
    
    public WurstplusCompass() {
		super("Compass", "Compass", 1, 0, 0);
    }
    
    public WurstplusDraw font = new WurstplusDraw(1);

    private static final double half_pi = Math.PI / 2;

    private enum Direction {
        N,
        W,
        S,
        E
    }

    @Override
	public void render() {
        
        int r = SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
        int g = SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
        int b = SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
        int a = SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);

        for (Direction dir : Direction.values()) {

            double rad = get_pos_on_compass(dir);
            if (dir.name().equals("N")) {
                create_line(dir.name(), (int) (this.docking(1, dir.name()) + get_x(rad)), (int) get_y(rad), r, g, b, a);
            } else {
                create_line(dir.name(), (int) (this.docking(1, dir.name()) + get_x(rad)), (int) get_y(rad), 225, 225, 225, 225);
            }
            
        }

        this.set_width(50);
		this.set_height(50);

    }

    private double get_pos_on_compass(Direction dir) {

        double yaw = Math.toRadians(WurstplusMathUtil.wrap(mc.getRenderViewEntity().rotationYaw));
        int index = dir.ordinal();
        return yaw + (index * half_pi);

    }

    private double get_x(double rad) {
        return Math.sin(rad) * (SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDCompassScale").get_value(1));
    }

    private double get_y(double rad) {

        final double epic_pitch = WurstplusMathUtil.clamp2(mc.getRenderViewEntity().rotationPitch + 30f, -90f, 90f);
        final double pitch_radians = Math.toRadians(epic_pitch);
        return Math.cos(rad) * Math.sin(pitch_radians) * (SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDCompassScale").get_value(1));

    }

}