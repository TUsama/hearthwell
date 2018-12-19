package wolforce.blocks.base;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface BlockEnergyProvider {

	boolean hasEnergy(World world, BlockPos pos, int energy);

	void consume(World world, BlockPos pos, int energy);
}