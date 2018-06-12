package com.wu.crawler;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.log4j.Logger;
import org.bson.Document;

import java.util.Scanner;
import java.util.regex.Pattern;

public class CrawlerApi {
    private static Logger logger = Logger.getLogger(CrawlerApi.class);
    private static MongoClient mongoClient;
    private static MongoDatabase mongoDatabase;
    private static MongoCollection<Document> collection;
    public CrawlerApi() {
        try {

            // 连接到 mongodb 服务
            mongoClient = new MongoClient("localhost", 27017);

            // 连接到数据库
            mongoDatabase = mongoClient.getDatabase("crawler");
            collection = mongoDatabase.getCollection("goods");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sortScore(double score){
        Document doc = new Document("score",score);
        FindIterable<Document> iterable = collection.find(doc);
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                logger.info(document.toJson());
            }
        });
    }

    public void sortPrice(double price){
        Document doc = new Document("price",price);
        FindIterable<Document> iterable = collection.find(doc);
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                logger.info(document.toJson());
            }
        });
    }

    public void sortType(String type){
        Document doc = new Document("type",type);
        FindIterable<Document> iterable = collection.find(doc);
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                logger.info(document.toJson());
            }
        });
    }

    public void sortName(String name){
        Document doc = new Document();
        Pattern pattern = Pattern.compile("^.*"+name+".*$",Pattern.CASE_INSENSITIVE);
        doc.put("name",pattern);
        FindIterable<Document> iterable = collection.find(doc);
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                logger.info(document.toJson());
            }
        });
    }

    public void sortDetail(String dname,String dvalue){
        Document doc = new Document();
        Pattern pattern2 = Pattern.compile("^.*"+dvalue+".*$",Pattern.CASE_INSENSITIVE);
        doc.put("detail."+dname,pattern2);
        FindIterable<Document> iterable = collection.find(doc);
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                logger.info(document.toJson());
            }
        });
    }

    public void mongoClose(){
        mongoClient.close();
    }

    public static void main(String[] args){
        int k = -1;
        CrawlerApi c = new CrawlerApi();
        Scanner scanner = new Scanner(System.in);
        while (k!=0) {
            System.out.println("查询名字：1    查询类别：2    查询价格：3    查询评分：4   细节查询：5  断开数据库并退出：0");
            k = scanner.nextInt();
            switch (k) {
                case 1:
                    System.out.println("输入查询的名字：");
                    String name = scanner.next();
                    c.sortName(name);
                    break;
                case 2:
                    System.out.println("输入查询的类别：");
                    String type = scanner.next();
                    c.sortType(type);
                    break;
                case 3:
                    System.out.println("输入查询的价格：");
                    double price = scanner.nextDouble();
                    c.sortPrice(price);
                    break;
                case 4:
                    System.out.println("输入查询的评分：");
                    double score = scanner.nextDouble();
                    c.sortScore(score);
                    break;
                case 5:
                    System.out.println("输入细节的名称：");
                    String dname = scanner.next();
                    System.out.println("输入细节的值：");
                    String dvalue = scanner.next();
                    c.sortDetail(dname,dvalue);
                    break;
                case 0:
                    c.mongoClose();
                    break;
            }
        }

    }
}
