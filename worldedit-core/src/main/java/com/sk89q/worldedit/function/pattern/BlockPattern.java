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

package com.sk89q.worldedit.function.pattern;

import static com.google.common.base.Preconditions.checkNotNull;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.block.BaseBlock;
import com.sk89q.worldedit.world.block.BlockStateHolder;

/**
 * A pattern that returns the same {@link BaseBlock} each time.
 *
 * @deprecated all BlockStateHolders can be used directly as a pattern
 */
@Deprecated
public class BlockPattern extends AbstractPattern {

    private BaseBlock block;

    /**
     * Create a new pattern with the given block.
     *
     * @param block the block
     */
    public BlockPattern(BlockStateHolder<?> block) {
        setBlock(block);
    }

    /**
     * Get the block.
     *
     * @return the block that is always returned
     */
    public BaseBlock getBlock() {
        return block;
    }

    /**
     * Set the block that is returned.
     *
     * @param block the block
     */
    public void setBlock(BlockStateHolder<?> block) {
        checkNotNull(block);
        this.block = block.toBaseBlock();
    }

    @Override
    public BaseBlock applyBlock(BlockVector3 position) {
        return block;
    }

}
