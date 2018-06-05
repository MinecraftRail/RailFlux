package io.github.phantamanta44.mcrail.railflux;

import io.github.phantamanta44.mcrail.Rail;
import io.github.phantamanta44.mcrail.item.IItemBehaviour;
import io.github.phantamanta44.mcrail.module.IRailModule;
import io.github.phantamanta44.mcrail.railflux.item.IEnergyItemBehaviour;
import io.github.phantamanta44.mcrail.railflux.item.LoreEnergyStackWrapper;
import io.github.phantamanta44.mcrail.tile.RailTile;
import io.github.phantamanta44.mcrail.util.TileUtils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Function;

public class RFMain extends JavaPlugin implements IRailModule {

    @Override
    public void phaseSetup() {
        Rail.blockAdapters().register(IEnergyContainer.class, block -> {
            RailTile tile = TileUtils.getAt(block);
            return (tile instanceof IEnergyContainer) ? (IEnergyContainer)tile : null;
        });
        Rail.blockAdapters().register(IEnergyConsumer.class, block -> {
            RailTile tile = TileUtils.getAt(block);
            return (tile instanceof IEnergyConsumer) ? (IEnergyConsumer)tile : null;
        });
        Rail.blockAdapters().register(IEnergyProvider.class, block -> {
            RailTile tile = TileUtils.getAt(block);
            return (tile instanceof IEnergyProvider) ? (IEnergyProvider)tile : null;
        });
        Rail.blockAdapters().register(IEnergized.class, block -> {
            RailTile tile = TileUtils.getAt(block);
            return (tile instanceof IEnergized) ? (IEnergized)tile : null;
        });
        Rail.itemAdapters().register(IEnergyContainer.class, itemMapper(IEnergyContainer.class));
        Rail.itemAdapters().register(IEnergyConsumer.class, itemMapper(IEnergyConsumer.class));
        Rail.itemAdapters().register(IEnergyProvider.class, itemMapper(IEnergyProvider.class));
        Rail.itemAdapters().register(IEnergized.class, itemMapper(IEnergized.class));
    }

    @SuppressWarnings("unchecked")
    private static <T> Function<ItemStack, T> itemMapper(Class<T> type) {
        return stack -> {
            IItemBehaviour item = Rail.itemRegistry().get(stack);
            return (T)(item instanceof IEnergyItemBehaviour
                    ? ((IEnergyItemBehaviour)item).wrap(stack)
                    : LoreEnergyStackWrapper.wrap(stack, 0));
        };
    }

}
