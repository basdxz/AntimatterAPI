package muramasa.gregtech.loaders;

import muramasa.gregtech.GregTech;
import muramasa.gregtech.Ref;
import muramasa.gregtech.api.data.Machines;
import muramasa.gregtech.api.enums.*;
import muramasa.gregtech.api.interfaces.GregTechRegistrar;
import muramasa.gregtech.api.items.ItemFluidCell;
import muramasa.gregtech.api.items.MaterialItem;
import muramasa.gregtech.api.items.StandardItem;
import muramasa.gregtech.api.machines.types.Machine;
import muramasa.gregtech.api.materials.ItemFlag;
import muramasa.gregtech.api.materials.Material;
import muramasa.gregtech.api.pipe.types.Cable;
import muramasa.gregtech.api.pipe.types.FluidPipe;
import muramasa.gregtech.common.blocks.*;
import muramasa.gregtech.common.blocks.pipe.BlockCable;
import muramasa.gregtech.common.blocks.pipe.BlockFluidPipe;
import muramasa.gregtech.common.items.*;
import muramasa.gregtech.common.tileentities.base.TileEntityMachine;
import muramasa.gregtech.common.tileentities.base.multi.TileEntityCasing;
import muramasa.gregtech.common.tileentities.base.multi.TileEntityCoil;
import muramasa.gregtech.common.tileentities.base.multi.TileEntityHatch;
import muramasa.gregtech.common.tileentities.base.multi.TileEntityMultiMachine;
import muramasa.gregtech.common.tileentities.overrides.*;
import muramasa.gregtech.common.tileentities.pipe.TileEntityCable;
import muramasa.gregtech.common.tileentities.pipe.TileEntityFluidPipe;
import muramasa.gregtech.common.tileentities.pipe.TileEntityPipe;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.LinkedList;
import java.util.List;

import static muramasa.gregtech.api.machines.MachineFlag.MULTI;

@Mod.EventBusSubscriber
public class ContentLoader {

    public static ItemFluidCell fluidCell;

