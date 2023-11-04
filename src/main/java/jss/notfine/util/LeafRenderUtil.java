package jss.notfine.util;

import jss.notfine.core.Settings;
import jss.notfine.core.SettingsManager;
import jss.util.DirectionHelper;
import net.minecraft.block.Block;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;

public class LeafRenderUtil {

    public static boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        Block otherBlock = world.getBlock(x, y, z);
        if(otherBlock.isOpaqueCube()) {
            return false;
        }
        switch((int)Settings.MODE_LEAVES.getValue()) {
            case 1:
            case 2:
                return !(otherBlock instanceof ILeafBlock);
            case 3:
            case 4:
                if(otherBlock instanceof ILeafBlock) {
                    x -= Facing.offsetsXForSide[side];
                    y -= Facing.offsetsYForSide[side];
                    z -= Facing.offsetsZForSide[side];
                    int renderCheck = 0;
                    otherBlock = world.getBlock(x + 1, y, z);
                    if(((otherBlock instanceof ILeafBlock) || otherBlock.isOpaqueCube())) {
                        renderCheck++;
                    }
                    otherBlock = world.getBlock(x - 1, y, z);
                    if(((otherBlock instanceof ILeafBlock) || otherBlock.isOpaqueCube())) {
                        renderCheck++;
                    }
                    otherBlock = world.getBlock(x, y + 1, z);
                    if(((otherBlock instanceof ILeafBlock) || otherBlock.isOpaqueCube())) {
                        renderCheck++;
                    }
                    otherBlock = world.getBlock(x, y - 1, z);
                    if(((otherBlock instanceof ILeafBlock) || otherBlock.isOpaqueCube())) {
                        renderCheck++;
                    }
                    otherBlock = world.getBlock(x, y, z + 1);
                    if(((otherBlock instanceof ILeafBlock) || otherBlock.isOpaqueCube())) {
                        renderCheck++;
                    }
                    otherBlock = world.getBlock(x, y, z - 1);
                    if(((otherBlock instanceof ILeafBlock) || otherBlock.isOpaqueCube())) {
                        renderCheck++;
                    }
                    boolean renderSide = renderCheck == 6;
                    if(renderSide) {
                        x += 2 * Facing.offsetsXForSide[side];
                        y += 2 * Facing.offsetsYForSide[side];
                        z += 2 * Facing.offsetsZForSide[side];
                        otherBlock = world.getBlock(x, y, z);
                        if(((otherBlock instanceof ILeafBlock) || otherBlock.isOpaqueCube())) {
                            renderSide = false;
                        }
                        x -= Facing.offsetsXForSide[side];
                        y -= Facing.offsetsYForSide[side];
                        z -= Facing.offsetsZForSide[side];
                        int nextSide = DirectionHelper.relativeADirections[side];
                        otherBlock = world.getBlock(
                            x + Facing.offsetsXForSide[nextSide],
                            y + Facing.offsetsYForSide[nextSide],
                            z + Facing.offsetsZForSide[nextSide]
                        );
                        if(!((otherBlock instanceof ILeafBlock) || otherBlock.isOpaqueCube())) {
                            renderSide = true;
                        }
                        nextSide = DirectionHelper.relativeBDirections[side];
                        otherBlock = world.getBlock(
                            x + Facing.offsetsXForSide[nextSide],
                            y + Facing.offsetsYForSide[nextSide],
                            z + Facing.offsetsZForSide[nextSide]
                        );
                        if(!((otherBlock instanceof ILeafBlock) || otherBlock.isOpaqueCube())) {
                            renderSide = true;
                        }
                        nextSide = DirectionHelper.relativeCDirections[side];
                        otherBlock = world.getBlock(
                            x + Facing.offsetsXForSide[nextSide],
                            y + Facing.offsetsYForSide[nextSide],
                            z + Facing.offsetsZForSide[nextSide]
                        );
                        if(!((otherBlock instanceof ILeafBlock) || otherBlock.isOpaqueCube())) {
                            renderSide = true;
                        }
                        nextSide = DirectionHelper.relativeDDirections[side];
                        otherBlock = world.getBlock(
                            x + Facing.offsetsXForSide[nextSide],
                            y + Facing.offsetsYForSide[nextSide],
                            z + Facing.offsetsZForSide[nextSide]
                        );
                        if(!((otherBlock instanceof ILeafBlock) || otherBlock.isOpaqueCube())) {
                            renderSide = true;
                        }
                    }
                    return renderSide;
                }
            default:
                return !SettingsManager.leavesOpaque || !(otherBlock instanceof ILeafBlock);
        }
    }

}
