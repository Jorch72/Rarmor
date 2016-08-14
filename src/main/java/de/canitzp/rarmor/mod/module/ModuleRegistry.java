/*
 * This file ("ModuleRegistry.java") is part of the Rarmor mod for Minecraft.
 * It is created by Ellpeck and owned by canitzp & Ellpeck and distributed
 * under the Rarmor License to be found at
 * https://github.com/Ellpeck/Rarmor/blob/master/LICENSE.md
 * View the source code at https://github.com/Ellpeck/Rarmor
 *
 * © 2015-2016 canitzp & Ellpeck
 */

package de.canitzp.rarmor.mod.module;

import de.canitzp.rarmor.api.RarmorAPI;
import de.canitzp.rarmor.mod.module.ender.ActiveModuleEnder;
import de.canitzp.rarmor.mod.module.main.ActiveModuleMain;
import de.canitzp.rarmor.mod.module.storage.ActiveModuleStorage;

public final class ModuleRegistry{

    public static void init(){
        RarmorAPI.registerRarmorModule(ActiveModuleMain.IDENTIFIER, ActiveModuleMain.class);
        RarmorAPI.registerRarmorModule(ActiveModuleStorage.IDENTIFIER, ActiveModuleStorage.class);
        RarmorAPI.registerRarmorModule(ActiveModuleEnder.IDENTIFIER, ActiveModuleEnder.class);
    }

}
