package org.example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.Map;


public class Hello implements RequestHandler<Map<String, String>, String> {

    @Override
    public String handleRequest(Map<String, String> input, Context context) {
        String name = input.get("name");
        String surname = input.get("surname");

        String greeting = "Hello, " + name + " " + surname;

        return greeting;
    }
}