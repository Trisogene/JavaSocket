import java.io.*;
import java.net.*;

public class Server {

    ServerSocket ss;
    Socket c;
    InputStream in;
    OutputStream out;

    /* ---------------------------------- main ---------------------------------- */
    public static void main(final String[] args) {
        final Server s = new Server();
        s.init();
        s.waitForConnection();
    }

    /* ---------------------------------- init ---------------------------------- */
    public void init() {
        System.out.println("-------------------");
        System.out.println("[Server] Init");
        System.out.println("-------------------\n");

        try {
            ss = new ServerSocket(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("-------------------");
        System.out.println(
                "[Server] Created socket\nLocal port : " + ss.getLocalPort() + "\nAddress : " + ss.getInetAddress());
        System.out.println("-------------------\n");
    }

    /* ---------------------------- waitForConnection --------------------------- */
    public void waitForConnection() {
        while (true) {
            try {
                c = ss.accept();

                System.out.println("-------------------");
                System.out.println("[Server] Client accepted \nLocal port : " + c.getLocalPort() + "\nPort : "
                        + c.getPort() + "\nLocal address : " + c.getLocalAddress().toString().replace("/", "")
                        + "\nAddress : " + c.getInetAddress().toString().replace("/", ""));
                System.out.println("-------------------\n");

                Thread t = new Service(c);
                t.start();

            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }
}