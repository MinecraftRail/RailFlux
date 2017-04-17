package io.github.phantamanta44.mcrail.railtech;

public interface IEnergyConsumer {

    int offerEnergy(int amount);

    boolean canAccept(int amount);

}
