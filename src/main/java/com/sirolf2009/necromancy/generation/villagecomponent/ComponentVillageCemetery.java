package com.sirolf2009.necromancy.generation.villagecomponent;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.ComponentVillage;
import net.minecraft.world.gen.structure.ComponentVillageStartPiece;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

import com.sirolf2009.necroapi.NecroEntityBase;
import com.sirolf2009.necroapi.NecroEntityRegistry;
import com.sirolf2009.necromancy.lib.ConfigurationNecromancy;

public class ComponentVillageCemetery extends ComponentVillage {

    private int averageGroundLevel = -1;

    public ComponentVillageCemetery(ComponentVillageStartPiece par1ComponentVillageStartPiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5) {
        super(par1ComponentVillageStartPiece, par2);
        coordBaseMode = par5;
        boundingBox = par4StructureBoundingBox;
    }

    @SuppressWarnings("rawtypes")
    public static ComponentVillageCemetery func_74919_a(ComponentVillageStartPiece par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7) {
        StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, 0, 0, 17, 5, 18, par6);
        return canVillageGoDeeper(var8) && StructureComponent.findIntersecting(par1List, var8) == null ? new ComponentVillageCemetery(par0ComponentVillageStartPiece, par7, par2Random, var8, par6) : null;
    }

    @Override
    public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox) {
        if (averageGroundLevel < 0) {
            averageGroundLevel = this.getAverageGroundLevel(par1World, par3StructureBoundingBox);
            if (averageGroundLevel < 0)
                return true;
        }
        boundingBox.offset(0, averageGroundLevel - boundingBox.minY - 1, 0);

        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 17, 5, 18, 0, 0, false); // clear
                                                                                              // area
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 17, 0, 18, Block.grass.blockID, Block.grass.blockID, false); // create
                                                                                                                                  // ground
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 1, 0, 17, 1, 0, Block.cobblestoneWall.blockID, Block.cobblestone.blockID, false); // front
                                                                                                                                                 // wall
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 1, 17, 16, 1, 18, Block.cobblestoneWall.blockID, Block.cobblestone.blockID, false); // back
                                                                                                                                                   // wall
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 1, 0, 0, 1, 17, Block.cobblestoneWall.blockID, Block.cobblestone.blockID, false); // left
                                                                                                                                                 // wall
        fillWithBlocks(par1World, par3StructureBoundingBox, 17, 1, 0, 17, 1, 18, Block.cobblestoneWall.blockID, Block.cobblestone.blockID, false); // right
                                                                                                                                                   // wall
        for (int i = 0; i < 4; i++) { // left graves
            placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 3, 1, 2 + i * 2, par3StructureBoundingBox);
            if (par2Random.nextInt(10) == 1) {
                generateBodypartChest(par1World, par3StructureBoundingBox, par2Random, 3, 0, 2 + i * 2);
            }
            placeBlockAtCurrentPosition(par1World, Block.slowSand.blockID, 0, 4, 0, 2 + i * 2, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.slowSand.blockID, 0, 5, 0, 2 + i * 2, par3StructureBoundingBox);
        }
        for (int i = 0; i < 4; i++) { // right graves
            placeBlockAtCurrentPosition(par1World, Block.slowSand.blockID, 0, 11, 0, 2 + i * 2, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.slowSand.blockID, 0, 12, 0, 2 + i * 2, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 13, 1, 2 + i * 2, par3StructureBoundingBox);
            if (par2Random.nextInt(10) == 1) {
                generateBodypartChest(par1World, par3StructureBoundingBox, par2Random, 13, 0, 2 + i * 2);
            }
        }
        fillWithBlocks(par1World, par3StructureBoundingBox, 6, 1, 0, 10, 1, 0, 0, 0, false); // clear
                                                                                             // door
                                                                                             // area
        fillWithBlocks(par1World, par3StructureBoundingBox, 5, 1, 0, 5, 3, 0, Block.cobblestone.blockID, Block.cobblestone.blockID, false); // door
                                                                                                                                            // part
        fillWithBlocks(par1World, par3StructureBoundingBox, 6, 3, 0, 6, 4, 0, Block.cobblestone.blockID, Block.cobblestone.blockID, false); // door
                                                                                                                                            // part
        fillWithBlocks(par1World, par3StructureBoundingBox, 7, 4, 0, 7, 5, 0, Block.cobblestone.blockID, Block.cobblestone.blockID, false); // door
                                                                                                                                            // part
        placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 8, 5, 0, par3StructureBoundingBox); // door
                                                                                                                 // part
        fillWithBlocks(par1World, par3StructureBoundingBox, 9, 4, 0, 9, 5, 0, Block.cobblestone.blockID, Block.cobblestone.blockID, false); // door
                                                                                                                                            // part
        fillWithBlocks(par1World, par3StructureBoundingBox, 10, 3, 0, 10, 4, 0, Block.cobblestone.blockID, Block.cobblestone.blockID, false); // door
                                                                                                                                              // part
        fillWithBlocks(par1World, par3StructureBoundingBox, 11, 1, 0, 11, 3, 0, Block.cobblestone.blockID, Block.cobblestone.blockID, false); // door
                                                                                                                                              // part

        fillWithBlocks(par1World, par3StructureBoundingBox, 7, 0, 0, 9, 0, 14, Block.gravel.blockID, Block.gravel.blockID, false); // path

        fillWithBlocks(par1World, par3StructureBoundingBox, 3, 1, 11, 5, 3, 11, Block.cobblestone.blockID, Block.cobblestone.blockID, false); // tomb
                                                                                                                                              // front
                                                                                                                                              // wall
        fillWithBlocks(par1World, par3StructureBoundingBox, 3, 1, 15, 5, 3, 15, Block.cobblestone.blockID, Block.cobblestone.blockID, false); // tomb
                                                                                                                                              // back
                                                                                                                                              // wall
        fillWithBlocks(par1World, par3StructureBoundingBox, 2, 1, 12, 2, 3, 14, Block.cobblestone.blockID, Block.cobblestone.blockID, false); // tomb
                                                                                                                                              // left
                                                                                                                                              // wall
        fillWithBlocks(par1World, par3StructureBoundingBox, 6, 1, 12, 6, 3, 14, Block.cobblestone.blockID, Block.cobblestone.blockID, false); // tomb
                                                                                                                                              // right
                                                                                                                                              // wall
        fillWithBlocks(par1World, par3StructureBoundingBox, 3, 0, 12, 5, 0, 14, Block.cobblestone.blockID, Block.cobblestone.blockID, false); // tomb
                                                                                                                                              // floor
        fillWithBlocks(par1World, par3StructureBoundingBox, 3, 4, 12, 5, 4, 14, Block.cobblestone.blockID, Block.cobblestone.blockID, false); // tomb
                                                                                                                                              // roof
        placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 3, 1, 13, par3StructureBoundingBox); // tomb
                                                                                                                  // chest
                                                                                                                  // seperator
        for (int i = 0; i < 3; i++) {
            placeBlockAtCurrentPosition(par1World, Block.stairsCobblestone.blockID, getMetadataWithOffset(Block.stairsCobblestone.blockID, 3), 3 + i, 4, 11, par3StructureBoundingBox); // tomb
                                                                                                                                                                                        // front
                                                                                                                                                                                        // stair
                                                                                                                                                                                        // roof
        }
        for (int i = 0; i < 3; i++) {
            placeBlockAtCurrentPosition(par1World, Block.stairsCobblestone.blockID, getMetadataWithOffset(Block.stairsCobblestone.blockID, 2), 3 + i, 4, 15, par3StructureBoundingBox); // tomb
                                                                                                                                                                                        // back
                                                                                                                                                                                        // stair
                                                                                                                                                                                        // roof
        }
        for (int i = 0; i < 3; i++) {
            placeBlockAtCurrentPosition(par1World, Block.stairsCobblestone.blockID, getMetadataWithOffset(Block.stairsCobblestone.blockID, 0), 2, 4, 12 + i, par3StructureBoundingBox); // tomb
                                                                                                                                                                                        // left
                                                                                                                                                                                        // stair
                                                                                                                                                                                        // roof
        }
        for (int i = 0; i < 3; i++) {
            placeBlockAtCurrentPosition(par1World, Block.stairsCobblestone.blockID, getMetadataWithOffset(Block.stairsCobblestone.blockID, 1), 6, 4, 12 + i, par3StructureBoundingBox); // tomb
                                                                                                                                                                                        // right
                                                                                                                                                                                        // stair
                                                                                                                                                                                        // roof
        }
        placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 6, 0, 13, par3StructureBoundingBox); // tomb
                                                                                                                  // door
                                                                                                                  // support
        placeDoorAtCurrentPosition(par1World, par3StructureBoundingBox, par2Random, 6, 1, 13, getMetadataWithOffset(Block.doorWood.blockID, 0));

        fillWithBlocks(par1World, par3StructureBoundingBox, 10, 0, 11, 14, 0, 15, Block.cobblestone.blockID, Block.cobblestone.blockID, false); // house
                                                                                                                                                // floor
        fillWithBlocks(par1World, par3StructureBoundingBox, 10, 1, 11, 14, 3, 11, Block.planks.blockID, Block.planks.blockID, false); // house
                                                                                                                                      // front
                                                                                                                                      // wall
        fillWithBlocks(par1World, par3StructureBoundingBox, 10, 1, 15, 14, 3, 15, Block.planks.blockID, Block.planks.blockID, false); // house
                                                                                                                                      // back
                                                                                                                                      // wall
        fillWithBlocks(par1World, par3StructureBoundingBox, 10, 1, 11, 10, 3, 15, Block.planks.blockID, Block.planks.blockID, false); // house
                                                                                                                                      // left
                                                                                                                                      // wall
        fillWithBlocks(par1World, par3StructureBoundingBox, 14, 1, 11, 14, 3, 15, Block.planks.blockID, Block.planks.blockID, false); // house
                                                                                                                                      // right
                                                                                                                                      // wall
        fillWithBlocks(par1World, par3StructureBoundingBox, 10, 4, 11, 14, 4, 15, Block.wood.blockID, Block.wood.blockID, false); // house
                                                                                                                                  // roof
        fillWithBlocks(par1World, par3StructureBoundingBox, 11, 4, 12, 13, 4, 14, Block.planks.blockID, Block.planks.blockID, false); // house
                                                                                                                                      // roof
        fillWithBlocks(par1World, par3StructureBoundingBox, 10, 1, 11, 10, 3, 11, Block.cobblestone.blockID, Block.cobblestone.blockID, false); // house
                                                                                                                                                // front
                                                                                                                                                // left
                                                                                                                                                // corner
        fillWithBlocks(par1World, par3StructureBoundingBox, 14, 1, 11, 14, 3, 11, Block.cobblestone.blockID, Block.cobblestone.blockID, false); // house
                                                                                                                                                // front
                                                                                                                                                // right
                                                                                                                                                // corner
        fillWithBlocks(par1World, par3StructureBoundingBox, 10, 1, 15, 10, 3, 15, Block.cobblestone.blockID, Block.cobblestone.blockID, false); // house
                                                                                                                                                // back
                                                                                                                                                // left
                                                                                                                                                // corner
        fillWithBlocks(par1World, par3StructureBoundingBox, 14, 1, 15, 14, 3, 15, Block.cobblestone.blockID, Block.cobblestone.blockID, false); // house
                                                                                                                                                // back
                                                                                                                                                // right
                                                                                                                                                // corner
        placeDoorAtCurrentPosition(par1World, par3StructureBoundingBox, par2Random, 10, 1, 13, getMetadataWithOffset(Block.doorWood.blockID, 2));

        generateBodypartChest(par1World, par3StructureBoundingBox, par2Random, 3, 1, 12);
        generateBodypartChest(par1World, par3StructureBoundingBox, par2Random, 3, 1, 14);
        this.spawnVillagers(par1World, par3StructureBoundingBox, 11, 3, 12, 1);
        return true;
    }

    private void generateBodypartChest(World par1World, StructureBoundingBox structureBoundingBox, Random par2Random, int x, int y, int z, Object... content) {
        int mobs = NecroEntityRegistry.registeredEntities.size();
        ItemStack headItem, torsoItem, armLeftItem, armRightItem, legItem;
        while (true) {
            headItem = ((NecroEntityBase) NecroEntityRegistry.registeredEntities.values().toArray()[par2Random.nextInt(mobs)]).headItem;
            torsoItem = ((NecroEntityBase) NecroEntityRegistry.registeredEntities.values().toArray()[par2Random.nextInt(mobs)]).torsoItem;
            armLeftItem = ((NecroEntityBase) NecroEntityRegistry.registeredEntities.values().toArray()[par2Random.nextInt(mobs)]).armItem;
            armRightItem = ((NecroEntityBase) NecroEntityRegistry.registeredEntities.values().toArray()[par2Random.nextInt(mobs)]).armItem;
            legItem = ((NecroEntityBase) NecroEntityRegistry.registeredEntities.values().toArray()[par2Random.nextInt(mobs)]).legItem;
            if (headItem != null && torsoItem != null && armLeftItem != null && armRightItem != null && legItem != null) {
                break;
            }
        }
        generateChest(par1World, structureBoundingBox, x, y, z, 12, headItem, 13, torsoItem, 14, legItem, 4, armRightItem, 22, armLeftItem);
    }

    private void generateChest(World par1World, StructureBoundingBox structureBoundingBox, int x, int y, int z, Object... content) {
        int i1 = this.getXWithOffset(x, z);
        int j1 = this.getYWithOffset(y);
        int k1 = this.getZWithOffset(x, z);

        if (structureBoundingBox.isVecInside(i1, j1, k1) && par1World.getBlockId(i1, j1, k1) != Block.chest.blockID) {
            par1World.setBlock(i1, j1, k1, Block.chest.blockID, 0, 2);
            TileEntityChest tileentitychest = (TileEntityChest) par1World.getBlockTileEntity(i1, j1, k1);

            if (tileentitychest != null) {
                for (int i = 0; i < content.length; i++) {
                    if (content[i] instanceof Integer && content[i + 1] instanceof ItemStack) {
                        tileentitychest.setInventorySlotContents(Integer.valueOf(content[i].toString()), (ItemStack) content[i + 1]);
                    }
                }
            }
        }/*
          * 
          * if(par1World.getBlockId(x, y, z) != Block.chest.blockID &&
          * par1World.getBlockTileEntity(x, y, z) == null) {
          * placeBlockAtCurrentPosition(par1World, Block.chest.blockID, 0, x, y,
          * z, structureBoundingBox); int j1 =
          * this.getBiomeSpecificBlock(Block.chest.blockID, 0); int k1 =
          * this.getBiomeSpecificBlockMetadata(Block.chest.blockID, 0); int x1 =
          * this.getXWithOffset(j1, k1); int y1 = this.getYWithOffset(y); int z1
          * = this.getZWithOffset(j1, k1); TileEntityChest chest = null;
          * System.out.println(x1+", "+y1+", "+z1); chest = (TileEntityChest)
          * par1World.getBlockTileEntity(x1, y1, z1); for(int i=0;
          * i<content.length; i++) { if(content[i] instanceof Integer &&
          * content[i+1] instanceof ItemStack && chest != null) {
          * System.out.println(chest);
          * System.out.println(Integer.valueOf(content
          * [i].toString())+" "+(ItemStack) content[i+1]);
          * chest.setInventorySlotContents
          * (Integer.valueOf(content[i].toString()), (ItemStack) content[i+1]);
          * } else if(chest != null) { throw new
          * IllegalArgumentException("Wrong chest content pars used: "
          * +content[i]+" "+content[i+1]); } i+=1; } }
          */
    }

    /**
     * Returns the villager type to spawn in this component, based on the number
     * of villagers already spawned.
     */
    @Override
    protected int getVillagerType(int par1) {
        return ConfigurationNecromancy.NecroVillagerID;
    }
}
