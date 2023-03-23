package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GeneralUtils {

	public static String readTxtFile(String filename) {

		try {
			String content = new String(Files.readAllBytes(Paths.get(filename)));
			return content;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "error";
	}
}
