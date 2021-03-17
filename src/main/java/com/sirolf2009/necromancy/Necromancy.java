package com.sirolf2009.necromancy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.server.dedicated.PropertyManager;
import net.minecraftforge.common.MinecraftForge;

import com.sirolf2009.necroapi.NecroEntityBase;
import com.sirolf2009.necromancy.block.BlockNecromancy;
import com.sirolf2009.necromancy.core.handler.EventHandler;
import com.sirolf2009.necromancy.core.handler.PacketHandler;
import com.sirolf2009.necromancy.core.proxy.CommonProxy;
import com.sirolf2009.necromancy.creativetab.CreativeTabNecro;
import com.sirolf2009.necromancy.entity.EntityNecromancy;
import com.sirolf2009.necromancy.generation.WorldGenerator;
import com.sirolf2009.necromancy.item.ItemNecroSkull;
import com.sirolf2009.necromancy.item.ItemNecromancy;
import com.sirolf2009.necromancy.lib.ConfigurationNecromancy;
import com.sirolf2009.necromancy.lib.ReferenceNecromancy;

import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.ServerStarted;
import net.minecraftforge.fml.common.Mod.ServerStarting;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkMod;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.LanguageRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

@Mod(Necromancy.MOD_ID)
public class Necromancy {
    // General meta info
    public static final String MOD_ID = "necromancy2";
    public static final String MOD_NAME = "Necromancy 2";
    public static final String MOD_VERSION = "@VERSION@";
    public static final String MOD_FINGERPRINT = "@FINGERPRINT@";

    // TODO: Initialize network stuff

    public static final CreativeTabs tabNecromancy = new CreativeTabNecro(CreativeTabs.getNextID(), "Necromancy", 1)
            .setBackgroundImageName("necro_gui.png");
    public static final CreativeTabs tabNecromancyBodyParts = new CreativeTabNecro(CreativeTabs.getNextID(),
            "BodyParts", 2).setBackgroundImageName("necro_gui.png");

    public int scentProgram;

    public static List<String> specialFolk = new ArrayList<String>();

    public static int maxSpawn = -1;

    public static Logger loggerNecromancy;

    public static PacketHandler PacketHandler = new PacketHandler();
    public static EventHandler EventHandler = new EventHandler();

    @SidedProxy(clientSide = "com.sirolf2009.necromancy.core.proxy.ClientProxy", serverSide = "com.sirolf2009.necromancy.core.proxy.CommonProxy")
    public static CommonProxy Proxy;

    @Mod.Instance("necromancy")
    public static Necromancy Instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        loggerNecromancy = Logger.getLogger("necromancy");
        loggerNecromancy.setParent(FMLLog.getLogger());

        ConfigurationNecromancy.initProperties(event);

        NecroEntityBase.organID = ConfigurationNecromancy.OrgansID;

        MinecraftForge.EVENT_BUS.register(EventHandler);

        try {
            URL url = new URL("https://dl.dropboxusercontent.com/u/50553915/necromancy/specialFolk.txt");
            Scanner s = new Scanner(url.openStream());
            while (s.hasNext()) {
                specialFolk.add(s.nextLine());
            }
            s.close();
        } catch (IOException e) {
            System.err.println("not connected to the internet, special scythes are de-activated");
        }

        if (ConfigurationNecromancy.InitDuringPreInit) {
            init();
        }

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        if (!ConfigurationNecromancy.InitDuringPreInit) {
            init();
        }
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        ItemNecroSkull.initSkulls();
        Proxy.refreshTextures();
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        EventHandler.initCommands(event);
    }

    @Mod.EventHandler
    public void serverStarted(FMLServerStartedEvent event) {
        if (new File("server.properties").exists()) {
            PropertyManager manager = new PropertyManager(new File("server.properties"), null);
            maxSpawn = manager.getIntProperty("max_minion_spawn", -1);
        }
    }

    private void init() {
        LanguageRegistry.instance().addStringLocalization("itemGroup.Necromancy", "en_US", "Necromancy");
        LanguageRegistry.instance().addStringLocalization("itemGroup.BodyParts", "en_US", "Bodyparts");
        ItemNecromancy.initItems();
        EntityNecromancy.initEntities();
        BlockNecromancy.initBlocks();
        Proxy.init();
        VillagerRegistry.instance().registerVillageCreationHandler(PacketHandler);
        ArrayList<Class<PacketHandler>> villageComponentsList = new ArrayList<Class<PacketHandler>>();
        villageComponentsList.add(PacketHandler.class);

        GameRegistry.registerWorldGenerator(new WorldGenerator());
    }

    private int createShader(String filename, int shaderType) throws Exception {
        int shader = 0;
        try {
            shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);

            if (shader == 0)
                return 0;

            ARBShaderObjects.glShaderSourceARB(shader, readFileAsString(filename));
            ARBShaderObjects.glCompileShaderARB(shader);

            if (ARBShaderObjects.glGetObjectParameteriARB(shader,
                    ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL11.GL_FALSE)
                throw new RuntimeException(
                        "Error creating shader: " + ARBShaderObjects.glGetInfoLogARB(shader, ARBShaderObjects
                                .glGetObjectParameteriARB(shader, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB)));

            return shader;
        } catch (Exception exc) {
            ARBShaderObjects.glDeleteObjectARB(shader);
            throw exc;
        }
    }

    private String readFileAsString(String filename) throws Exception {
        StringBuilder source = new StringBuilder();

        FileInputStream in = new FileInputStream(getClass().getClassLoader().getResource(filename).toURI().getPath());

        Exception exception = null;

        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            Exception innerExc = null;
            try {
                String line;
                while ((line = reader.readLine()) != null)
                    source.append(line).append('\n');
            } catch (Exception exc) {
                exception = exc;
            } finally {
                try {
                    reader.close();
                } catch (Exception exc) {
                    if (innerExc == null)
                        innerExc = exc;
                    else
                        exc.printStackTrace();
                }
            }

            if (innerExc != null)
                throw innerExc;
        } catch (Exception exc) {
            exception = exc;
        } finally {
            try {
                in.close();
            } catch (Exception exc) {
                if (exception == null)
                    exception = exc;
                else
                    exc.printStackTrace();
            }

            if (exception != null)
                throw exception;
        }

        return source.toString();
    }
}