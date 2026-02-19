package Client;

public class MainClient {
    public static void main(String[] args) {
        new Thread(new Client("Pippo", "localhost", 3000)).start();
        new Thread(new Client("Pluto", "localhost", 3000)).start();
    }
}