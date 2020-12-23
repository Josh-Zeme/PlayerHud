package com.zemeck.playerhud;

import com.zemeck.playerhud.setup.ClientSetup;
import com.zemeck.playerhud.setup.Config;
import com.zemeck.playerhud.setup.ModSetup;
import com.zemeck.playerhud.setup.Registration;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(PlayerHud.MODID)
public class PlayerHud {

    public static final String MODID = "playerhud";

    private static final Logger LOGGER = LogManager.getLogger();

    public PlayerHud() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_CONFIG);

        Registration.init();

        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ModSetup::init);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::init);
    }
}
