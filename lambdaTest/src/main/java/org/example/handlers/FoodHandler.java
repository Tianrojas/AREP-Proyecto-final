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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class FoodHandler implements RequestHandler<Map<String, Object>, String> {

    private MongoCollection<Document> foodsCollection;

    private void initConnection() {
        MongoDBConnectionManager connectionManager = new MongoDBConnectionManager("mongodb://54.158.42.42:27017");
        MongoDatabase database = connectionManager.getDatabase("arep_finalproject");
        foodsCollection = database.getCollection("foods");
    }

    @Override
    public String handleRequest(Map<String, Object> input, Context context) {
        initConnection();
        String httpMethod = (String) input.get("httpMethod");

        switch (httpMethod) {
            case "GET":
                return handleGetRequest(input, context);
            case "POST":
                return handlePostRequest(input, context);
            case "PUT":
                return handlePutRequest(input, context);
            case "DELETE":
                return handleDeleteRequest(input, context);
            default:
                return "Unsupported HTTP method";
        }
    }

    private String handleGetRequest(Map<String, Object> input, Context context) {
        String donnorID = (String) input.get("donnorID");
        int page = Integer.parseInt((String) input.getOrDefault("page", "1"));
        int pageSize = 10;

        int skip = (page - 1) * pageSize;
        BasicDBObject query = new BasicDBObject("donnorID", donnorID);
        MongoCursor<Document> cursor = foodsCollection.find(query).skip(skip).limit(pageSize).iterator();

        JSONArray foodsArray = new JSONArray();
        while (cursor.hasNext()) {
            Document foodDocument = cursor.next();
            JSONObject foodJson = new JSONObject(foodDocument.toJson());
            foodsArray.put(foodJson);
        }
        cursor.close();

        return foodsArray.toString();
    }

    private String handlePostRequest(Map<String, Object> input, Context context) {
        try {
            String requestBody = (String) input.get("body");
            JSONObject bodyJson = new JSONObject(requestBody);

            Document newFoodDocument = new Document()
                    .append("name", bodyJson.getString("name"))
                    .append("description", bodyJson.getString("description"))
                    .append("quantity", bodyJson.getInt("quantity"))
                    .append("expiryDate", bodyJson.getString("expiryDate"))
                    .append("donnorID", bodyJson.getString("donnorID"));

            foodsCollection.insertOne(newFoodDocument);

            return "New food item added successfully";
        } catch (JSONException e) {
            return "Error parsing JSON: " + e.getMessage();
        } catch (Exception e) {
            return "Error adding new food item: " + e.getMessage();
        }
    }

    private String handlePutRequest(Map<String, Object> input, Context context) {
        String itemId = (String) input.get("item_id");
        Integer number = (Integer) input.get("number");

        if (itemId == null || itemId.isEmpty()) {
            return "Item ID is required";
        }
        if (number == null || number <= 0) {
            return "Number must be a positive integer";
        }

        try {
            ObjectId objectId = new ObjectId(itemId);
            BasicDBObject query = new BasicDBObject("_id", objectId);
            Document foodDocument = foodsCollection.find(query).first();

            if (foodDocument == null) {
                return "Item not found";
            }

            int currentQuantity = foodDocument.getInteger("quantity");
            int newQuantity = currentQuantity - number;

            if (newQuantity < 0) {
                return "Not enough quantity to fulfill the request";
            }

            BasicDBObject updateFields = new BasicDBObject("quantity", newQuantity);
            BasicDBObject setQuery = new BasicDBObject("$set", updateFields);

            foodsCollection.updateOne(query, setQuery);

            return "Quantity updated successfully";
        } catch (Exception e) {
            return "Error updating quantity: " + e.getMessage();
        }
    }

    private String handleDeleteRequest(Map<String, Object> input, Context context) {
        String itemId = (String) input.get("item_id");
        if (itemId == null || itemId.isEmpty()) {
            return "Item ID is required";
        }

        try {
            ObjectId objectId = new ObjectId(itemId);
            BasicDBObject query = new BasicDBObject("_id", objectId);

            foodsCollection.deleteOne(query);

            return "Item deleted successfully";
        } catch (Exception e) {
            return "Error deleting item: " + e.getMessage();
        }
    }
}
