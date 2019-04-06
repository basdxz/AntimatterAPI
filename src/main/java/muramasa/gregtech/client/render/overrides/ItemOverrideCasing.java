package muramasa.gregtech.client.render.overrides;

import muramasa.gregtech.client.render.ModelUtils;
import muramasa.gregtech.client.render.bakedmodels.BakedTextureDataItem;
import muramasa.gregtech.common.blocks.BlockBaked;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ItemOverrideCasing extends ItemOverrideTextureData {

    public ItemOverrideCasing() {
        super(ModelUtils.BAKED_BASIC);
    }

    @Override
    public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) {
        return new BakedTextureDataItem(ModelUtils.BAKED_BASIC, ((BlockBaked) Block.getBlockFromItem(stack.getItem())).getBlockData());
    }
}
