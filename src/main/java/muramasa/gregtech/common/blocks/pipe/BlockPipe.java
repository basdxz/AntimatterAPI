package muramasa.gregtech.common.blocks.pipe;

import muramasa.gregtech.Ref;
import muramasa.gregtech.api.registration.IHasItemBlock;
import muramasa.gregtech.api.registration.IHasModelOverride;
import muramasa.gregtech.api.pipe.PipeSize;
import muramasa.gregtech.api.pipe.PipeStack;
import muramasa.gregtech.api.pipe.types.Pipe;
import muramasa.gregtech.api.properties.UnlistedInteger;
import muramasa.gregtech.api.util.Utils;
import muramasa.gregtech.client.render.StateMapperRedirect;
import muramasa.gregtech.api.tileentities.pipe.TileEntityPipe;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public abstract class BlockPipe extends Block implements IHasItemBlock, IHasModelOverride {

    private static StateMapperRedirect stateMapRedirect = new StateMapperRedirect(new ResourceLocation(Ref.MODID, "block_pipe"));

    public static final UnlistedInteger CONNECTIONS = new UnlistedInteger();
    public static final UnlistedInteger SIZE = new UnlistedInteger();

    public BlockPipe(String name) {
        super(Material.IRON);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Ref.TAB_MACHINES);
    }

    public abstract Pipe getType();

    public int getRGB() {
        return getType().getRGB();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer.Builder(this).add(CONNECTIONS, SIZE).build();
    }

    @Override
    public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
        IExtendedBlockState exState = (IExtendedBlockState) state;
        TileEntity tile = Utils.getTile(world, pos);
        if (tile instanceof TileEntityPipe) {
            PipeSize size = ((TileEntityPipe) tile).getSize();
            exState = exState.withProperty(SIZE, size != null ? size.ordinal() : PipeSize.TINY.ordinal());
        }
        return exState;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        TileEntity tile = Utils.getTile(source, pos);
        if (tile instanceof TileEntityPipe) {
            PipeSize size = ((TileEntityPipe) tile).getSize();
            return size != null ? size.getAABB() : PipeSize.TINY.getAABB();
        }
        return FULL_BLOCK_AABB;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public abstract TileEntity createTileEntity(World world, IBlockState state);

    @Nullable
    @Override
    public String getHarvestTool(IBlockState state) {
        return "wire_cutter";
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
//        TileEntityCable tile = (TileEntityCable) world.getTileEntity(pos);
//        if (tile != null) {
//            if (ItemList.DebugScanner.isEqual(player.getHeldItem(hand))) {
//                player.sendMessage(new TextComponentString(tile.cableConnections + ""));
//                tile.toggleShouldConnect(facing);
//            }
//            return true;
//        }
        return false;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if (stack.hasTagCompound()) {
            TileEntity tile = Utils.getTile(world, pos);
            if (tile instanceof TileEntityPipe) {
                PipeSize size = PipeSize.VALUES[stack.getTagCompound().getInteger(Ref.KEY_PIPE_STACK_SIZE)];
                ((TileEntityPipe) tile).init(getType(), size);
            }
        }
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        TileEntity tile = Utils.getTile(world, pos);
        if (tile instanceof TileEntityPipe) {
            TileEntityPipe pipe = (TileEntityPipe) tile;
            return new PipeStack(pipe.getBlockType(), pipe.getType(), pipe.getSize()).asItemStack();
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        TileEntity tile = Utils.getTile(world, pos);
        if (tile instanceof TileEntityPipe) {
            ((TileEntityPipe) tile).refreshConnections();
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
        TileEntity tile = Utils.getTile(world, pos);
        if (tile instanceof TileEntityPipe) {
            ((TileEntityPipe) tile).refreshConnections();
        }
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public String getItemStackDisplayName(Block block, ItemStack stack) {
        return getType().getDisplayName(stack);
    }

    @Override
    public List<String> addInformation(ItemStack stack) {
        return getType().getTooltip(stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(Ref.MODID + ":block_pipe", "inventory"));
        ModelLoader.setCustomStateMapper(this, stateMapRedirect);
    }
}
