package io.github.phantamanta44.mcrail.railflux;

public interface IEnergyProvider {

    int requestEnergy(int amount);

    boolean canProvide(int amount);

}
