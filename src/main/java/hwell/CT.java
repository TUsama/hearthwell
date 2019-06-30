package hwell;

import java.util.Iterator;

import crafttweaker.annotations.ZenDoc;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;

import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import wolforce.Util;
import wolforce.items.ItemGrindingWheel;
import wolforce.recipes.Irio;
import wolforce.recipes.RecipeCharger;
import wolforce.recipes.RecipeCrushing;
import wolforce.recipes.RecipeFreezer;
import wolforce.recipes.RecipeGrinding;
import wolforce.recipes.RecipeNetherPortal;
import wolforce.recipes.RecipePowerCrystal;
import wolforce.recipes.RecipePowerCrystal.ItemAndVals;
import wolforce.recipes.RecipePuller;
import wolforce.recipes.RecipeRepairingPaste;
import wolforce.recipes.RecipeSeedOfLife;
import wolforce.recipes.RecipeSeparator;
import wolforce.recipes.RecipeTube;

@ZenClass("mods.hwell")
@ZenRegister
public class CT {

	// private static LinkedList<ItemStack> getItems(IItemStack ing) {
	// for (IItemStack iis : ing.getItems()) {
	// IItemStack stack = iis.getDefinition().makeStack(iis.getMetadata());
	// }
	// return null;
	// }

	private static FluidStack is(ILiquidStack iLiquidStack) {
		return CraftTweakerMC.getLiquidStack(iLiquidStack);
	}

	private static ItemStack is(IItemStack iItemStack) {
		return CraftTweakerMC.getItemStack(iItemStack);
	}

	private static ItemStack[] is(IItemStack[] _outputs) {
		ItemStack[] outputs = new ItemStack[_outputs.length];
		for (int i = 0; i < outputs.length; i++) {
			outputs[i] = is(_outputs[i]);
		}
		return outputs;
	}

	@ZenDoc("Add a recipe for the Charger.")
	@ZenMethod
	public static void addChargerRecipe(IItemStack input, int power) {
		RecipeCharger.recipes.add(new RecipeCharger(is(input), ItemStack.EMPTY, power));
	}

	@ZenDoc("Add a recipe for the Charger that outputs a byproduct from the top.")
	@ZenMethod
	public static void addChargerRecipe(IItemStack input, IItemStack output, int power) {
		RecipeCharger.recipes.add(new RecipeCharger(is(input), is(output), power));
	}

	@ZenDoc("Add a recipe for the Crushing Block. The number of probabilities must be the same as the number of outputs. If the sum of the probabilities isn't 1, there is a chance for an output of nothing.")
	@ZenMethod
	public static void addCrushingBlockRecipe(IItemStack input, IItemStack[] outputs, double[] probs) {
		RecipeCrushing[] recipes = new RecipeCrushing[outputs.length];
		for (int i = 0; i < recipes.length; i++) {
			recipes[i] = new RecipeCrushing(is(outputs[i]), probs[i]);
		}
		RecipeCrushing.recipes.put(new Irio(is(input)), recipes);
	}

	@ZenDoc("Add a recipe for the Freezer. If there is more than one output, a random output will be selected.")
	@ZenMethod
	public static void addFreezerRecipe(ILiquidStack input, IItemStack[] outputs) {
		RecipeFreezer.recipes.add(new RecipeFreezer(is(input), is(outputs)));
	}

	@ZenDoc("Add a recipe for the Grinder. The grinding wheel names are: \"iron\", \"diamond\" and \"crystal\".")
	@ZenMethod
	public static void addGrinderRecipe(IItemStack input, IItemStack output, String[] gearNames) {
		ItemGrindingWheel[] gears = new ItemGrindingWheel[gearNames.length];
		for (int i = 0; i < gears.length; i++) {
			ItemGrindingWheel wheel = RecipeGrinding.getWheelOf(gearNames[i]);
			if (wheel == null)
				throw new RuntimeException(gearNames[i] + " is not an id of a valid grinding wheel");
			gears[i] = wheel;
		}
		RecipeGrinding.recipes.put(new Irio(is(input)), new RecipeGrinding(is(output), gears));
	}

