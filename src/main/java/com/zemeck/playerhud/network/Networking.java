package com.zemeck.playerhud.network;

import com.zemeck.playerhud.PlayerHud;
import com.zemeck.playerhud.capabilities.Player.Mana.ManaStateSyncMessage;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class Networking {

    private static SimpleChannel INSTANCE;
    private static int ID = 0;

    private static int nextID() {
        return ID++;
    }

    public static void registerMessages() {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(PlayerHud.MODID, "playerhud"),
                () -> "1.0",
                s -> true,
                s -> true);

       INSTANCE.messageBuilder(ManaStateSyncMessage.class,nextID())
                .encoder(ManaStateSyncMessage::encode)
                .decoder(ManaStateSyncMessage::new)
                .consumer(ManaStateSyncMessage::handle)
                .add();
    }

    public static void sendToClient(Object packet, ServerPlayerEntity player) {
        INSTANCE.sendTo(packet, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void sendToServer(Object packet) {
        INSTANCE.sendToServer(packet);
    }
}
