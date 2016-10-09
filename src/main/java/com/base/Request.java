package com.base;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Request implements ServletRequest {
    private InputStream input;
    private String uri;
    private Map<String, Object> session;

    public Map<String, Object> getApplication() {
        return application;
    }

    public void setApplication(Map<String, Object> application) {
        this.application = application;
    }

    private Map<String, Object> application;
    private Map<String, Object> cookies = new HashMap<>();
    private Map<String, String> params = new HashMap<>();

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public void setCookies(Map<String, Object> cookies) {
        this.cookies = cookies;
    }

    public Request(InputStream input) {
        this.input = input;
    }

    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    public String getUri() {

        return uri;
    }

    public Map<String, Object> getSession() {
        return this.session;
    }

    private String parseUri(String requestString) {

        int index1, index2;
        index1 = requestString.indexOf(' ');
        if (index1 != -1) {
            index2 = requestString.indexOf(' ', index1 + 1);
            if (index2 > index1) return requestString.substring(index1 + 1, index2);
        }
        return null;
    }

    public void parse() throws Exception {
        // TODO: 2016/10/8 获取params

        BufferedReader br = new BufferedReader(new InputStreamReader(input));

        StringBuffer request = new StringBuffer();

        String line = null;
        while ((line = br.readLine()) != null) {

            request.append(line);

            if (line.startsWith("Cookie")) {
                //前面的cookie: 不要
                String[] cookiesStr = line.substring(7).split(";");
                for (String cookie : cookiesStr) {
                    String[] pair = cookie.split("=");
                    cookies.put(pair[0].trim(), pair[1].trim());
                }
            }

            //目前只处理get请求
            if (line.equals("")) {
                break;
            }
        }

        System.out.println(request);

        if (request == null || request.length() == 0) {
            throw new RuntimeException("请求为空，应该为浏览器探针，不用理会");
        }

        uri = parseUri(request.toString());

        //?a=hello&b=ni
        if (uri != null && !"".equals(uri.trim())) {
            int index = uri.indexOf("?");

            if (index >= 0) {

                String paramsStr = uri.substring(index + 1);
                String[] paramsArr = paramsStr.split("&");

                for (String param : paramsArr) {
                    String[] paramPair = param.split("=");
                    params.put(paramPair[0], paramPair[1]);
                }
            }
        }
        // TODO: 2016/10/8  解析params 包括post 和get的
    }

    /* implementation of ServletRequest */
    public Object getAttribute(String attribute) {
        return null;
    }

    public Enumeration getAttributeNames() {
        return null;
    }

    public String getRealPath(String path) {
        return null;
    }

    public RequestDispatcher getRequestDispatcher(String path) {
        return null;
    }

    public boolean isSecure() {
        return false;
    }

    public String getCharacterEncoding() {
        return null;
    }

    public int getContentLength() {
        return 0;
    }

    public String getContentType() {
        return null;
    }

    public ServletInputStream getInputStream() throws IOException {
        return null;
    }

    public Locale getLocale() {
        return null;
    }

    public Enumeration getLocales() {
        return null;
    }

    public String getParameter(String name) {
        return params.get(name);
    }

    public Map getParameterMap() {
        return null;
    }

    public Enumeration getParameterNames() {
        return null;
    }

    public String[] getParameterValues(String parameter) {
        return null;
    }

    public String getProtocol() {
        return null;
    }

    public BufferedReader getReader() throws IOException {
        return null;
    }

    public String getRemoteAddr() {
        return null;
    }

    public String getRemoteHost() {
        return null;
    }

    public String getScheme() {
        return null;
    }

    public String getServerName() {
        return null;
    }

    public int getServerPort() {
        return 0;
    }

    public void removeAttribute(String attribute) {
    }

    public void setAttribute(String key, Object value) {
    }

    public void setCharacterEncoding(String encoding) throws UnsupportedEncodingException {
    }

    @Override
    public String getLocalAddr() {
        return null;
    }

    @Override
    public String getLocalName() {
        return null;
    }

    @Override
    public int getLocalPort() {
        return 0;
    }

    @Override
    public int getRemotePort() {
        return 0;
    }

    public boolean hasJsessionId() {
        return false;
    }

    public Map<String, Object> getCookies() {
        return this.cookies;
    }
}