package com.teampotato.potatostew;

import com.mojang.logging.LogUtils;
import com.teampotato.potatostew.config.ModConfig;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod(PotatoStew.MODID)
public class PotatoStew {

    public static final String MODID = "potato_stew";
    public static final Logger LOGGER = LogUtils.getLogger();
    public PotatoStew(){
        ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.CLIENT, ModConfig.SPEC);
    }
}
