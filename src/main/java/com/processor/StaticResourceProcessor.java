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
}