package org.digitalpear.manaful.init;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import org.digitalpear.manaful.Manaful;

public class ManaAttributes {
    public static final double DEFAULT_MANA_REGEN = 0.04;
    public static final double DEFAULT_MAX_MANA = 100.0;

    public static final Holder<Attribute> MAX_MANA = register("max_mana", (new RangedAttribute("attribute.name.max_mana", DEFAULT_MAX_MANA, 0.0F, 2048.0F)).setSyncable(true));
    public static final Holder<Attribute> MANA_REGENERATION = register("mana_regeneration", (new RangedAttribute("attribute.name.mana_regeneration", DEFAULT_MANA_REGEN, 0.0F, 2048.0F)).setSyncable(true));

    private static Holder<Attribute> register(final String name, final Attribute attribute) {
        return Registry.registerForHolder(BuiltInRegistries.ATTRIBUTE, Manaful.id(name), attribute);
    }

    public static void init() {}
}
