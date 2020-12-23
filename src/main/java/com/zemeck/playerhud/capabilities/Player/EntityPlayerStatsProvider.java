package com.zemeck.playerhud.capabilities.Player;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EntityPlayerStatsProvider implements ICapabilitySerializable<CompoundNBT> {

    private final PlayerStats playerStats = new PlayerStats();
    private final LazyOptional<IPlayerStats> playerStatsLazyOptional = LazyOptional.of(() -> playerStats);

    public void invalidate() {
        playerStatsLazyOptional.invalidate();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return playerStatsLazyOptional.cast();
    }

    @Override
    public CompoundNBT serializeNBT() {
        if (CapabilityEntityPlayerStats.ENTITY_PLAYER_STATS_CAPABILITY == null) {
            return new CompoundNBT();
        } else {
            return (CompoundNBT) CapabilityEntityPlayerStats.ENTITY_PLAYER_STATS_CAPABILITY.writeNBT(playerStats, null);
        }
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (CapabilityEntityPlayerStats.ENTITY_PLAYER_STATS_CAPABILITY != null) {
            CapabilityEntityPlayerStats.ENTITY_PLAYER_STATS_CAPABILITY.readNBT(playerStats, null, nbt);
        }
    }
}
