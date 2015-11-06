package net.ilexiconn.llibrary.common.item;

import net.ilexiconn.llibrary.common.nbt.NbtHelper;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ChestGenHooks;

import java.util.Iterator;
import java.util.List;

/**
 * Helper class to save ItemStacks to NBT, and removing item/block recipes.
 *
 * @author iLexiconn
 * @since 0.1.0
 */
public class ItemHelper {
    /**
     * Use {@link net.ilexiconn.llibrary.common.nbt.NbtHelper#readStackFromNbt(NBTTagCompound, String)} instead.
     *
     * @param nbtTag
     * @param name
     * @return
     * @since 0.1.0
     */
    @Deprecated
    public static ItemStack getStackFromNBT(NBTTagCompound nbtTag, String name) {
        return NbtHelper.readStackFromNbt(nbtTag, name);
    }

    /**
     * Use {@link net.ilexiconn.llibrary.common.nbt.NbtHelper#writeStackToNbt(NBTTagCompound, String, ItemStack)} instead.
     *
     * @param nbtTag
     * @param name
     * @param stack
     * @since 0.1.0
     */
    @Deprecated
    public static void saveStackToNBT(NBTTagCompound nbtTag, String name, ItemStack stack) {
        NbtHelper.writeStackToNbt(nbtTag, name, stack);
    }

    public static void removeRecipe(Block block) {
        removeRecipe(Item.getItemFromBlock(block));
    }

    public static void removeRecipe(Item item) {
        List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
        Iterator<IRecipe> iterator = recipes.iterator();

        while (iterator.hasNext()) {
            ItemStack stack = iterator.next().getRecipeOutput();
            if (stack != null && stack.getItem() == item) {
                iterator.remove();
            }
        }
    }

    /**
     * removes the item from all the world generated chests
     *
     * @param item
     * @since 0.2.1
     */
    public static void removeItemFromChests(ItemStack item) {
        ChestGenHooks.removeItem(ChestGenHooks.STRONGHOLD_CORRIDOR, item);
        ChestGenHooks.removeItem(ChestGenHooks.VILLAGE_BLACKSMITH, item);
        ChestGenHooks.removeItem(ChestGenHooks.PYRAMID_JUNGLE_DISPENSER, item);
        ChestGenHooks.removeItem(ChestGenHooks.DUNGEON_CHEST, item);
        ChestGenHooks.removeItem(ChestGenHooks.STRONGHOLD_LIBRARY, item);
        ChestGenHooks.removeItem(ChestGenHooks.BONUS_CHEST, item);
        ChestGenHooks.removeItem(ChestGenHooks.PYRAMID_DESERT_CHEST, item);
        ChestGenHooks.removeItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST, item);
        ChestGenHooks.removeItem(ChestGenHooks.STRONGHOLD_CROSSING, item);
        ChestGenHooks.removeItem(ChestGenHooks.MINESHAFT_CORRIDOR, item);
    }
}
