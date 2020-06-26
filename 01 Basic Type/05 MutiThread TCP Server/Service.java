import java.io.*;
import java.net.*;

public class Service extends Thread {

    /* -------------------------------- variables ------------------------------- */
    Socket c;

    /* ------------------------------- constructor ------------------------------ */
    public Service(Socket client) {
        c = client;
    }

    /* ----------------------------------- run ---------------------------------- */
    public void run() {
        while (true) {
            try {
                System.out.println("-------------------");
                System.out.println("[Server] Listening for packet");
                System.out.println("-------------------\n");

                InputStream in = c.getInputStream();
                OutputStream out = c.getOutputStream();
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