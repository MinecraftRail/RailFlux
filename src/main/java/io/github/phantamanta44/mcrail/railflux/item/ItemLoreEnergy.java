package io.github.phantamanta44.mcrail.railflux.item;

import io.github.phantamanta44.mcrail.item.characteristic.CharLore;
import io.github.phantamanta44.mcrail.item.characteristic.IItemCharacteristic;
import io.github.phantamanta44.mcrail.railflux.IEnergized;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.LinkedList;

public abstract class ItemLoreEnergy implements IEnergyItemBehaviour {

    protected final Collection<IItemCharacteristic> characteristics;

    private final int loreLine;

    public ItemLoreEnergy(int loreLine, int maxCharge, int charge) {
        this.loreLine = loreLine;
        this.characteristics = new LinkedList<>();
        this.characteristics.add(new CharLore(loreLine,
                LoreEnergyStackWrapper.format(charge, maxCharge),
                LoreEnergyStackWrapper.ENERGY_PATTERN_STR));
    }

    public ItemLoreEnergy(int loreLine, int maxCharge) {
        this(loreLine, maxCharge, 0);
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
        return wrap(stack).canAcceptEnergy(amount);
    }

    @Override
    public int requestEnergy(int amount, ItemStack stack) {
        return wrap(stack).requestEnergy(amount);
    }

    @Override
    public boolean canProvide(int amount, ItemStack stack) {
        return wrap(stack).canProvideEnergy(amount);
    }

    @Override
    public IEnergized wrap(ItemStack stack) {
        return LoreEnergyStackWrapper.wrap(stack, loreLine);
    }

}
