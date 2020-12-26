package com.zemeck.playerhud.setup;

import com.zemeck.playerhud.PlayerHud;
import com.zemeck.playerhud.capabilities.Player.CapabilityEntityPlayerStats;
import com.zemeck.playerhud.capabilities.Player.PlayerStatsEventHandler;
import com.zemeck.playerhud.commands.ModCommands;
import com.zemeck.playerhud.network.Networking;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

@Mod.EventBusSubscriber(modid = PlayerHud.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModSetup {

    public static final ItemGroup ITEM_GROUP = new ItemGroup("playerhud") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Registration.FIRSTBLOCK.get());
        }
    };

    public static void init(final FMLCommonSetupEvent event) {
        Networking.registerMessages();
        CapabilityEntityPlayerStats.register();

        MinecraftForge.EVENT_BUS.addListener(PlayerStatsEventHandler::serverLoginEvent);
        MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, PlayerStatsEventHandler::onAttachCapabilitiesEvent);
        MinecraftForge.EVENT_BUS.addListener(PlayerStatsEventHandler::onAttackEvent);
        MinecraftForge.EVENT_BUS.addListener(PlayerStatsEventHandler::renderGameOverlay);
        MinecraftForge.EVENT_BUS.addListener(PlayerStatsEventHandler::preventGameOverlay);
        MinecraftForge.EVENT_BUS.addListener(PlayerStatsEventHandler::midGameOverlay);
        MinecraftForge.EVENT_BUS.addListener(PlayerStatsEventHandler::playerClone);

    }

    @SubscribeEvent
    public void onCommandsRegister(RegisterCommandsEvent event)
    {
        ModCommands.register(event.getDispatcher());

    }
}
