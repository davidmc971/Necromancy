package com.sirolf2009.necroapi;

import java.io.File;
import java.util.HashMap;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.SharedMonsterAttributes;

/**
 * The registry class to register necro mobs
 * 
 * @author sirolf2009
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class NecroEntityRegistry {

	/**
	 * Call this to register your necro mob
	 * 
	 * @param the
	 *            mob to be registered
	 */
	public static void RegisterEntity(NecroEntityBase data) {
		if (data.isNecromancyInstalled && !registeredEntities.containsKey(data.mobName)) {
			registeredEntities.put(data.mobName, data);
			if(data instanceof ISkull) {
				registeredSkullEntities.put(data.mobName, (ISkull) data);
			}
		}
	}

	/**
	 * The map containing the registered mobs
	 */
	public static HashMap<String, NecroEntityBase> registeredEntities = new HashMap<String, NecroEntityBase>();
	/**
	 * The map containing the registered mobs that have registered a skull
	 */
	public static HashMap<String, ISkull> registeredSkullEntities = new HashMap<String, ISkull>();
}
