package net.ilexiconn.llibrary.client.gui;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

/**
 * @author FiskFile
 * @see net.ilexiconn.llibrary.client.gui.GuiHelper
 * @since 0.1.0
 */
@SideOnly(Side.CLIENT)
public class GuiOverride extends GuiScreen {
    public Minecraft mc = Minecraft.getMinecraft();
    public Random rand = new Random();
    public GuiScreen overriddenScreen;

    public List<GuiButton> buttonList = Lists.newArrayList();

    @Override
    public void drawScreen(int mouseX, int mouseY, float partalTicks) {
        super.drawScreen(mouseX, mouseY, partalTicks);
        List<GuiLabel> labelList = ObfuscationReflectionHelper.getPrivateValue(GuiScreen.class, overriddenScreen, "labelList", "field_146293_o");
        int k1;

        for (k1 = 0; k1 < buttonList.size(); ++k1) {
            buttonList.get(k1).drawButton(mc, mouseX, mouseY);
        }

        for (k1 = 0; k1 < labelList.size(); ++k1) {
            labelList.get(k1).drawLabel(mc, mouseX, mouseY);
        }
    }

    @Override
    public void setWorldAndResolution(Minecraft mc, int width, int height) {
        this.mc = mc;
        this.fontRendererObj = mc.fontRendererObj;
        this.width = width;
        this.height = height;
        if (!MinecraftForge.EVENT_BUS.post(new InitGuiEvent.Pre(this, this.buttonList))) {
            this.buttonList.clear();
            this.initGui();
        }
        MinecraftForge.EVENT_BUS.post(new InitGuiEvent.Post(this, this.buttonList));
    }

    @Override
    public void actionPerformed(GuiButton button) {

    }
}