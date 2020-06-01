import java.io.*;
import java.net.*;

public class Server {

    ServerSocket s = null;
    Socket ss = null;

    BufferedReader in;
    DataOutputStream out;

    public Socket connect(){
        try {
            System.out.println("[Server] Init");

            s = new ServerSocket(12345);
            System.out.println("[Server] Listening");

            ss = s.accept();   
            System.out.println("[Server] Client Accepted");

            in = new BufferedReader(new InputStreamReader(ss.getInputStream()));
            out = new DataOutputStream(ss.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ss;
    }

    public static void main(String[] args) {
        Server s = new Server();
        s.connect();
    }
}