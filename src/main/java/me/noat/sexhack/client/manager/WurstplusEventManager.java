package me.noat.sexhack.client.manager;

import me.noat.sexhack.SexHack;
import me.noat.sexhack.client.command.WurstplusCommand;
import me.noat.sexhack.client.command.WurstplusCommands;
import me.noat.sexhack.client.event.WurstplusEventBus;
import me.noat.sexhack.client.event.events.WurstplusEventGameOverlay;
import me.noat.sexhack.client.util.WurstplusMessageUtil;
import me.noat.turok.draw.RenderHelp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

// External.
// Travis.


public
class WurstplusEventManager {

    private final Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public
    void onUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.isCanceled()) {
        }
    }

    @SubscribeEvent (priority = EventPriority.HIGHEST)
    public
    void onTick(TickEvent.ClientTickEvent event) {
        if (mc.player == null) {
            return;
        }

        SexHack.get_hack_manager().update();
    }

    @SubscribeEvent
    public
    void onWorldRender(RenderWorldLastEvent event) {
        if (event.isCanceled()) {
            return;
        }

        SexHack.get_hack_manager().render(event);
    }

    @SubscribeEvent
    public
    void onRender(RenderGameOverlayEvent.Post event) {

        if (event.isCanceled()) {
            return;
        }

        WurstplusEventBus.EVENT_BUS.post(new WurstplusEventGameOverlay(event.getPartialTicks(), new ScaledResolution(mc)));

        RenderGameOverlayEvent.ElementType target = RenderGameOverlayEvent.ElementType.EXPERIENCE;

        if (!mc.player.isCreative() && mc.player.getRidingEntity() instanceof AbstractHorse) {
            target = RenderGameOverlayEvent.ElementType.HEALTHMOUNT;
        }

        if (event.getType() == target) {
            SexHack.get_hack_manager().render();

            if (!SexHack.get_hack_manager().get_module_with_tag("GUI").is_active()) {
                SexHack.get_hud_manager().render();
            }

            GL11.glPushMatrix();

            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_BLEND);

            GlStateManager.enableBlend();

            GL11.glPopMatrix();

            RenderHelp.release_gl();
        }
    }

    @SubscribeEvent (priority = EventPriority.NORMAL, receiveCanceled = true)
    public
    void onKeyInput(InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState()) {
            SexHack.get_hack_manager().bind(Keyboard.getEventKey());
        }
    }

    @SubscribeEvent (priority = EventPriority.NORMAL)
    public
    void onChat(ClientChatEvent event) {
        String message = event.getMessage();
        String[] message_args = me.noat.sexhack.client.manager.WurstplusCommandManager.command_list.get_message(event.getMessage());

        boolean true_command = false;

        if (message_args.length > 0) {

            event.setCanceled(true);

            mc.ingameGUI.getChatGUI().addToSentMessages(event.getMessage());

            for (WurstplusCommand command : WurstplusCommands.get_pure_command_list()) {
                try {
                    if (me.noat.sexhack.client.manager.WurstplusCommandManager.command_list.get_message(event.getMessage())[0].equalsIgnoreCase(command.get_name())) {
                        true_command = command.get_message(me.noat.sexhack.client.manager.WurstplusCommandManager.command_list.get_message(event.getMessage()));
                    }
                } catch (Exception ignored) {
                }
            }

            if (!true_command && me.noat.sexhack.client.manager.WurstplusCommandManager.command_list.has_prefix(event.getMessage())) {
                WurstplusMessageUtil.send_client_message("Try using " + me.noat.sexhack.client.manager.WurstplusCommandManager.get_prefix() + "help list to see all commands");

            }
        }
    }

    @SubscribeEvent
    public
    void onInputUpdate(InputUpdateEvent event) {
        WurstplusEventBus.EVENT_BUS.post(event);
    }


}