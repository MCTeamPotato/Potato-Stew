package com.teampotato.potatostew.mixin;

import com.teampotato.potatostew.util.ButtonList;
import com.teampotato.potatostew.impl.ScreenExtensions;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;

@Mixin(Screen.class)
public class ScreenMixin implements ScreenExtensions {
    @Shadow @Final public List<Renderable> renderables;
    @Shadow @Final private List<NarratableEntry> narratables;
    @Shadow @Final private List<GuiEventListener> children;
    @Unique
    private ButtonList fabricButtons;
    @Override
    public List<AbstractWidget> fabric_getButtons() {
        // Lazy init to make the list access safe after Screen#init
        if (this.fabricButtons == null) {
            this.fabricButtons = new ButtonList(this.renderables, this.narratables, this.children);
        }

        return this.fabricButtons;
    }
}
