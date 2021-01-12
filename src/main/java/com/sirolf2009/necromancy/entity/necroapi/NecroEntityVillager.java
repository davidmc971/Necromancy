package com.sirolf2009.necromancy.entity.necroapi;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import com.sirolf2009.necroapi.BodyPart;
import com.sirolf2009.necroapi.BodyPartLocation;
import com.sirolf2009.necroapi.NecroEntityBase;
import com.sirolf2009.necromancy.item.ItemBodyPart;

public class NecroEntityVillager extends NecroEntityBase {

    public NecroEntityVillager() {
        this("Villager");
    }

    public NecroEntityVillager(String name) {
        super(name);
        headItem = ItemBodyPart.getItemStackFromName("Villager Head", 1);
        torsoItem = ItemBodyPart.getItemStackFromName("Villager Torso", 1);
        armItem = ItemBodyPart.getItemStackFromName("Villager Arm", 1);
        legItem = ItemBodyPart.getItemStackFromName("Villager Legs", 1);
        texture = new ResourceLocation("textures/entity/villager/villager.png");
        textureHeight = 64;
    }

    @Override
    public void initRecipes() {
        initDefaultRecipes(Item.book);
    }

    @Override
    public BodyPart[] initHead(ModelBase model) {
        BodyPart villagerHead = new BodyPart(this, model, 0, 0);
        villagerHead.addBox(-4.0F, -6.0F, -4.0F, 8, 10, 8, 0.0F);
        BodyPart nose = new BodyPart(this, model, 24, 0);
        nose.setRotationPoint(0.0F, -2.0F, 0.0F);
        nose.addBox(-1.0F, 3.0F, -6.0F, 2, 4, 2, 0.0F);
        return new BodyPart[] { villagerHead, nose };
    }

    @Override
    public BodyPart[] initTorso(ModelBase model) {
        float[] headPos = { 4.0F, -4.0F, 2.0F };
        float[] armRightPos = { 8.0F, 0.0F, 0.0F };
        float[] armLeftPos = { -4.0F, 0.0F, 0.0F };
        BodyPart villagerBody = new BodyPart(this, armLeftPos, armRightPos, headPos, model, 16, 20);
        villagerBody.addBox(0.0F, 0.0F, -1.0F, 8, 12, 6, 0.0F);
        BodyPart villagerBody2 = new BodyPart(this, armLeftPos, armRightPos, headPos, model, 0, 38);
        villagerBody2.addBox(0.0F, 0.0F, -1.0F, 8, 18, 6, 0.5F);
        return new BodyPart[] { villagerBody, villagerBody2 };
    }

    @Override
    public BodyPart[] initArmLeft(ModelBase model) {
        BodyPart arm = new BodyPart(this, model, 44, 22);
        arm.addBox(0.0F, -2.0F, -2.0F, 4, 8, 4, 0.0F);
        arm.setRotationPoint(0.0F, 2.0F, 0.0F);
        arm.addBox(4.0F, 2F, -2F, 4, 4, 4, 0.0F);
        return new BodyPart[] { arm };
    }

    @Override
    public BodyPart[] initArmRight(ModelBase model) {
        BodyPart arm = new BodyPart(this, model, 44, 22);
        arm.setRotationPoint(0.0F, 2.0F, 0.0F);
        arm.addBox(0.0F, -2.0F, -2.0F, 4, 8, 4, 0.0F);
        arm.addBox(-4.0F, 2F, -2F, 4, 4, 4, 0.0F);
        return new BodyPart[] { arm };
    }

    @Override
    public BodyPart[] initLegs(ModelBase model) {
        float[] torsoPos = { -4.0F, 0.0F, -2.0F };
        BodyPart rightVillagerLeg = new BodyPart(this, torsoPos, model, 0, 22);
        rightVillagerLeg.setRotationPoint(-2.0F, 12.0F + 0.0F, 0.0F);
        rightVillagerLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        BodyPart leftVillagerLeg = new BodyPart(this, torsoPos, model, 0, 22);
        leftVillagerLeg.mirror = true;
        leftVillagerLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
        leftVillagerLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        return new BodyPart[] { rightVillagerLeg, leftVillagerLeg };
    }

