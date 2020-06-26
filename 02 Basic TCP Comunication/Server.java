import java.io.*;
import java.net.*;

public class Server {

    /* -------------------------------- variables ------------------------------- */

    ServerSocket ss;
    Socket c;
    InputStream in;
    OutputStream out;

    /* ---------------------------------- main ---------------------------------- */

    public static void main(final String[] args) {
        final Server s = new Server();
        s.waitForConnection();
        s.talk();
    }

    /* ---------------------------- waitForConnection --------------------------- */

    public void waitForConnection() {
        try {

            System.out.println("-------------------");
            System.out.println("[Server] Init");
            System.out.println("-------------------\n");

            ss = new ServerSocket(0);

            System.out.println("-------------------");
            System.out.println("[Server] Created socket\nLocal port : " + ss.getLocalPort() + "\nAddress : "
                    + ss.getInetAddress());
            System.out.println("-------------------\n");

            c = ss.accept();

            System.out.println("-------------------");
            System.out.println("[Server] Client accepted \nLocal port : " + c.getLocalPort() + "\nPort : " + c.getPort()
                    + "\nLocal address : " + c.getLocalAddress().toString().replace("/", "") + "\nAddress : "
                    + c.getInetAddress().toString().replace("/", ""));
            System.out.println("-------------------\n");

        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public void talk() {
        while (true) {
            try {

                System.out.println("-------------------");
                System.out.println("[Server] Listening for packet");
                System.out.println("-------------------\n");

                in = c.getInputStream();
                out = c.getOutputStream();
                byte[] buf = new byte[100];
                int read = in.read(buf);
                String msg = new String(buf, 0, read);
                System.out.println("[Server] Packet received : " + msg + "\n");

                if (msg.equals("close")) {
                    System.out.println("-------------------");
                    System.out.println("[Server] closing");
                    System.out.println("-------------------\n");
                    out.write("closeClient".getBytes());
                    c.close();
                    ss.close();
                    return;
                }

                System.out.println("-------------------");
                System.out.println("[Server] Sending Response");
                System.out.println("-------------------\n");

                out.write("Packet received".getBytes());

                System.out.println("-------------------");
                System.out.println("[Server] Response Sent");
                System.out.println("-------------------\n");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}