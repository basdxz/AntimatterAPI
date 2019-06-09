package muramasa.gtu.loaders;

import muramasa.gtu.api.GregTechAPI;
import muramasa.gtu.api.items.MaterialItem;
import muramasa.gtu.api.items.MaterialTool;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictLoader {

    public static void init() {
        //Register materialItem entries (prefixMaterialName)
        //TODO use CaseFormat
        GregTechAPI.all(MaterialItem.class).forEach(item -> {
            String[] prefix = item.getPrefix().getId().split("_");
            for (int i = 1; i < prefix.length; i++) {
                prefix[i] = prefix[i].substring(0, 1).toUpperCase() + prefix[i].substring(1);
            }
            String[] mat = item.getMaterial().getId().split("_");
            for (int i = 0; i < mat.length; i++) {
                mat[i] = mat[i].substring(0, 1).toUpperCase() + mat[i].substring(1);
            }
            OreDictionary.registerOre(String.join("", prefix) + String.join("", mat), item);
        });

        //Register craftingTool entries (craftingToolType)
        GregTechAPI.all(MaterialTool.class).forEach(t -> OreDictionary.registerOre(t.getType().getOreDict(), new ItemStack(t, 1, OreDictionary.WILDCARD_VALUE)));
    }
}
