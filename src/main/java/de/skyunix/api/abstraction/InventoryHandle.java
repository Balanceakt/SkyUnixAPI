package de.skyunix.api.abstraction;

import org.bukkit.inventory.ItemStack;

public record InventoryHandle(ItemStack[] inventory, ItemStack[] armor, ItemStack[] enderChest) {}
