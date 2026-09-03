package org.digitalpear.manaful.init;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import org.digitalpear.manaful.init.ids.ManaPotionIds;

public class ManaPotions {

    public static final Holder<Potion> MANA = register(ManaPotionIds.MANA, new Potion("mana", new MobEffectInstance(ManaMobEffects.INSTANT_MANA, 1)));
    public static final Holder<Potion> STRONG_MANA = register(ManaPotionIds.STRONG_MANA, new Potion("strong_mana", new MobEffectInstance(ManaMobEffects.INSTANT_MANA, 1, 1)));

    public static final Holder<Potion> REGENERATION = register(ManaPotionIds.MANA_REGENERATION, new Potion("mana_regeneration", new MobEffectInstance(ManaMobEffects.MANA_REGENERATION, 900)));
    public static final Holder<Potion> LONG_REGENERATION = register(ManaPotionIds.LONG_MANA_REGENERATION, new Potion("long_mana_regeneration", new MobEffectInstance(ManaMobEffects.MANA_REGENERATION, 1800)));
    public static final Holder<Potion> STRONG_REGENERATION = register(ManaPotionIds.STRONG_MANA_REGENERATION, new Potion("strong_mana_regeneration", new MobEffectInstance(ManaMobEffects.MANA_REGENERATION, 450, 1)));

    private static Holder<Potion> register(final ResourceKey<Potion> key, final Potion potion) {
        return Registry.registerForHolder(BuiltInRegistries.POTION, key, potion);
    }

    public static void init() {

    }
}
