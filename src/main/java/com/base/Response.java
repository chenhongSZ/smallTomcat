package com.base;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Response implements ServletResponse {
    private static final int BUFFER_SIZE = 1024;
    public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "src\\main\\resources" +
            "\\webroot";
    Request request;
    OutputStream output;
    PrintWriter writer;


    private Map<String, Object> header = new HashMap<>();


    public void addHeader(String key, Object value) {
        header.put(key, value);
    }

    public Object getHeader(String key) {
        return header.get(key);
    }

    public Map<String, Object> getHeaders() {
        return header;
    }

    public Response(OutputStream output) {
        this.output = output;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Request getRequest() {
        return this.request;
    }

    /**
     * @throws IOException
     */
    /* This method is used to serve static pages */
    public void sendStaticResource() throws IOException {
        byte[] bytes = new byte[BUFFER_SIZE];
        FileInputStream fis = null;
        try {
            /* request.getUri has been replaced by request.getRequestURI */
            File file = new File(WEB_ROOT, request.getUri());
            fis = new FileInputStream(file);
            /*
             * HTTP Response = Status-Line(( general-header | response-header |
             * entity-header ) CRLF) CRLF [ message-body ] Status-Line =
             * HTTP-Version SP Status-Code SP Reason-Phrase CRLF
             */
            int ch = fis.read(bytes, 0, BUFFER_SIZE);

            // 获取类型
            String contentType = getContentType(file.getName());
            long contentLength = file.length();

            // 换行的多种形式演示
            String headers = "HTTP/1.1 200 OK\n" + "Content-Type:" + contentType + "\r" + "Content-Length:" +
                    contentLength + "\r\n";

            output.write(headers.getBytes());

            for (Map.Entry item : header.entrySet()) {
                String str = item.getKey() + ":" + item.getValue() + "\r\n";
                output.write(str.getBytes());
            }

            // http 头和身体要隔一个空行
            output.write("\n".getBytes());

            while (ch != -1) {
                output.write(bytes, 0, ch);
                ch = fis.read(bytes, 0, BUFFER_SIZE);
            }
        }
        catch (FileNotFoundException e) {
            String errorMessage = "HTTP/1.1 404 File Not Found\r\n" + "Content-Type: text/html\r\n" +
                    "Content-Length: 23\r\n" + "\r\n" + "<h1>File Not Found</h1>";
            output.write(errorMessage.getBytes());
        }
        finally {
            if (fis != null) fis.close();
        }
    }

    private static String getContentType(String name) {

        String[] token = name.split("\\.");
        String extension = token[1];

        switch (extension) {
            case "html":
                return "text/html";
            case "js":
                return "text/javascript";
            case "css":
                return "text/css";
        }

        return "";
    }

    /**
     * implementation of ServletResponse
     */
    public void flushBuffer() throws IOException {
    }

    public int getBufferSize() {
        return 0;
    }

    public String getCharacterEncoding() {
        return null;
    }

    public Locale getLocale() {
        return null;
    }

    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    public PrintWriter getWriter() throws IOException {
        // autoflush is true, println() will flush,
        // but print() will not.
        writer = new PrintWriter(output, true);
        return writer;
    }

    public boolean isCommitted() {
        return false;
    }

    public void reset() {
    }

    public void resetBuffer() {
    }

    public void setBufferSize(int size) {
    }

    public void setContentLength(int length) {
    }

    public void setContentType(String type) {
    }

    @Override
    public void setLocale(Locale locale) {
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public void setCharacterEncoding(String arg0) {

    }

}
