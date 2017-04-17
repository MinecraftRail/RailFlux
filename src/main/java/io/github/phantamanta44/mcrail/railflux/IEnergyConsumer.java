package io.github.phantamanta44.mcrail.railflux;

public interface IEnergyConsumer {

    int offerEnergy(int amount);

    boolean canAccept(int amount);

}
