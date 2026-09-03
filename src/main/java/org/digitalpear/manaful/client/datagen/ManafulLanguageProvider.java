package org.digitalpear.manaful.client.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.alchemy.Potion;
import org.digitalpear.manaful.Manaful;
import org.digitalpear.manaful.init.ManaAttributes;
import org.digitalpear.manaful.init.ManaMobEffects;
import org.digitalpear.manaful.init.ManaPotions;
import org.digitalpear.manaful.init.ManaStats;
import org.digitalpear.manaful.init.ids.ManaPotionIds;

import java.util.concurrent.CompletableFuture;

public class ManafulLanguageProvider extends FabricLanguageProvider {
    public ManafulLanguageProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(packOutput, registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider provider, TranslationBuilder translationBuilder) {
        translationBuilder.add(ManaAttributes.MAX_MANA, "Max Mana");
        translationBuilder.add(ManaAttributes.MANA_REGENERATION, "Mana Regeneration");

        translateStat(translationBuilder, ManaStats.MANA_USED, "Mana Used");

        translationBuilder.add(Manaful.MOD_ID + ".mana.description", "Mana Cost");

        translationBuilder.add(ManaMobEffects.INSTANT_MANA.value(), "Instant Mana");
        translationBuilder.add(ManaMobEffects.MANA_REGENERATION.value(), "Mana Regeneration");
        translatePotion(translationBuilder, ManaPotionIds.MANA, "Mana");
        translatePotion(translationBuilder, ManaPotionIds.STRONG_MANA, "Mana");

        translatePotion(translationBuilder, ManaPotionIds.MANA_REGENERATION, "Mana Regeneration");
        translatePotion(translationBuilder, ManaPotionIds.STRONG_MANA_REGENERATION, "Mana Regeneration");
        translatePotion(translationBuilder, ManaPotionIds.LONG_MANA_REGENERATION, "Mana Regeneration");
    }

    public static void translatePotion(TranslationBuilder translationBuilder, ResourceKey<Potion> potionId, String translation) {
        translationBuilder.add("item.minecraft.potion.effect." + potionId.identifier().getPath(), "Potion of " + translation);
        translationBuilder.add("item.minecraft.splash_potion.effect." + potionId.identifier().getPath(), "Splash Potion of " + translation);
        translationBuilder.add("item.minecraft.lingering_potion.effect." + potionId.identifier().getPath(), "Lingering Potion of " + translation);
        translationBuilder.add("item.minecraft.tipped_arrow.effect." + potionId.identifier().getPath(), "Arrow of " + translation);
    }

    public static void translateStat(TranslationBuilder translationBuilder, Identifier id, String translation){
        translationBuilder.add("stat.%s.%s".formatted(id.getNamespace(), id.getPath()), translation);
    }
}
