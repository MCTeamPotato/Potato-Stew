package com.teampotato.potatostew.mixin.coloredfishingline;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.FishingHookRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.DyeColor;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static java.util.Map.entry;
import net.minecraft.client.renderer.entity.FishingHookRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FishingHookRenderer.class)
public class FishingBobberEntityRendererMixin {
    private static final Map<String, DyeColor> colorMap = Map.ofEntries(
            entry("black", DyeColor.BLACK),
            entry("blue", DyeColor.BLUE),
            entry("cyan", DyeColor.CYAN),
            entry("gray", DyeColor.GRAY),
            entry("green", DyeColor.GREEN),
            entry("light_blue", DyeColor.LIGHT_BLUE),
            entry("lime", DyeColor.LIME),
            entry("magenta", DyeColor.MAGENTA),
            entry("orange", DyeColor.ORANGE),
            entry("pink", DyeColor.PINK),
            entry("purple", DyeColor.PURPLE),
            entry("red", DyeColor.RED),
            entry("white", DyeColor.WHITE),
            entry("yellow", DyeColor.YELLOW)
    );

    private static boolean checkNameForColor(String name) {
        for (String colorName: colorMap.keySet()) {
            if (name.contains(colorName)) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkNameForHex(String name) {
        String[] splitedName = name.split(" ");
        for (String namePart: splitedName) {
            if (namePart.contains("#")) {
                try {
                    Color.decode(namePart);
                } catch (NumberFormatException e) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    private static Color getHexFromName(String name) {
        String[] splitedName = name.split(" ");
        Color resultColor = Color.BLACK;
        for (String namePart: splitedName) {
            if (namePart.contains("#")) {
                try {
                    resultColor = Color.decode(namePart);
                } catch (NumberFormatException e) {
                    return resultColor;
                }
                return resultColor;
            }
        }
        return resultColor;
    }

    private static DyeColor getColorFromName(String name) {
        for (String colorName: colorMap.keySet()) {
            if (name.contains(colorName)) {
                return colorMap.get(colorName);
            }
        }
        return colorMap.get("black");
    }

    @Shadow
    private static float fraction(int value, int max) {
        return 0;
    }

    @Inject(method = "Lnet/minecraft/client/renderer/entity/FishingHookRenderer;render(Lnet/minecraft/world/entity/projectile/FishingHook;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/FishingHookRenderer;stringVertex(FFFLcom/mojang/blaze3d/vertex/VertexConsumer;Lcom/mojang/blaze3d/vertex/PoseStack$Pose;FF)V"),
            locals = LocalCapture.CAPTURE_FAILHARD)
    private void onRenderFishingLine(FishingHook fishingBobberEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, CallbackInfo ci, Player player, PoseStack.Pose posestack$pose, Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer vertexconsumer, int i, ItemStack itemstack, float f, float f1, float f2, double d0, double d1, double d2, double d3, double d4, double d5, double d6, float f3, double d9, double d10, double d8, float v, float w, float x, VertexConsumer vertexConsumer2, PoseStack.Pose entry2, int y, int z) {
        Player bobberOwner = fishingBobberEntity.getPlayerOwner();
        if (bobberOwner != null) {
            Iterable<ItemStack> itemsHand = bobberOwner.getHandSlots();
            List<ItemStack> selectedItems = new ArrayList<>();
            itemsHand.forEach(selectedItems::add);

            String name = selectedItems.get(0).getHoverName().getString().toLowerCase(Locale.ROOT) + " " +
                    selectedItems.get(1).getHoverName().getString().toLowerCase(Locale.ROOT);
            if (name.contains("trans") || name.contains("lesbian") || name.contains("genderfluid") || name.contains("pan")
                    || name.contains("poly") || name.contains("aro") || name.contains("aroace")) {
                if (z <= 15) {
                    customRenderFishingLine(fishingBobberEntity, v, w, x, vertexConsumer2, entry2, fraction(z, 15),
                            fraction(z + 1, 15));
                }
            } else if (name.contains("mlm") || name.contains("agender") || name.contains("demiboy") || name.contains("demigirl")
                    || name.contains("bigender")) {
                if (z <= 14) {
                    customRenderFishingLine(fishingBobberEntity, v, w, x, vertexConsumer2, entry2, fraction(z, 14),
                            fraction(z + 1, 14));
                }
            } else if (name.contains("gay") || name.contains("proud")) {
                if (z <= 12) {
                    customRenderFishingLine(fishingBobberEntity, v, w, x, vertexConsumer2, entry2, fraction(z, 12),
                            fraction(z + 1, 12));
                }
            } else if (checkNameForColor(name) || name.contains("invis")) {
                customRenderFishingLine(fishingBobberEntity, v, w, x, vertexConsumer2, entry2, fraction(z, 16),
                        fraction(z + 1, 16));
            } else {
                customRenderFishingLine(fishingBobberEntity, v, w, x, vertexConsumer2, entry2, fraction(z, 16),
                        fraction(z + 1, 16));
            }
        }
    }

    @Redirect(method = "Lnet/minecraft/client/renderer/entity/FishingHookRenderer;render(Lnet/minecraft/world/entity/projectile/FishingHook;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/FishingHookRenderer;stringVertex(FFFLcom/mojang/blaze3d/vertex/VertexConsumer;Lcom/mojang/blaze3d/vertex/PoseStack$Pose;FF)V"))
    private void mixin(float x, float y, float z, VertexConsumer entry2, PoseStack.Pose matrices, float segmentStart, float segmentEnd) {
    }

    private static void customRenderFishingLine(FishingHook fishingBobberEntity, float x, float y, float z, VertexConsumer buffer,
                                                PoseStack.Pose matrices, float segmentStart, float segmentEnd) {
        float f = x * segmentStart;
        float gg = y * (segmentStart * segmentStart + segmentStart) * 0.5F + 0.25F;
        float h = z * segmentStart;
        float i = x * segmentEnd - f;
        float j = y * (segmentEnd * segmentEnd + segmentEnd) * 0.5F + 0.25F - gg;
        float k = z * segmentEnd - h;
        float l = Mth.sqrt(i * i + j * j + k * k);
        i /= l;
        j /= l;
        k /= l;
        float r2;
        float g2;
        float b2;
        Player bobberOwner = fishingBobberEntity.getPlayerOwner();
        if (bobberOwner != null) {
            Iterable<ItemStack> itemsHand = bobberOwner.getHandSlots();
            List<ItemStack> selectedItems = new ArrayList<>();
            itemsHand.forEach(selectedItems::add);

            String name = selectedItems.get(0).getHoverName().getString().toLowerCase(Locale.ROOT) + " " +
                    selectedItems.get(1).getHoverName().getString().toLowerCase(Locale.ROOT);
            if (name.contains("flux")) {
                r2 = (float) (Math.sin((((segmentStart * 16) * 2 + fishingBobberEntity.tickCount) % 240f) * Math.PI / 60f) + 1) / 2;
                g2 = (float) (Math.sin(((segmentStart * 16) * 2 + (fishingBobberEntity.tickCount + 80) % 240f) * Math.PI / 60f) + 1) / 2;
                b2 = (float) (Math.sin(((segmentStart * 16) * 2 + (fishingBobberEntity.tickCount + 160) % 240f) * Math.PI / 60f) + 1) / 2;
                buffer.vertex(matrices.pose(), f, gg, h).color(r2, g2, b2, 1.0f).normal(matrices.normal(), i, j, k).endVertex();
            } else if (name.contains("lesbian")) {
                if (segmentStart <= 1f && segmentStart > 12f / 15f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(213, 45, 0, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 12f / 15f && segmentStart > 9f / 15f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(255, 154, 86, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 9f / 15f && segmentStart > 6f / 15f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(255, 255, 255, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 6f / 15f && segmentStart > 3f / 15f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(211, 98, 164, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 3f / 15f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(163, 2, 98, 255).normal(matrices.normal(), i, j, k).endVertex();
            } else if (name.contains("nonbinary")) {
                if (segmentStart <= 1f && segmentStart > 12f / 16f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(255, 244, 48, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 12f / 16f && segmentStart > 8f / 16f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(255, 255, 255, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 8f / 16f && segmentStart > 4f / 16f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(156, 89, 209, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 4f / 16f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(0, 0, 0, 255).normal(matrices.normal(), i, j, k).endVertex();
            } else if (name.contains("intersex")) {
                if (segmentStart <= 1f && segmentStart > 12f / 16f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(255, 216, 0, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 12f / 16f && segmentStart > 10f / 16f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(121, 2, 170, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 10f / 16f && segmentStart > 6f / 16f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(255, 216, 0, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 6f / 16f && segmentStart > 4f / 16f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(121, 2, 170, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 4f / 16f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(255, 216, 0, 255).normal(matrices.normal(), i, j, k).endVertex();
            } else if (name.contains("demisexual")) {
                if (segmentStart <= 1f && segmentStart > 11f / 16f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(255, 255, 255, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 11f / 16f && segmentStart > 9f / 16f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(109, 0, 112, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 9f / 16f && segmentStart > 7f / 16f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(0, 0, 0, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 7f / 16f && segmentStart > 5f / 16f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(109, 0, 112, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 5f / 16f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(210, 210, 210, 255).normal(matrices.normal(), i, j, k).endVertex();
            } else if (name.contains("trans")) {
                if (segmentStart <= 1f && segmentStart > 12f / 15f || segmentStart <= 3f / 16f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(91, 206, 250, 255).normal(matrices.normal(), i, j, k).endVertex();
                if ((segmentStart <= 12f / 15f && segmentStart > 9f / 15f) || (segmentStart <= 6f / 15f && segmentStart > 3f / 15f))
                    buffer.vertex(matrices.pose(), f, gg, h).color(245, 169, 184, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 9f / 15f && segmentStart > 6f / 15f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(255, 255, 255, 255).normal(matrices.normal(), i, j, k).endVertex();
            } else if (name.contains("mlm")) {
                if (segmentStart <= 1f && segmentStart > 12f / 14f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(7, 141, 112, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 12f / 14f && segmentStart > 10f / 14f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(39, 201, 171, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 10f / 14f && segmentStart > 8f / 14f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(154, 233, 195, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 8f / 14f && segmentStart > 6f / 14f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(255, 255, 255, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 6f / 14f && segmentStart > 4f / 14f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(124, 174, 228, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 4f / 14f && segmentStart > 2f / 14f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(81, 74, 204, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 2f / 14f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(62, 26, 120, 255).normal(matrices.normal(), i, j, k).endVertex();
            } else if (name.contains("gay") || name.contains("proud")) {
                if (segmentStart <= 1f && segmentStart > 10f / 12f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(255, 0, 0, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 10f / 12f && segmentStart > 8f / 12f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(255, 140, 0, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 8f / 12f && segmentStart > 6f / 12f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(255, 255, 0, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 6f / 12f && segmentStart > 4f / 12f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(0, 255, 0, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 4f / 12f && segmentStart > 2f / 12f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(0, 0, 255, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 2f / 12f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(255, 0, 255, 255).normal(matrices.normal(), i, j, k).endVertex();
            } else if (name.contains("agender")) {
                if (segmentStart <= 1f && segmentStart > 12f / 14f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(0, 0, 0, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 12f / 14f && segmentStart > 10f / 14f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(188, 196, 198, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 10f / 14f && segmentStart > 8f / 14f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(255, 255, 255, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 8f / 14f && segmentStart > 6f / 14f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(182, 245, 131, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 6f / 14f && segmentStart > 4f / 14f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(255, 255, 255, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 4f / 14f && segmentStart > 2f / 14f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(188, 196, 198, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 2f / 14f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(0, 0, 0, 255).normal(matrices.normal(), i, j, k).endVertex();
            } else if (name.contains("plural")) {
                if (segmentStart <= 1f && segmentStart > 12f / 16f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(48, 198, 159, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 12f / 16f && segmentStart > 8f / 16f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(52, 125, 223, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 8f / 16f && segmentStart > 4f / 16f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(107, 63, 190, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 4f / 16f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(0, 0, 0, 255).normal(matrices.normal(), i, j, k).endVertex();
            } else if (name.contains("genderfluid")) {
                if (segmentStart <= 1f && segmentStart > 12f / 15f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(255, 117, 162, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 12f / 15f && segmentStart > 9f / 15f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(255, 255, 255, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 9f / 15f && segmentStart > 6f / 15f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(190, 24, 214, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 6f / 15f && segmentStart > 3f / 15f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(0, 0, 0, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 3f / 15f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(51, 62, 189, 255).normal(matrices.normal(), i, j, k).endVertex();
            } else if (name.contains("aroace")) {
                if (segmentStart <= 1f && segmentStart > 12f / 15f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(226, 140, 0, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 12f / 15f && segmentStart > 9f / 15f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(236, 205, 0, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 9f / 15f && segmentStart > 6f / 15f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(255, 255, 255, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 6f / 15f && segmentStart > 3f / 15f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(98, 174, 220, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 3f / 15f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(32, 56, 86, 255).normal(matrices.normal(), i, j, k).endVertex();
            } else if (name.contains("aro")) {
                if (segmentStart <= 1f && segmentStart > 12f / 15f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(61, 165, 66, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 12f / 15f && segmentStart > 9f / 15f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(167, 211, 121, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 9f / 15f && segmentStart > 6f / 15f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(255, 255, 255, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 6f / 15f && segmentStart > 3f / 15f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(169, 169, 169, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 3f / 15f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(0, 0, 0, 255).normal(matrices.normal(), i, j, k).endVertex();
            } else if (name.contains("ace") || name.contains("asexual")) {
                if (segmentStart <= 1f && segmentStart > 12f / 16f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(0, 0, 0, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 12f / 16f && segmentStart > 8f / 16f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(163, 163, 163, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 8f / 16f && segmentStart > 4f / 16f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(255, 255, 255, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 4f / 16f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(128, 0, 128, 255).normal(matrices.normal(), i, j, k).endVertex();
            } else if (name.contains("pan")) {
                if (segmentStart <= 1f && segmentStart > 10f / 15f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(254, 33, 139, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 10f / 15f && segmentStart > 5f / 15f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(254, 215, 0, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 5f / 15f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(33, 176, 254, 255).normal(matrices.normal(), i, j, k).endVertex();
            } else if (name.contains("poly")) {
                if (segmentStart <= 1f && segmentStart > 10f / 15f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(246, 28, 158, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 10f / 15f && segmentStart > 5f / 15f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(7, 213, 105, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 5f / 15f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(28, 146, 246, 255).normal(matrices.normal(), i, j, k).endVertex();
            } else if (name.contains("genderqueer")) {
                if (segmentStart <= 1f && segmentStart > 10f / 15f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(181, 126, 220, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 10f / 15f && segmentStart > 5f / 15f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(255, 255, 255, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 5f / 15f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(74, 129, 35, 255).normal(matrices.normal(), i, j, k).endVertex();
            } else if (name.contains("disability")) {
                if (segmentStart <= 1f && segmentStart > 10f / 15f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(234, 191, 63, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 10f / 15f && segmentStart > 5f / 15f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(207, 209, 208, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 5f / 15f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(211, 152, 74, 255).normal(matrices.normal(), i, j, k).endVertex();
            } else if (name.contains("demiboy")) {
                if ((segmentStart <= 1f && segmentStart > 12f / 14f) || segmentStart <= 2f / 14f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(127, 127, 127, 255).normal(matrices.normal(), i, j, k).endVertex();
                if ((segmentStart <= 12f / 14f && segmentStart > 10f / 14f) || (segmentStart <= 4f / 14f && segmentStart > 2f / 14f))
                    buffer.vertex(matrices.pose(), f, gg, h).color(196, 196, 196, 255).normal(matrices.normal(), i, j, k).endVertex();
                if ((segmentStart <= 10f / 14f && segmentStart > 8f / 14f) || (segmentStart <= 6f / 14f && segmentStart > 4f / 14f))
                    buffer.vertex(matrices.pose(), f, gg, h).color(154, 217, 235, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 8f / 14f && segmentStart > 6f / 14f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(255, 255, 255, 255).normal(matrices.normal(), i, j, k).endVertex();
            } else if (name.contains("demigirl")) {
                if ((segmentStart <= 1f && segmentStart > 12f / 14f) || segmentStart <= 2f / 14f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(127, 127, 127, 255).normal(matrices.normal(), i, j, k).endVertex();
                if ((segmentStart <= 12f / 14f && segmentStart > 10f / 14f) || (segmentStart <= 4f / 14f && segmentStart > 2f / 14f))
                    buffer.vertex(matrices.pose(), f, gg, h).color(196, 196, 196, 255).normal(matrices.normal(), i, j, k).endVertex();
                if ((segmentStart <= 10f / 14f && segmentStart > 8f / 14f) || (segmentStart <= 6f / 14f && segmentStart > 4f / 14f))
                    buffer.vertex(matrices.pose(), f, gg, h).color(255, 174, 201, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 8f / 14f && segmentStart > 6f / 14f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(255, 255, 255, 255).normal(matrices.normal(), i, j, k).endVertex();
            } else if (name.contains("bigender")) {
                if (segmentStart <= 1f && segmentStart > 12f / 14f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(196, 121, 160, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 12f / 14f && segmentStart > 10f / 14f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(236, 166, 203, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 10f / 14f && segmentStart > 8f / 14f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(214, 199, 233, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 8f / 14f && segmentStart > 6f / 14f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(255, 255, 255, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 6f / 14f && segmentStart > 4f / 14f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(214, 199, 233, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 4f / 14f && segmentStart > 2f / 14f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(155, 199, 232, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 2f / 14f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(107, 131, 207, 255).normal(matrices.normal(), i, j, k).endVertex();
            } else if (name.contains("amongus") || name.contains("sus")) {
                if (segmentStart <= 1f && segmentStart > 14f / 16f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(199, 16, 18, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 14f / 16f && segmentStart > 13f / 16f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(19, 40, 57, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 13f / 16f && segmentStart > 10f / 16f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(149, 202, 220, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 10f / 16f && segmentStart > 8f / 16f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(79, 125, 161, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 8f / 16f && segmentStart > 7f / 16f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(19, 40, 57, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 7f / 16f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(199, 16, 18, 255).normal(matrices.normal(), i, j, k).endVertex();
            } else if (name.contains("pineapple")) {
                if (segmentStart <= 1f && segmentStart > 14f / 16f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(0, 85, 16, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 14f / 16f && segmentStart > 11f / 16f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(255, 113, 0, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 11f / 16f && segmentStart > 10f / 16f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(0, 0, 0, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 10f / 16f && segmentStart > 4f / 16f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(255, 113, 0, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 4f / 16f && segmentStart > 3f / 16f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(0, 0, 0, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 3f / 16f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(255, 113, 0, 255).normal(matrices.normal(), i, j, k).endVertex();
            } else if (name.contains("bi")) {
                if (segmentStart <= 1f && segmentStart > 10f / 16f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(214, 2, 112, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 10f / 16f && segmentStart > 6f / 16f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(155, 79, 150, 255).normal(matrices.normal(), i, j, k).endVertex();
                if (segmentStart <= 6f / 16f)
                    buffer.vertex(matrices.pose(), f, gg, h).color(0, 56, 168, 255).normal(matrices.normal(), i, j, k).endVertex();
            } else if (checkNameForColor(name)) {
                int color = getColorFromName(name).getMapColor().col;
                int r = FastColor.ARGB32.red(color);
                int g = FastColor.ARGB32.green(color);
                int b = FastColor.ARGB32.blue(color);
                buffer.vertex(matrices.pose(), f, gg, h).color(r, g, b, 255).normal(matrices.normal(), i, j, k).endVertex();
            } else if (checkNameForHex(name)) {
                Color color = getHexFromName(name);
                int r = color.getRed();
                int g = color.getGreen();
                int b = color.getBlue();
                int alpha = color.getAlpha();
                buffer.vertex(matrices.pose(), f, gg, h).color(r, g, b, alpha).normal(matrices.normal(), i, j, k).endVertex();
            } else if (name.contains("invis")) {
            } else {
                buffer.vertex(matrices.pose(), f, gg, h).color(0, 0, 0, 255).normal(matrices.normal(), i, j, k).endVertex();
            }
        }
    }
}
