package io.github.mpontoc.picaroon.core.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Prop {

	public static Properties getProp() {
		Properties props = new Properties();
		FileInputStream file = null;
		try {
			file = new FileInputStream("./src/main/resources/application.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			props.load(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
	}

	public static void setPropAndSave(String key, String value) {

		String file = System.getProperty("user.dir") + "/src/main/resources/project.properties";
		Path path = (Path) Paths.get(file);
		Charset charset = StandardCharsets.UTF_8;

		try {
			String content = new String(Files.readAllBytes((java.nio.file.Path) path), charset);
			content = content.replaceAll(key + " = " + getProp(key), key + " = " + value);
			Files.write((java.nio.file.Path) path, content.getBytes(charset));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static String getProp(String prop) {
		Properties props = new Properties();
		FileInputStream file = null;
		try {
			file = new FileInputStream("./src/main/resources/project.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			props.load(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props.getProperty(prop);
	}

	public static BufferedReader getQueries() throws IOException {
		File file = new File(
				"." + File.separator + "lib" + File.separator + "data" + File.separator + "DatabaseQueries.txt");
		FileReader fr = new FileReader(file);
		BufferedReader queries = new BufferedReader(fr);
		return queries;
	}
}
