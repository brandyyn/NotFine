package jss.notfine.mixins.late.twilightforest.leaves;

import jss.notfine.core.Settings;
import jss.notfine.core.SettingsManager;
import jss.notfine.util.ILeafBlock;
import net.minecraft.block.BlockLeaves;
import net.minecraft.init.Blocks;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import twilightforest.block.BlockTFLeaves;

@Mixin(value = BlockTFLeaves.class)
public abstract class MixinBlockTFLeaves extends BlockLeaves {

    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        int renderMode = (int)Settings.MODE_LEAVES.getValue();
        int maskedMeta = world.getBlockMetadata(x, y, z) & 3;
        switch(renderMode) {
            case -1:
                renderMode = SettingsManager.leavesOpaque ? 1 : 0;
                break;
            case 4:
                renderMode = world.getBlock(
                    x + Facing.offsetsXForSide[side],
                    y + Facing.offsetsYForSide[side],
                    z + Facing.offsetsZForSide[side]
                ) instanceof ILeafBlock ? 1 : 0;
                break;
            default:
                renderMode = renderMode > 1 ? 0 : renderMode;
                break;
        }
        maskedMeta = maskedMeta > 1 ? 0 : maskedMeta;
        return Blocks.leaves.field_150129_M[renderMode][maskedMeta];
    }

}
