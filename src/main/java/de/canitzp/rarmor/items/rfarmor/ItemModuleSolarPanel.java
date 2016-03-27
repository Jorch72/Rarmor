package de.canitzp.rarmor.items.rfarmor;

import de.canitzp.rarmor.RarmorProperties;
import de.canitzp.rarmor.api.modules.IRarmorModule;
import de.canitzp.rarmor.util.EnergyUtil;
import de.canitzp.rarmor.util.NBTUtil;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author canitzp
 */
public class ItemModuleSolarPanel extends ItemModule implements IRarmorModule {

    private int energyPerTick;

    public ItemModuleSolarPanel() {
        super("moduleSolarPanel");
        this.energyPerTick = RarmorProperties.getInteger("moduleSolarEnergyPerTick");
    }

    @Override
    public String getUniqueName() {
        return "SolarPanel";
    }

    @Override
    public void onModuleTickInArmor(World world, EntityPlayer player, ItemStack armorChestplate, ItemStack module, IInventory inventory) {
        if(NBTUtil.getInteger(module, "tick") >= 50){
            if(canPlayerSeeSky(player)){
                EnergyUtil.addEnergy(armorChestplate, this.energyPerTick, armorChestplate.getMaxDamage());
                NBTUtil.setInteger(module, "tick", 0);
                NBTUtil.setBoolean(module, "doWork", true);
            } else {
                NBTUtil.setBoolean(module, "doWork", false);
            }
        } else {
            NBTUtil.setInteger(module, "tick", NBTUtil.getInteger(module, "tick") + 1);
            if(NBTUtil.getBoolean(module, "doWork")){
                EnergyUtil.addEnergy(armorChestplate, this.energyPerTick, armorChestplate.getMaxDamage());
            }
        }
    }

    private boolean canPlayerSeeSky(EntityPlayer player) {
        if(!player.worldObj.isRaining() && player.worldObj.isDaytime()){
            for(int i = (int) player.posY + 1; i <= 256; i++){
                IBlockState block = player.worldObj.getBlockState(new BlockPos(player.posX, i, player.posZ));
                if(block != null && ((block.isFullBlock() || block instanceof BlockLiquid) && !player.worldObj.isAirBlock(new BlockPos(player.posX, i, player.posZ)))){
                    return false;
                }
            }
            return true;
        }
        return false;
    }


}