	@ZenDoc("Add a recipe for the Puller. The probability will be dependent on the sum of all probabilities. A filter must be provided.")
	@ZenMethod
	public static void addPullerRecipe(IItemStack output, double prob, IItemStack filter) {
		RecipePuller.recipes.add(new RecipePuller(is(output), prob, is(filter)));
	}

	@ZenDoc("Add a new item that has durability that the Repairing Paste can repair.")
	@ZenMethod
	public static void addRepairingPasteRecipe(IItemStack input) {
		RecipeRepairingPaste.items.add(is(input).getItem());
	}

	@ZenDoc("Add a new block for the Seed of Life to be right clicked on.")
	@ZenMethod
	public static void addSeedOfLifeRecipe(IItemStack input) {
		RecipeSeedOfLife.blocks.add(new Irio(is(input)));
	}

	@ZenDoc("Add a recipe for the Tube with a liquid output.")
	@ZenMethod
	public static void addTubeRecipe(IItemStack input, ILiquidStack output) {
		RecipeTube.put(is(input), is(output));
	}

	@ZenDoc("Add a recipe for the Tube with a solid block output.")
	@ZenMethod
	public static void addTubeRecipe(IItemStack input, IItemStack output) {
		RecipeTube.put(is(input), is(output));
	}

	@ZenDoc("Add a recipe for the Separator that outputs only two items.")
	@ZenMethod
	public static void addSeparatorRecipe(IItemStack input, IItemStack leftOutput, IItemStack rightOutput) {
		RecipeSeparator.recipes.put(new Irio(is(input)), new RecipeSeparator(is(leftOutput), is(rightOutput)));
	}

	@ZenDoc("Add a recipe for the Separator that outputs all three items.")
	@ZenMethod
	public static void addSeparatorRecipe(IItemStack input, IItemStack leftOutput, IItemStack rightOutput,
			IItemStack backOutput) {
		RecipeSeparator.recipes.put(new Irio(is(input)),
				new RecipeSeparator(is(leftOutput), is(rightOutput), is(backOutput)));
	}

	@ZenDoc("Add a new item to be used as a Nucleous in the Power Crystal Recipe.")
	@ZenMethod
	public static void addPowerCrystalNucleous(IItemStack item, String name, int power, int range, float purity) {
		RecipePowerCrystal.nucleousRecipes.add(new ItemAndVals(is(item), name, power, range, purity));
	}

	@ZenDoc("Add a new item to be used as a Relay in the Power Crystal Recipe.")
	@ZenMethod
	public static void addPowerCrystalRelay(IItemStack item, String name, int power, int range, float purity) {
		RecipePowerCrystal.relayRecipes.add(new ItemAndVals(is(item), name, power, range, purity));
	}

	@ZenDoc("Add a new item to be used as a Screen in the Power Crystal Recipe.")
	@ZenMethod
	public static void addPowerCrystalScreen(IItemStack item, String name, int power, int range, float purity) {
		RecipePowerCrystal.screenRecipes.add(new ItemAndVals(is(item), name, power, range, purity));
	}

	@ZenDoc("Add a new transformation recipe through the nether portal. It is not possible to change the number of items this way. 1 to 1 transformations only.")
	@ZenMethod
	public static void addNetherPortalRecipe(IItemStack input, IItemStack output) {
		RecipeNetherPortal.recipes.add(new RecipeNetherPortal(is(input), is(output)));
	}

	//

	//

	//

	@ZenDoc("Remove a recipe for the Charger.")
	@ZenMethod
	public static void removeChargerRecipe(IItemStack input) {
		for (Iterator<RecipeCharger> it = RecipeCharger.recipes.iterator(); it.hasNext();) {
			RecipeCharger recipe = (RecipeCharger) it.next();
			if (Util.equalExceptAmount(recipe.input, is(input))) {
				it.remove();
				return;
			}
		}
	}

	@ZenDoc("Remove a recipe for the Crushing Block.")
	@ZenMethod
	public static void removeCrushingBlockRecipe(IItemStack input) {
		RecipeCrushing.recipes.remove(new Irio(is(input)));
	}

