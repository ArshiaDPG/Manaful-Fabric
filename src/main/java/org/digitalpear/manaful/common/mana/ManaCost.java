package org.digitalpear.manaful.common.mana;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.util.StringRepresentable;
import org.digitalpear.manaful.Manaful;


public record ManaCost(double amount, Operation operation) {

    public ManaCost(double amount) {
        this(amount, Operation.ADD);
    }

    public static final Codec<ManaCost> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.DOUBLE.fieldOf("amount").forGetter(ManaCost::amount),
            ManaCost.Operation.CODEC.fieldOf("operation").orElse(Operation.ADD).forGetter(ManaCost::operation)
    ).apply(instance, ManaCost::new));

    public Component tooltip() {
        Component costTooltip = Component.literal("%.1f".formatted((float) amount));
        if (operation == Operation.ADD_PERCENT) {
            costTooltip = Component.literal("%.1f".formatted((float) amount) + "%");
        }
        return CommonComponents.optionNameValue(Component.translatable(Manaful.MOD_ID + ".mana.description"), costTooltip).withColor(TextColor.AQUA);
    }

    public enum Operation implements StringRepresentable {
        ADD("add"),
        ADD_PERCENT("add_percent");

        public static final Codec<ManaCost.Operation> CODEC = StringRepresentable.fromEnum(ManaCost.Operation::values);

        private final String type;

        Operation(String type) {
            this.type = type;
        }

        @Override
        public String getSerializedName() {
            return type;
        }
    }
}
