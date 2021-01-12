package com.sirolf2009.necromancy.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import com.sirolf2009.necromancy.client.renderer.ItemScytheRenderer;
import com.sirolf2009.necromancy.core.proxy.ClientProxy;
import com.sirolf2009.necromancy.lib.ReferenceNecromancy;

public class ModelScytheSpecial extends ModelBase {
    // fields
    ModelRenderer HandleMiddle;
    ModelRenderer HandleBottom;
    ModelRenderer HandleTop;
    ModelRenderer Joint;
    ModelRenderer Blade;
    ModelRenderer BladeBaseRight;
    ModelRenderer BladeBaseLeft;

    private ResourceLocation metal = new ResourceLocation(ReferenceNecromancy.MOD_ID+":"+ReferenceNecromancy.LOC_RESOURCES_TEXTURES_MODELS + "/swordmetal.jpg");
    private ResourceLocation cloth = new ResourceLocation(ReferenceNecromancy.MOD_ID+":"+ReferenceNecromancy.LOC_RESOURCES_TEXTURES_MODELS + "/cloth.jpg");
    private ResourceLocation gun = new ResourceLocation(ReferenceNecromancy.MOD_ID+":"+ReferenceNecromancy.LOC_RESOURCES_TEXTURES_MODELS + "/guntex.jpg");
    
    public ItemScytheRenderer renderer;
    private IModelCustom scytheSpecial;

    public ModelScytheSpecial() {
        scytheSpecial = AdvancedModelLoader.loadModel("/assets/necromancy/models/scythe.obj");
    }

    public void render() {
        ClientProxy.mc.renderEngine.func_110577_a(metal);;
        scytheSpecial.renderPart("Blade_Blade_Material");
        ClientProxy.mc.renderEngine.func_110577_a(cloth);
        scytheSpecial.renderPart("Joint2_Joint2_Material");
        scytheSpecial.renderPart("Joint1_Joint1_Material");
        ClientProxy.mc.renderEngine.func_110577_a(gun);
        scytheSpecial.renderPart("Handle_Handle_Material");
    }
}
