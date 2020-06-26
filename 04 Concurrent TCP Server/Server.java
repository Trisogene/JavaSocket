import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {

    /* -------------------------------- variables ------------------------------- */
    ServerSocket ss;
    ArrayList<Socket> clients;
    InputStream in;
    OutputStream out;

    /* ---------------------------------- main ---------------------------------- */
    public static void main(final String[] args) {
        final Server s = new Server();
        s.init();
        while (true) {
            s.waitForConnection();
            s.talk();
        }
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
        clients = new ArrayList<Socket>();

        System.out.println("-------------------");
        System.out.println(
                "[Server] Created socket\nLocal port : " + ss.getLocalPort() + "\nAddress : " + ss.getInetAddress());
        System.out.println("-------------------\n");

    }

    /* ---------------------------- waitForConnection --------------------------- */
    public void waitForConnection() {
        while (true) {
            try {

                // System.out.println("-------------------");
                // System.out.println("[Server] Waiting for client");
                // System.out.println("-------------------\n");

                ss.setSoTimeout(1000);

                Socket c = ss.accept();
                clients.add(c);

                System.out.println("-------------------");
                System.out.println("[Server] Client accepted \nLocal port : " + c.getLocalPort() + "\nPort : "
                        + c.getPort() + "\nLocal address : " + c.getLocalAddress().toString().replace("/", "")
                        + "\nAddress : " + c.getInetAddress().toString().replace("/", ""));
                System.out.println("-------------------\n");

            } catch (SocketTimeoutException e) {
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* ---------------------------------- talk ---------------------------------- */
    public void talk() {
        for (Socket client : clients) {
            try {

                // System.out.println("-------------------");
                // System.out.println("[Server] Listening for packet");
                // System.out.println("-------------------\n");

                client.setSoTimeout(3);
                in = client.getInputStream();
                out = client.getOutputStream();
                byte[] buf = new byte[100];
                int read = in.read(buf);
                String msg = new String(buf, 0, read);
                System.out.println("[Server] Packet received : " + msg + "\n");

                if (msg.equals("close")) {
                    out.write("closeClient".getBytes());
                    client.close();
                    clients.remove(client);
                    return;
                }

                System.out.println("-------------------");
                System.out.println("[Server] Sending Response");
                System.out.println("-------------------\n");

                out.write("Packet received".getBytes());

                System.out.println("-------------------");
                System.out.println("[Server] Response Sent");
                System.out.println("-------------------\n");

            } catch (SocketTimeoutException e) {
                continue;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}