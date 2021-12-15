package com.gr.utils.fileutils;

import java.io.File;
import java.util.List;

import com.gr.utils.fileutils.filereader.FileRead;
import com.gr.utils.fileutils.filewriter.FileWrite;
import com.gr.utils.fileutils.folder.Folder;

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

	public static boolean isFileExisting(String filePath) {
		File file = new File(filePath);
		return file.exists();
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

	public static List<String> readAllLines(String filePath) {
		reader = getReader();
		reader.openFile(filePath);
		List<String> allLines = reader.readAllLines();
		reader.close();
		return allLines;
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
