package org.example.mongodb;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnectionManager {
    private final String connectionString;

    public MongoDBConnectionManager(String connectionString) {
        this.connectionString = connectionString;
    }

    public MongoClient connect() {
        ConnectionString connString = new ConnectionString(connectionString);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connString)
                .build();
        return MongoClients.create(settings);
    }

    public MongoDatabase getDatabase(String databaseName) {
        MongoClient client = connect();
        return client.getDatabase(databaseName);
    }
}

