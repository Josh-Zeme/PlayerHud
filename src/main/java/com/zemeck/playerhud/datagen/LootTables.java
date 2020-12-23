package com.zemeck.playerhud.datagen;

import com.zemeck.playerhud.setup.Registration;
import net.minecraft.data.DataGenerator;

public class LootTables extends BaseLootTableProvider {

    public LootTables(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected void addTables() {
        lootTables.put(Registration.FIRSTBLOCK.get(), createStandardTable("firstblock", Registration.FIRSTBLOCK.get()));
    }
}
