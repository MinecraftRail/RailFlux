package io.github.phantamanta44.mcrail.railtech;

public interface IEnergyProvider {

    int requestEnergy(int amount);

    boolean canProvide(int amount);

}
