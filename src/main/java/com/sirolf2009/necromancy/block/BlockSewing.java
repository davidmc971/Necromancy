package com.sirolf2009.necromancy.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.sirolf2009.necromancy.Necromancy;
import com.sirolf2009.necromancy.tileentity.TileEntitySewing;

public class BlockSewing extends BlockContainer {
    public static int guiID = 1;

    public BlockSewing(int par1, Material par2Material) {
        super(par1, par2Material);
        setCreativeTab(Necromancy.tabNecromancy);
        this.setBlockBounds(0.3F, 0.5F, 0.2F, 0.7F, 0.0F, 0.95F);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int idk, float what, float these, float are) {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if (tileEntity == null || player.isSneaking())
            return false;
        else {
            player.openGui(Necromancy.Instance, guiID, world, x, y, z);
            return true;
        }
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World var1) {
        return new TileEntitySewing();
    }
}
