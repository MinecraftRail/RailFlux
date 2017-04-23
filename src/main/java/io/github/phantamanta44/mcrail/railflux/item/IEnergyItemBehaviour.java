package io.github.phantamanta44.mcrail.railflux.item;

import io.github.phantamanta44.mcrail.item.IItemBehaviour;
import io.github.phantamanta44.mcrail.railflux.IEnergized;
import org.bukkit.inventory.ItemStack;

public interface IEnergyItemBehaviour extends IItemBehaviour {

    int energyStored(ItemStack stack);

    int energyCapacity(ItemStack stack);

    int offerEnergy(int amount, ItemStack stack);

    boolean canAccept(int amount, ItemStack stack);

    int requestEnergy(int amount, ItemStack stack);

    boolean canProvide(int amount, ItemStack stack);

    default IEnergized wrap(ItemStack stack) {
        return new BehaviourDelegateEnergyStackWrapper(this, stack);
    }

}
