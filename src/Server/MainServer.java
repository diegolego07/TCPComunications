package Server;

public class MainServer {
    public static void main(String[] args) {
        Server server1 = new Server(3000);
        Thread t1 = new Thread(server1);
        
        while (true) {
            if (!t1.isAlive()) {
                t1 = new Thread(server1);
                t1.start();
            }
        }
    }
}