import java.io.*;
import java.net.*;

public class Server {

    /* ---------------------------------- main ---------------------------------- */

    public static void main(String[] args) {
        Server s = new Server();
        s.talk();
    }

    /* ---------------------------------- talk ---------------------------------- */

    public void talk() {
        try {

            DatagramSocket dss = new DatagramSocket();

            System.out.println("-------------------");
            System.out
                    .println("[Server] Created socket\nLocal port : " + dss.getLocalPort() + "\nPort : " + dss.getPort()
                            + "\nLocal address : " + dss.getLocalAddress() + "\nAddress : " + dss.getInetAddress());
            System.out.println("-------------------\n");

            while (true) {
                System.out.println("-------------------");
                System.out.println("[Server] Waiting for msg from client");
                System.out.println("-------------------\n");

                byte[] buf = new byte[100];
                DatagramPacket dpIn = new DatagramPacket(buf, buf.length);
                dss.receive(dpIn);
                InetSocketAddress isa = new InetSocketAddress(dpIn.getAddress(), dpIn.getPort());
                String content = new String(dpIn.getData(), dpIn.getOffset(), dpIn.getLength());

                System.out.println("-------------------");
                System.out.println("[Server] msg received , content : " + content);
                System.out.println("-------------------\n");

                if (content.equals("close")) {
                    String closeResp = "closeClient";
                    DatagramPacket dpOut = new DatagramPacket(closeResp.getBytes(), closeResp.getBytes().length, isa);
                    dss.send(dpOut);
                    dss.close();
                    return;
                }

                System.out.println("-------------------");
                System.out.println("[Server] Sending resp");
                System.out.println("-------------------\n");

                String resp = "Packet Received";
                DatagramPacket dpOut = new DatagramPacket(resp.getBytes(), resp.getBytes().length, isa);
                dss.send(dpOut);

                System.out.println("-------------------");
                System.out.println("[Server] resp sent");
                System.out.println("-------------------\n");
            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}