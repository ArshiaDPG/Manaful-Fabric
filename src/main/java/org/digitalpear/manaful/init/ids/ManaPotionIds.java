package org.digitalpear.manaful.init.ids;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.alchemy.Potion;

public class ManaPotionIds {
    public static final ResourceKey<Potion> MANA = register("mana");
    public static final ResourceKey<Potion> STRONG_MANA = register("strong_mana");

    public static final ResourceKey<Potion> MANA_REGENERATION = register("mana_regeneration");
    public static final ResourceKey<Potion> STRONG_MANA_REGENERATION = register("strong_mana_regeneration");
    public static final ResourceKey<Potion> LONG_MANA_REGENERATION = register("long_mana_regeneration");

    private static ResourceKey<Potion> register(final String name) {
        return ResourceKey.create(Registries.POTION, Identifier.withDefaultNamespace(name));
    }
}
