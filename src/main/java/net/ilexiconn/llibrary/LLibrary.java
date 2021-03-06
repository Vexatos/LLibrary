package net.ilexiconn.llibrary;

import net.ilexiconn.llibrary.common.ServerProxy;
import net.ilexiconn.llibrary.common.log.LoggerHelper;
import net.ilexiconn.llibrary.common.message.MessageLLibraryAnimation;
import net.ilexiconn.llibrary.common.message.MessageLLibraryAnimationAction;
import net.ilexiconn.llibrary.common.message.MessageLLibraryIntemittentAnimation;
import net.ilexiconn.llibrary.common.message.MessageLLibrarySurvivalTab;
import net.ilexiconn.llibrary.common.update.UpdateHelper;
import net.minecraft.crash.CrashReport;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = "llibrary", name = "LLibrary", version = LLibrary.VERSION, guiFactory = "net.ilexiconn.llibrary.client.gui.GuiLLibraryConfigFactory", dependencies = "required-after:Forge")
public class LLibrary {
    @Mod.Instance("llibrary")
    public static LLibrary instance;
    @SidedProxy(serverSide = "net.ilexiconn.llibrary.common.ServerProxy", clientSide = "net.ilexiconn.llibrary.client.ClientProxy")
    public static ServerProxy proxy;
    public static LoggerHelper logger = new LoggerHelper("llibrary");
    public static SimpleNetworkWrapper networkWrapper;

    public static final String VERSION = "0.6.2";

    //public static SurvivalTab tabInventory = SurvivalTab.create("container.inventory").setIcon(new ItemStack(Items.diamond_sword));

    @Mod.EventHandler
    private void preInit(FMLPreInitializationEvent event) {
        networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel("llibrary");
        networkWrapper.registerMessage(MessageLLibrarySurvivalTab.class, MessageLLibrarySurvivalTab.class, 0, Side.SERVER);
        networkWrapper.registerMessage(MessageLLibraryIntemittentAnimation.class, MessageLLibraryIntemittentAnimation.class, 1, Side.CLIENT);
        networkWrapper.registerMessage(MessageLLibraryAnimation.class, MessageLLibraryAnimation.class, 2, Side.CLIENT);
        networkWrapper.registerMessage(MessageLLibraryAnimationAction.class, MessageLLibraryAnimationAction.class, 3, Side.CLIENT);

        proxy.preInit(event.getSuggestedConfigurationFile());
    }

    @Mod.EventHandler
    private void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
    }

    @Mod.EventHandler
    public void messageReceived(FMLInterModComms.IMCEvent event) {
        for (FMLInterModComms.IMCMessage message : event.getMessages()) {
            if (message.key.equalsIgnoreCase("update-checker") && message.isStringMessage()) {
                try {
                    ModContainer modContainer = null;
                    for (ModContainer mod : Loader.instance().getModList()) {
                        if (mod.getModId().equals(message.getSender())) {
                            modContainer = mod;
                        }
                    }
                    if (modContainer == null) {
                        throw new Exception();
                    }
                    UpdateHelper.registerUpdateChecker(modContainer, message.getStringValue());
                } catch (Exception e) {
                    LLibrary.logger.info(CrashReport.makeCrashReport(e, "Failed to register update checker for mod " + message.getSender()).getCompleteReport());
                }
            }
        }
    }
}
