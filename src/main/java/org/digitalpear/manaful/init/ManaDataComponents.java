package org.digitalpear.manaful.init;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import org.digitalpear.manaful.Manaful;
import org.digitalpear.manaful.common.mana.ManaCost;

import java.util.function.UnaryOperator;

public class ManaDataComponents {

    public static final DataComponentType<ManaCost> MANA_COST = register("mana_cost", builder -> builder.persistent(ManaCost.CODEC));

    private static <T> DataComponentType<T> register(final String id, final UnaryOperator<DataComponentType.Builder<T>> builder) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, Manaful.id(id), (builder.apply(DataComponentType.builder())).build());
    }

    public static void init() {

    }
}
