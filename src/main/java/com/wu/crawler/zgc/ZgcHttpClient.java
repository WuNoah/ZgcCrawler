package com.wu.crawler.zgc;


import com.wu.crawler.Main;
import com.wu.crawler.core.util.Config;
import com.wu.crawler.praser.DetailParser;
import com.wu.crawler.praser.ListPraser;
import com.wu.crawler.queue.Queue;

import org.apache.log4j.Logger;

import com.wu.crawler.core.util.ZgcJsoupUtil;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ZgcHttpClient {
	private static ZgcHttpClient instance = null;
	private static Logger logger = Logger.getLogger(ZgcHttpClient.class);
	private ThreadPoolExecutor detailThreadPool;
	private ThreadPoolExecutor listPageThreadPool;
	public static ZgcHttpClient getInstance() {
		if(instance == null) {
			instance = new ZgcHttpClient();
		}
		return instance;
	}

	public void startCrawl()  {
		initStartUrl();
		initThreadPool();
//			Thread t1 = new Thread(new ListPraser());
//			Thread t2 = new Thread(new DetailParser());
//			t1.start();
			listPageThreadPool.execute(new ListPraser());
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
//			t2.start();
		detailThreadPool.execute(new DetailParser());
		detailThreadPool.execute(new DetailParser());
		detailThreadPool.execute(new DetailParser());
		detailThreadPool.execute(new DetailParser());
		detailThreadPool.execute(new DetailParser());
		detailThreadPool.execute(new DetailParser());
		detailThreadPool.execute(new DetailParser());
		detailThreadPool.execute(new DetailParser());
		detailThreadPool.execute(new DetailParser());
		detailThreadPool.execute(new DetailParser());
	}
	
	public void initStartUrl() {
		ZgcJsoupUtil zgcJsoupUtil = new ZgcJsoupUtil();
		Queue.startUrl = zgcJsoupUtil.getStartUrl();
		logger.info("起始url装填完毕，共计:"+ Queue.startUrl.size()+"个产品线");
	}
	private void initThreadPool(){
		detailThreadPool = new ThreadPoolExecutor(Config.downloadThreadSize
				,Config.downloadThreadSize,0L
				,TimeUnit.MILLISECONDS
				,new LinkedBlockingQueue<Runnable>());

		listPageThreadPool = new ThreadPoolExecutor(Config.downloadThreadSize/10
				,Config.downloadThreadSize/10,0L
				,TimeUnit.MILLISECONDS
				,new LinkedBlockingQueue<Runnable>());
	}
}