	@ZenDoc("Remove a recipe for the Freezer.")
	@ZenMethod
	public static void removeFreezerRecipe(ILiquidStack input) {
		for (Iterator<RecipeFreezer> it = RecipeFreezer.recipes.iterator(); it.hasNext();) {
			RecipeFreezer recipe = (RecipeFreezer) it.next();
			if (recipe.fluidIn.isFluidEqual(is(input))) {
				it.remove();
				return;
			}
		}
	}

	@ZenDoc("Remove a recipe for the Grinder.")
	@ZenMethod
	public static void removeGrinderRecipe(IItemStack input) {
		RecipeGrinding.recipes.remove(new Irio(is(input)));
	}

	@ZenDoc("Remove a recipe for the Puller.")
	@ZenMethod
	public static void removePullerRecipe(IItemStack output) {
		for (Iterator<RecipePuller> it = RecipePuller.recipes.iterator(); it.hasNext();) {
			RecipePuller recipe = (RecipePuller) it.next();
			if (Util.equalExceptAmount(recipe.output, is(output))) {
				it.remove();
				return;
			}
		}
	}

	@ZenDoc("Remove a new item that has durability that the Repairing Paste can repair.")
	@ZenMethod
	public static void removeRepairingPasteRecipe(IItemStack input) {
		RecipeRepairingPaste.items.remove(is(input).getItem());
	}

	@ZenDoc("Remove a new block for the Seed of Life to be right clicked on.")
	@ZenMethod
	public static void removeSeedOfLifeRecipe(IItemStack input) {
		RecipeSeedOfLife.blocks.remove(new Irio(is(input)));
	}

	@ZenDoc("Remove a recipe for the Tube.")
	@ZenMethod
	public static void removeTubeRecipe(IItemStack input) {
		for (Iterator<RecipeTube> it = RecipeTube.recipes.iterator(); it.hasNext();) {
			RecipeTube recipe = (RecipeTube) it.next();
			if (Util.equalExceptAmount(recipe.in, is(input))) {
				it.remove();
				return;
			}
		}
	}

	@ZenDoc("Remove a recipe for the Separator.")
	@ZenMethod
	public static void removeSeparatorRecipe(IItemStack input) {
		RecipeSeparator.recipes.remove(new Irio(is(input)));
	}

	@ZenDoc("Remove a Nucleous Item.")
	@ZenMethod
	public static void removePowerCrystalNucleous(IItemStack item) {
		for (Iterator<ItemAndVals> it = RecipePowerCrystal.nucleousRecipes.iterator(); it.hasNext();) {
			ItemAndVals recipe = (ItemAndVals) it.next();
			if (Util.equalExceptAmount(recipe.stack, is(item))) {
				it.remove();
				return;
			}
		}
	}

	@ZenDoc("Remove a Relay Item.")
	@ZenMethod
	public static void removePowerCrystalRelay(IItemStack item) {
		for (Iterator<ItemAndVals> it = RecipePowerCrystal.relayRecipes.iterator(); it.hasNext();) {
			ItemAndVals recipe = (ItemAndVals) it.next();
			if (Util.equalExceptAmount(recipe.stack, is(item))) {
				it.remove();
				return;
			}
		}
	}

	@ZenDoc("Remove a Screen Item.")
	@ZenMethod
	public static void removePowerCrystalScreen(IItemStack item) {
		for (Iterator<ItemAndVals> it = RecipePowerCrystal.screenRecipes.iterator(); it.hasNext();) {
			ItemAndVals recipe = (ItemAndVals) it.next();
			if (Util.equalExceptAmount(recipe.stack, is(item))) {
				it.remove();
				return;
			}
		}
	}

	@ZenDoc("Remove a nether portal transformation recipe.")
	@ZenMethod
	public static void removeNetherPortalRecipe(IItemStack input) {
		for (Iterator<RecipeNetherPortal> it = RecipeNetherPortal.recipes.iterator(); it.hasNext();) {
			RecipeNetherPortal recipe = (RecipeNetherPortal) it.next();
			if (Util.equalExceptAmount(recipe.input, is(input))) {
				it.remove();
				return;
			}
		}
	}
}
