package me.iseal.powergems.tasks;

import me.iseal.powergems.Main;
import me.iseal.powergems.misc.Utils;
import me.iseal.powergems.managers.GemManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Random;

public class CheckMultipleEmeraldsTask extends BukkitRunnable {

    Utils ut = Main.getSingletonManager().utils;
    GemManager dm = Main.getSingletonManager().gemManager;
    Random random = new Random();

    @Override
    public void run() {
        ArrayList<ItemStack> gems = new ArrayList<>();
        Bukkit.getServer().getOnlinePlayers().forEach(player -> {
            if (ut.hasAtLeastTwo(player, Material.EMERALD)) {
                player.getInventory().all(Material.EMERALD)
                        .values()
                        .forEach(itemStack -> {
                            if (dm.isGem(itemStack)) {
                                itemStack.setAmount(1);
                                gems.add(itemStack);
                            }
                        });
                if (dm.isGem(player.getInventory().getItemInOffHand())) {
                    ItemStack itemStack = player.getInventory().getItemInOffHand();
                    itemStack.setAmount(1);
                    gems.add(itemStack);
                }
                if (gems.size() > 1) {
                    while (gems.size() > 1) {
                        ItemStack gem = gems.get(random.nextInt(gems.size()));
                        player.getInventory().remove(gem);
                        gems.remove(gem);
                    }
                }
            }
        });
    }
}