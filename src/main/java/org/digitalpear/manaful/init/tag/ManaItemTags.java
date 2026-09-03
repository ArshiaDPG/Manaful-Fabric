package org.digitalpear.manaful.init.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.digitalpear.manaful.Manaful;

public class ManaItemTags {

    public static final TagKey<Item> REQUIRES_MANA = bind("requires_mana");

    private static TagKey<Item> bind(final String name) {
        return TagKey.create(Registries.ITEM, Manaful.id(name));
    }
}
