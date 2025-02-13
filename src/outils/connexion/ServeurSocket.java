package outils.connexion;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class ServeurSocket extends Thread {
    private Object leRecepteur;
    private ServerSocket serverSocket;
    private static final int MAX_CONNECTIONS = 100;
    private AtomicInteger activeConnections = new AtomicInteger(0);

    public ServeurSocket(Object leRecepteur, int port) {
        this.leRecepteur = leRecepteur;
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Erreur grave création socket serveur : " + e);
            System.exit(0);
        }
        this.start();
    }

    public void run() {
        while (true) {
            try {
                System.out.println("Le serveur attend");
                Socket socket = serverSocket.accept();
                if (activeConnections.get() < MAX_CONNECTIONS) {
                    System.out.println("Un client s'est connecté");
                    activeConnections.incrementAndGet();
                    new Connection(socket, leRecepteur);
                } else {
                    System.out.println("Nombre maximum de connexions atteint");
                }
            } catch (IOException e) {
                System.out.println("Erreur sur l'objet serverSocket : " + e);
                System.exit(0);
            }
        }
    }
}