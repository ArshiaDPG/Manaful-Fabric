package org.digitalpear.manaful.client.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.Identifier;
import org.digitalpear.manaful.Manaful;
import org.digitalpear.manaful.init.ManaAttributes;
import org.digitalpear.manaful.init.ManaStats;

import java.util.concurrent.CompletableFuture;

public class ManafulLanguageProvider extends FabricLanguageProvider {
    public ManafulLanguageProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(packOutput, registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider provider, TranslationBuilder translationBuilder) {
        translationBuilder.add(ManaAttributes.MAX_MANA, "Max Mana");
        translationBuilder.add(ManaAttributes.MANA_REGENERATION, "Max Regeneration");

        translateStat(translationBuilder, ManaStats.MANA_USED, "Mana Used");

        translationBuilder.add(Manaful.MOD_ID + ".mana.description", "Mana Cost");
    }

    public static void translateStat(TranslationBuilder translationBuilder, Identifier id, String translation){
        translationBuilder.add("stat.%s.%s".formatted(id.getNamespace(), id.getPath()), translation);
    }
}
