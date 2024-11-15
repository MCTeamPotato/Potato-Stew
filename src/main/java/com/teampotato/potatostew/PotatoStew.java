package com.teampotato.potatostew;

import com.mojang.logging.LogUtils;
import com.teampotato.potatostew.config.ModConfigClient;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(PotatoStew.MODID)
public class PotatoStew {

    public static final String MODID = "potato_stew";
    public static final Logger LOGGER = LogUtils.getLogger();
    public PotatoStew(){
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ModConfigClient.SPEC);
    }
}
