package muramasa.gtu.api.container;

import muramasa.gtu.api.tileentities.TileEntityMachine;
import net.minecraft.entity.player.PlayerInventory;

public class ContainerBasicMachine extends ContainerMachine {

    public ContainerBasicMachine(TileEntityMachine tile, PlayerInventory playerInv, MenuHandler handler, int windowId) {
        super(tile, playerInv, handler, windowId);
    }
}
