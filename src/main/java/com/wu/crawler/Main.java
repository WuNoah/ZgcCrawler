package com.wu.crawler;


import com.wu.crawler.zgc.ZgcHttpClient;
import org.apache.log4j.Logger;


public class Main {
	public static long start = System.currentTimeMillis();
	public static void main(String[] args){
		// TODO Auto-generated method stub
		ZgcHttpClient.getInstance().startCrawl();
	}

}
