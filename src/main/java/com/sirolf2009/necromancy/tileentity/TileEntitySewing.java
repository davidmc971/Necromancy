package com.sirolf2009.necromancy.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntitySewing extends TileEntity implements IInventory {

    private ItemStack[] inventory;

    public TileEntitySewing() {
        setInventory(new ItemStack[2]);
    }

    @Override
    public int getSizeInventory() {
        return getInventory().length;
    }

    @Override
    public ItemStack getStackInSlot(int slotIndex) {
        return getInventory()[slotIndex];
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        getInventory()[slot] = stack;
        if (stack != null && stack.stackSize > getInventoryStackLimit()) {
            stack.stackSize = getInventoryStackLimit();
        }
    }

    @Override
    public ItemStack decrStackSize(int slotIndex, int amount) {
        ItemStack stack = getStackInSlot(slotIndex);

        if (stack != null) {
            if (stack.stackSize <= amount) {
                setInventorySlotContents(slotIndex, null);
            } else {
                stack = stack.splitStack(amount);
                if (stack.stackSize == 0) {
                    setInventorySlotContents(slotIndex, null);
                }
            }
        }
        return stack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slotIndex) {
        ItemStack stack = getStackInSlot(slotIndex);

        if (stack != null) {
            setInventorySlotContents(slotIndex, null);
        }

        return stack;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
    }

    @Override
    public void openChest() {
    }

    @Override
    public void closeChest() {
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);

        NBTTagList tagList = tagCompound.getTagList("Inventory");

        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);

            byte slot = tag.getByte("Slot");

            if (slot >= 0 && slot < getInventory().length) {
                getInventory()[slot] = ItemStack.loadItemStackFromNBT(tag);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);

        NBTTagList itemList = new NBTTagList();

        for (int i = 0; i < getInventory().length; i++) {
            ItemStack stack = getInventory()[i];

            if (stack != null) {
                NBTTagCompound tag = new NBTTagCompound();

                tag.setByte("Slot", (byte) i);
                stack.writeToNBT(tag);
                itemList.appendTag(tag);
            }
        }

        tagCompound.setTag("Inventory", itemList);
    }

    @Override
    public String getInvName() {
        return "TileEntitySewing";
    }

    public ItemStack[] getInventory() {
        return inventory;
    }

    public void setInventory(ItemStack[] inventory) {
        this.inventory = inventory;
    }

    @Override
    public boolean isInvNameLocalized() {
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return false;
    }

    @Override
    public boolean canUpdate()
    {
        return false;
    }
}