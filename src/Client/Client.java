package Client;

import java.io.*;
import java.net.Socket;

public class Client implements Runnable {
    private final String name;
    private final String host;
    private final int port;
    private Socket socket;

    public Client(String name, String host, int port) {
        this.name = name;
        this.host = host;
        this.port = port;
    }

    private void connect() {
        try {
            socket = new Socket(host, port);
            System.out.println("Client " + name + " connected to server on port " + port);
        } catch (IOException e) {
            System.err.println("Connection failed for " + name);
        }
    }

    private void write(String message) {
        try {
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            writer.print(message + "\n");
            writer.flush();
        } catch (IOException e) {
            System.err.println("Write error for " + name);
        }
    }

    private void read() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = reader.readLine();
            System.out.println(name + " received: " + response);
        } catch (IOException e) {
            System.err.println("Read error for " + name);
        }
    }

    private void close() {
        try {
            socket.close();
            System.out.println(name + " disconnected.");
        } catch (IOException e) {
            System.err.println("Close error for " + name);
        }
    }

    @Override
    public void run() {
        connect();
        try {
            write("Message from " + name);
            read();
            Thread.sleep(1000);
            write("stop");
            close();
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted for " + name);
        }
    }
}