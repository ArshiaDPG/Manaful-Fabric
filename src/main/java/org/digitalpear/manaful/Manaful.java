package org.digitalpear.manaful;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.ItemEvents;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.digitalpear.manaful.common.ManaChangePacket;
import org.digitalpear.manaful.common.ManaCost;
import org.digitalpear.manaful.init.ManaAttachments;
import org.digitalpear.manaful.init.ManaAttributes;
import org.digitalpear.manaful.init.ManaDataComponents;
import org.digitalpear.manaful.init.ManaStats;

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

//        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
//            modTests();
//        }
    }

    private static void modTests(){
        ItemEvents.USE.register((level, player, interactionHand) -> {
            ItemStack stack = player.getItemInHand(interactionHand);
            if (stack.is(ItemTags.SWORDS) && player.getAttached(ManaAttachments.MANA).hasEnoughMana(stack)) {
                level.explode(player, player.getX(), player.getY(), player.getZ(), 1, Level.ExplosionInteraction.NONE);
                player.getAttached(ManaAttachments.MANA).depleteMana(player, stack.get(ManaDataComponents.MANA_COST).cost());
                return InteractionResult.SUCCESS;
            }
            return null;
        });

        DefaultItemComponentEvents.MODIFY.register(context -> {
            context.modify(item -> item.getDefaultInstance().is(ItemTags.SWORDS), (builder, _) -> builder.set(ManaDataComponents.MANA_COST, new ManaCost(12)));
        });
    }
}
