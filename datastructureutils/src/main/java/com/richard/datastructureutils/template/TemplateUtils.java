package com.richard.datastructureutils.template;

import java.io.File;
import java.io.StringWriter;
import java.util.Map;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;

public class TemplateUtils {
	private static Version FREEMARKER_VERSION = new Version(2,3,21);
	public static String generateContent(String templateFilePath, Map<String,Object> params) {
		try(StringWriter writer = new StringWriter()) {
			Configuration cfg = new Configuration(FREEMARKER_VERSION);
			File templateFileLocation = new File(templateFilePath);
			
			cfg.setTemplateLoader(new FileTemplateLoader(templateFileLocation.getParentFile()));
			Template template = cfg.getTemplate(templateFileLocation.getName());
			template.process(params, writer);
			return writer.toString();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
