package org.example;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.InetSocketAddress;
import java.nio.file.Files;

public class FileWebSocketServer extends WebSocketServer {

    public FileWebSocketServer(int port) {
        super(new InetSocketAddress("localhost", port));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("New connection: " + conn.getRemoteSocketAddress());
        sendFileRepeatedly(conn); // 클라이언트에 파일 전송
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("Closed connection: " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        // 클라이언트가 보낸 메시지 처리
        System.out.println("Received: " + message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }

    @Override
    public void onStart() {
        System.out.println("Server started successfully!");
    }

    // 파일을 계속해서 전송하는 메소드
    private void sendFileRepeatedly(WebSocket conn) {
        new Thread(() -> {
            while (true) {
                try {
//                    File file = new File("../sample_meetup_data.json");
//                    if (file.exists() && file.isFile()) {
//                        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(file.toPath())))) {
//                            StringBuilder fileContent = new StringBuilder();
//                            String line;
//                            while ((line = reader.readLine()) != null) {
//                                fileContent.append(line).append("\n"); // 줄바꿈 유지
//                            }
//                            System.out.println("@@@@@@@@@@@@@@@@@@@@");
//                            System.out.println(fileContent);
//                            System.out.println("@@@@@@@@@@@@@@@@@@@@");
//                            conn.send(fileContent.toString()); // 전체 파일을 한 번에 전송
//                        }
//                    } else {
//                        conn.send("File not found.");
//                        break;
//                    }

                    String jsonData = "{\"group\": " + (int) (Math.random() * 30) + "}";
                    conn.send(jsonData);
                } catch (RuntimeException e) {
                    e.printStackTrace();
//                    conn.send("Error reading file.");
                    break;
                }

                try {
                    Thread.sleep(50); // 5초마다 파일을 다시 전송
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        int port = 8080; // 웹소켓 서버 포트
        FileWebSocketServer server = new FileWebSocketServer(port);
        server.start();
        System.out.println("FileWebSocketServer started on port: " + port);
    }
}