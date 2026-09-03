package org.digitalpear.manaful.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudStatusBarHeightRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.Identifier;
import org.digitalpear.manaful.Manaful;
import org.digitalpear.manaful.common.mana.ManaChangePacket;
import org.digitalpear.manaful.common.mana.ManaSource;
import org.digitalpear.manaful.init.ManaAttachments;

public class ManafulClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Identifier manaBar = Manaful.id("mana_bar");

        HudStatusBarHeightRegistry.addLeft(manaBar, player -> 11);
        HudElementRegistry.attachElementAfter(VanillaHudElements.HELD_ITEM_TOOLTIP, manaBar, (graphics, deltaTracker) -> {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null) {
                int height = graphics.guiHeight() - HudStatusBarHeightRegistry.getHeight(VanillaHudElements.ARMOR_BAR);
                if (mc.player.getArmorValue() > 0) {
                    height -= 10;
                }
                ManaBarRenderer.INSTANCE.extract(graphics, graphics.guiWidth()/2, height, mc.player);
            }
        });

        ClientPlayNetworking.registerGlobalReceiver(ManaChangePacket.TYPE, (payload, context) -> {
            ClientLevel level = context.client().level;

            if (level == null) {
                return;
            }
            context.player().setAttached(ManaAttachments.MANA, new ManaSource(payload.newAmount()));
        });

    }

}
