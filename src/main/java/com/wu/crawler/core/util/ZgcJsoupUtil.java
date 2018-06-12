package com.wu.crawler.core.util;


import java.net.UnknownHostException;
import java.util.*;

import com.wu.crawler.praser.DetailParser;
import com.wu.crawler.queue.Queue;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class ZgcJsoupUtil {
	private static Logger logger = Logger.getLogger(ZgcJsoupUtil.class);
	//产品列表页数
	private static int page =1;

	public Vector<String> getStartUrl() {
		Vector<String> startUrl = new Vector<>();
		Document doc = getDocument(Config.startUrl);
		Elements elements = doc.select("div.subcate-list").select("a");
		for(Element e:elements) {
			startUrl.add(Config.mainUrl+e.attr("href"));
		}
		return startUrl;
	}
public static int m = 1;
	public void getListPage(String url) {
//		logger.info("第"+page++ + "页产品列表:"+url);
		Document doc = getDocument(url);
		Elements elementsNext = doc.select("a.next");
		Elements elementsGoods = doc.select("ul#J_PicMode").select("a.pic");
		if (elementsGoods.first()==null){
			elementsGoods = doc.select("div.list-box").select("div.pro-intro").select("h3").select("a");
		}
		for(Element e : elementsGoods){
			Queue.add(Config.mainUrl+e.attr("href"));
			logger.info("第"+m++ +"个商品录入");
		}
		Element e = elementsNext.first();
		if(e!=null) {
			getListPage(Config.mainUrl+e.attr("href"));
		}
		else{
			logger.warn("已无列表可以爬取");
		}
	}

	/**
	 *
	 * @param url
	 * @return
	 */
	public List getDetailPageAndNor(String url) {
		Document doc = getDocument(url);
		Elements elementsDetail = doc.select("div.section-header-link").select("a");
		Elements elementsName = doc.select("h1.product-model__name");
		Elements elementsPrice = doc.select("b.price-type");
		Elements elementsScore = doc.select("div.total-score").select("strong");
		String eType = doc.select("strong.logo__classify").text();
		Element eDetail = elementsDetail.first();
		String eName;
		if (elementsName.first()!=null) {
			eName = elementsName.first().text();
		}else {
			eName = "";
		}
		String ePrice;
		//价格在标签<a>里
		if (elementsPrice.select("a").first()!=null) {
			ePrice = elementsPrice.select("a").first().text();
		}
		//价格不在标签<a>里
		else {
			ePrice = elementsPrice.text();
		}

		Element eScore = elementsScore.first();
		String score ;
		//没有评分
		if (eScore == null){
			score = "0";
		}else {
			score = eScore.text();
		}
		//详情页的标签类为第二种
		if (eDetail == null){
			elementsDetail = doc.select("div.section-header").select("a");
			eDetail = elementsDetail.first();
		}
		//没有详情页
		if(eDetail == null){
			logger.warn("没有详情页");
			List<String> nor = new ArrayList<>(5);
			nor.add(eName);
			nor.add(ePrice);
			nor.add(score);
			nor.add(eType);
			nor.add("null");
			return nor;
		}else if (url.contains("series")){
			sortSeries(Config.mainUrl + eDetail.attr("href"));
			return null;
		}
		else {
			List<String> nor = new ArrayList<>(5);
			nor.add(eName);
			nor.add(ePrice);
			nor.add(score);
			nor.add(eType);
			nor.add(Config.mainUrl + eDetail.attr("href"));
			return nor;
		}
	}


	/**
	 * 获取产品细节
	 * @param url
	 */
	public Map getDetail(String url){
		Document doc = getDocument(url);
			Elements elements = doc.select("div#newTb").select("table").select("li");
			Map<String,String> detail = new HashMap<>();
			if (elements.first()!=null) {
				for (Element element : elements) {
					String key = element.select("span").get(0).text();
					String value = element.select("span").get(1).text();
					Queue.visitedUrl.add(url);
					detail.put(key, value);
				}
			}
			else {
				elements =doc.select("div.detailed-parameters").select("tr");
				for (Element element : elements){
					if (element.select("span").first()!=null) {
						String key = element.select("span").get(0).text();
						String value = element.select("span").get(1).text();
						Queue.visitedUrl.add(url);
						detail.put(key, value);
					}
				}
			}
			return detail;

	}

	private void sortSeries(String url){
		Document doc = getDocument(url);
		Elements elements = doc.select("table#seriesParamTable").select("td.pro_name").select("a");
		int num = 0;
		for (Element element : elements) {
			Queue.add(Config.mainUrl + element.attr("href"));
			num ++;
		}
		logger.info("@@@"+num+"个系列商品入队列@@@");
		Queue.visitedUrl.add(url);
	}


	public Document getDocument(String url){
            String content = null;
            int time = 0;
             do {
                try {
                    content = HttpClientUtil.getContent(url, Config.encoding);
                    if (content==null){
                        Thread.sleep(10000);
                        time ++;
                    }
                    if (time == 10){
                        return Jsoup.parse(" ");
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }while (content ==null);
            return Jsoup.parse(content);
	}
}
