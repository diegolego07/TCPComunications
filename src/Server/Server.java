package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private final int port;
    private ServerSocket serverSocket;
    private Socket clientSocket;

    /**
     * * @param port the port on which the server will listen for incoming
     * connections * The constructor initializes the server socket and starts
     * listening on the specified port. * If the server cannot start, it prints an
     * error message.
     */
    public Server(int port) {
        this.port = port;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server listening on port " + port);
        } catch (IOException e) {
            System.err.println("Cannot start server on port " + port);
        }
    }

    /**
     * * The method waitConnection waits for a client to connect to the server. *
     * When a client connects, it accepts the connection and prints a message
     * indicating a client connected * If there is an error while accepting the
     * connection, it prints an error message.
     */
    private void waitConnection() {
        try {
            clientSocket = serverSocket.accept();
            System.out.println("Client connected on port " + port);
        } catch (IOException e) {
            System.err.println("Accept failed on port " + port);
        }
    }

    /**
     * * @return the message received from the client, or null if an error occurs *
     * The method reads a message from the client. It uses a BufferedReader to read
     * from the client's input stream. * If a message is successfully read, it
     * prints the message and returns it.
     */
    private String read() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String message = reader.readLine();
            System.out.println("Port " + port + " received: " + message);
            return message;
        } catch (IOException e) {
            System.err.println("Read error on port " + port);
            return null;
        }
    }

    /**
     * * @param message the message to send to the client * The method sends a
     * message to the client. It uses a PrintWriter to write to the client's output
     * stream. * If the message is successfully sent, it prints a message indicating
     * that the message was sent.
     */
    private void write(String message) {
        try {
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
            writer.print(message + "\n");
            writer.flush();
        } catch (IOException e) {
            System.err.println("Write error on port " + port);
        }
    }

    /**
     * * The method closeClient closes the connection with the client. * It attempts
     * to close the client socket and prints a message indicating that the client
     * was disconnected.
     */
    private void closeClient() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Close client error on port " + port);
        }
    }

    @Override
    public void run() {
        waitConnection();
        String message = "";
        while (!"stop".equals(message)) {
            message = read();
            write("Hello from server on port " + port);
        }
        closeClient();
    }
}