import java.io.*;
import java.net.*;

public class Client {

    /* -------------------------------- variables ------------------------------- */
    Socket s;
    InputStream in;
    OutputStream out;

    /* ---------------------------------- main ---------------------------------- */
    public static void main(String[] args) throws IOException {
        Client c = new Client();
        c.connect();
        c.talk();
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
                    + "\nLocal address : " + s.getLocalAddress().toString().replace("/", "") + "\nAddress : "
                    + s.getInetAddress().toString().replace("/", " "));
            System.out.println("-------------------\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* ---------------------------------- talk ---------------------------------- */
    public void talk() {
        try {
            while (true) {

                BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("-------------------");
                System.out.print("[Client] Insert msg : ");
                String msg = keyboard.readLine();
                System.out.println("-------------------\n");

                System.out.println("-------------------");
                System.out.println("[Client] Sending msg");
                System.out.println("-------------------\n");

                out = s.getOutputStream();
                out.write(msg.getBytes());

                System.out.println("-------------------");
                System.out.println("[Client] Msg send");
                System.out.println("-------------------\n");

                System.out.println("-------------------");
                System.out.println("[Client] Waiting for resp");
                System.out.println("-------------------\n");

                byte[] buf = new byte[100];
                in = s.getInputStream();
                int read = in.read(buf);
                String resp = new String(buf, 0, read);

                if (resp.equals("closeClient")) {
                    System.out.println("-------------------");
                    System.out.println("[Client] resp received : " + resp + ", client will close");
                    System.out.println("-------------------\n");
                    s.close();
                    return;
                }

                System.out.println("-------------------");
                System.out.println("[Client] resp received : " + resp);
                System.out.println("-------------------\n");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}