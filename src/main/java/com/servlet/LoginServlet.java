package com.servlet;

import com.base.Request;
import com.base.Response;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginServlet extends AbstractServlet {


    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {

        Response myResponse = (Response) response;
        Request myRequest = (Request) request;

        String userName = myRequest.getParameter("userName");
        String password = myRequest.getParameter("password");

        String body;

        if (userName.equals(password)) {
            myRequest.getSession().put("userName", userName);

            body = "{\"result\":true}";
        }
        else {
            body = "{\"result\":false}";
        }

        PrintWriter out = myResponse.getWriter();

        myResponse.addHeader("Content-Type", "application/json;charset=UTF-8");
        myResponse.addHeader("Content-Length", body.getBytes().length);

        out.println("HTTP/1.1 200 OK");

        writeHeaders(myResponse);

        //body
        out.println(body);

    }
}
