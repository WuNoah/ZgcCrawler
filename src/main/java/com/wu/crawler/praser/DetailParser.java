package com.wu.crawler.praser;


import com.wu.crawler.Main;
import com.wu.crawler.core.util.ZgcJsoupUtil;
import com.wu.crawler.dao.MongoJDBC;
import com.wu.crawler.entity.Product;
import com.wu.crawler.queue.Queue;
import org.apache.log4j.Logger;
import java.util.List;
import java.util.Map;

public class DetailParser implements Runnable{
    private static Logger logger = Logger.getLogger(DetailParser.class);
private static int m = 0;
    @Override
    public void run() {

            while (true) {
                long startTime = System.currentTimeMillis();
                String url = Queue.get();
                ZgcJsoupUtil zgcJsoupUtil = new ZgcJsoupUtil();
                if (url == null) break;
                Product product = new Product();
                List<String> nor;
                Map<String, String> detail;
                    nor = zgcJsoupUtil.getDetailPageAndNor(url);

                if (nor != null) {
                    if (!nor.get(4).equals("null"))
                    detail = zgcJsoupUtil.getDetail(nor.get(4));
                    else detail = null;
                    product.setMainUrl(url);
                    product.setName(nor.get(0));
                    product.setType(nor.get(3));
                    try {
                        if (nor.get(1).contains("万")) {
                            String price = nor.get(1).substring(0, nor.get(1).length() - 1);
                            product.setPrice(Double.parseDouble(price) * 10000);
                        } else if(nor.get(1).contains("-")){
                            String price = nor.get(1).substring(0,4);
                            product.setPrice(Double.parseDouble(price));
                        }
                        else {
                            product.setPrice(Double.parseDouble(nor.get(1)));
                        }
                        product.setGoal(Double.parseDouble(nor.get(2)));
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.warn("商品价格格式有误");
                    }
                    product.setDetail(detail);
                    logger.info("--------------------------------------------耗时："+(System.currentTimeMillis()-startTime)+" ms");
                    logger.info("产品名称：" + product.getName());
                    logger.info("产品价格：" + product.getPrice());
                    logger.info("产品评分：" + product.getGoal());
                    logger.info("产品类别：" + product.getType());
                    logger.info("产品页面：" + product.getMainUrl());
                    logger.info("产品细节：" + product.getDetail());
//                    logger.info("产品细节：" + product.getDetail());
                    MongoJDBC.insert(product);
//                    m++;
//                    if (m==100)logger.info("!!!!!!!!!!!!!!!!!!耗时:"+(System.currentTimeMillis()- Main.start));
                }


            }
    }
}
