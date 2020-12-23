package com.zemeck.playerhud.capabilities.Player.Mana;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class CapabilityEntityMana {

    @CapabilityInject(IMana.class)
    public static Capability<IMana> ENTITY_MANA_CAPABILITY = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IMana.class, new Storage(), Mana::new);
    }

    public static class Storage implements Capability.IStorage<IMana> {

        @Nullable
        @Override
        public CompoundNBT writeNBT(Capability<IMana> capability, IMana instance, Direction side) {
            CompoundNBT tag = new CompoundNBT();
            tag.putFloat("mana", instance.getMana());
            tag.putFloat("maxmana", instance.getMaxMana());
            return tag;
        }

        @Override
        public void readNBT(Capability<IMana> capability, IMana instance, Direction side, INBT nbt) {
            float mana = ((CompoundNBT) nbt).getInt("mana");
            instance.setMana(mana);

            float maxMana = ((CompoundNBT) nbt).getInt("maxmana");
            instance.setMaxMana(maxMana);
        }

    }
}
