package com.zemeck.playerhud.setup;

import com.zemeck.playerhud.PlayerHud;
import com.zemeck.playerhud.capabilities.Player.Mana.CapabilityEntityMana;
import com.zemeck.playerhud.capabilities.Player.Mana.ManaEventHandler;
import com.zemeck.playerhud.commands.ModCommands;
import com.zemeck.playerhud.network.Networking;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
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
        CapabilityEntityMana.register();

        MinecraftForge.EVENT_BUS.addListener(ManaEventHandler::onAttachCapabilitiesEvent);
        MinecraftForge.EVENT_BUS.addListener(ManaEventHandler::onAttackEvent);
        MinecraftForge.EVENT_BUS.addListener(ManaEventHandler::renderGameOverlay);
        MinecraftForge.EVENT_BUS.addListener(ManaEventHandler::preventGameOverlay);
        MinecraftForge.EVENT_BUS.addListener(ManaEventHandler::midGameOverlay);
        MinecraftForge.EVENT_BUS.addListener(ManaEventHandler::onPlayerTracking);
    }

    @SubscribeEvent
    public static void serverLoad(FMLServerStartingEvent event) {
        ModCommands.register(event.getCommandDispatcher());
    }

}