    @Override
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity entity, BodyPart[] part, BodyPartLocation location) {
        if (location == BodyPartLocation.Head) {
            part[0].rotateAngleY = par4 / (180F / (float) Math.PI);
            part[0].rotateAngleX = par5 / (180F / (float) Math.PI);
            part[1].rotateAngleY = par4 / (180F / (float) Math.PI);
            part[1].rotateAngleX = par5 / (180F / (float) Math.PI);
        }
        if (location == BodyPartLocation.ArmLeft || location == BodyPartLocation.ArmRight) {
            part[0].rotateAngleX = -0.75F;
        }
        if (location == BodyPartLocation.Legs) {
            part[0].rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float) Math.PI) * 1.4F * par2 * 0.5F;
            part[1].rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2 * 0.5F;
        }
    }

    @Override
	public void setAttributes(EntityLivingBase minion, BodyPartLocation location) {
		if(location == BodyPartLocation.Head) {
			head[0].attributes.func_111150_b(SharedMonsterAttributes.field_111267_a).func_111128_a(2.0D); //health
			head[0].attributes.func_111150_b(SharedMonsterAttributes.field_111265_b).func_111128_a(16.0D); //followrange
			head[0].attributes.func_111150_b(SharedMonsterAttributes.field_111266_c).func_111128_a(0.0D); //knockback res
			head[0].attributes.func_111150_b(SharedMonsterAttributes.field_111263_d).func_111128_a(0.0D); //speed
			head[0].attributes.func_111150_b(SharedMonsterAttributes.field_111264_e).func_111128_a(0.0D); //damage
		} else if(location == BodyPartLocation.Torso) {
			torso[0].attributes.func_111150_b(SharedMonsterAttributes.field_111267_a).func_111128_a(12.0D); //health
			torso[0].attributes.func_111150_b(SharedMonsterAttributes.field_111265_b).func_111128_a(0.0D); //followrange
			torso[0].attributes.func_111150_b(SharedMonsterAttributes.field_111266_c).func_111128_a(0.0D); //knockback res
			torso[0].attributes.func_111150_b(SharedMonsterAttributes.field_111263_d).func_111128_a(0.0D); //speed
			torso[0].attributes.func_111150_b(SharedMonsterAttributes.field_111264_e).func_111128_a(0.0D); //damage
		} else if(location == BodyPartLocation.ArmLeft) {
			armLeft[0].attributes.func_111150_b(SharedMonsterAttributes.field_111267_a).func_111128_a(2.0D); //health
			armLeft[0].attributes.func_111150_b(SharedMonsterAttributes.field_111265_b).func_111128_a(0.0D); //followrange
			armLeft[0].attributes.func_111150_b(SharedMonsterAttributes.field_111266_c).func_111128_a(0.0D); //knockback res
			armLeft[0].attributes.func_111150_b(SharedMonsterAttributes.field_111263_d).func_111128_a(0.0D); //speed
			armLeft[0].attributes.func_111150_b(SharedMonsterAttributes.field_111264_e).func_111128_a(1.0D); //damage
		} else if(location == BodyPartLocation.ArmRight) {
			armRight[0].attributes.func_111150_b(SharedMonsterAttributes.field_111267_a).func_111128_a(2.0D); //health
			armRight[0].attributes.func_111150_b(SharedMonsterAttributes.field_111265_b).func_111128_a(0.0D); //followrange
			armRight[0].attributes.func_111150_b(SharedMonsterAttributes.field_111266_c).func_111128_a(0.0D); //knockback res
			armRight[0].attributes.func_111150_b(SharedMonsterAttributes.field_111263_d).func_111128_a(0.0D); //speed
			armRight[0].attributes.func_111150_b(SharedMonsterAttributes.field_111264_e).func_111128_a(1.0D); //damage
		} else if(location == BodyPartLocation.Legs) {
			legs[0].attributes.func_111150_b(SharedMonsterAttributes.field_111267_a).func_111128_a(2.0D); //health
			legs[0].attributes.func_111150_b(SharedMonsterAttributes.field_111265_b).func_111128_a(0.0D); //followrange
			legs[0].attributes.func_111150_b(SharedMonsterAttributes.field_111266_c).func_111128_a(0.0D); //knockback res
			legs[0].attributes.func_111150_b(SharedMonsterAttributes.field_111263_d).func_111128_a(0.5D); //speed
			legs[0].attributes.func_111150_b(SharedMonsterAttributes.field_111264_e).func_111128_a(0.0D); //damage
		}
	}
}
