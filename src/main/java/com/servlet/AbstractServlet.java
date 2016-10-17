package com.servlet;

import com.base.Response;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import static java.lang.System.out;

/**
 * ajax
 */
public abstract class AbstractServlet implements Servlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        out.println("init");
    }

    public void destroy() {
        out.println("destroy");
    }

    public String getServletInfo() {
        return null;
    }

    public ServletConfig getServletConfig() {
        return null;
    }

    protected void writeHeaders(Response response) throws IOException {

        PrintWriter write = response.getWriter();

        Map<String, Object> headers = response.getHeaders();

        for (Map.Entry header : headers.entrySet()) {
            write.println(header.getKey() + ":" + header.getValue());
        }

        write.println();

    }


}