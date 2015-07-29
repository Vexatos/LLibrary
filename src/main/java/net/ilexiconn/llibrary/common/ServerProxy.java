package net.ilexiconn.llibrary.common;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInterModComms;
import net.ilexiconn.llibrary.common.config.ConfigHelper;
import net.ilexiconn.llibrary.common.config.LLibraryConfigHandler;
import net.ilexiconn.llibrary.common.entity.EntityHelper;
import net.ilexiconn.llibrary.common.entity.EntityMountableBlock;
import net.ilexiconn.llibrary.common.json.container.JsonModUpdate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;

public class ServerProxy
{
    public void preInit(File config)
    {
        MinecraftForge.EVENT_BUS.register(new ServerEventHandler());
        FMLCommonHandler.instance().bus().register(new ServerEventHandler());
        EntityHelper.registerEntity("mountableBlock", EntityMountableBlock.class);
        ConfigHelper.registerConfigHandler("llibrary", config, new LLibraryConfigHandler());

        FMLInterModComms.sendMessage("llibrary", "update-checker", "https://github.com/iLexiconn/LLibrary/raw/1.7.10/versions.json");
    }

    public void postInit()
    {

    }

    public void openChangelogGui(JsonModUpdate mod, String version)
    {

    }

    public EntityPlayer getClientPlayer()
    {
        return null;
    }
}
