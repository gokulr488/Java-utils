package com.gr.utils.gen;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

public class GenUtils {

	public static ST getTemplate(String path, String key) {
		STGroup stGroup = new STGroupFile(path);
		ST template = stGroup.getInstanceOf(key);
		return template;
	}

}
