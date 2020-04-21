package wolforce.hwell.base;

import java.util.List;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import wolforce.hwell.Hwell;
import wolforce.hwell.entities.EntityPower;

public interface BlockEnergyConsumer extends BlockWithDescription {

	public static final int MAX_ENERGY_DISTANCE = 10;

	int getEnergyConsumption();

	static boolean tryConsume(World world, BlockPos pos, int energy) {

		Vec3d min = new Vec3d(pos.getX() - MAX_ENERGY_DISTANCE, pos.getY() - MAX_ENERGY_DISTANCE,
				pos.getZ() - MAX_ENERGY_DISTANCE);
		Vec3d max = new Vec3d(pos.getX() + MAX_ENERGY_DISTANCE, pos.getY() + MAX_ENERGY_DISTANCE,
				pos.getZ() + MAX_ENERGY_DISTANCE);
		List<EntityPower> entities = world.getEntitiesWithinAABB(EntityPower.class,
				new AxisAlignedBB(min.x, min.y, min.z, max.x, max.y, max.z));
		// System.out.println("SEARCHING FOR ENERGY PROVIDERS");
		// System.out.println(world.isRemote + "->" + entities.size());
		for (EntityPower e : entities) {
			// System.out.println("FOUND ENERGY PROVIDER " + e.getName());
			float range = (float) new Vec3d(e.posX, e.posY, e.posZ).distanceTo(//
					new Vec3d(pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5));
			if (e.hasEnergy(energy, range)) {
				return e.tryConsume(energy, range);
			}
		}

		// BlockPos pos2 = null;
		// {// FIND ENERGY
		// double distsq = 0;
		// for (int x = -MAX_ENERGY_DISTANCE; x <= MAX_ENERGY_DISTANCE; x++) {
		// for (int y = -MAX_ENERGY_DISTANCE; y <= MAX_ENERGY_DISTANCE; y++) {
		// for (int z = -MAX_ENERGY_DISTANCE; z <= MAX_ENERGY_DISTANCE; z++) {
		// BlockPos posTemp = pos.add(x, y, z);
		// Block blockat = world.getBlockState(posTemp).getBlock();
		// if (blockat instanceof BlockEnergyProvider && //
		// ((BlockEnergyProvider) blockat).hasEnergy(world, posTemp, energy)) {
		// double distsqTemp = posTemp.distanceSq(pos);
		// if (pos2 == null || distsqTemp < distsq) {
		// pos2 = posTemp;
		// distsq = distsqTemp;
		// }
		// }
		// }
		// }
		// }
		// }
		//
		// if (pos2 == null) {
		// return false;
		// }
		//
		// BlockEnergyProvider provider = (BlockEnergyProvider)
		// world.getBlockState(pos2).getBlock();
		//
		// int loss = (int) Math.max(0, .01 * pos2.distanceSq(pos) - 1);
		//
		// if (provider.hasEnergy(world, pos2, energy + loss)) {
		// return provider.tryConsume(world, pos2, energy + loss);
		// }
		if (System.currentTimeMillis() > Hwell.nextAvailableNoEnergySound) {
			playSoundNoEnergy(world, pos);
			Hwell.nextAvailableNoEnergySound = System.currentTimeMillis() + 2000;
		}
		return false;
	}

	static void playSoundNoEnergy(World world, BlockPos pos) {
		// if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
		Hwell.proxy.playSoundNoEnergy(world, pos);

	}

}
