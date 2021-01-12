package com.sirolf2009.necromancy.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import com.sirolf2009.necromancy.client.model.ModelNecronomicon;
import com.sirolf2009.necromancy.core.proxy.ClientProxy;
import com.sirolf2009.necromancy.item.ItemNecronomicon;
import com.sirolf2009.necromancy.lib.ReferenceNecromancy;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ItemNecronomiconRenderer implements IItemRenderer {

    public ModelNecronomicon modelInteractive = new ModelNecronomicon();
    public ModelNecronomicon modelStatic = new ModelNecronomicon();

    public String[] leftPageContent = {}, rightPageContent = {};

    FontRenderer font = Minecraft.getMinecraft().fontRenderer;

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        if (type.equals(ItemRenderType.ENTITY)) {
            renderNecronomiconStatic(0F, 0.2F, 0F, 180F, 1F, 1F, 0.004F);
        } else if (type.equals(ItemRenderType.EQUIPPED)) {
            renderNecronomiconInteractive(0.8F, 1.3F, 0.6F, 180F, 180F, 0F, 0.009F, (ItemNecronomicon) item.getItem());
        } else if (type.equals(ItemRenderType.EQUIPPED_FIRST_PERSON)) {
            renderNecronomiconInteractive(0.8F, 1F, -1F, 20F, 100F, 160F, 0.01F, (ItemNecronomicon) item.getItem());
        } else if (type.equals(ItemRenderType.INVENTORY)) {
            renderNecronomiconStatic(-0.2F, 0.2F, 0.2F, 100F, -20F, -20F, 0.007F);
        }
    }

    private void renderNecronomiconInteractive(float posX, float posY, float posZ, float rotX, float rotY, float rotZ, float scale, ItemNecronomicon book) {
        modelInteractive.setRotationAngles(100, 0, 0, 0, 0, 0, null);
        GL11.glPushMatrix(); // start
        GL11.glTranslatef(posX, posY, posZ);
        GL11.glRotatef(rotX, 1, 0, 0);
        GL11.glRotatef(rotY, 0, 1, 0);
        GL11.glRotatef(rotZ, 0, 0, 1);
        GL11.glScalef(scale, scale, scale);
        ClientProxy.mc.renderEngine.func_110577_a(ReferenceNecromancy.TEXTURES_MODELS_NECRONOMICON);
        book.page = 0;
        // GL11.glRotatef((float)(book.page/(book.page+1)), 0, -1, 0);
        modelInteractive.render(null, 1, 0, 0, (float) (book.page * 1.6), 1, 1);
        GL11.glRotatef(-54, 0, 1, 0);
        GL11.glTranslatef(8, -20, -30);
        GL11.glScalef(0.8F, 0.8F, 0.8F);
        for (int i = 0; i < rightPageContent.length; i++) {
            font.drawString(rightPageContent[i], 10, -50 + i * 10, 7208960);
        }

        GL11.glRotatef(-69, 0, 1, 0);
        GL11.glTranslatef(-104, 0, 3);
        for (int i = 0; i < leftPageContent.length; i++) {
            font.drawString(leftPageContent[i], 10, -50 + i * 10, 7208960);
        }
        GL11.glPopMatrix();// end
    }

    private void renderNecronomiconStatic(float posX, float posY, float posZ, float rotX, float rotY, float rotZ, float scale) {
        modelInteractive.setRotationAngles(100, 0, 0, 0, 0, 0, null);
        GL11.glPushMatrix(); // start
        GL11.glTranslatef(posX, posY, posZ);
        GL11.glRotatef(rotX, 1, 0, 0);
        GL11.glRotatef(rotY, 0, 1, 0);
        GL11.glRotatef(rotZ, 0, 0, 1);
        GL11.glScalef(scale, scale, scale);
        ClientProxy.bindTexture(ReferenceNecromancy.TEXTURES_MODELS_NECRONOMICON);
        modelStatic.render(null, 0, 0, 0, 0f, 1, 1);
        GL11.glPopMatrix();// end
    }
}
