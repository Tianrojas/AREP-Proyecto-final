package org.example.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.mongodb.MongoDBConnectionManager;
import org.json.JSONArray;

import java.util.Map;

public class UserHandler implements RequestHandler<Map<String, Object>, String> {

    private MongoCollection<Document> usersCollection;

    private void initConnection() {
        MongoDBConnectionManager connectionManager = new MongoDBConnectionManager("mongodb://54.158.42.42:27017");
        MongoDatabase database = connectionManager.getDatabase("arep_finalproject");
        usersCollection = database.getCollection("users");
    }

    @Override
    public String handleRequest(Map<String, Object> input, Context context) {
        initConnection();
        String httpMethod = (String) input.get("httpMethod");

        switch (httpMethod) {
            case "GET":
                return handleGetRequest(input, context);
            default:
                return "Unsupported HTTP method";
        }
    }

    private String handleGetRequest(Map<String, Object> input, Context context) {
        BasicDBObject query = new BasicDBObject("role", "donor");
        MongoCursor<Document> cursor = usersCollection.find(query).iterator();

        JSONArray donorIdsArray = new JSONArray();
        while (cursor.hasNext()) {
            Document userDocument = cursor.next();
            ObjectId donorId = userDocument.getObjectId("_id");
            donorIdsArray.put(donorId.toHexString());
        }
        cursor.close();

        return donorIdsArray.toString();
    }
}
