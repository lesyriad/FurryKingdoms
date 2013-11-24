/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.FurryKingdoms;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import Reika.DragonAPI.Base.DragonAPIMod;
import Reika.DragonAPI.Instantiable.IO.ControlledConfig;
import Reika.DragonAPI.Interfaces.ConfigList;
import Reika.DragonAPI.Interfaces.IDRegistry;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.FurryKingdoms.Registry.FurryBlocks;
import Reika.FurryKingdoms.Registry.FurryItems;
import Reika.FurryKingdoms.Registry.FurryOptions;
import Reika.FurryKingdoms.Registry.SpeciesType;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class FurryConfig extends ControlledConfig {

	public FurryConfig(DragonAPIMod mod, ConfigList[] option, IDRegistry[] blocks, IDRegistry[] items, IDRegistry[] id, int cfg) {
		super(mod, option, blocks, items, id, cfg);
	}

	private int[] cityIDs = new int[SpeciesType.speciesList.length];

	public static final boolean debugmode = false;

	//Initialization of the config
	@Override
	public void initProps(FMLPreInitializationEvent event) {

		super.initProps(event);

		config.load();
		for (int i = 0; i < SpeciesType.speciesList.length; i++) {
			String name = SpeciesType.speciesList[i].name();
			cityIDs[i] = config.get("City Biome IDs", name, 100+i).getInt();
		}

		/*******************************/
		//save the data
		config.save();
	}

	@Override
	protected void resetConfigFile() {
		String path = this.getConfigPath()+"_Old_Config_Backup.txt";
		File backup = new File(path);
		if (backup.exists())
			backup.delete();
		try {
			ReikaJavaLibrary.pConsole(configMod.getDisplayName().toUpperCase()+": Writing Backup File to "+path);
			ReikaJavaLibrary.pConsole(configMod.getDisplayName().toUpperCase()+": Use this to restore custom IDs if necessary.");
			backup.createNewFile();
			if (!backup.exists())
				ReikaJavaLibrary.pConsole(configMod.getDisplayName().toUpperCase()+": Could not create backup file at "+path+"!");
			else {
				PrintWriter p = new PrintWriter(backup);
				p.println("#####----------THESE ARE ALL THE OLD CONFIG SETTINGS YOU WERE USING----------#####");
				p.println("#####---IF THEY DIFFER FROM THE DEFAULTS, YOU MUST RE-EDIT THE CONFIG FILE---#####");
				for (int i = 0; i < FurryOptions.optionList.length; i++) {
					String label = FurryOptions.optionList[i].getLabel();
					if (FurryOptions.optionList[i].isBoolean())
						controls[i] = FurryOptions.optionList[i].setState(config);
					if (FurryOptions.optionList[i].isNumeric())
						controls[i] = FurryOptions.optionList[i].setValue(config);
					if (FurryOptions.optionList[i].isDecimal())
						controls[i] = FurryOptions.optionList[i].setDecimal(config);
					p.println(label+": "+String.valueOf(controls[i]));
				}

				for (int i = 0; i < FurryBlocks.blockList.length; i++) {
					String name = FurryBlocks.blockList[i].getBlockVariableName();
					blockIDs[i] = config.get("Machine Block IDs", name, 490+i).getInt();
					p.println(name+": "+String.valueOf(blockIDs[i]));
				}

				for (int i = 0; i < FurryItems.itemList.length; i++) {
					String name = FurryItems.itemList[i].getBasicName();
					itemIDs[i] = config.get("Item IDs", name, 30500+i).getInt();
					p.println(name+": "+String.valueOf(itemIDs[i]));
				}

				p.close();
			}
		}
		catch (IOException e) {
			ReikaJavaLibrary.pConsole(configMod.getDisplayName().toUpperCase()+": Could not create backup file due to IOException!");
			e.printStackTrace();
		}
		configFile.delete();
	}

	public int getCityID(int ordinal) {
		return cityIDs[ordinal];
	}
}
