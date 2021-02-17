package Utils.fileutils.filewriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import Utils.Utils;

public class FileWrite {
	BufferedWriter buffer;
	private String folder;
	private String file = "";

	public void write(List<String> data) {
		for (String line : data) {
			try {
				buffer.write(line);
				buffer.newLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		data.clear();
	}

	public void write(String data) {

		try {
			buffer.write(data);
			buffer.newLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createFile(String filePath) {
		if (folder == null) {
			file = filePath;
		} else {
			file = folder + "//" + filePath;
		}
		try {
			Utils.logger.info("Creating File {}", file);
			this.buffer = new BufferedWriter(new FileWriter(file));
		} catch (IOException e) {
			file = file.replaceAll("//", "\\\\");
			try {
				this.buffer = new BufferedWriter(new FileWriter(file));
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}
	}

	public void appendFile(String filePath) {
		try {
			this.buffer = new BufferedWriter(new FileWriter("output//" + filePath, true));
		} catch (IOException e) {
			try {
				this.buffer = new BufferedWriter(new FileWriter("output\\" + filePath, true));
			} catch (IOException e1) {
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
