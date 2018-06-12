package com.wu.crawler.praser;

import com.wu.crawler.Main;
import com.wu.crawler.core.util.ZgcJsoupUtil;
import com.wu.crawler.queue.Queue;
import org.apache.log4j.Logger;

public class ListPraser implements Runnable {
    private static Logger logger = Logger.getLogger(ListPraser.class);
    @Override
    public void run() {
        ZgcJsoupUtil zgcJsoupUtil = new ZgcJsoupUtil();
        for (int i = 0; i < Queue.startUrl.size() ; i++)
        zgcJsoupUtil.getListPage(Queue.startUrl.get(i));

//        ZgcJsoupUtil.getListPage(Queue.startUrl.get(4));
    }
}
