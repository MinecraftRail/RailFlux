package io.github.phantamanta44.mcrail.railflux.item;

import io.github.phantamanta44.mcrail.item.characteristic.CharLastLore;
import io.github.phantamanta44.mcrail.item.characteristic.IItemCharacteristic;
import io.github.phantamanta44.mcrail.railflux.IEnergized;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.LinkedList;

public abstract class ItemLastLoreEnergy implements IEnergyItemBehaviour {

    protected final Collection<IItemCharacteristic> characteristics;

    public ItemLastLoreEnergy(int maxCharge, int charge) {
        this.characteristics = new LinkedList<>();
        this.characteristics.add(new CharLastLore(
                LastLoreEnergyStackWrapper.format(charge, maxCharge),
                LastLoreEnergyStackWrapper.ENERGY_PATTERN_STR));
    }

    public ItemLastLoreEnergy(int maxCharge) {
        this(maxCharge, 0);
    }

    @Override
    public Collection<IItemCharacteristic> characteristics() {
        return characteristics;
    }

    @Override
    public int energyStored(ItemStack stack) {
        return wrap(stack).energyStored();
    }

    @Override
    public int energyCapacity(ItemStack stack) {
        return wrap(stack).energyCapacity();
    }

    @Override
    public int offerEnergy(int amount, ItemStack stack) {
        return wrap(stack).offerEnergy(amount);
    }

    @Override
    public boolean canAccept(int amount, ItemStack stack) {
        return wrap(stack).canAccept(amount);
    }

    @Override
    public int requestEnergy(int amount, ItemStack stack) {
        return wrap(stack).requestEnergy(amount);
    }

    @Override
    public boolean canProvide(int amount, ItemStack stack) {
        return wrap(stack).canProvide(amount);
    }

    @Override
    public IEnergized wrap(ItemStack stack) {
        return LastLoreEnergyStackWrapper.wrap(stack);
    }

}
