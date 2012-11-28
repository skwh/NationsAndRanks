package net.skwh.NationsAndRanks.Config;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;

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
	
	public void createNewFiles() {
		getBaseCore().log("Creating new files...");
		Path pNationList = fNationList.toPath();
		Path pNationNameList = fNationList.toPath();
		Path pNationNames = fNationNames.toPath();
		Path pUserList = fUserList.toPath();
		
		try {
			Files.createFile(pNationList);
			Files.createFile(pNationNameList);
			Files.createFile(pNationNames);
			Files.createFile(pUserList);
		} catch (FileAlreadyExistsException e) {
			getBaseCore().log("Error creating new files (createNewFiles called erroneously): " + e.getMessage());
		}catch (IOException e) {
			getBaseCore().log("Error creating new files: " + e.getMessage());
		}
	}
	
	public FileHandler() {
		try {
			fNationList = new File("\\NationsAndRanks\\NationList.bin");
			fNationNameList = new File("\\NationsAndRanks\\NationList.bin");
			fNationNames = new File("\\NationsAndRanks\\NationList.bin");
			fUserList = new File("\\NationsAndRanks\\NationList.bin");
		} catch (NullPointerException e) {
		}
	}
}
