package org.digitalpear.manaful.common;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import org.digitalpear.manaful.Manaful;

public record ManaCost(int cost) {

    public static final Codec<ManaCost> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("cost").forGetter(ManaCost::cost)
    ).apply(instance, ManaCost::new));

    public Component tooltip(){
        return CommonComponents.optionNameValue(Component.translatable(Manaful.MOD_ID + ".mana.description"), Component.literal("%.1f".formatted(((float)cost)/10))).withColor(TextColor.AQUA);
    }
}
