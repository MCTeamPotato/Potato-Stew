package com.teampotato.potatostew.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.Builder push = BUILDER.push("PotatoStew");
    private static final ForgeConfigSpec.Builder client = BUILDER.push("client");
    public static final ForgeConfigSpec.ConfigValue<Boolean> LASTLOCAL = BUILDER.define("lastLocal", true);
    public static final ForgeConfigSpec.ConfigValue<String> SERVERNAME = BUILDER.define("serverName", "");
    public static final ForgeConfigSpec.ConfigValue<String> SERVERADDRESS  = BUILDER.define("serverAddress", "");
    private static final ForgeConfigSpec.Builder pop_client = BUILDER.pop();
    private static final ForgeConfigSpec.Builder pop = BUILDER.pop();
    public static final ForgeConfigSpec SPEC = BUILDER.build();
}
