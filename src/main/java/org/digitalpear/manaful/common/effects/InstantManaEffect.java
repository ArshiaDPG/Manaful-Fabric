package org.digitalpear.manaful.common.effects;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.InstantaneousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.digitalpear.manaful.init.ManaAttachments;
import org.jspecify.annotations.Nullable;

public class InstantManaEffect extends InstantaneousMobEffect {
    public InstantManaEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyInstantaneousEffect(ServerLevel level, @Nullable Entity source, @Nullable Entity owner, LivingEntity mob, int amplification, double scale) {
        super.applyInstantaneousEffect(level, source, owner, mob, amplification, scale);
        mob.getAttachedOrCreate(ManaAttachments.MANA).increaseMana(mob,20 * (amplification + 1));
    }
}
