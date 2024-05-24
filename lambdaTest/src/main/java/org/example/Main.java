package org.example;

import org.example.handlers.FoodHandler;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        FoodHandler foodHandler = new FoodHandler();

        Map<String, Object> input = new HashMap<>();
        input.put("httpMethod", "GET");
        input.put("donnorID", "6643df99c261e702a9a8dc6e");

        String response = foodHandler.handleRequest(input, null);

        System.out.println("Response: " + response);
    }
}
