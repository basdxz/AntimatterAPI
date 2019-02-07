package muramasa.gregtech.client.render.overrides;

import com.google.common.collect.ImmutableList;
import muramasa.gregtech.client.render.models.ModelBase;
import muramasa.gregtech.common.utils.Ref;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.HashMap;

public class ItemOverrideMachine extends ItemOverrideList {

    private HashMap<String, IBakedModel> bakedItems;

    public ItemOverrideMachine(HashMap<String, IBakedModel> items) {
        super(ImmutableList.of());
        bakedItems = items;
    }

    @Override
    public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey(Ref.TAG_MACHINE_STACK_DATA)) {
            NBTTagCompound tag = (NBTTagCompound) stack.getTagCompound().getTag(Ref.TAG_MACHINE_STACK_DATA);
            IBakedModel bakedModel = bakedItems.get(tag.getString(Ref.KEY_MACHINE_STACK_TYPE) + tag.getString(Ref.KEY_MACHINE_STACK_TIER));
            if (bakedModel != null) {
                return bakedModel;
            }
        }
        return ModelBase.missingBaked;
    }
}