    static {
//        Ref.TAB_MATERIALS.setTabStack(Materials.Titanium.getIngot(1));
//        Ref.TAB_ITEMS.setTabStack(ItemList.Debug_Scanner.get(1));
//        Ref.TAB_BLOCKS.setTabStack(new ItemStack(blockCasing, 1, CasingType.FUSION3.ordinal()));
//        Ref.TAB_MACHINES.setTabStack(new MachineStack(Machines.ALLOY_SMELTER, Tier.EV).asItemStack());
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        //TODO auto register all type tiles???
        GameRegistry.registerTileEntity(TileEntityMachine.class, new ResourceLocation(Ref.MODID, "tile_machine"));
        GameRegistry.registerTileEntity(TileEntityBasicMachine.class, new ResourceLocation(Ref.MODID, "tile_basic_machine"));
        GameRegistry.registerTileEntity(TileEntityItemMachine.class, new ResourceLocation(Ref.MODID, "tile_item_machine"));
        GameRegistry.registerTileEntity(TileEntityFluidMachine.class, new ResourceLocation(Ref.MODID, "tile_fluid_machine"));
        GameRegistry.registerTileEntity(TileEntityItemFluidMachine.class, new ResourceLocation(Ref.MODID, "tile_item_fluid_machine"));
        GameRegistry.registerTileEntity(TileEntitySteamMachine.class, new ResourceLocation(Ref.MODID, "tile_steam_machine"));
        GameRegistry.registerTileEntity(TileEntityMultiMachine.class, new ResourceLocation(Ref.MODID, "tile_multi_machine"));
        GameRegistry.registerTileEntity(TileEntityHatch.class, new ResourceLocation(Ref.MODID, "tile_hatch"));
        List<String> registeredTiles = new LinkedList<>();
        for (Machine type : Machines.getAll()) {
            event.getRegistry().register(type.getBlock());
            if (type.hasFlag(MULTI) && !registeredTiles.contains(type.getTileClass().getName())) {
                GameRegistry.registerTileEntity(type.getTileClass(), new ResourceLocation(Ref.MODID, "tile_" + type.getName()));
                registeredTiles.add(type.getTileClass().getName());
            }
        }
        for (Cable type : Cable.getAll()) {
            event.getRegistry().register(new BlockCable(type));
        }
        for (FluidPipe type : FluidPipe.getAll()) {
            event.getRegistry().register(new BlockFluidPipe(type));
        }
        for (Casing type : Casing.getAll()) {
            event.getRegistry().register(new BlockCasing(type));
        }
        for (Coil type : Coil.getAll()) {
            event.getRegistry().register(new BlockCoil(type));
        }
        for (Material m : ItemFlag.ORE.getMats()) {
            for (StoneType type : StoneType.getAll()) {
                event.getRegistry().register(new BlockOre(type, m));
            }
        }
        for (Material m : ItemFlag.BLOCK.getMats()) {
            event.getRegistry().register(new BlockStorage(m));
        }
        for (StoneType type : StoneType.getGenerating()) {
            event.getRegistry().register(new BlockStone(type));
        }


        GameRegistry.registerTileEntity(TileEntityPipe.class, new ResourceLocation(Ref.MODID, "block_pipe"));
        GameRegistry.registerTileEntity(TileEntityFluidPipe.class, new ResourceLocation(Ref.MODID, "block_fluid_pipe"));
        GameRegistry.registerTileEntity(TileEntityCable.class, new ResourceLocation(Ref.MODID, "block_cable"));
        GameRegistry.registerTileEntity(TileEntityCasing.class, new ResourceLocation(Ref.MODID, "block_casing"));
        GameRegistry.registerTileEntity(TileEntityCoil.class, new ResourceLocation(Ref.MODID, "block_coil"));
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        for (MaterialItem item : MaterialItem.getAll()) {
            event.getRegistry().register(item);
        }
        for (ItemType type : ItemType.getAll()) {
            if (!type.isEnabled()) continue;
            event.getRegistry().register(new StandardItem(type));
        }
        for (Machine type : Machines.getAll()) {
            event.getRegistry().register(new ItemBlockMachine(type.getBlock()).setRegistryName(type.getBlock().getRegistryName()));
        }
        for (Cable type : Cable.getAll()) {
            Block block = GregTechRegistry.getCable(type);
            event.getRegistry().register(new ItemBlockPipe(block).setRegistryName(block.getRegistryName()));
        }
        for (FluidPipe type : FluidPipe.getAll()) {
            Block block = GregTechRegistry.getFluidPipe(type);
            event.getRegistry().register(new ItemBlockPipe(block).setRegistryName(block.getRegistryName()));
        }
        for (StoneType type : StoneType.getAll()) {
            for (Material material : ItemFlag.ORE.getMats()) {
                Block block = GregTechRegistry.getOre(type, material);
                event.getRegistry().register(new ItemBlockOre(block).setRegistryName(block.getRegistryName()));
            }
        }
        for (Material material : ItemFlag.BLOCK.getMats()) {
            Block block = GregTechRegistry.getStorage(material);
            event.getRegistry().register(new ItemBlockStorage(block).setRegistryName(block.getRegistryName()));
        }
        for (Casing type : Casing.getAll()) {
            Block block = GregTechRegistry.getCasing(type);
            event.getRegistry().register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
        }
        for (Coil type : Coil.getAll()) {
            Block block = GregTechRegistry.getCoil(type);
            event.getRegistry().register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
        }
        for (StoneType type : StoneType.getGenerating()) {
            Block block = GregTechRegistry.getStone(type);
            event.getRegistry().register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
        }
        for (ToolType type : ToolType.values()) {
            event.getRegistry().register(type.getInstance());
        }

        event.getRegistry().register((fluidCell = new ItemFluidCell()));

        GregTech.INTERNAL_REGISTRAR.onCoverRegistration();
        for (GregTechRegistrar registrar : GregTechRegistry.getRegistrars()) {
            registrar.onCoverRegistration();
        }
    }
}
