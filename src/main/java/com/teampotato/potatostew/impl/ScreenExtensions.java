package com.teampotato.potatostew.impl;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;

import java.util.List;

public interface ScreenExtensions {
    static ScreenExtensions getExtensions(Screen screen) {
        return (ScreenExtensions) screen;
    }
    List<AbstractWidget> fabric_getButtons();
}
