package com.zemeck.playerhud.capabilities.Player;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class CapabilityEntityPlayerStats {

    @CapabilityInject(IPlayerStats.class)
    public static Capability<IPlayerStats> ENTITY_PLAYER_STATS_CAPABILITY = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IPlayerStats.class, new Storage(), PlayerStats::new);
    }

    public static class Storage implements Capability.IStorage<IPlayerStats> {

        @Nullable
        @Override
        public CompoundNBT writeNBT(Capability<IPlayerStats> capability, IPlayerStats instance, Direction side) {
            CompoundNBT tag = new CompoundNBT();
            tag.putFloat("mana", instance.getMana());
            tag.putFloat("maxMana", instance.getMaxMana());
            tag.putInt("power", instance.getPower());
            tag.putInt("defence", instance.getDefence());
            tag.putInt("stamina", instance.getStamina());
            tag.putString("profession", instance.getProfession());
            return tag;
        }

        @Override
        public void readNBT(Capability<IPlayerStats> capability, IPlayerStats instance, Direction side, INBT nbt) {
            instance.setMana(((CompoundNBT) nbt).getFloat("mana"));
            instance.setMaxMana(((CompoundNBT) nbt).getFloat("maxMana"));
            instance.setPower(((CompoundNBT) nbt).getInt("power"));
            instance.setDefence(((CompoundNBT) nbt).getInt("defence"));
            instance.setStamina(((CompoundNBT) nbt).getInt("stamina"));
            instance.setProfession(((CompoundNBT) nbt).getString("profession"));
        }
    }
}
