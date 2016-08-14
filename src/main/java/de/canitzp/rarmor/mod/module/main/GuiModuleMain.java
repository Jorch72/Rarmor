/*
 * This file ("GuiModuleMain.java") is part of the Rarmor mod for Minecraft.
 * It is created by Ellpeck and owned by canitzp & Ellpeck and distributed
 * under the Rarmor License to be found at
 * https://github.com/Ellpeck/Rarmor/blob/master/LICENSE.md
 * View the source code at https://github.com/Ellpeck/Rarmor
 *
 * © 2015-2016 canitzp & Ellpeck
 */

package de.canitzp.rarmor.mod.module.main;

import de.canitzp.rarmor.api.RarmorAPI;
import de.canitzp.rarmor.api.internal.IRarmorData;
import de.canitzp.rarmor.api.inventory.RarmorModuleGui;
import de.canitzp.rarmor.api.module.ActiveRarmorModule;
import de.canitzp.rarmor.mod.misc.Helper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collections;

@SideOnly(Side.CLIENT)
public class GuiModuleMain extends RarmorModuleGui{

    public static final ResourceLocation RES_LOC = Helper.getGuiLocation("guiRarmorMain");

    public GuiModuleMain(GuiContainer container, ActiveRarmorModule module, IRarmorData currentData){
        super(container, module, currentData);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
        this.mc.getTextureManager().bindTexture(RES_LOC);
        this.drawTexturedModalRect(this.guiLeft+6, this.guiTop+5, 0, 0, 217, 136);

        GuiInventory.drawEntityOnScreen(this.guiLeft+118, this.guiTop+128, 55, (float)(this.guiLeft+118)-mouseX, (float)(this.guiTop+45)-mouseY, this.mc.thePlayer);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        Slot slot = this.actualGui.getSlotUnderMouse();
        if(slot != null && slot.getSlotIndex() == 38){ //chestplate slot
            GuiUtils.drawHoveringText(Collections.singletonList(TextFormatting.GOLD+""+TextFormatting.ITALIC+I18n.format(RarmorAPI.MOD_ID+".removeChest", this.mc.gameSettings.keyBindDrop.getDisplayName())), mouseX, mouseY-20, this.mc.displayWidth, this.mc.displayHeight, -1, this.mc.fontRendererObj);
        }
    }
}
