package com.zemeck.playerhud.capabilities.Player;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

import net.minecraft.nbt.CompoundNBT;

public class PlayerStatsStateSyncMessage {
    private IPlayerStats playerStats;

    public PlayerStatsStateSyncMessage(PacketBuffer buf) {
        CompoundNBT tag = buf.readCompoundTag();
        this.playerStats = new PlayerStats();
        this.playerStats.setMana(tag.getFloat("mana"));
        this.playerStats.setMaxMana(tag.getFloat("maxMana"));
        this.playerStats.setPower(tag.getInt("power"));
        this.playerStats.setDefence(tag.getInt("defence"));
        this.playerStats.setStamina(tag.getInt("stamina"));
        this.playerStats.setProfession(tag.getString("profession"));
    }

    public PlayerStatsStateSyncMessage(IPlayerStats playerStats) {
        this.playerStats = playerStats;
    }

    public void encode(PacketBuffer buf) {
        CompoundNBT tag = new CompoundNBT();
        tag.putFloat("mana", playerStats.getMana());
        tag.putFloat("maxMana", playerStats.getMaxMana());
        tag.putInt("power", playerStats.getPower());
        tag.putInt("defence", playerStats.getDefence());
        tag.putInt("stamina", playerStats.getStamina());
        tag.putString("profession", playerStats.getProfession());
        buf.writeCompoundTag(tag);
    }

    private CompoundNBT toTag(){
        CompoundNBT tag = new CompoundNBT();
        tag.putFloat("mana", playerStats.getMana());
        tag.putFloat("maxMana", playerStats.getMaxMana());
        tag.putInt("power", playerStats.getPower());
        tag.putInt("defence", playerStats.getDefence());
        tag.putInt("stamina", playerStats.getStamina());
        tag.putString("profession", playerStats.getProfession());
        return tag;
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context ctx = context.get();
        ctx.enqueueWork(() -> {
            if (ctx.getDirection().getReceptionSide().isClient() && ctx.getDirection().getOriginationSide().isServer()) {
                ClientPlayerEntity player = Minecraft.getInstance().player;
                player.getCapability(CapabilityEntityPlayerStats.ENTITY_PLAYER_STATS_CAPABILITY, null)
                        .ifPresent(state -> {
                            Capability.IStorage<IPlayerStats> storage = CapabilityEntityPlayerStats.ENTITY_PLAYER_STATS_CAPABILITY.getStorage();
                            storage.readNBT(CapabilityEntityPlayerStats.ENTITY_PLAYER_STATS_CAPABILITY, state, null, toTag());

                        });
            }
        });
        ctx.setPacketHandled(true);
    }
}