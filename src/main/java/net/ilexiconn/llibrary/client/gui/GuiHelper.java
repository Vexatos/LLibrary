package net.ilexiconn.llibrary.client.gui;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.llibrary.client.toast.Toast;
import net.minecraft.client.gui.GuiScreen;

import java.util.List;
import java.util.Map;

/**
 * Helper class for GUIs.
 *
 * @author FiskFille
 * @author iLexiconn
 * @since 0.1.0
 */
@SideOnly(Side.CLIENT)
public class GuiHelper {
    private static Map<GuiOverride, Class<? extends GuiScreen>> overrideMap = Maps.newHashMap();

    /* x */

    /**
     * @param x    The x position.
     * @param y    The y position.
     * @param text The text to display. Every string is rendered on a new line.
     * @deprecated Use {@link Toast#makeText(String...)} instead.
     */
    @Deprecated
    public static void createToast(int x, int y, String... text) {
        Toast.makeText(text).setPosition(x, y).show();
    }

    /**
     * A method for adding {@link net.ilexiconn.llibrary.client.gui.GuiOverride} to an existing {@link net.minecraft.client.gui.GuiScreen} or {@link net.minecraft.client.gui.inventory.GuiContainer} {@link net.ilexiconn.llibrary.client.gui.GuiOverride} classes may get added twice.
     *
     * @see #getOverridesForGui(java.lang.Class)
     * @see net.ilexiconn.llibrary.client.gui.GuiOverride
     * @since 0.1.0
     */
    public static void addOverride(Class<? extends GuiScreen> clazz, GuiOverride gui) {
        overrideMap.put(gui, clazz);
    }

    /**
     * Get a list of all the overrides of a specific GUI class.
     *
     * @return the list with {@link net.ilexiconn.llibrary.client.gui.GuiOverride} instances
     * @see #addOverride(java.lang.Class, net.ilexiconn.llibrary.client.gui.GuiOverride)
     * @see net.ilexiconn.llibrary.client.gui.GuiOverride
     * @since 0.1.0
     */
    public static List<GuiOverride> getOverridesForGui(Class<? extends GuiScreen> clazz) {
        List<GuiOverride> list = Lists.newArrayList();

        for (Map.Entry<GuiOverride, Class<? extends GuiScreen>> e : overrideMap.entrySet()) {
            if (e.getValue() == clazz) {
                list.add(e.getKey());
            }
        }

        return list;
    }

    /**
     * Get a list of all the overrides of all the GUI class.
     *
     * @return the list with {@link net.ilexiconn.llibrary.client.gui.GuiOverride} instances
     * @see #addOverride(java.lang.Class, net.ilexiconn.llibrary.client.gui.GuiOverride)
     * @see net.ilexiconn.llibrary.client.gui.GuiOverride
     * @since 0.1.0
     */
    public static Map<GuiOverride, Class<? extends GuiScreen>> getOverrides() {
        return overrideMap;
    }
}