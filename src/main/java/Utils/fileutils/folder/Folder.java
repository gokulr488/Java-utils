package Utils.fileutils.folder;

import java.io.File;

public class Folder {

	public  String[] getAllFilesIn(String folderPath) {
		File directory = new File(folderPath);
		return directory.list();
	}
}
