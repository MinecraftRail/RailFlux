package io.github.phantamanta44.mcrail.railflux.item;

import io.github.phantamanta44.mcrail.railflux.IEnergized;
import org.bukkit.inventory.ItemStack;

public class BehaviourDelegateEnergyStackWrapper implements IEnergized {

    private final ItemStack stack;
    private final IEnergyItemBehaviour item;

    public BehaviourDelegateEnergyStackWrapper(IEnergyItemBehaviour item, ItemStack stack) {
        this.item = item;
        this.stack = stack;
    }

    @Override
    public int offerEnergy(int amount) {
        return item.offerEnergy(amount, stack);
    }

    @Override
    public boolean canAcceptEnergy(int amount) {
        return item.canAccept(amount, stack);
    }

    @Override
    public int requestEnergy(int amount) {
        return item.requestEnergy(amount, stack);
    }

    @Override
    public boolean canProvideEnergy(int amount) {
        return item.canProvide(amount, stack);
    }

    @Override
    public int energyStored() {
        return item.energyStored(stack);
    }

    @Override
    public int energyCapacity() {
        return item.energyCapacity(stack);
    }

}
