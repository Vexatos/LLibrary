package net.ilexiconn.llibrary.client;

import java.io.File;

import net.ilexiconn.llibrary.client.gui.GuiHelper;
import net.ilexiconn.llibrary.client.gui.GuiLLibraryMainMenu;
import net.ilexiconn.llibrary.client.render.entity.RenderLLibraryPlayer;
import net.ilexiconn.llibrary.common.ServerProxy;
import net.ilexiconn.llibrary.common.config.LLibraryConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Timer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends ServerProxy {
    public static RenderLLibraryPlayer renderCustomPlayer;
    public Timer timer;

    @Override
    public void preInit(File config) {
        super.preInit(config);

        //LLibrary.tabInventory.setContainer(GuiInventory.class);

        timer = ReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), "field_71428_T", "S", "timer");

        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
        GuiHelper.addOverride(GuiMainMenu.class, new GuiLLibraryMainMenu());

        if (LLibraryConfigHandler.threadedScreenshots) {
            ClientEventHandler.screenshotKeyBinding = new KeyBinding(Minecraft.getMinecraft().gameSettings.keyBindScreenshot.getKeyDescription(), Minecraft.getMinecraft().gameSettings.keyBindScreenshot.getKeyCode(), Minecraft.getMinecraft().gameSettings.keyBindScreenshot.getKeyCategory());
            for (int i = 0; i < Minecraft.getMinecraft().gameSettings.keyBindings.length; ++i) {
                if (Minecraft.getMinecraft().gameSettings.keyBindings[i] == Minecraft.getMinecraft().gameSettings.keyBindScreenshot) {
                    Minecraft.getMinecraft().gameSettings.keyBindings[i] = ClientEventHandler.screenshotKeyBinding;
                    Minecraft.getMinecraft().gameSettings.keyBindScreenshot.setKeyCode(-1);
                    break;
                }
            }
        }
    }

    @Override
    public void postInit() {
        super.postInit();

        renderCustomPlayer = new RenderLLibraryPlayer();
        Minecraft.getMinecraft().getRenderManager().entityRenderMap.put(EntityPlayer.class, renderCustomPlayer);
    }

    @Override
    public EntityPlayer getClientPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }

    @Override
    public float getPartialTicks() {
        return timer.renderPartialTicks;
    }
}
