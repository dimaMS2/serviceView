package server;

import javafx.scene.control.TextArea;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Arrays;

public class ServerView extends TextArea {
    private static final String proxy = "192.168.43.112";
    private static final int port = 8080;
    private String message;
    private Socket socket;
    private ServerSocket serverSocket;
    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;
    private Thread thread;

    public ServerView() {
        System.out.println(Arrays.toString(getFileBytes()));
        init();
    }

    private void init() {
        thread = new Thread(this::initServer2);
        thread.start();


    }

    private void initServer() {
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                socket = serverSocket.accept();
                inputStreamReader = new InputStreamReader(socket.getInputStream());
                bufferedReader = new BufferedReader(inputStreamReader);
                message = bufferedReader.readLine();

                System.out.println(message);
                this.appendText(message + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initServer2() {
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                socket = serverSocket.accept();
                DataInputStream dIn = new DataInputStream(socket.getInputStream());

                int length = dIn.readInt();                    // read length of incoming message
                byte[] message = null;
                if (length > 0) {
                    message = new byte[length];
                    dIn.readFully(message, 0, message.length); // read the message
                }

                System.out.println(Arrays.toString(message));
                System.out.println(Arrays.equals(message, getFileBytes()));

                this.appendText("file was accepted" + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] getFileBytes() {
        try {
            return Files.readAllBytes(new File("C:\\IdeaProjects\\serviceView\\src\\main\\resources\\compare\\mishkinskoe_1945_20190427_080301.D").toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
