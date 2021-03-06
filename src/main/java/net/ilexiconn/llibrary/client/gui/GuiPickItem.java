package net.ilexiconn.llibrary.client.gui;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockMobSpawner;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDoublePlant;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author FiskFile
 * @since 0.1.0
 */
@SideOnly(Side.CLIENT)
public abstract class GuiPickItem extends GuiScreen {
    public String title;
    public ArrayList<ItemStack> itemsFiltered = Lists.newArrayList();
    private GuiScreen parentScreen;
    private GuiSlotItemStackList itemList;
    private GuiTextField textField;
    private ArrayList<ItemStack> items = Lists.newArrayList();
    private int selectedIndex;
    private int listWidth;

    public GuiPickItem(String t) {
        title = t;
        parentScreen = Minecraft.getMinecraft().currentScreen;

        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution scaledresolution = new ScaledResolution(mc);
        int w = scaledresolution.getScaledWidth();
        int h = scaledresolution.getScaledHeight();
        setWorldAndResolution(mc, w, h);

        textField = new GuiTextField(0, fontRendererObj, 20, 30, 103, 12);
        textField.setMaxStringLength(40);

        for (Item item : Item.itemRegistry) {
            ItemStack itemstack = new ItemStack(item);

            if (item != null) {
                try {
                    items.add(itemstack);
                    List<ItemStack> subItems = Lists.newArrayList();
                    item.getSubItems(item, null, subItems);
                    int maxDamage = subItems.size() - 1;

                    while (item.getHasSubtypes() && itemstack.getItemDamage() < maxDamage) {
                        itemstack.setItemDamage(itemstack.getItemDamage() + 1);

                        if (!(item instanceof ItemDoublePlant) && !(Block.getBlockFromItem(item) instanceof BlockMobSpawner) && !(Block.getBlockFromItem(item) instanceof BlockDoublePlant) && !(item instanceof ItemMonsterPlacer)) {
                            items.add(new ItemStack(item, 1, itemstack.getItemDamage()));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void initGui() {
        super.initGui();

        for (ItemStack itemstack : items) {
            listWidth = Math.max(listWidth, fontRendererObj.getStringWidth(StatCollector.translateToLocal(itemstack.getDisplayName())) + 30);
        }

        listWidth = Math.min(listWidth, 300);

        if (textField != null) {
            textField.xPosition = 20 + listWidth / 2 - textField.width / 2;
            textField.yPosition = 30;
        }

        buttonList.add(new GuiButton(0, 20, height - 40, listWidth, 20, "Select"));
        itemList = new GuiSlotItemStackList(this, listWidth);
        itemList.registerScrollButtons(buttonList, 7, 8);
    }

    public abstract void onSelectEntry(ItemStack itemstack, EntityPlayer player);

    @Override
    protected void actionPerformed(GuiButton button) {
        int id = button.id;

        if (id == 0) {
            onSelectEntry(itemsFiltered.get(selectedIndex), Minecraft.getMinecraft().thePlayer);
        }
    }

    @Override
    protected void keyTyped(char c, int key) {
        Keyboard.enableRepeatEvents(true);
        textField.textboxKeyTyped(c, key);

        if (key == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(parentScreen);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
        super.mouseClicked(mouseX, mouseY, button);
        textField.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        filterItemsBySearch();
        itemList.drawScreen(mouseX, mouseY, partialTicks);

        drawCenteredString(fontRendererObj, title, 20 + listWidth / 2, 15, 16777215);
        textField.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void filterItemsBySearch() {
        itemsFiltered.clear();

        for (ItemStack itemstack : items) {
            try {
                String name = StatCollector.translateToLocal(itemstack.getDisplayName());
                Item item = itemstack.getItem();
                boolean tabEquals = false;

                if (item != null) {
                    for (CreativeTabs tab : item.getCreativeTabs()) {
                        if (tab != null) {
                            tabEquals = StatCollector.translateToLocal(tab.getTranslatedTabLabel()).toLowerCase().contains(textField.getText().toLowerCase());

                            if (tabEquals) {
                                break;
                            }
                        }
                    }
                }

                if (name.toLowerCase().contains(textField.getText().toLowerCase()) || tabEquals) {
                    itemsFiltered.add(itemstack);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void drawItemStack(int x, int y, ItemStack itemstack) {
        RenderHelper.enableGUIStandardItemLighting();
        zLevel = 100f;
        itemRender.zLevel = 100f;
        GL11.glEnable(2896);
        GL11.glEnable(32826);
        itemRender.renderItemAndEffectIntoGUI(itemstack, x, y);
        itemRender.renderItemOverlayIntoGUI(fontRendererObj, itemstack, x, y, null);
        GL11.glDisable(2896);
        GL11.glEnable(3042);
        itemRender.zLevel = 0f;
        zLevel = 0f;
        RenderHelper.disableStandardItemLighting();
    }

    public void selectItemIndex(int var1) {
        selectedIndex = var1;
    }

    public boolean itemIndexSelected(int var1) {
        return selectedIndex == var1;
    }
}