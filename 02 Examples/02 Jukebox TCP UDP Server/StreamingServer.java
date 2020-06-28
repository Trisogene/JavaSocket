import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class StreamingServer {

    /* -------------------------------- variables ------------------------------- */
    InputStream in;
    OutputStream out;
    DatagramSocket ds;
    DatagramPacket dpIn, dpOut;
    String msgIn;
    String msgOut;
    ArrayList<User> users = new ArrayList<User>();

    /* ---------------------------------- main ---------------------------------- */
    public static void main(String[] args) {
        StreamingServer ss = new StreamingServer();
        ss.init();
        ss.start();
    }

    /* ---------------------------------- init ---------------------------------- */
    public void init() {
        try {
            ds = new DatagramSocket();
            System.out.println("[WEBSERVER] Open on port UDP " + ds.getLocalPort());

            byte[] buf = new byte[100];
            dpIn = new DatagramPacket(buf, buf.length);
            ds.receive(dpIn);
            msgIn = new String(buf, 0, buf.length).trim();

            String[] tempMsg1 = msgIn.split("@");
            String[] tempMsg2 = tempMsg1[0].split("-");
            users.add(new User(tempMsg2[0], Integer.parseInt(tempMsg2[1])));
            System.out.println("[STREAMINGSERVER] User Added, IP: " + users.get(0).ip + ", UDP: " + users.get(0).UDP);
            tempMsg2 = tempMsg1[1].split("-");
            users.add(new User(tempMsg2[0], Integer.parseInt(tempMsg2[1])));
            System.out.println("[STREAMINGSERVER] User Added, IP: " + users.get(1).ip + ", UDP: " + users.get(1).UDP);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* ---------------------------------- start --------------------------------- */
    public void start() {
        while(true){
            try {
                byte[] buf = new byte[100];
                dpIn = new DatagramPacket(buf, buf.length);
                ds.receive(dpIn);
                msgIn = new String(buf,0,buf.length).trim();
                System.out.println("[STREAMINGSERVER] Client requested song : " + msgIn);

                msgOut = "song" + msgIn;
                for (int i = 0; i <2; i++) {
                    InetSocketAddress clientISA = new InetSocketAddress(users.get(i).ip, users.get(i).UDP);
                    dpOut = new DatagramPacket(msgOut.getBytes(),msgOut.getBytes().length, clientISA);
                    ds.send(dpOut);
                    System.out.println("[STREAMINGSERVER] Client on UDP port [" + users.get(i).UDP + "] notified");
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}