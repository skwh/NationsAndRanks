package net.skwh.NationsAndRanks.Config;

import java.io.File;

import net.skwh.NationsAndRanks.Core;
import net.skwh.NationsAndRanks.Utilites.SLAPI;

public class FileHandler extends Core {
	private File fNationList, fNationNameList, fUserList, fNationNames;
	public boolean checkExistingFile() { //returns true if a file with nations exists
		return fNationList.exists();
	}
	public void assignExistingFile() {
		getBaseCore().log("Settings files detected, loading those...");
		try {
			NationList = SLAPI.load(fNationList.getPath());
			Nation_NameList = SLAPI.load(fNationNameList.getPath());
			UserList = SLAPI.load(fUserList.getPath());
			NationNames = SLAPI.load(fNationNames.getPath());
		} catch (Exception e) {
			getBaseCore().log("Error loading settings files, " + e.getMessage());
		} finally {
			getBaseCore().log("Finished loading settings files.");
		}
	}
	public void saveCreatedFiles() {
		getBaseCore().log("Saving files... ");
		try {
			SLAPI.save(NationList, fNationList.getPath());
			SLAPI.save(Nation_NameList, fNationNameList.getPath());
			SLAPI.save(UserList, fUserList.getPath());
			SLAPI.save(NationNames, fNationNames.getPath());
		} catch (Exception e) {
			getBaseCore().log("Error saving settings files, " + e.getMessage());
		} finally {
			getBaseCore().log("Finished saving settings files.");
		}
	}
	public FileHandler() {
		fNationList = new File(getDataFolder() + File.separator + "NationList.bin");
		fNationNameList = new File(getDataFolder() + File.separator + "NationNameList.bin");
		fNationNames = new File(getDataFolder() + File.separator + "NationNames.bin");
		fUserList = new File(getDataFolder() + File.separator + "UserList.bin");
	}
}
