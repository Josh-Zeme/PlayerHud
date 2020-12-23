package com.zemeck.playerhud.capabilities.Player.Mana;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EntityManaProvider implements ICapabilitySerializable<CompoundNBT> {

    private final Mana mana = new Mana();
    private final LazyOptional<IMana> manaOptional = LazyOptional.of(() -> mana);

    public void invalidate() {
        manaOptional.invalidate();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return manaOptional.cast();
    }

    @Override
    public CompoundNBT serializeNBT() {
        if (CapabilityEntityMana.ENTITY_MANA_CAPABILITY == null) {
            return new CompoundNBT();
        } else {
            return (CompoundNBT) CapabilityEntityMana.ENTITY_MANA_CAPABILITY.writeNBT(mana, null);
        }
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (CapabilityEntityMana.ENTITY_MANA_CAPABILITY != null) {
            CapabilityEntityMana.ENTITY_MANA_CAPABILITY.readNBT(mana, null, nbt);
        }
    }
}
