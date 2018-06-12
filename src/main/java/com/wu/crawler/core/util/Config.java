package com.wu.crawler.core.util;

import java.io.IOException;
import java.util.Properties;

public class Config {
	public static String startUrl = null;
	public static String mainUrl = null;
	public static String encoding = null;
	public static int downloadThreadSize ;
	static {
		Properties p = new Properties();
		try {
			p.load(Config.class.getResourceAsStream("/config.properties"));
		}catch(IOException e) {
			e.printStackTrace();
		}
		startUrl = p.getProperty("startUrl");
		mainUrl = p.getProperty("mainUrl");
		encoding = p.getProperty("encoding");
		downloadThreadSize = Integer.parseInt(p.getProperty("downloadThreadSize"));
	}

}
