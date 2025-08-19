package net.drey.tutorialmod.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

// Example: Mixin into Minecraft class (just a template)
@Mixin(net.minecraft.client.MinecraftClient.class)
public class ExampleMixin {
    @Shadow private static net.minecraft.client.MinecraftClient instance;

    // Add your mixin logic later
}