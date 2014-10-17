package com.darkona.adventurebackpack.inventory;

import com.darkona.adventurebackpack.block.TileAdventureBackpack;
import com.darkona.adventurebackpack.common.IAdvBackpack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by Darkona on 12/10/2014.
 */
public class BackpackContainer extends Container
{

    public IAdvBackpack inventory;
    public static boolean SOURCE_TILE = true;
    public static boolean SOURCE_ITEM = false;
    public boolean needsUpdate;

    public BackpackContainer(InventoryPlayer invPlayer, IAdvBackpack backpack, boolean source)
    {
        needsUpdate = false;
        inventory = backpack;
        makeSlots(invPlayer);
    }


    // =============================================== GETTERS ====================================================== //
    // =============================================== SETTERS ====================================================== //
    // ================================================ ORDER ======================================================= //
    // ================================================ STACKS ====================================================== //


    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return inventory.isUseableByPlayer(player);
    }

    private void makeSlots(InventoryPlayer invPlayer)
    {

        IInventory sexy = inventory;

        // Player's Hotbar
        for (int x = 0; x < 9; x++)
        {
            addSlotToContainer(new Slot(invPlayer, x, 8 + 18 * x, 142));
        }

        // Player's Inventory
        for (int y = 0; y < 3; y++)
        {
            for (int x = 0; x < 9; x++)
            {
                addSlotToContainer(new Slot(invPlayer, (x + y * 9 + 9), (8 + 18 * x), (84 + y * 18)));
            }
        }
        int thing = 0;

        // Backpack Inventory

        //Upper Tool Slot
        addSlotToContainer(new SlotTool(sexy, thing++, 62, 37));// Upper Tool 0

        addSlotToContainer(new SlotBackpack(sexy, thing++, 80, 37));// 1
        addSlotToContainer(new SlotBackpack(sexy, thing++, 98, 37));// 2

        //Lower Tool slot
        addSlotToContainer(new SlotTool(sexy, thing++, 62, 55));// Lower Tool 3

        addSlotToContainer(new SlotBackpack(sexy, thing++, 80, 55));// 4
        addSlotToContainer(new SlotBackpack(sexy, thing++, 98, 55));// 5

        //Bucket Slots

        // bucket left 6
        addSlotToContainer(new SlotFluid(sexy, thing++, 7, 25));
        // bucket out left 7
        addSlotToContainer(new SlotFluid(sexy, thing++, 7, 55));
        // bucket right out 8
        addSlotToContainer(new SlotFluid(sexy, thing++, 153, 25));
        // bucket right out 9
        addSlotToContainer(new SlotFluid(sexy, thing++, 153, 55));
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int i)
    {
        // TODO Fix the shit disrespecting slot accepting itemstack.
        Slot slot = getSlot(i);
        if (slot != null && slot.getHasStack())
        {
            ItemStack stack = slot.getStack();
            ItemStack result = stack.copy();
            if (i >= 36)
            {
                if (!mergeItemStack(stack, 0, 36, false))
                {
                    return null;
                }
            } else if (!mergeItemStack(stack, 36, 36 + inventory.getSizeInventory(), false))
            {
                return null;
            }

            if (stack.stackSize == 0)
            {
                slot.putStack(null);
            } else
            {
                slot.onSlotChanged();
            }

            slot.onPickupFromSlot(player, stack);

            return result;
        }
        return null;
    }

    @Override
    public void onContainerClosed(EntityPlayer par1EntityPlayer)
    {
        super.onContainerClosed(par1EntityPlayer);
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();
    }

    @Override
    public Slot getSlotFromInventory(IInventory p_75147_1_, int p_75147_2_)
    {
        return super.getSlotFromInventory(p_75147_1_, p_75147_2_);
    }

    @Override
    protected boolean mergeItemStack(ItemStack stack, int minSlot, int maxSlot, boolean par4)
    {
        boolean flag1 = false;
        int slotInit = minSlot;

        if (par4)
        {
            slotInit = maxSlot - 1;
        }

        Slot slot;
        ItemStack itemstack1;

        if (stack.isStackable())
        {
            while (stack.stackSize > 0 && (!par4 && slotInit < maxSlot || par4 && slotInit >= minSlot))
            {
                slot = (Slot) this.inventorySlots.get(slotInit);
                itemstack1 = slot.getStack();

                if (itemstack1 != null && itemstack1.getItem() == stack.getItem() && (!stack.getHasSubtypes() || stack.getItemDamage() == itemstack1.getItemDamage()) && ItemStack.areItemStackTagsEqual(stack, itemstack1))
                {

                    int newStackSize = itemstack1.stackSize + stack.stackSize;

                    if (newStackSize <= stack.getMaxStackSize())
                    {
                        stack.stackSize = 0;
                        itemstack1.stackSize = newStackSize;
                        slot.onSlotChanged();
                        flag1 = true;
                    } else if (itemstack1.stackSize < stack.getMaxStackSize())
                    {
                        stack.stackSize -= stack.getMaxStackSize() - itemstack1.stackSize;
                        itemstack1.stackSize = stack.getMaxStackSize();
                        slot.onSlotChanged();
                        flag1 = true;
                    }
                }

                if (par4)
                {
                    --slotInit;
                } else
                {
                    ++slotInit;
                }
            }
        }

        if (stack.stackSize > 0)
        {
            if (par4)
            {
                slotInit = maxSlot - 1;
            } else
            {
                slotInit = minSlot;
            }

            while (!par4 && slotInit < maxSlot || par4 && slotInit >= minSlot)
            {
                slot = (Slot) this.inventorySlots.get(slotInit);
                itemstack1 = slot.getStack();

                if (itemstack1 == null)
                {
                    slot.putStack(stack.copy());
                    slot.onSlotChanged();
                    stack.stackSize = 0;
                    flag1 = true;
                    break;
                }

                if (par4)
                {
                    --slotInit;
                } else
                {
                    ++slotInit;
                }
            }
        }

        return flag1;
    }

    @Override
    public void putStackInSlot(int par1, ItemStack par2ItemStack)
    {
        super.putStackInSlot(par1, par2ItemStack);
    }

    public NBTTagCompound getCompound()
    {
        this.needsUpdate = false;
        return ((InventoryItem) inventory).writeToNBT();

    }

    @Override
    public ItemStack slotClick(int par1, int par2, int par3, EntityPlayer par4EntityPlayer)
    {
        return super.slotClick(par1, par2, par3, par4EntityPlayer);
    }
}
