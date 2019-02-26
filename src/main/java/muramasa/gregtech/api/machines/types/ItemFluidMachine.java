package muramasa.gregtech.api.machines.types;

import muramasa.gregtech.api.capability.impl.MachineFluidHandler;
import muramasa.gregtech.api.capability.impl.MachineItemHandler;
import muramasa.gregtech.api.machines.MachineFlag;
import muramasa.gregtech.api.recipe.Recipe;
import muramasa.gregtech.api.recipe.RecipeMap;

public class ItemFluidMachine extends BasicMachine {

    public ItemFluidMachine(String name) {
        super(name);
        addFlags(MachineFlag.FLUID);
    }

    @Override
    public Recipe findRecipe(MachineItemHandler stackHandler, MachineFluidHandler tankHandler) {
        return RecipeMap.findRecipeItemFluid(recipeMap, stackHandler.getInputs(), tankHandler.getInputs());
    }
}
