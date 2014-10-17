package com.darkona.adventurebackpack.init;

import com.darkona.adventurebackpack.AdventureBackpack;
import com.darkona.adventurebackpack.network.CycleToolMessage;
import com.darkona.adventurebackpack.network.GuiBackpackMessage;
import com.darkona.adventurebackpack.network.NyanCatMessage;
import com.darkona.adventurebackpack.reference.ModInfo;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

/**
 * Created by Darkona on 12/10/2014.
 */
public class ModNetwork
{

    public static SimpleNetworkWrapper networkWrapper;

    public static void init()
    {

        int messageCounter = 0;
        networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(ModInfo.MOD_ID + "ch");
        networkWrapper.registerMessage(CycleToolMessage.CycleToolMessageServerHandler.class, CycleToolMessage.class, messageCounter++, Side.SERVER);
        networkWrapper.registerMessage(GuiBackpackMessage.GuiBackpackMessageServerHandler.class, GuiBackpackMessage.class, messageCounter++, Side.SERVER);
        networkWrapper.registerMessage(NyanCatMessage.NyanCatMessageServerHandler.class, NyanCatMessage.class, messageCounter++, Side.SERVER);
        networkWrapper.registerMessage(NyanCatMessage.NyanCatMessageClientHandler.class, NyanCatMessage.class, messageCounter++, Side.CLIENT);
    }
}
