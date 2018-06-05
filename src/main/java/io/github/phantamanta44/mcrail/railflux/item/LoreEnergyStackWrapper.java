package io.github.phantamanta44.mcrail.railflux.item;

import io.github.phantamanta44.mcrail.railflux.IEnergized;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoreEnergyStackWrapper implements IEnergized {

    public static final String ENERGY_PATTERN_STR = Pattern.quote(ChatColor.GRAY.toString()) +
            "Energy: " +
            Pattern.quote(ChatColor.AQUA.toString()) +
            "(\\d+) / (\\d+) RJ";
    public static final Pattern ENERGY_PATTERN = Pattern.compile(ENERGY_PATTERN_STR);

    public static LoreEnergyStackWrapper wrap(ItemStack stack, int index) {
        LoreEnergyStackWrapper item = new LoreEnergyStackWrapper(stack, index);
        return item.matcher() == null ? null : item;
    }

    protected final ItemStack stack;

    private final int index;

    protected LoreEnergyStackWrapper(ItemStack stack, int index) {
        this.stack = stack;
        this.index = index;
    }

    protected Matcher matcher() {
        Matcher m = ENERGY_PATTERN.matcher(getLoreLine());
        return m.matches() ? m : null;
    }

    protected String getLoreLine() {
        if (!stack.getItemMeta().hasLore())
            return "";
        List<String> lore = stack.getItemMeta().getLore();
        return lore.size() <= index ? "" : lore.get(index);
    }

    protected void update(int charge, int maxCharge) {
        ItemMeta meta = stack.getItemMeta();
        List<String> lore = new ArrayList<>(meta.getLore());
        lore.set(index, format(charge, maxCharge));
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
        update(charge + toTransfer, max);
        return toTransfer;
    }

    @Override
    public boolean canAcceptEnergy(int amount) {
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
    public boolean canProvideEnergy(int amount) {
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
