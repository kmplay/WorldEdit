/*
 * WorldEdit, a Minecraft world manipulation toolkit
 * Copyright (C) sk89q <http://www.sk89q.com>
 * Copyright (C) WorldEdit team and contributors
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.sk89q.worldedit.bukkit.adapter;

import com.sk89q.jnbt.CompoundTag;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.blocks.BaseItem;
import com.sk89q.worldedit.blocks.BaseItemStack;
import com.sk89q.worldedit.entity.BaseEntity;
import com.sk89q.worldedit.internal.wna.WorldNativeAccess;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.registry.state.Property;
import com.sk89q.worldedit.util.Direction;
import com.sk89q.worldedit.util.SideEffect;
import com.sk89q.worldedit.util.formatting.text.Component;
import com.sk89q.worldedit.world.DataFixer;
import com.sk89q.worldedit.world.RegenOptions;
import com.sk89q.worldedit.world.block.BaseBlock;
import com.sk89q.worldedit.world.block.BlockState;
import com.sk89q.worldedit.world.block.BlockType;
import com.sk89q.worldedit.world.item.ItemType;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Set;

/**
 * An interface for adapters of various Bukkit implementations.
 */
public interface BukkitImplAdapter {

    /**
     * Get the Minecraft data version for the current world data.
     *
     * @return the data version
     */
    int getDataVersion();

    /**
     * Get a data fixer, or null if not supported
     *
     * @return the data fixer
     */
    @Nullable
    DataFixer getDataFixer();

    /**
     * @return {@code true} if {@link #tickWatchdog()} is implemented
     */
    default boolean supportsWatchdog() {
        return false;
    }

    /**
     * Tick the server watchdog, if possible.
     */
    default void tickWatchdog() {
    }

    /**
     * Get the block at the given location.
     *
     * @param location the location
     * @return the block
     */
    BaseBlock getBlock(Location location);

    /**
     * Create a {@link WorldNativeAccess} for the given world reference.
     *
     * @param world the world reference
     * @return the native access object
     */
    WorldNativeAccess<?, ?, ?> createWorldNativeAccess(World world);

    /**
     * Get the state for the given entity.
     *
     * @param entity the entity
     * @return the state, or null
     */
    @Nullable
    BaseEntity getEntity(Entity entity);

    /**
     * Create the given entity.
     *
     * @param location the location
     * @param state the state
     * @return the created entity or null
     */
    @Nullable
    Entity createEntity(Location location, BaseEntity state);

    /**
     * Gets the name for the given block.
     *
     * @param blockType the block
     * @return The name
     */
    Component getRichBlockName(BlockType blockType);

    /**
     * Gets the name for the given item.
     *
     * @param itemType the item
     * @return The name
     */
    Component getRichItemName(ItemType itemType);

    /**
     * Gets the name for the given item stack.
     *
     * @param itemStack the item stack
     * @return The name
     */
    Component getRichItemName(BaseItemStack itemStack);

    /**
     * Get a map of {@code string -> property}.
     *
     * @param blockType The block type
     * @return The properties map
     */
    Map<String, ? extends Property<?>> getProperties(BlockType blockType);

    /**
     * Send the given NBT data to the player.
     *
     * @param player The player
     * @param pos The position
     * @param nbtData The NBT Data
     */
    void sendFakeNBT(Player player, BlockVector3 pos, CompoundTag nbtData);

    /**
     * Make the client think it has operator status.
     * This does not give them any operator capabilities.
     *
     * @param player The player
     */
    void sendFakeOP(Player player);

    /**
     * Simulates a player using an item.
     *
     * @param world the world
     * @param position the location
     * @param item the item to be used
     * @param face the direction in which to "face" when using the item
     * @return whether the usage was successful
     */
    default boolean simulateItemUse(World world, BlockVector3 position, BaseItem item, Direction face) {
        return false;
    }

    /**
     * Create a Bukkit ItemStack with NBT, if available.
     *
     * @param item the WorldEdit BaseItemStack to adapt
     * @return the Bukkit ItemStack
     */
    ItemStack adapt(BaseItemStack item);

    /**
     * Create a WorldEdit ItemStack with NBT, if available.
     *
     * @param itemStack the Bukkit ItemStack to adapt
     * @return the WorldEdit BaseItemStack
     */
    BaseItemStack adapt(ItemStack itemStack);

    /**
     * Get the {@link SideEffect}s that this adapter supports.
     *
     * @return The side effects that are supported
     */
    Set<SideEffect> getSupportedSideEffects();

    default OptionalInt getInternalBlockStateId(BlockData data) {
        return OptionalInt.empty();
    }

    /**
     * Retrieve the internal ID for a given state, if possible.
     *
     * @param state The block state
     * @return the internal ID of the state
     */
    default OptionalInt getInternalBlockStateId(BlockState state) {
        return OptionalInt.empty();
    }

    /**
     * Regenerate a region in the given world, so it appears "as new".
     * @param world the world to regen in
     * @param region the region to regen
     * @param session the session to use for setting blocks
     * @param options the regeneration options
     * @return true on success, false on failure
     */
    default boolean regenerate(World world, Region region, EditSession session, RegenOptions options) {
        throw new UnsupportedOperationException("This adapter does not support regeneration.");
    }
}
