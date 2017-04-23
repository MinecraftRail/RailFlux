package io.github.phantamanta44.mcrail.railflux;

import io.github.phantamanta44.mcrail.Rail;
import io.github.phantamanta44.mcrail.item.IItemBehaviour;
import io.github.phantamanta44.mcrail.railflux.item.IEnergyItemBehaviour;
import io.github.phantamanta44.mcrail.railflux.item.LastLoreEnergyStackWrapper;
import io.github.phantamanta44.mcrail.sign.SignEntity;
import io.github.phantamanta44.mcrail.util.SignUtils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Function;

public class RFMain extends JavaPlugin {

    @Override
    public void onEnable() {
        Rail.blockAdapters().register(IEnergyContainer.class, block -> {
            SignEntity se = SignUtils.getAt(block);
            return (se != null && se instanceof IEnergyContainer) ? (IEnergyContainer)se : null;
        });
        Rail.blockAdapters().register(IEnergyConsumer.class, block -> {
            SignEntity se = SignUtils.getAt(block);
            return (se != null && se instanceof IEnergyConsumer) ? (IEnergyConsumer)se : null;
        });
        Rail.blockAdapters().register(IEnergyProvider.class, block -> {
            SignEntity se = SignUtils.getAt(block);
            return (se != null && se instanceof IEnergyProvider) ? (IEnergyProvider)se : null;
        });
        Rail.blockAdapters().register(IEnergized.class, block -> {
            SignEntity se = SignUtils.getAt(block);
            return (se != null && se instanceof IEnergized) ? (IEnergized)se : null;
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
            return (T)(item != null && item instanceof IEnergyItemBehaviour
                    ? ((IEnergyItemBehaviour)item).wrap(stack)
                    : LastLoreEnergyStackWrapper.wrap(stack));
        };
    }

}
