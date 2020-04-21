package wolforce.hwell.blocks.tile;

import java.util.HashMap;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import wolforce.hwell.Main;
import wolforce.hwell.blocks.BlockHeatFurnace;
import wolforce.hwell.blocks.BlockTube;
import wolforce.mechanics.Util;
import wolforce.mechanics.Util.BlockWithMeta;

public class TileHeatFurnace extends TileEntity implements ITickable {

	String[][][] multiblock = new String[][][] { //
			{ //
					{ null, "EB", "EB", "EB", null }, //
					{ null, "PB", "L0", "PB", null }, //
					{ "HB", "L1", "L0", "L1", "HB" }, //
					{ null, "PB", "L0", "PB", null }, //
					{ null, "PB", "L0", "PB", null }, //
					{ "HB", "L1", "L0", "L1", "HB" }, //
					{ null, "PB", "L0", "PB", null }, //
					{ null, "EB", "EB", "EB", null } //
			}, { //
					{ null, "EB", "AR", "EB", null }, //
					{ null, "PB", "FT", "PB", null }, //
					{ "HB", "HB", "FT", "HB", "HB" }, //
					{ null, "PB", "FT", "PB", null }, //
					{ null, "PB", "FT", "PB", null }, //
					{ "HB", "HB", "FT", "HB", "HB" }, //
					{ null, "PB", "00", "PB", null }, //
					{ null, "EB", "AR", "EB", null } //
			}, { //
					{ null, "EB", "EB", "EB", null }, //
					{ null, "PB", "W0", "PB", null }, //
					{ null, "PB", "W0", "PB", null }, //
					{ null, "PB", "W0", "PB", null }, //
					{ null, "PB", "W0", "PB", null }, //
					{ null, "PB", "W0", "PB", null }, //
					{ null, "PB", "W0", "PB", null }, //
					{ null, "EB", "EB", "EB", null } //
			} };

	@Override
	public void update() {
		if (world.isRemote)
			return;

		IBlockState block = world.getBlockState(pos);
		EnumFacing facing = block.getValue(BlockHeatFurnace.FACING);

		List<EntityItem> entities = world.getEntitiesWithinAABB(EntityItem.class,
				new AxisAlignedBB(new BlockPos(pos).offset(facing.getOpposite())));
		if (entities.size() > 0) {

			HashMap<String, BlockWithMeta> table = new HashMap<>();
			table.put("PB", new BlockWithMeta(Main.protection_block));
			table.put("EB", new BlockWithMeta(Main.heat_block));
			table.put("HB", new BlockWithMeta(Main.heat_block));
			EnumFacing.Axis axis = facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH ? EnumFacing.Axis.Z
					: EnumFacing.Axis.X;
			table.put("FT", new BlockWithMeta(Main.furnace_tube, Main.furnace_tube
					.getMetaFromState(Main.furnace_tube.getDefaultState().withProperty(BlockTube.AXIS, axis))));
			table.put("W0", new BlockWithMeta(Blocks.WATER, 0));
			table.put("L0", new BlockWithMeta(Blocks.LAVA, 0));
			table.put("L1", new BlockWithMeta(Blocks.LAVA));
			table.put("AR", new BlockWithMeta(Blocks.AIR));

			if (Util.isMultiblockBuilt(world, pos, facing, multiblock, table)) {
				EntityItem entityItem = entities.get(0);
				ItemStack result = FurnaceRecipes.instance().getSmeltingResult(entityItem.getItem().copy()).copy();
				if (Util.isValid(result)) {

					// if (!BlockEnergyConsumer.tryConsume(world, pos,
					// ((BlockEnergyConsumer) Main.heat_furnace).getEnergyConsumption())) {
					// return;
					// }

					BlockPos newpos = pos.offset(facing, 6);
					ItemStack itemToSpawn = new ItemStack(result.getItem(),
							result.getCount() * entityItem.getItem().getCount(), result.getMetadata());
					Util.spawnItem(world, newpos, itemToSpawn, facing);
					entityItem.setDead();
					if (Math.random() < .000244 * entityItem.getItem().getCount() * entityItem.getItem().getCount())
						deleteSomeWater(pos, facing);
				}
			}
		}
	}

	private void deleteSomeWater(BlockPos center, EnumFacing facing) {
		BlockPos pos = center.up().offset(facing, (int) (Math.random() * 5));
		world.setBlockToAir(pos);
		world.setBlockToAir(pos.offset(facing));
	}

}
