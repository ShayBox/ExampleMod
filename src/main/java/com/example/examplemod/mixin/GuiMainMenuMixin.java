package com.example.examplemod.mixin;

import net.minecraft.client.gui.GuiMainMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMainMenu.class)
public class GuiMainMenuMixin {

    @Inject(at = @At("HEAD"), method = "initGui")
    public void initGui(CallbackInfo ci) {
        System.out.println("Hello from GuiMainMenu!");
    }

}
