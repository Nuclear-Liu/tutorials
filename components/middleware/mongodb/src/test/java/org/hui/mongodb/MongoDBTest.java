package org.hui.mongodb;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.Test;

@Slf4j
public class MongoDBTest {
    private static final String DATABASE = "test";

    public MongoCollection<Document> getDb(String collectionName) {
        ConnectionString connectionString = new ConnectionString("mongodb+srv://hui:liu18562310585@cluster0.1nd5y.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString).
                retryWrites(true)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        return mongoClient.getDatabase(collectionName).getCollection(collectionName);
    }
    @Test
    public void insert() {
        String collectionName = "shop";
        MongoCollection<Document> collection = getDb(collectionName);
        Document document = new Document();
        document.append("name", "手机");
        document.append("price", 8000);
        InsertOneResult insertOneResult = collection.insertOne(document);
        log.info("{}", insertOneResult);
    }
    @Test public void update() {
        String collectionName = "shop";
        MongoCollection<Document> collection = getDb(collectionName);
        Bson condition = Filters.and(Filters.eq("name", "手机"));
//        new Document()
    }
}
