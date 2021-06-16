package Utils.fileutils.folder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Utils.fileutils.Files;

public class Folder {

	private static Logger logger = LoggerFactory.getLogger(Folder.class);
	private List<String> listOfAllFiles;
	private List<String> processedFiles;

	public static void createFolder(String folderName) {
		new File(folderName).mkdir();
	}

	public static void createIfNotExists(String folderName) {
		File folder = new File(folderName);
		if (!folder.exists()) {
			folder.mkdir();
		}
	}

	public String[] getAllFilesIn(String folderPath) {
		File directory = new File(folderPath);
		return directory.list();
	}

	public boolean isDotDonePresentFor(String filename, String folderPath) {

		for (String fileInFolder : getAllFilesIn(folderPath)) {
			if (fileInFolder.equals(filename + ".done")) {
				return true;
			}
		}
		return false;

	}

	/**
	 * Use New Folder object rather than taking from static Files class if multiple
	 * folders are being processed simultaneously
	 */
	public String takeFileForProcessing(String folder) {
		if (listOfAllFiles == null) {
			listOfAllFiles = new ArrayList<String>();
		}
		if (processedFiles == null) {
			processedFiles = new ArrayList<String>();
		}
		addNewFilesToMap(folder);
		for (String file : listOfAllFiles) {
			if (Files.getFolderUtils().isDotDonePresentFor(file, folder) && !processedFiles.contains(file)) {
				processedFiles.add(file);
				return file;
			}

		}

		return null;

	}

//	private void createProcessingFolder(String folder) {
//		if (map.get("Processing") == null) {
//			logger.info("Creating a Processing folder in {}", folder);
//			File file = new File(folder + "Processing");
//			file.mkdir();
//		}
//
//	}

	private void addNewFilesToMap(String folder) {
		String[] files = getAllFilesIn(folder);
		if (files.length > 0) {
			for (String file : files) {
				if (!listOfAllFiles.contains(file) && !file.contains(".done")) {
					logger.info("Found new file {} in folder {}", file, folder);
					listOfAllFiles.add(file);
				}

			}
		}
	}
}
