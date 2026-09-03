package org.digitalpear.manaful.init;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.digitalpear.manaful.Manaful;
import org.digitalpear.manaful.common.effects.InstantManaEffect;

public class ManaMobEffects {

    public static final Holder<MobEffect> INSTANT_MANA = register("instant_mana", new InstantManaEffect(MobEffectCategory.BENEFICIAL, 7842542));
    public static final Holder<MobEffect> MANA_REGENERATION = register("mana_regeneration", new MobEffect(MobEffectCategory.BENEFICIAL, 4482500)
            .addAttributeModifier(ManaAttributes.MANA_REGENERATION, Manaful.id("mana_regen_effect"), 2, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
    );


    private static Holder<MobEffect> register(final String name, final MobEffect mobEffect) {
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Manaful.id(name), mobEffect);
    }

    public static void init() {

    }
}
