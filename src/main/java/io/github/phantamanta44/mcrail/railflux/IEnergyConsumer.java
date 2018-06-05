package io.github.phantamanta44.mcrail.railflux;

public interface IEnergyConsumer {

    int offerEnergy(int amount);

    boolean canAcceptEnergy(int amount);

}
