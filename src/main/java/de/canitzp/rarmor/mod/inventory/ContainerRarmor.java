/*
 * This file ("ContainerRarmor.java") is part of the Rarmor mod for Minecraft.
 * It is created by Ellpeck and owned by canitzp & Ellpeck and distributed
 * under the Rarmor License to be found at
 * https://github.com/Ellpeck/Rarmor/blob/master/LICENSE.md
 * View the source code at https://github.com/Ellpeck/Rarmor
 *
 * © 2015-2016 canitzp & Ellpeck
 */

package de.canitzp.rarmor.mod.inventory;

import de.canitzp.rarmor.api.internal.IRarmorData;
import de.canitzp.rarmor.api.inventory.RarmorModuleContainer;
import de.canitzp.rarmor.api.module.ActiveRarmorModule;
import de.canitzp.rarmor.mod.inventory.gui.slot.SlotModule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerRarmor extends Container{

    private final IRarmorData currentData;
    public final RarmorModuleContainer container;

    //this is needed due to putStack() also being called when the container is opened
    //but only on the client for some reason. Ugh.
    public boolean isPuttingStacksInSlots;

    public ContainerRarmor(EntityPlayer player, ActiveRarmorModule currentModule, IRarmorData currentData){
        this.container = currentModule.createContainer(player, this, currentData);
        this.currentData = currentData;

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 9; j++){
                this.addSlotToContainer(new Slot(player.inventory, j+i*9+9, 38+j*18, 147+i*18));
            }
        }

        for(int k = 0; k < 9; k++){
            this.addSlotToContainer(new Slot(player.inventory, k, 38+k*18, 205));
        }

        for(Slot slot : this.container.getSlots()){
            this.addSlotToContainer(slot);
        }
    }

    @Override
    public void addListener(IContainerListener listener){
        super.addListener(listener);
        this.container.addListener(listener);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void removeListener(IContainerListener listener){
        super.removeListener(listener);
        this.container.removeListener(listener);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn){
        return true;
    }

    @Override
    public void putStacksInSlots(ItemStack[] stack){
        this.isPuttingStacksInSlots = true;
        super.putStacksInSlots(stack);
        this.isPuttingStacksInSlots = false;
    }

    @Override
    public void putStackInSlot(int slotID, ItemStack stack){
        this.isPuttingStacksInSlots = true;
        super.putStackInSlot(slotID, stack);
        this.isPuttingStacksInSlots = false;
    }

    @Override
    public void detectAndSendChanges(){
        super.detectAndSendChanges();
        this.container.detectAndSendChanges();
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index){
        return this.container.transferStackInSlot(player, index);
    }

    @Override
    public void onContainerClosed(EntityPlayer player){
        super.onContainerClosed(player);
        this.container.onContainerClosed(player);
    }

    @Override
    public void onCraftMatrixChanged(IInventory inventory){
        super.onCraftMatrixChanged(inventory);
        this.container.onCraftMatrixChanged(inventory);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data){
        super.updateProgressBar(id, data);
        this.container.updateProgressBar(id, data);
    }

    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickType, EntityPlayer player){
        ItemStack stack = this.container.slotClick(slotId, dragType, clickType, player);
        if(stack == null){
            stack = super.slotClick(slotId, dragType, clickType, player);
        }

        if(!player.worldObj.isRemote){
            if(slotId > 0 && slotId < this.inventorySlots.size()){
                Slot slot = this.inventorySlots.get(slotId);
                if(slot instanceof SlotModule){
                    this.currentData.sendUpdate(player, true, -1);
                }
            }
        }

        return stack;
    }
}
