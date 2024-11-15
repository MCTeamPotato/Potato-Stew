package com.teampotato.potatostew.config;

import com.google.common.collect.Lists;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class ModConfigClient {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.Builder push = BUILDER.push("PotatoStew-Client");
    private static final ForgeConfigSpec.Builder continueButton = BUILDER.push("ContinueButton");
    public static final ForgeConfigSpec.ConfigValue<Boolean> LASTLOCAL = BUILDER.define("lastLocal", true);
    public static final ForgeConfigSpec.ConfigValue<String> SERVERNAME = BUILDER.define("serverName", "");
    public static final ForgeConfigSpec.ConfigValue<String> SERVERADDRESS = BUILDER.define("serverAddress", "");
    private static final ForgeConfigSpec.Builder popCB = BUILDER.pop();
    public static final ForgeConfigSpec SPEC = BUILDER.build();
}
