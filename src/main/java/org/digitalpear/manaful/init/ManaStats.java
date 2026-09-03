package org.digitalpear.manaful.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;
import org.digitalpear.manaful.Manaful;

public class ManaStats {
    public static final Identifier MANA_USED = register("mana_used", StatFormatter.DIVIDE_BY_TEN);
    private static Identifier register(String name, StatFormatter formatter) {
        Identifier id = Manaful.id(name);
        Registry.register(BuiltInRegistries.CUSTOM_STAT, name, id);
        Stats.CUSTOM.get(id, formatter);
        return id;
    }

    public static void init() {

    }
}
