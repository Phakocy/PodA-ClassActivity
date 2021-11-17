package com.week5;

import Services.ReadAndWriteImplementation;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import static java.nio.file.Files.*;
import static java.nio.file.Files.readAllBytes;

public class HttpServer {

    public static void main(String[] args) throws IOException {

        /* Open TCP socket to listen to a port
         * Accept the client and read request messages
         * Parse request message i.e. You open and read file from the file system or disk
         * Write or send a response message
         * Test
         */

        ReadAndWriteImplementation rw = new ReadAndWriteImplementation();
        System.out.println("Server Started");

        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Listening for connection on port 8080 ....");


        while (true) {

            Socket clientSocket = serverSocket.accept();

            System.out.println("Client is connected to the server");

            //Reading data from the client

            InputStreamReader fetchData = new InputStreamReader(clientSocket.getInputStream());
            BufferedReader reader = new BufferedReader(fetchData);

            StringBuilder requestBuilder = new StringBuilder();
            String line;
            while (!(line = reader.readLine()).isBlank()) {
                requestBuilder.append(line + "\r\n");
            }

            String request = requestBuilder.toString();
            String[] requestsLines = request.split("\r\n");
            String[] requestLine = requestsLines[0].split(" ");
            String method = requestLine[0];
            String path = requestLine[1];
            String version = requestLine[2];
            String host = requestsLines[1].split(" ")[1];

            List<String> headers = new ArrayList<>();
            for (int i = 2; i < requestsLines.length; i++) {
                String header = requestsLines[i];
                headers.add(header);
            }

            String accessLog = String.format("Client %s, method %s, path %s, version %s, host %s, headers %s",
                    clientSocket.toString(), method, path, version, host, headers.toString());
            System.out.println(accessLog);



            if (path.equals("/") && exists(rw.getFilePath(path))) {
                String contentType = rw.guessContentType(rw.getFilePath(path));
                rw.sendResponse(clientSocket, "200 OK", contentType, readAllBytes(rw.getFilePath(path)));
            } else if (path.equals("/json") && exists(rw.getFilePath(path))) {
                String contentType = rw.guessContentType(rw.getFilePath(path));
                rw.sendResponse(clientSocket, "200 OK", contentType, readAllBytes(rw.getFilePath(path)));
            } else {
                String contentType = rw.guessContentType(rw.getFilePath(path));
                rw.sendResponse(clientSocket, "200 OK", contentType, readAllBytes(rw.getFilePath(path)));
            }
            System.out.println(requestBuilder.toString());
        }
    }
}
