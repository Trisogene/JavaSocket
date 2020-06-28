import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class WebServer {

    /* -------------------------------- variables ------------------------------- */
    InputStream in;
    OutputStream out;
    String msgIn, msgOut;
    ServerSocket ss;
    DatagramSocket ds;
    DatagramPacket dpIn, dpOut;
    InetSocketAddress streamingServerISA;
    ArrayList<User> users = new ArrayList<User>();
    BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

    /* ---------------------------------- main ---------------------------------- */
    public static void main(String[] args) {
        WebServer ws = new WebServer();
        ws.init();
        ws.login();
        ws.start();
    }

    /* ---------------------------------- init ---------------------------------- */
    public void init() {
        try {
            ss = new ServerSocket(0);
            ds = new DatagramSocket();
            System.out.print("[WEBSERVER] Insert Streaming server UDP port: ");
            streamingServerISA = new InetSocketAddress("localhost", Integer.parseInt(keyboard.readLine()));
            System.out.println("[WEBSERVER] Open on port TCP " + ss.getLocalPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* ---------------------------------- login --------------------------------- */
    public void login() {
        while (users.size() < 2) {
            try {
                Socket client = ss.accept();

                byte[] buf = new byte[100];
                in = client.getInputStream();
                in.read(buf, 0, buf.length);
                msgIn = new String(buf, 0, buf.length).trim();

                String userIP = client.getInetAddress().toString().replace("/", "");
                int userUDP = Integer.parseInt(msgIn);
                users.add(new User(userIP, userUDP));

                System.out.println("[WEBSERVER] User added, IP: " + userIP + ", UDP: " + userUDP);

                out = client.getOutputStream();
                msgOut = "ACK";
                out.write(msgOut.getBytes());
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        msgOut = users.get(0).ip + "-" + users.get(0).UDP + "@" + users.get(1).ip + "-" + users.get(1).UDP;
        dpOut = new DatagramPacket(msgOut.getBytes(), msgOut.getBytes().length, streamingServerISA);
        try {
            ds.send(dpOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* ---------------------------------- start --------------------------------- */
    public void start() {
        while(true){
            try {
                Socket client = ss.accept();
    
                byte[] buf = new byte[100];
                in = client.getInputStream();
                in.read(buf, 0, buf.length);
                msgIn = new String(buf, 0, buf.length).trim();
                
                System.out.println("[WebServer] Client connected , requesting song: " + msgIn);

                msgOut = msgIn;
                dpOut = new DatagramPacket(msgOut.getBytes(), msgOut.getBytes().length, streamingServerISA);
                try {
                    ds.send(dpOut);
                } catch (IOException e) {
                    e.printStackTrace();
                }
    
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}