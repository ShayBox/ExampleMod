# Forge 1.12 + FG3 + Mixins 0.8.2

#### build.gradle
```groovy
buildscript {
    repositories {
        maven { url = 'https://repo.spongepowered.org/repository/maven-public/' }
    }
    dependencies {
        classpath 'org.spongepowered:mixingradle:0.7-SNAPSHOT'
    }
}

apply plugin: 'org.spongepowered.mixin'

minecraft {
    runs {
        client {
            jvmArg "-Dfml.coreMods.load=com.example.examplemod.LoadingPlugin"
        }

        server {
            jvmArg "-Dfml.coreMods.load=com.example.examplemod.LoadingPlugin"
        }
    }
}

repositories {
    maven { url = 'https://repo.spongepowered.org/repository/maven-public/' }
}

dependencies {
    compile "org.spongepowered:mixin:0.8.2"
}

jar {
    from {
        configurations.compile
                .filter { "mixin-0.8.2.jar" == it.name }
                .collect { it.isDirectory() ? it : zipTree(it) }
    } {
        exclude "LICENSE.txt", "META-INF/MANIFSET.MF", "META-INF/maven/**", "META-INF/*.RSA", "META-INF/*.SF"
    }

    manifest {
        attributes([
                "FMLCorePluginContainsFMLMod": true,
                "ForceLoadAsMod"             : true,
                "MixinConfigs"               : "mixin.examplemod.json",
                "TweakClass"                 : "org.spongepowered.asm.launch.MixinTweaker",
                "TweakOrder"                 : 0
        ])
    }
}
```

#### resources/mixin.examplemod.json
```json
{
  "compatibilityLevel": "JAVA_8",
  "package": "com.example.examplemod.mixin",
  "mixins": [
    "GuiMainMenuMixin"
  ]
}
```

#### com.example.examplemod.LoadingPlugin
```java
package com.example.examplemod;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import javax.annotation.Nullable;
import java.util.Map;

public class LoadingPlugin implements IFMLLoadingPlugin {

    public LoadingPlugin() {
        MixinBootstrap.init();
        Mixins.addConfiguration("mixin.examplemod.json");
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {

    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

}
```

#### com.example.examplemod.mixin.GuiMainMenuMixin
```java
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
```
