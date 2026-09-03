package org.digitalpear.manaful.common.mana;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.digitalpear.manaful.init.ManaAttributes;
import org.digitalpear.manaful.init.ManaDataComponents;
import org.digitalpear.manaful.init.ManaStats;

public class ManaSource {
    public static final ManaSource INSTANCE = new ManaSource(ManaAttributes.DEFAULT_MAX_MANA);

    public static final Codec<ManaSource> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.DOUBLE.fieldOf("mana").forGetter(ManaSource::getMana)
    ).apply(instance, ManaSource::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ManaSource> STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistries(CODEC);


    private double mana;

    public ManaSource(double mana) {
        this.mana = mana;
    }

    public double getMana() {
        return mana;
    }

    private boolean changeMana(LivingEntity entity, double amount) {
        if (entity.hasInfiniteMaterials()){
            return true;
        }
        if (amount == 0) {
            return false;
        }
        mana = Math.clamp(mana + amount, 0, entity.getAttribute(ManaAttributes.MAX_MANA).getValue());
        if (entity instanceof ServerPlayer serverPlayer) {
            ServerPlayNetworking.send(serverPlayer, new ManaChangePacket(mana));
        }
        return true;
    }
    public void increaseMana(LivingEntity entity, double amount) {
        changeMana(entity, amount);
    }
    public void depleteMana(LivingEntity entity, double amount) {
        if (changeMana(entity, -amount) && entity instanceof ServerPlayer player){
            player.awardStat(ManaStats.MANA_USED, (int) (amount * 10));
        }
    }

    public double getAmount(LivingEntity entity, ManaCost costType) {
        double amount = costType.amount();
        if (costType.operation() == ManaCost.Operation.MAX_PERCENT) {
            amount = entity.getAttribute(ManaAttributes.MAX_MANA).getValue() * (costType.amount() / 100);
        }
        return amount;
    }

    public boolean hasEnoughMana(ItemStack stack) {
        if (!stack.has(ManaDataComponents.MANA_COST)){
            return false;
        }
        return mana >= stack.get(ManaDataComponents.MANA_COST).amount();
    }
}
