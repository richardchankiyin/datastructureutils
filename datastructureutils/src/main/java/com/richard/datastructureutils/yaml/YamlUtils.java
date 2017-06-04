package com.richard.datastructureutils.yaml;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class YamlUtils {
	private static Yaml YAML = new Yaml();
	
	@SuppressWarnings("unchecked")
	public static Map<String,Object> readYamlFile(String filePath) {
		try(FileInputStream io = new FileInputStream(new File(filePath))) {
			return (Map<String,Object>)YAML.load(io);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

}
