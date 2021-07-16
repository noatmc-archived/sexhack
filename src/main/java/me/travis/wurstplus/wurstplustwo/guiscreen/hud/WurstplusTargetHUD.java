package me.travis.wurstplus.wurstplustwo.guiscreen.hud;

import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusPinnable;
import me.travis.wurstplus.wurstplustwo.util.WurstplusTextureHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import javax.imageio.ImageIO;
import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;
import me.travis.wurstplus.wurstplustwo.util.*;
import me.travis.wurstplus.wurstplustwo.hacks.combat.*;

public class WurstplusTargetHUD extends WurstplusPinnable {
  public WurstplusTargetHUD() {
      super("Target HUD", "TargetHud", 1, 0, 0);
  }
  Image image = null;

  public static void getImage() {
      try {
        URL url = new URL("https://crafatar.com/avatars/" + WurstplusAutoCrystalNew.autoez_target.getUUID() + "?overlay=true");
        image = ImageIO.read(url);
      } catch (IOException e) {
        e.printStackTrace();
      }
  }

  @Override
  public void render() {
    this.getImage();
    if (image != null) {
      this.create_rect(0, 0, this.get_width(), this.get_height(), 0, 0, 0, 50);
    }
    this.set_width(370);
    this.set_height(140);
  }
}
