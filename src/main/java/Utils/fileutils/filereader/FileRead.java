package Utils.fileutils.filereader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileRead {
	BufferedReader buffer;
	private String folder = "";

	public List<String> getAllLines() {
		List<String> lines = new ArrayList<String>();
		String line = "";
		while (line != null) {
			try {
				line = buffer.readLine();
				if (line == null) {
					break;
				}
				lines.add(line);

			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		return lines;
	}

	public void openFile(String filePath) {
		try {
			this.buffer = new BufferedReader(new FileReader(folder + "//" + filePath));
		} catch (FileNotFoundException e) {

			try {
				this.buffer = new BufferedReader(new FileReader(folder + "\\" + filePath));
			} catch (IOException e1) {
				System.out.println("Could not Read file " + folder + "\\" + filePath);
				e1.printStackTrace();
			}

		}
	}

	public void close() {
		try {
			this.buffer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

}
