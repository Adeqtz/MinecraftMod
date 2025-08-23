package net.drey.tutorialmod.datagen;

import net.drey.tutorialmod.item.ModItems;
import net.drey.tutorialmod.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            getOrCreateTagBuilder(ModTags.Items.TRANSFORMABLE_ITEMS)
                    .add(ModItems.PINK_GARNET)
                    .add(ModItems.RAW_PINK_GARNET)
                    .add(Items.COAL)
                    .add(Items.APPLE)
                    .add(Items.STICK);
    }
}
