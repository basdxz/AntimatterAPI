package muramasa.gregtech.client.render.bakedmodels;

import muramasa.gregtech.Ref;
import muramasa.gregtech.client.render.ModelUtils;
import muramasa.gregtech.client.render.RenderHelper;
import muramasa.gregtech.client.render.models.ModelPipe;
import muramasa.gregtech.client.render.overrides.ItemOverridePipe;
import muramasa.gregtech.common.blocks.pipe.BlockPipe;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.property.IExtendedBlockState;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class BakedPipe extends BakedBase {

    protected static ItemOverrideList OVERRIDE;
    protected static TextureAtlasSprite PARTICLE;

    private static HashMap<Integer, int[]> CONFIG_MAP = new HashMap<>();
    public static IBakedModel[][] BAKED;

//    public static HashMap<Integer, List<BakedQuad>> CACHE = new HashMap<>();

    static {
        //Default Shape (0 Connections)
        CONFIG_MAP.put(0, new int[]{0});

        //Single Shapes (1 Connections)
        CONFIG_MAP.put(1, new int[]{1, EnumFacing.DOWN.getIndex()});
        CONFIG_MAP.put(2, new int[]{1, EnumFacing.UP.getIndex()});
        CONFIG_MAP.put(4, new int[]{1});
        CONFIG_MAP.put(8, new int[]{1, EnumFacing.SOUTH.getIndex()});
        CONFIG_MAP.put(16, new int[]{1, EnumFacing.WEST.getIndex()});
        CONFIG_MAP.put(32, new int[]{1, EnumFacing.EAST.getIndex()});

        //Line Shapes (2 Connections)
        CONFIG_MAP.put(3, new int[]{2, EnumFacing.UP.getIndex()});
        CONFIG_MAP.put(12, new int[]{2});
        CONFIG_MAP.put(48, new int[]{2, EnumFacing.WEST.getIndex()});

        //Corner Shapes (2 Connections)
        CONFIG_MAP.put(5, new int[]{3, EnumFacing.WEST.getIndex(), EnumFacing.UP.getIndex(), EnumFacing.EAST.getIndex()});
        CONFIG_MAP.put(6, new int[]{3, EnumFacing.WEST.getIndex(), EnumFacing.DOWN.getIndex(), EnumFacing.EAST.getIndex()});
        CONFIG_MAP.put(9, new int[]{3, EnumFacing.EAST.getIndex(), EnumFacing.UP.getIndex(), EnumFacing.EAST.getIndex()});
        CONFIG_MAP.put(10, new int[]{3, EnumFacing.EAST.getIndex(), EnumFacing.DOWN.getIndex(), EnumFacing.EAST.getIndex()});
        CONFIG_MAP.put(17, new int[]{3, EnumFacing.NORTH.getIndex(), EnumFacing.DOWN.getIndex(), EnumFacing.WEST.getIndex()});
        CONFIG_MAP.put(18, new int[]{3, EnumFacing.SOUTH.getIndex(), EnumFacing.DOWN.getIndex(), EnumFacing.EAST.getIndex()});
        CONFIG_MAP.put(20, new int[]{3, EnumFacing.WEST.getIndex()});
        CONFIG_MAP.put(24, new int[]{3, EnumFacing.SOUTH.getIndex()});
        CONFIG_MAP.put(33, new int[]{3, EnumFacing.NORTH.getIndex(), EnumFacing.UP.getIndex(), EnumFacing.EAST.getIndex()});
        CONFIG_MAP.put(34, new int[]{3, EnumFacing.NORTH.getIndex(), EnumFacing.DOWN.getIndex(), EnumFacing.EAST.getIndex()});
        CONFIG_MAP.put(36, new int[]{3});
        CONFIG_MAP.put(40, new int[]{3, EnumFacing.EAST.getIndex()});

        //Side Shapes (3 Connections)
        CONFIG_MAP.put(7, new int[]{4});
        CONFIG_MAP.put(11, new int[]{4});
        CONFIG_MAP.put(13, new int[]{4, EnumFacing.WEST.getIndex(), EnumFacing.DOWN.getIndex()});
        CONFIG_MAP.put(14, new int[]{4, EnumFacing.WEST.getIndex(), EnumFacing.UP.getIndex()});
        CONFIG_MAP.put(19, new int[]{4, EnumFacing.UP.getIndex(), EnumFacing.EAST.getIndex(), EnumFacing.SOUTH.getIndex()});
        CONFIG_MAP.put(28, new int[]{4, EnumFacing.WEST.getIndex()});
        CONFIG_MAP.put(35, new int[]{4, EnumFacing.UP.getIndex(), EnumFacing.WEST.getIndex(), EnumFacing.SOUTH.getIndex()});
        CONFIG_MAP.put(44, new int[]{4, EnumFacing.EAST.getIndex()});
        CONFIG_MAP.put(49, new int[]{4, EnumFacing.DOWN.getIndex()});
        CONFIG_MAP.put(50, new int[]{4, EnumFacing.UP.getIndex()});
        CONFIG_MAP.put(52, new int[]{4});
        CONFIG_MAP.put(56, new int[]{4, EnumFacing.SOUTH.getIndex()});

        //Corner Shapes (3 Connections)
        CONFIG_MAP.put(21, new int[]{5, EnumFacing.WEST.getIndex(), EnumFacing.DOWN.getIndex()});
        CONFIG_MAP.put(22, new int[]{5, EnumFacing.WEST.getIndex()});
        CONFIG_MAP.put(25, new int[]{5, EnumFacing.SOUTH.getIndex(), EnumFacing.DOWN.getIndex()});
        CONFIG_MAP.put(26, new int[]{5, EnumFacing.SOUTH.getIndex()});
        CONFIG_MAP.put(41, new int[]{5, EnumFacing.EAST.getIndex(), EnumFacing.DOWN.getIndex()});
        CONFIG_MAP.put(42, new int[]{5, EnumFacing.EAST.getIndex()});
        CONFIG_MAP.put(37, new int[]{5, EnumFacing.NORTH.getIndex(), EnumFacing.DOWN.getIndex()});
        CONFIG_MAP.put(38, new int[]{5});

        //Arrow Shapes (4 Connections)
        CONFIG_MAP.put(23, new int[]{5});
        CONFIG_MAP.put(27, new int[]{5});
        CONFIG_MAP.put(29, new int[]{5});
        CONFIG_MAP.put(30, new int[]{5});
        CONFIG_MAP.put(39, new int[]{5});
        CONFIG_MAP.put(43, new int[]{5});
        CONFIG_MAP.put(45, new int[]{5});
        CONFIG_MAP.put(46, new int[]{5});
        CONFIG_MAP.put(53, new int[]{5});
        CONFIG_MAP.put(54, new int[]{5});
        CONFIG_MAP.put(57, new int[]{5});
        CONFIG_MAP.put(58, new int[]{5});

        //Cross Shapes (4 Connections)
        CONFIG_MAP.put(15, new int[]{6});
        CONFIG_MAP.put(51, new int[]{6});
        CONFIG_MAP.put(60, new int[]{6});

        //Five Shapes (5 Connections)
        CONFIG_MAP.put(31, new int[]{7});
        CONFIG_MAP.put(47, new int[]{7});
        CONFIG_MAP.put(55, new int[]{7});
        CONFIG_MAP.put(59, new int[]{7});
        CONFIG_MAP.put(61, new int[]{7, EnumFacing.DOWN.getIndex(), EnumFacing.DOWN.getIndex()});
        CONFIG_MAP.put(62, new int[]{7});

        //All Shapes (6 Connections)
        CONFIG_MAP.put(63, new int[]{8});
    }

    public BakedPipe(IBakedModel[][] baked) {
        BAKED = baked;
        PARTICLE = RenderHelper.getSprite(ModelPipe.PIPE);
        OVERRIDE = new ItemOverridePipe();
    }

    @Override
    public List<BakedQuad> getBakedQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        IExtendedBlockState exState = (IExtendedBlockState) state;
        int size = exState.getValue(BlockPipe.SIZE);
        int connections = exState.getValue(BlockPipe.CONNECTIONS);


//        List<BakedQuad> quads = ModelUtils.getQuads(Ref.CACHE_ID_PIPE, connections);
        List<BakedQuad> quads = null;
        if (quads == null) {
            quads = new LinkedList<>();
            int[] config = CONFIG_MAP.get(connections);
            quads.addAll(BAKED[size][config[0]].getQuads(state, side, rand));


            if (connections == 7) {
                quads = ModelUtils.trans(quads, EnumFacing.UP.getIndex(), EnumFacing.NORTH.getIndex(), EnumFacing.WEST.getIndex(), EnumFacing.EAST.getIndex());
            } else {
                if (config.length > 1) quads = ModelUtils.trans(quads, Arrays.copyOfRange(config, 1, config.length));
            }

            //if (config.length > 1) quads = ModelUtils.trans(quads, Arrays.copyOfRange(config, 1, config.length));

            ModelUtils.putQuads(Ref.CACHE_ID_PIPE, connections, quads);
        }

        return quads;
    }

    @Override
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
        return super.handlePerspective(cameraTransformType);
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return PARTICLE;
    }

    @Override
    public ItemOverrideList getOverrides() {
        return OVERRIDE;
    }
}
