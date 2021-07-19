package me.travis.wurstplus.wurstplustwo.guiscreen.hud;

import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusPinnable;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.hacks.combat.WurstplusAutoCrystalNew;

public class WurstplusAutoCrystalTarget extends WurstplusPinnable {

  private String coolMessage = WurstplusAutoCrystalNew.detail_name;
  public WurstplusAutoCrystalTarget() {
		super("Crystal Target", "ACTarget", 1, 0, 0);
	}

  @Override
  public void render() {
    if (coolMessage != null) {
      String line = "we targettin' " + coolMessage;
    } else {
      String line = "we ain't targettin'";
    }

    create_line(line, this.docking(1, line), 2, 255, 255, 255, 255);

    this.set_width(this.get(line, "width") + 2);
    this.set_height(this.get(line, "height") + 2);
  }
}
