import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;

public class Server {

    /* -------------------------------- variables ------------------------------- */

    ServerSocket ss;
    Socket client;
    InputStream in;
    OutputStream out;
    String state;

    /* ---------------------------------- main ---------------------------------- */

public static void main(String[] args) {
    Server s = new Server();
    s.init();
    while(true){
        s.waitForConnection();
        s.talk();
    }
}

    /* ---------------------------------- init ---------------------------------- */

    public void init() {
        try {
            ss = new ServerSocket(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("[Server] Open on port : " + ss.getLocalPort());
}

/* ---------------------------- waitForConnection --------------------------- */

    public void waitForConnection(){
        try {
            client = ss.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("[Server] Accepted client , client port : " + client.getPort());
    }

/* ---------------------------------- talk ---------------------------------- */

    public void talk(){
        try {
            in = client.getInputStream();
            out = client.getOutputStream();
        
            byte[] buf = new byte[100];
            in.read(buf);
            String msg = new String(buf,0,buf.length);
            System.out.println(msg);
            checkMsg(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

/* -------------------------------- checkMsg -------------------------------- */

    public boolean checkMsg(String msg){
        switch (msg) {
            case "+": case "-" : 
                
                break;
        
            default:
                break;
        }

        return true;
    }
}