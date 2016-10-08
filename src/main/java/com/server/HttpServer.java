package com.server;

import com.base.Request;
import com.base.Response;
import com.processor.ServletProcessor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpServer {
    /**
     * WEB_ROOT is the directory where our HTML and other files reside. For this
     * package, WEB_ROOT is the "webroot" directory under the working directory.
     * The working directory is the location in the file system from where the
     * java command was invoked.
     */
    // shutdown command
    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
    // the shutdown command received
    private boolean shutdown = false;


    /**
     * servlet context
     */
    public static HashMap<String, Object> application = new HashMap<String, Object>();

    /**
     * sessions
     */
    public static HashMap<String, Map<String, Object>> sessions = new HashMap<>();

    public static void main(String[] args) {
        HttpServer server = new HttpServer();
        server.await();
    }

    public void await() {
        ServerSocket serverSocket = null;
        int port = 8080;
        try {
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
            System.out.println("已经监听在" + port + "端口上");
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        // Loop waiting for a request
        while (!shutdown) {

            try {
                final Socket socket = serverSocket.accept();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        InputStream input = null;
                        OutputStream output = null;

                        try {
                            input = socket.getInputStream();
                            output = socket.getOutputStream();
                            // create Request object and parse
                            Request request = new Request(input);
                            request.parse();
                            // create Response object
                            Response response = new Response(output);
                            response.setRequest(request);

                            Map<String, Object> cookies = request.getCookies();

                            String jsessionId = (String) cookies.get("JSESSIONID");

                            if (jsessionId == null) {

                                String uuid = UUID.randomUUID().toString();
                                response.addHeader("Set-Cookie", " JSESSIONID=" + uuid + "; HttpOnly");

                                Map<String, Object> session = new HashMap<>();
                                sessions.put(uuid, session);
                                request.setSession(session);

                            }
                            else {
                                request.setSession(sessions.get(jsessionId));
                            }

                            if (request.getUri().startsWith("/servlet/")) {
                                ServletProcessor processor = new ServletProcessor();
                                processor.process(request, response);
                            }
                            else {
                                StaticResourceProcessor processor = new StaticResourceProcessor();
                                processor.process(request, response);
                            }

                            socket.close();
                        }

                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
            catch (Exception e) {

                e.printStackTrace();
                System.exit(1);
            }
        }
    }
}