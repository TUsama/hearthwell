package wolforce.hwell.base;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wolforce.hwell.Main;

public class MyGlass extends MyBlock {

	private static boolean ignoreSimilarity = false;

	public MyGlass() {
		super(Material.GLASS);
		// super(name, new Material(MapColor.AIR) {
		//
		// @Override
		// public boolean isSolid() {
		// return false;
		// }
		//
		// @Override
		// public EnumPushReaction getMobilityFlag() {
		// return EnumPushReaction.IGNORE;
		// }
		// });

		setSoundType(SoundType.GLASS);
		setHardness(0.05f);
	}

	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
		return layer == BlockRenderLayer.TRANSLUCENT;
	}

	//
	//// @SideOnly(Side.CLIENT)
	// @Override
	// public boolean isCollidable() {
	//
	// return Minecraft.getMinecraft().player.isSneaking();
	// }

	// @Override
	// public boolean isSideSolid(IBlockState base_state, IBlockAccess world,
	// BlockPos pos, EnumFacing side) {
	// return side == EnumFacing.UP;
	// }

	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@SuppressWarnings("deprecation")
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos,
			EnumFacing side) {
		IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
		Block block = iblockstate.getBlock();

		if (this == Main.crystal_block) {
			if (blockState != iblockstate)
				return true;
			if (block == this)
				return false;
		}

		return !ignoreSimilarity && block == this ? false
				: super.shouldSideBeRendered(blockState, blockAccess, pos, side);
	}
}
