package Utils.fileutils;

import Utils.fileutils.filereader.FileRead;
import Utils.fileutils.filewriter.FileWrite;
import Utils.fileutils.folder.Folder;

public class Files {

	private static FileRead reader;
	private static FileWrite writer;
	private static Folder folder;

	public static FileRead getReader() {
		if (reader == null) {
			reader = new FileRead();
		}
		return reader;

	}

	public static String readResourceFile(String filePath) {
		reader = getReader();
		reader.openResourceFile(filePath);
		String data = reader.readAsSingleString();
		reader.close();
		return data;

	}

	public static String readFullFile(String filePath) {
		reader = getReader();
		reader.openFile(filePath);
		String data = reader.readAsSingleString();
		reader.close();
		return data;

	}

	public static void writeFullFile(String data, String filePath) {
		writer = getWriter();
		writer.createFile(filePath);
		writer.write(data);
		writer.close();
	}

	public static FileRead getNewReader() {
		return new FileRead();

	}

	public static FileWrite getWriter() {
		if (writer == null) {
			writer = new FileWrite();
		}
		return writer;
	}

	public static FileWrite getNewWriter() {
		return new FileWrite();
	}

	public static Folder getFolderUtils() {
		if (folder == null) {
			folder = new Folder();
		}
		return folder;
	}

}
