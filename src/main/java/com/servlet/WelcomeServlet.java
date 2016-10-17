package com.servlet;

import com.base.Request;
import com.base.Response;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * ajax
 */
public class WelcomeServlet extends AbstractServlet {


    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {

        Response myResponse = (Response) response;
        Request myRequest = (Request) request;

        PrintWriter out = response.getWriter();

        String userName = (String) myRequest.getSession().get("userName");

        if (userName == null) {
            // 重定向

            out.println("HTTP/1.1 302 Moved Temporarily Server");

            myResponse.addHeader("Location", "/login.html");

            writeHeaders(myResponse);

        }
        else {

            out.println("HTTP/1.1 200 OK");

            writeHeaders(myResponse);

            out.println("<html><head><title>linkin.html</title>" + "<meta http-equiv=\"keywords\" content=\"keyword1," +
                    "" + "keyword2,keyword3\">" + "<meta http-equiv='description' content='this is my " + "page'>" +
                    "<meta " + "http-equiv='content-type' content='text/html; charset=UTF-8'>" + "<!--<link " +
                    "rel='stylesheet' " + "type='text/css' href='./styles.css'>-->" + "</head><h1>欢迎欢迎，热烈欢迎 " +
                    userName + "</h1><br>");

            out.println("您已登录成功");
            out.println("</br>");
            out.println("当前站点UV:" + myRequest.getApplication().get("uv"));

            out.println("</body></html>");
        }
    }
}