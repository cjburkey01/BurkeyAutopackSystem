package com.cjburkey.autopacker;

import java.io.File;
import java.util.regex.Pattern;
import com.cjburkey.autopacker.local.LocalVersion;

public class FileSafe {
	
	public static final File getFile(String input) {
		input = input.replaceAll(Pattern.quote("\\"), "/");
		input = input.replaceAll(Pattern.quote("../"), "");
		input = input.replaceAll(Pattern.quote("./"), "");
		while (input.startsWith(".") || input.startsWith("/")) {
			input = input.substring(1);
		}
		return new File(LocalVersion.getMcDir(), input);
	}
	
}