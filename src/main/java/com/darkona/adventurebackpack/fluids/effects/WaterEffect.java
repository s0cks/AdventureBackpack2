package com.darkona.adventurebackpack.fluids.effects;

import adventurebackpack.api.FluidEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fluids.FluidRegistry;

/**
 * Created by Darkona on 12/10/2014.
 */
public class WaterEffect extends FluidEffect
{

    public WaterEffect()
    {
        super(FluidRegistry.WATER, 7);
    }

    @Override
    public void affectDrinker(World world, Entity entity)
    {
        if (entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entity;
            BiomeGenBase biome = world.getWorldChunkManager().getBiomeGenAt(player.serverPosX, player.serverPosZ);

            //If it's hot
            if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.HOT) ||
                    BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.DRY) ||
                    BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.SANDY) ||
                    BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.WASTELAND) ||
                    BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.NETHER)
                    )
            {
                // player.getFoodStats().addStats(1, 0.1f);
                player.addPotionEffect(new PotionEffect(Potion.regeneration.id, timeInSeconds * 20, 0));
            }
            if (player.isBurning())
            {
                player.extinguish();
            }
        }
    }
}
