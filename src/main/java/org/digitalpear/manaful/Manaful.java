package org.digitalpear.manaful;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.resources.Identifier;
import org.digitalpear.manaful.common.mana.ManaChangePacket;
import org.digitalpear.manaful.common.mana.ManaCost;
import org.digitalpear.manaful.init.*;

import java.util.logging.Logger;

public class Manaful implements ModInitializer {

    public static final String MOD_ID = "manaful";
    public static final Logger LOGGER = Logger.getLogger(MOD_ID);


    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    @Override
    public void onInitialize() {
        ManaAttachments.init();
        ManaStats.init();
        ManaAttributes.init();
        ManaDataComponents.init();
        ManaMobEffects.init();
        ManaPotions.init();

        PayloadTypeRegistry.clientboundPlay().register(ManaChangePacket.TYPE, ManaChangePacket.CODEC);

        ServerTickEvents.END_SERVER_TICK.register((server) -> server.getPlayerList().getPlayers().forEach(player -> {
            if (player.getAttachedOrCreate(ManaAttachments.MANA).getMana() < player.getAttribute(ManaAttributes.MAX_MANA).getValue()) {
                player.getAttachedOrCreate(ManaAttachments.MANA).increaseMana(player, player.getAttribute(ManaAttributes.MANA_REGENERATION).getValue());
            }
        }));

        ItemTooltipCallback.EVENT.register((stack, tooltipContext, tooltipFlag, lines) -> {
            if (stack.has(ManaDataComponents.MANA_COST)){
                ManaCost cost = stack.get(ManaDataComponents.MANA_COST);
                lines.add(1, cost.tooltip());
            }
        });
    }


}
