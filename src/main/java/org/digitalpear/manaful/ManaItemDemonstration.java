package org.digitalpear.manaful;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.event.player.ItemEvents;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.digitalpear.manaful.common.mana.ManaCost;
import org.digitalpear.manaful.common.mana.ManaSource;
import org.digitalpear.manaful.init.ManaAttachments;
import org.digitalpear.manaful.init.ManaDataComponents;

public class ManaItemDemonstration {

    protected static void init(){

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(event -> {
            ItemStack itemStack = new ItemStack(Items.RESIN_BRICK);
            ItemStack itemStack2 = new ItemStack(Items.BRICK);

            //Costs a flat 25 mana.
            itemStack.set(ManaDataComponents.MANA_COST, new ManaCost(25));

            //Costs 30% of the user's total mana capacity.
            itemStack2.set(ManaDataComponents.MANA_COST, new ManaCost(30, ManaCost.Operation.ADD_PERCENT));

            event.accept(itemStack);
            event.accept(itemStack2);
        });

        ItemEvents.USE.register((level, player, interactionHand) -> {

            ItemStack itemStack = player.getItemInHand(interactionHand);
            if (itemStack.is(Items.RESIN_BRICK) || itemStack.is(Items.BRICK)) {

                //Extract the player's mana source.
                ManaSource manaSource = player.getAttachedOrCreate(ManaAttachments.MANA);

                //Check if the given stack has the mana component, then check if the player has enough to use it.
                boolean hasEnough = manaSource.hasEnoughMana(itemStack);

                if (hasEnough){
                    BlockPos pos = level.getRespawnData().pos();
                    player.setPos(new Vec3(pos.getX(), pos.getY(), pos.getZ()));

                    //Decrease the player's mana according to the parameters of the component in the item.
                    manaSource.decreaseMana(player, itemStack);

                    //Directly using the component.
                    manaSource.decreaseMana(player, itemStack.get(ManaDataComponents.MANA_COST));

                    //Directly inputting the amount.
                    manaSource.decreaseMana(player, 12);

                    return InteractionResult.SUCCESS;
                }
            }

            return null;
        });
    }
    protected static void modTests(){
        ItemEvents.USE.register((level, player, interactionHand) -> {
            ItemStack stack = player.getItemInHand(interactionHand);
            if (stack.is(ItemTags.SWORDS) && player.getAttached(ManaAttachments.MANA).hasEnoughMana(stack)) {
                level.explode(player, player.getX(), player.getY(), player.getZ(), 1, Level.ExplosionInteraction.NONE);
                player.getAttached(ManaAttachments.MANA).decreaseMana(player, stack.get(ManaDataComponents.MANA_COST));
                return InteractionResult.SUCCESS;
            }
            return null;
        });

        DefaultItemComponentEvents.MODIFY.register(context -> {
            context.modify(item -> item.getDefaultInstance().is(ItemTags.SWORDS), (builder, _) -> builder.set(ManaDataComponents.MANA_COST, new ManaCost(12, ManaCost.Operation.ADD_PERCENT)));
            context.modify(Items.WOODEN_SWORD, builder -> builder.set(ManaDataComponents.MANA_COST, new ManaCost(20)));
        });
    }
}
