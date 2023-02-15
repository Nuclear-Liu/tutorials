package org.hui.database.mongo;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class ConnectionTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionTest.class);

    @Test
    void testConnect() {
        String url = "mongodb://root:root@127.0.0.1";

        try (MongoClient client = MongoClients.create(url)) {

            MongoDatabase learnDB = client.getDatabase("learn");
            MongoCollection<Document> fooCollection = learnDB.getCollection("foo");
            long countDocuments = fooCollection.countDocuments();
            System.out.println(countDocuments);
            LOGGER.info("{}", countDocuments);

        }
    }
}
