package com.teampotato.potatostew.mixin.continuebutton;

import com.teampotato.potatostew.config.ModConfigClient;
import com.teampotato.potatostew.impl.ScreenExtensions;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerStatusPinger;
import net.minecraft.client.quickplay.QuickPlay;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

// https://github.com/IMB11/continue-button/blob/1.20/src/main/java/com/mineblock11/continuebutton/mixin/MixinTitleScreen.java
@Mixin(TitleScreen.class)
public class TitleMixin extends Screen {
    private final ServerStatusPinger serverListPinger = new ServerStatusPinger();
    Button continueButtonWidget = null;
    private ServerData serverInfo = null;
    private boolean isFirstRender = false;
    private boolean readyToShow = false;

    protected TitleMixin(Component title) {
        super(title);
    }

    @Inject(at = @At("HEAD"), method = "createNormalMenuOptions(II)V")
    public void drawMenuButton(int y, int spacingY, CallbackInfo info) {
        Button.Builder continueButtonBuilder = Button.builder(Component.translatable("button.potato_stew.continue"), button -> {
            if (ModConfigClient.LASTLOCAL.get()) {
                if (!ModConfigClient.SERVERNAME.get().isBlank()) {
                        QuickPlay.joinSingleplayerWorld(minecraft, ModConfigClient.SERVERADDRESS.get());
                } else {
                    CreateWorldScreen.openFresh(minecraft, this);
                }
            } else {
                QuickPlay.joinMultiplayerWorld(minecraft, ModConfigClient.SERVERADDRESS.get());
            }
        });
        continueButtonBuilder.bounds(this.width / 2 - 100, y, 98, 20);
        continueButtonWidget = continueButtonBuilder.build();
        ScreenExtensions.getExtensions(this).fabric_getButtons().add(continueButtonWidget);
    }

    @Inject(at = @At("HEAD"), method = "init()V")
    public void initAtHead(CallbackInfo info) {
        this.isFirstRender = true;
    }

    @Inject(at = @At("TAIL"), method = "init()V")
    public void init(CallbackInfo info) {
        for (AbstractWidget button : ScreenExtensions.getExtensions(this).fabric_getButtons()) {
            if (button.visible && !button.getMessage().equals(Component.translatable("button.potato_stew.continue"))) {
                button.setX(this.width / 2 + 2);
                button.setWidth(98);
                break;
            }
        }
    }

    private void atFirstRender() {
        new Thread(() -> {
            if (!ModConfigClient.LASTLOCAL.get()) {
                //serverInfo = new ServerData(ModConfig.SERVERNAME.get(), ModConfig.SERVERADDRESS.get(), ServerInfo.ServerType.OTHER);
                serverInfo = new ServerData(ModConfigClient.SERVERNAME.get(), ModConfigClient.SERVERADDRESS.get(), true);
                serverInfo.motd = Component.translatable("multiplayer.status.pinging");
                try {
                    serverListPinger.pingServer(serverInfo, () -> {
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            readyToShow = true;
        }).start();
    }

    @Inject(at = @At("HEAD"), method = "render")
    public void renderAtHead(GuiGraphics context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (isFirstRender) {
            isFirstRender = false;
            atFirstRender();
        }
    }

    @Inject(at = @At("TAIL"), method = "render")
    public void renderAtTail(GuiGraphics context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (continueButtonWidget.isHovered() && this.readyToShow) {
            if (ModConfigClient.LASTLOCAL.get()) {
                if (ModConfigClient.SERVERADDRESS.get().isEmpty()) {
                    List<FormattedCharSequence> list = new ArrayList<>();
                    list.add(Component.translatable("selectWorld.create").withStyle(ChatFormatting.GRAY).getVisualOrderText());
                    context.renderTooltip(this.font, list, mouseX, mouseY);
                } else {
                    List<FormattedCharSequence> list = new ArrayList<>();
                    list.add(Component.translatable("menu.singleplayer").withStyle(ChatFormatting.GRAY).getVisualOrderText());
                    list.add(Component.literal(ModConfigClient.SERVERNAME.get()).getVisualOrderText());
                    context.renderTooltip(this.font, list, mouseX, mouseY);
                }
            } else {
                List<FormattedCharSequence> list = new ArrayList<>(this.minecraft.font.split(serverInfo.motd, 270));
                list.add(0, Component.literal(serverInfo.name).withStyle(ChatFormatting.GRAY).getVisualOrderText());
                context.renderTooltip(this.font, list, mouseX, mouseY);
            }
        }
    }

    @Inject(at = @At("RETURN"), method = "tick()V")
    public void tick(CallbackInfo info) {
        serverListPinger.tick();
    }

    @Inject(at = @At("RETURN"), method = "removed()V")
    public void removed(CallbackInfo info) {
        serverListPinger.removeAll();
    }
}
