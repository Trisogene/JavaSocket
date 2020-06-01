import java.io.*;
import java.net.*;

public class Client{

    Socket s;

    BufferedReader in;
    DataOutputStream out;

    public Socket connect(final String ip,final int port) {
        try {
            System.out.println("[Client] Init");

            s = new Socket(ip, port);
            System.out.println("[Client] Created socket");

            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new DataOutputStream(s.getOutputStream());

        } catch (final Exception e) {
            System.out.println("[Client] Unknow error");
        }
        return s;
    }

    public static void main(final String[] args) {
        final Client c = new Client();
        c.connect("localhost",12345);
    }
}