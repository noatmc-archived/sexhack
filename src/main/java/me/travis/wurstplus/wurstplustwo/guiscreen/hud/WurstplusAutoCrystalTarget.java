package me.travis.wurstplus.wurstplustwo.guiscreen.hud;

import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusPinnable;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.hacks.combat.WurstplusAutoCrystalNew;

public class WurstplusAutoCrystalTarget extends WurstplusPinnable {
  public WurstplusAutoCrystalTarget() {
		super("Crystal Target", "ACTarget", 1, 0, 0);
	}

  @Override
  public void render() {
    if (WurstplusAutoCrystalNew.detail_name != null) {
      String line = "we targettin' " + WurstplusAutoCrystalNew.detail_name;
    } else {
      String line = "we ain't targettin'";
    }

    create_line(line, this.docking(1, line), 2, nl_r, nl_g, nl_b, nl_a);
    
    this.set_width(this.get(line, "width") + 2);
    this.set_height(this.get(line, "height") + 2);
  }
}
