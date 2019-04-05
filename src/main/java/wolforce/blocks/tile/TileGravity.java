package wolforce.blocks.tile;

import java.util.HashSet;
import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import wolforce.HwellConfig;
import wolforce.Main;
import wolforce.blocks.BlockFormer;

public class TileGravity extends TileEntity implements ITickable {

	@Override
	public void update() {
		if (world.isBlockPowered(pos))
			return;

		HashSet<Item> filter = BlockFormer.isFiltering(world, pos);
		System.out.println(filter);

		int dist = world.getBlockState(pos).getBlock() == Main.gravity_block_mini ? //
				HwellConfig.machines.gravityBlockRangeMini : HwellConfig.machines.gravityBlockRange;
		List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class,
				new AxisAlignedBB(pos.add(-dist, -dist, -dist), pos.add(dist + 1, dist + 1, dist + 1)));
		for (EntityItem entityItem : items) {
			if (filter.isEmpty() || filter.contains(entityItem.getItem().getItem())) {
				entityItem.motionX += .01 * (pos.getX() + .5 - entityItem.posX);
				entityItem.motionY += .01 * (pos.getY() - entityItem.posY);
				entityItem.motionZ += .01 * (pos.getZ() + .5 - entityItem.posZ);
				// entityItem.setNoDespawn();
				// double dir = Math.atan2(entityItem.posZ - pos.getZ(), entityItem.posX -
				// pos.getX());
				// entityItem.posX += Math.cos(dir);
				// entityItem.posY += Math.sin(dir);
			}
		}
	}

}
