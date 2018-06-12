package com.wu.crawler.dao;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.wu.crawler.entity.Product;
import org.bson.Document;

import javax.xml.bind.Element;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MongoJDBC extends MongoDao{
    private static MongoClient mongoClient;
    private static MongoDatabase mongoDatabase;
    private static MongoCollection<Document> collection;
    static {
        try {

            // 连接到 mongodb 服务
            mongoClient = new MongoClient("localhost", 27017);

            // 连接到数据库
            mongoDatabase = mongoClient.getDatabase("crawler");
            collection = mongoDatabase.getCollection("stuffs");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public synchronized static void insert(Product product){

        try {
            Document document = new Document("name", product.getName())
                    .append("price", product.getPrice())
                    .append("score", product.getGoal())
                    .append("type", product.getType())
                    .append("url" , product.getMainUrl())
                    .append("detail", product.getDetail());


            collection.insertOne(document);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
/*    public static void main( String args[] ){
        try{
            // 连接到 mongodb 服务
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );

            // 连接到数据库
            MongoDatabase mongoDatabase = mongoClient.getDatabase("goods");
            System.out.println("Connect to database successfully");
            MongoCollection<Document> collection = mongoDatabase.getCollection("test");
            System.out.println("集合 test 选择成功");
            //插入文档
            *//**
             * 1. 创建文档 org.bson.Document 参数为key-value的格式
             * 2. 创建文档集合List<Document>
             * 3. 将文档集合插入数据库集合中 mongoCollection.insertMany(List<Document>) 插入单个文档可以用 mongoCollection.insertOne(Document)
             * *//*
            HashMap<String,String> map = new HashMap<>();
            map.put("as1","as");
            map.put("as2","as");
            Document document = new Document("title", "MongoDB").
                    append("description", "database").
                    append("likes", 99).
                    append("by", "Fly").append("detail",map);
            List<Document> documents = new ArrayList<>();
            documents.add(document);
            collection.insertMany(documents);
            System.out.println("文档插入成功");

        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }*/
}
