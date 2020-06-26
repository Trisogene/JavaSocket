import java.io.*;
import java.net.*;

public class Client {
    /* -------------------------------- variables ------------------------------- */
    Socket s;

    /* ---------------------------------- main ---------------------------------- */
    public static void main(String[] args) throws IOException {

        Client c = new Client();
        c.connect();

    }

    /* --------------------------------- connect -------------------------------- */
    public void connect() {
        try {

            System.out.println("-------------------");
            System.out.println("[Client] Init");
            System.out.println("-------------------\n");

            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("-------------------");
            System.out.print("Insert Server Ip Address : ");
            String ip = keyboard.readLine();
            System.out.println("-------------------\n");
            System.out.println("-------------------");
            System.out.print("Insert Server Port : ");
            int port = Integer.parseInt(keyboard.readLine());
            System.out.println("-------------------\n");

            s = new Socket(ip, port);

            System.out.println("-------------------");
            System.out.println("[Client] Created socket\nLocal port : " + s.getLocalPort() + "\nPort : " + s.getPort()
                    + "\nLocal address : " + s.getLocalAddress() + "\nAddress : " + s.getInetAddress());
            System.out.println("-------------------\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}