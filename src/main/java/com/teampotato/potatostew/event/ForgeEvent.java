package com.teampotato.potatostew.event;

import com.google.common.io.Files;
import com.teampotato.potatostew.PotatoStew;
import com.teampotato.potatostew.config.ModConfigClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.nio.file.Path;

@Mod.EventBusSubscriber(modid = PotatoStew.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEvent {
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void clientJoin(ClientPlayerNetworkEvent.LoggingIn event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.isLocalServer()) {
            ModConfigClient.LASTLOCAL.set(true);
            String levelName = minecraft.getSingleplayerServer().getWorldData().getLevelName();
            Path pathToSave = Path.of(Files.simplifyPath(minecraft.getSingleplayerServer().getWorldPath(LevelResource.ROOT).toString()));
            String folderName = pathToSave.normalize().toFile().getName();
            ModConfigClient.SERVERNAME.set(levelName);
            ModConfigClient.SERVERADDRESS.set(folderName);
        } else {
            ServerData serverData = minecraft.getCurrentServer();
            ModConfigClient.LASTLOCAL.set(false);
            ModConfigClient.SERVERNAME.set(serverData.name);
            ModConfigClient.SERVERADDRESS.set(serverData.ip);
        }
    }
}
