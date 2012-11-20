package net.skwh.NationsAndRanks.Config;

import java.io.File;

import net.skwh.NationsAndRanks.Core;
import net.skwh.NationsAndRanks.Utilites.SLAPI;

public class FileHandler extends Core {
	public boolean checkExistingFile() { //returns true if a file with nations exists
		boolean failed = false;
		try {
			SLAPI.load(getDataFolder() + File.separator + "NationList.bin");
		} catch (Exception e) {
			getBaseCore().log("There was a problem loading the settings: "+ e.getMessage());
			failed = true;
		}
		return failed;
	}
	public void assignExistingFile() {
		getBaseCore().log("Settings files detected, loading those...");
		try {
			NationList = SLAPI.load(getDataFolder() + File.separator + "NationList.bin");
			Nation_NameList = SLAPI.load(getDataFolder() + File.separator + "NationNameList.bin");
			UserList = SLAPI.load(getDataFolder() + File.separator + "UserList.bin");
			NationNames = SLAPI.load(getDataFolder() + File.separator + "NationNames.bin");
		} catch (Exception e) {
			getBaseCore().log("Error loading settings files, " + e.getMessage());
		} finally {
			getBaseCore().log("Finished loading settings files.");
		}
	}
	public void saveCreatedFiles() {
		getBaseCore().log("Saving files... ");
		try {
			SLAPI.save(NationList, getDataFolder() + File.separator + "NationList.bin");
			SLAPI.save(Nation_NameList, getDataFolder() + File.separator + "NationNameList.bin");
			SLAPI.save(UserList, getDataFolder() + File.separator + "UserList.bin");
			SLAPI.save(NationNames, getDataFolder() + File.separator + "NationNames.bin");
		} catch (Exception e) {
			getBaseCore().log("Error saving settings files, " + e.getMessage());
		} finally {
			getBaseCore().log("Finished saving settings files.");
		}
	}
}
