import java.io.*;
import java.net.*;

public class Client {

    /* -------------------------------- variables ------------------------------- */

    DatagramSocket ds;

    /* ---------------------------------- main ---------------------------------- */

    public static void main(String[] args) throws IOException {
        Client c = new Client();
        c.talk();
    }

    /* ---------------------------------- talk ---------------------------------- */

    public void talk() {

        DatagramSocket ds;

        try {

            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("-------------------");
            System.out.print("Insert Server Ip Address : ");
            String ip = keyboard.readLine();
            System.out.println("-------------------\n");
            System.out.println("-------------------");
            System.out.print("Insert Server Port : ");
            int port = Integer.parseInt(keyboard.readLine());
            System.out.println("-------------------\n");

            ds = new DatagramSocket();
            InetSocketAddress isa = new InetSocketAddress(ip, port);

            System.out.println("-------------------");
            System.out.println("[Client] Created socket\nLocal port : " + ds.getLocalPort() + "\nPort : " + ds.getPort()
                    + "\nLocal address : " + ds.getLocalAddress() + "\nAddress : " + ds.getInetAddress());
            System.out.println("-------------------\n");

            while (true) {

                System.out.println("-------------------");
                System.out.print("[Client] write msg to server : ");
                String msg = keyboard.readLine();
                System.out.println("-------------------\n");

                byte[] buf = msg.getBytes();
                DatagramPacket dp = new DatagramPacket(buf, buf.length);
                dp.setSocketAddress(isa);
                ds.send(dp);

                System.out.println("-------------------");
                System.out.println("[Client] msg sent");
                System.out.println("-------------------\n");

                System.out.println("-------------------");
                System.out.println("[Client] listening for resp");
                System.out.println("-------------------\n");

                buf = new byte[100];
                DatagramPacket dpIn = new DatagramPacket(buf, buf.length);
                ds.receive(dpIn);
                String resp = new String(dpIn.getData(), dpIn.getOffset(), dpIn.getLength());

                System.out.println("-------------------");
                System.out.println("[Client] resp received : " + resp);
                System.out.println("-------------------\n");

                if (resp.equals("closeClient")) {
                    ds.close();
                    return;
                }
            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}