package Utils.fileutils.filereader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileRead {
	private BufferedReader buffer;
	private String folder;
	private String file = "";
	private int noOflines = 0;

	private static Logger logger = LoggerFactory.getLogger(FileRead.class);

	public List<String> readAllLines() {
		logger.debug("Reading all lines from file {}", file);
		List<String> lines = new ArrayList<String>();
		String line = "";
		while (line != null) {
			noOflines++;
			try {
				line = buffer.readLine();
				if (line == null) {
					break;
				}
				lines.add(line);

			} catch (IOException e) {
				logger.error("Unable to Read all lines from file {}", file, e);
			}
		}
		return lines;
	}

	public String readAsSingleString() {
		logger.debug("Reading all lines from file {} to single String", file);
		String data = "";
		String line = "";
		try {
			while ((line = buffer.readLine()) != null) {
				noOflines++;
				data = data + "\n" + line;

			}
		} catch (IOException e) {
			logger.error("Unable to Read all lines from file {}", file, e);
		}

		return data;
	}

	public List<String> readBinaryFile() {

		// TODO
		return null;

	}

	public String getLineIn(int lineNumber) {

		String line = null;
		// TODO
		return line;

	}

	public int getNoOfLines() {
		if (noOflines == 0) {
			try {
				while (buffer.readLine() != null)
					noOflines++;

			} catch (IOException e) {
				logger.error("Could not find the No:Of lines in file {}", file);
			}
		}
		logger.info("No Of lines in file {} = {}", file, noOflines);
		return noOflines;

	}

	public void openResourceFile(String filePath) {
		logger.debug("Opening Resource file {}", filePath);
		InputStream inputStream = getClass().getResourceAsStream("/" + filePath);
		this.buffer = new BufferedReader(new InputStreamReader(inputStream));
	}

	public void openFile(String filePath) {
		if (folder == null) {
			file = filePath;
		} else {
			file = folder + "//" + filePath;
		}
		try {
			logger.info("Opening File {}", file);
			this.buffer = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			file = file.replaceAll("//", "\\\\");
			logger.info("Could not open the file. Attempting to Open {}", file);
			try {
				this.buffer = new BufferedReader(new FileReader(file));
			} catch (IOException e1) {
				logger.error("Could not Open File {}", file, e);
			}

		}
	}

	public void close() {

		noOflines = 0;// To Reset the No Of Lines when current file is closed
		try {
			logger.debug("Closing File {}", file);
			this.buffer.close();
		} catch (IOException e) {
			logger.error("Unable to close file {}", file, e);
		}
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public BufferedReader getBuffer() {
		return buffer;
	}

}
