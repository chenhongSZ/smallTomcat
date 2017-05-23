package com.processor;

import com.base.Request;
import com.base.Response;

import java.io.IOException;

public class StaticResourceProcessor {

    public void process(Request request, Response response) {

        try {
            response.sendStaticResource();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
    }
}
