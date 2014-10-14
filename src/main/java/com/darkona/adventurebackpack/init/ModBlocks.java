package com.darkona.adventurebackpack.init;


import com.darkona.adventurebackpack.blocks.TileAdventureBackpack;
import com.darkona.adventurebackpack.blocks.BlockAdventureBackpack;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.material.Material;

/**
 * Created by Darkona on 12/10/2014.
 */
public class ModBlocks {

    public static BlockAdventureBackpack blockBackpack = new BlockAdventureBackpack();


    public static void init() {
        GameRegistry.registerBlock(blockBackpack, "blockBackpack");

        GameRegistry.registerTileEntity(TileAdventureBackpack.class, "adventureBackpackTileEntity");
    }

}
