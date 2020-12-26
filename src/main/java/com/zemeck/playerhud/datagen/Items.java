package com.zemeck.playerhud.datagen;

import com.zemeck.playerhud.PlayerHud;
import com.zemeck.playerhud.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ItemModelProvider;

public class Items extends ItemModelProvider {

    public Items(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, PlayerHud.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent(Registration.FIRSTBLOCK_ITEM.get().getRegistryName().getPath(), new ResourceLocation(PlayerHud.MODID, "block/firstblock"));
    }
}
