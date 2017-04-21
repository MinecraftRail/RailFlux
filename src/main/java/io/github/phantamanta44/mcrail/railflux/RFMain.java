package io.github.phantamanta44.mcrail.railflux;

import io.github.phantamanta44.mcrail.Rail;
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
        Rail.itemAdapters().register(EnergyItem.class, itemMapper());
        Rail.itemAdapters().register(IEnergyContainer.class, itemMapper());
        Rail.itemAdapters().register(IEnergyConsumer.class, itemMapper());
        Rail.itemAdapters().register(IEnergyProvider.class, itemMapper());
    }

    @SuppressWarnings("unchecked")
    private static <T> Function<ItemStack, T> itemMapper() {
        return stack -> (T)EnergyItem.wrap(stack);
    }

}
