package io.github.phantamanta44.mcrail.railflux;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnergyItem implements IEnergyContainer, IEnergyProvider, IEnergyConsumer {

    public static final Pattern ENERGY_PATTERN = Pattern.compile(
            Pattern.quote(ChatColor.GRAY.toString()) +
                    "Energy: " +
                    Pattern.quote(ChatColor.AQUA.toString()) +
                    "(\\d+) / (\\d+) RJ");

    protected final ItemStack stack;

    public static EnergyItem wrap(ItemStack stack) {
        EnergyItem item = new EnergyItem(stack);
        return item.matcher() == null ? null : item;
    }

    public EnergyItem(ItemStack stack) {
        this.stack = stack;
    }

    protected Matcher matcher() {
        Matcher m = ENERGY_PATTERN.matcher(getLastLore());
        return m.matches() ? m : null;
    }

    protected String getLastLore() {
        if (!stack.getItemMeta().hasLore())
            return "";
        List<String> lore = stack.getItemMeta().getLore();
        return lore.get(lore.size() - 1);
    }

    protected void update(int charge, int maxCharge) {
        ItemMeta meta = stack.getItemMeta();
        List<String> lore = new ArrayList<>(meta.getLore());
        lore.set(lore.size() - 1, format(charge, maxCharge));
        meta.setLore(lore);
        stack.setItemMeta(meta);
    }

    @Override
    public int energyStored() {
        return Integer.parseInt(matcher().group(1));
    }

    @Override
    public int energyCapacity() {
        return Integer.parseInt(matcher().group(2));
    }

    @Override
    public int offerEnergy(int amount) {
        Matcher m = matcher();
        int charge = Integer.parseInt(m.group(1));
        int max = Integer.parseInt(m.group(2));
        int toTransfer = Math.min(amount, max - charge);
        update(charge + amount, max);
        return toTransfer;
    }

    @Override
    public boolean canAccept(int amount) {
        Matcher m = matcher();
        return Integer.parseInt(m.group(2)) - Integer.parseInt(m.group(1)) >= amount;
    }

    @Override
    public int requestEnergy(int amount) {
        Matcher m = matcher();
        int charge = Integer.parseInt(m.group(1));
        int toTransfer = Math.min(amount, charge);
        update(charge - toTransfer, Integer.parseInt(m.group(2)));
        return toTransfer;
    }

    @Override
    public boolean canProvide(int amount) {
        return Integer.parseInt(matcher().group(1)) >= amount;
    }

    public static void energize(ItemStack stack, int maxCharge, int charge) {
        ItemMeta meta = stack.getItemMeta();
        String str = format(charge, maxCharge);
        if (meta.hasLore()) {
            List<String> lore = new LinkedList<>(meta.getLore());
            lore.add(str);
            meta.setLore(lore);
        } else {
            meta.setLore(Collections.singletonList(str));
        }
        stack.setItemMeta(meta);
    }

    public static void energize(ItemStack stack, int maxCharge) {
        energize(stack, maxCharge, 0);
    }

    public static String format(int charge, int maxCharge) {
        return String.format("%sEnergy: %s%d / %d RJ", ChatColor.GRAY, ChatColor.AQUA, charge, maxCharge);
    }

}
