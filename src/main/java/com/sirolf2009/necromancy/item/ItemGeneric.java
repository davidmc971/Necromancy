package com.sirolf2009.necromancy.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import com.sirolf2009.necromancy.Necromancy;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemGeneric extends Item {

    public ItemGeneric(int par1) {
        super(par1);
        icons = new Icon[names.length];
        setHasSubtypes(true);
        setMaxDamage(0);
        setCreativeTab(Necromancy.tabNecromancy);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        if (stack.getItemDamage() == 0)
            if (player.inventory.consumeInventoryItem(Item.glassBottle.itemID)) {
                stack.stackSize--;
                if (!player.inventory.addItemStackToInventory(new ItemStack(ItemNecromancy.genericItems, 1, 2))) {
                    player.dropPlayerItem(new ItemStack(ItemNecromancy.genericItems, 1, 2));
                }
            }
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    /**
     * Returns True is the item is renderer in full 3D when hold.
     */
    public boolean isFull3D() {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    /**
     * Returns true if this item should be rotated by 180 degrees around the Y axis when being held in an entities
     * hands.
     */
    public boolean shouldRotateAroundWhenRendering() {
        return true;
    }

    @Override
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
        if (par1ItemStack.getItemDamage() > names.length) {
            par1ItemStack.setItemDamage(2);
        }
    }

    @Override
    public String getItemDisplayName(ItemStack par1ItemStack) {
        return new StringBuilder().append("").append(names[par1ItemStack.getItemDamageForDisplay()]).toString();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) {
        for (int var4 = 0; var4 < names.length; var4++) {
            par3List.add(new ItemStack(par1, 1, var4));
        }
    }

    public static ItemStack getItemStackFromName(String name) {
        for (int i = 0; i < names.length; i++)
            if (names[i].equalsIgnoreCase(name))
                return new ItemStack(ItemNecromancy.genericItems, 1, i);
        return null;
    }

    public static ItemStack getItemStackFromName(String name, int amount) {
        for (int i = 0; i < names.length; i++)
            if (names[i].equalsIgnoreCase(name))
                return new ItemStack(ItemNecromancy.genericItems, amount, i);
        return null;
    }

    @Override
    public void registerIcons(IconRegister iconRegister) {
        for (int index = 0; index < names.length; index++) {
            String path = names[index].replace(" ", "").toLowerCase();
            icons[index] = iconRegister.registerIcon("necromancy:"+path.toLowerCase());
        }
        tearBlood = iconRegister.registerIcon("necromancy:BloodTear");
        tearNormal = iconRegister.registerIcon("necromancy:Tear");
    }

    @Override
    public Icon getIconFromDamage(int par1) {
        return icons[par1];
    }

    private Icon[] icons;
    public static String names[] = { "Bone Needle", "Soul in a Jar", "Jar of Blood", "Brain on a Stick" };
    public static Icon tearNormal;
    public static Icon tearBlood;
}
